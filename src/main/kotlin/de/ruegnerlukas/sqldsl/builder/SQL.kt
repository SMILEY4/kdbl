package de.ruegnerlukas.sqldsl.builder

import de.ruegnerlukas.sqldsl.dsl.expr.Column
import de.ruegnerlukas.sqldsl.dsl.expr.Expr
import de.ruegnerlukas.sqldsl.dsl.expr.OnConflict
import de.ruegnerlukas.sqldsl.dsl.expr.ReturnAllColumns
import de.ruegnerlukas.sqldsl.dsl.expr.ReturnColumns
import de.ruegnerlukas.sqldsl.dsl.expr.Returning
import de.ruegnerlukas.sqldsl.dsl.expr.Table
import de.ruegnerlukas.sqldsl.dsl.statements.CreateTableStatement
import de.ruegnerlukas.sqldsl.dsl.statements.DeleteStatement
import de.ruegnerlukas.sqldsl.dsl.statements.FromElement
import de.ruegnerlukas.sqldsl.dsl.statements.FromStatement
import de.ruegnerlukas.sqldsl.dsl.statements.GroupByStatement
import de.ruegnerlukas.sqldsl.dsl.statements.HavingStatement
import de.ruegnerlukas.sqldsl.dsl.statements.InsertContent
import de.ruegnerlukas.sqldsl.dsl.statements.InsertItem
import de.ruegnerlukas.sqldsl.dsl.statements.InsertStatement
import de.ruegnerlukas.sqldsl.dsl.statements.ItemsInsertContent
import de.ruegnerlukas.sqldsl.dsl.statements.LimitStatement
import de.ruegnerlukas.sqldsl.dsl.statements.OrderByElement
import de.ruegnerlukas.sqldsl.dsl.statements.OrderByStatement
import de.ruegnerlukas.sqldsl.dsl.statements.QueryStatement
import de.ruegnerlukas.sqldsl.dsl.statements.SelectAllElement
import de.ruegnerlukas.sqldsl.dsl.statements.SelectElement
import de.ruegnerlukas.sqldsl.dsl.statements.SelectStatement
import de.ruegnerlukas.sqldsl.dsl.statements.UpdateElement
import de.ruegnerlukas.sqldsl.dsl.statements.UpdateStatement
import de.ruegnerlukas.sqldsl.dsl.statements.WhereStatement

object SQL {

	fun query() = QueryBuilder()

	fun create(table: Table) = CreateTableStatement(table, false)

	fun createIfNotExists(table: Table) = CreateTableStatement(table, true)

	fun delete() = DeleteBuilder()

	fun insert() = InsertBuilder()

	fun item() = InsertItem()

	fun update() = UpdateBuilder()

}


class UpdateBuilder {

	private var target: Table? = null
	private var onConflict: OnConflict = OnConflict.ABORT
	private var set: List<UpdateElement<*>>? = null
	private var where: Expr<Boolean>? = null
	private var from: FromStatement? = null
	private var returning: Returning? = null

	fun target(table: Table): UpdateBuilder {
		this.target = table
		return this
	}

	fun or(onConflict: OnConflict): UpdateBuilder {
		this.onConflict = onConflict
		return this
	}

	fun set(vararg element: UpdateElement<*>): UpdateBuilder {
		this.set = element.toList()
		return this
	}

	fun where(where: Expr<Boolean>): UpdateBuilder {
		this.where = where
		return this
	}

	fun from(vararg elements: FromElement): UpdateBuilder {
		this.from = FromStatement(elements.toList())
		return this
	}

	fun returningAll(): UpdateBuilder {
		this.returning = ReturnAllColumns()
		return this
	}

	fun returning(vararg expr: Expr<*>): UpdateBuilder {
		this.returning = ReturnColumns(expr.toList())
		return this
	}

	fun build() = UpdateStatement(onConflict, target!!, set!!, where!!, from, returning)

}


class InsertBuilder {

	private var target: Table? = null
	private var onConflict: OnConflict = OnConflict.ABORT
	private var fields: List<Column<*>>? = null
	private var content: InsertContent? = null
	private var returning: Returning? = null

	fun into(target: Table): InsertBuilder {
		this.target = target
		return this
	}

	fun or(onConflict: OnConflict): InsertBuilder {
		this.onConflict = onConflict
		return this
	}

	fun allFields(): InsertBuilder {
		this.fields = listOf()
		return this
	}

	fun fields(vararg columns: Column<*>): InsertBuilder {
		this.fields = columns.toList()
		return this
	}

	fun query(query: QueryStatement<*>): InsertBuilder {
		this.content = query
		return this
	}

	fun items(vararg items: InsertItem): InsertBuilder {
		this.content = ItemsInsertContent(items.toList())
		return this
	}

	fun returningAll(): InsertBuilder {
		this.returning = ReturnAllColumns()
		return this
	}

	fun returning(vararg expr: Expr<*>): InsertBuilder {
		this.returning = ReturnColumns(expr.toList())
		return this
	}

	fun build() = InsertStatement(onConflict, target!!, fields!!, content!!, returning)

}


class DeleteBuilder {

	private var target: Table? = null
	private var where: Expr<Boolean>? = null
	private var returning: Returning? = null

	fun from(target: Table): DeleteBuilder {
		this.target = target
		return this
	}

	fun where(condition: Expr<Boolean>): DeleteBuilder {
		this.where = condition
		return this
	}

	fun returningAll(): DeleteBuilder {
		this.returning = ReturnAllColumns()
		return this
	}

	fun returning(vararg expr: Expr<*>): DeleteBuilder {
		this.returning = ReturnColumns(expr.toList())
		return this
	}

	fun build() = DeleteStatement(target!!, where, returning)

}

class QueryBuilder {

	private var select: SelectStatement? = null
	private var from: FromStatement? = null
	private var where: WhereStatement? = null
	private var groupBy: GroupByStatement? = null
	private var having: HavingStatement? = null
	private var orderBy: OrderByStatement? = null
	private var limit: LimitStatement? = null

	fun select(vararg elements: SelectElement): QueryBuilder {
		select = SelectStatement(false, elements.toList())
		return this
	}

	fun selectDistinct(vararg elements: SelectElement): QueryBuilder {
		select = SelectStatement(true, elements.toList())
		return this
	}

	fun selectAll(): QueryBuilder {
		select = SelectStatement(false, listOf(SelectAllElement()))
		return this
	}

	fun selectDistinctAll(): QueryBuilder {
		select = SelectStatement(true, listOf(SelectAllElement()))
		return this
	}

	fun from(vararg elements: FromElement): QueryBuilder {
		from = FromStatement(elements.toList())
		return this
	}

	fun where(condition: Expr<Boolean>): QueryBuilder {
		where = WhereStatement(condition)
		return this
	}

	fun groupBy(vararg elements: Expr<*>): QueryBuilder {
		groupBy = GroupByStatement(elements.toList())
		return this
	}

	fun having(condition: Expr<Boolean>): QueryBuilder {
		having = HavingStatement(condition)
		return this
	}

	fun orderBy(vararg elements: OrderByElement): QueryBuilder {
		orderBy = OrderByStatement(elements.toList())
		return this
	}

	fun limit(limit: Int, offset: Int): QueryBuilder {
		this.limit = LimitStatement(limit, offset)
		return this
	}

	fun limit(limit: Int) = limit(limit, 0)

	fun <T> build() = QueryStatement<T>(select!!, from!!, where, groupBy, having, orderBy, limit)

}