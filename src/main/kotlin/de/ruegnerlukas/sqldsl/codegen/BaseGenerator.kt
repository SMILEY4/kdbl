package de.ruegnerlukas.sqldsl.codegen

import de.ruegnerlukas.sqldsl.codegen.tokens.CsvListToken
import de.ruegnerlukas.sqldsl.codegen.tokens.GroupToken
import de.ruegnerlukas.sqldsl.codegen.tokens.ListToken
import de.ruegnerlukas.sqldsl.codegen.tokens.NoOpToken
import de.ruegnerlukas.sqldsl.codegen.tokens.StringToken
import de.ruegnerlukas.sqldsl.codegen.tokens.Token
import de.ruegnerlukas.sqldsl.dsl.expr.AddAllExpr
import de.ruegnerlukas.sqldsl.dsl.expr.AddExpr
import de.ruegnerlukas.sqldsl.dsl.expr.AliasExpr
import de.ruegnerlukas.sqldsl.dsl.expr.AliasTable
import de.ruegnerlukas.sqldsl.dsl.expr.AndChainExpr
import de.ruegnerlukas.sqldsl.dsl.expr.AndExpr
import de.ruegnerlukas.sqldsl.dsl.expr.AutoIncrementProperty
import de.ruegnerlukas.sqldsl.dsl.expr.BetweenExpr
import de.ruegnerlukas.sqldsl.dsl.expr.BooleanLiteralExpr
import de.ruegnerlukas.sqldsl.dsl.expr.Column
import de.ruegnerlukas.sqldsl.dsl.expr.ColumnProperty
import de.ruegnerlukas.sqldsl.dsl.expr.ColumnType
import de.ruegnerlukas.sqldsl.dsl.expr.CountAllDistinctExpr
import de.ruegnerlukas.sqldsl.dsl.expr.CountAllExpr
import de.ruegnerlukas.sqldsl.dsl.expr.CountDistinctExpr
import de.ruegnerlukas.sqldsl.dsl.expr.CountExpr
import de.ruegnerlukas.sqldsl.dsl.expr.DefaultBooleanValueProperty
import de.ruegnerlukas.sqldsl.dsl.expr.DefaultFloatValueProperty
import de.ruegnerlukas.sqldsl.dsl.expr.DefaultIntValueProperty
import de.ruegnerlukas.sqldsl.dsl.expr.DefaultStringValueProperty
import de.ruegnerlukas.sqldsl.dsl.expr.DerivedColumn
import de.ruegnerlukas.sqldsl.dsl.expr.DerivedTable
import de.ruegnerlukas.sqldsl.dsl.expr.DivExpr
import de.ruegnerlukas.sqldsl.dsl.expr.EqualExpr
import de.ruegnerlukas.sqldsl.dsl.expr.Expr
import de.ruegnerlukas.sqldsl.dsl.expr.FloatLiteralExpr
import de.ruegnerlukas.sqldsl.dsl.expr.ForeignKeyConstraint
import de.ruegnerlukas.sqldsl.dsl.expr.GreaterEqualThanExpr
import de.ruegnerlukas.sqldsl.dsl.expr.GreaterThanExpr
import de.ruegnerlukas.sqldsl.dsl.expr.InListExpr
import de.ruegnerlukas.sqldsl.dsl.expr.InQueryExpr
import de.ruegnerlukas.sqldsl.dsl.expr.IntLiteralExpr
import de.ruegnerlukas.sqldsl.dsl.expr.LessEqualThanExpr
import de.ruegnerlukas.sqldsl.dsl.expr.LessThanExpr
import de.ruegnerlukas.sqldsl.dsl.expr.LikeExpr
import de.ruegnerlukas.sqldsl.dsl.expr.ListLiteralExpr
import de.ruegnerlukas.sqldsl.dsl.expr.LiteralExpr
import de.ruegnerlukas.sqldsl.dsl.expr.MaxExpr
import de.ruegnerlukas.sqldsl.dsl.expr.MinExpr
import de.ruegnerlukas.sqldsl.dsl.expr.MulExpr
import de.ruegnerlukas.sqldsl.dsl.expr.NotEqualExpr
import de.ruegnerlukas.sqldsl.dsl.expr.NotExpr
import de.ruegnerlukas.sqldsl.dsl.expr.NotInListExpr
import de.ruegnerlukas.sqldsl.dsl.expr.NotInQueryExpr
import de.ruegnerlukas.sqldsl.dsl.expr.NotNullExpr
import de.ruegnerlukas.sqldsl.dsl.expr.NotNullProperty
import de.ruegnerlukas.sqldsl.dsl.expr.NullExpr
import de.ruegnerlukas.sqldsl.dsl.expr.OnConflict
import de.ruegnerlukas.sqldsl.dsl.expr.OnDelete
import de.ruegnerlukas.sqldsl.dsl.expr.OnUpdate
import de.ruegnerlukas.sqldsl.dsl.expr.OrChainExpr
import de.ruegnerlukas.sqldsl.dsl.expr.OrExpr
import de.ruegnerlukas.sqldsl.dsl.expr.PrimaryKeyProperty
import de.ruegnerlukas.sqldsl.dsl.expr.ReturnAllColumns
import de.ruegnerlukas.sqldsl.dsl.expr.ReturnColumns
import de.ruegnerlukas.sqldsl.dsl.expr.Returning
import de.ruegnerlukas.sqldsl.dsl.expr.StringLiteralExpr
import de.ruegnerlukas.sqldsl.dsl.expr.SubExpr
import de.ruegnerlukas.sqldsl.dsl.expr.SubQueryExpr
import de.ruegnerlukas.sqldsl.dsl.expr.SumExpr
import de.ruegnerlukas.sqldsl.dsl.expr.Table
import de.ruegnerlukas.sqldsl.dsl.expr.TableLike
import de.ruegnerlukas.sqldsl.dsl.expr.UniqueProperty
import de.ruegnerlukas.sqldsl.dsl.statements.CreateTableStatement
import de.ruegnerlukas.sqldsl.dsl.statements.DeleteStatement
import de.ruegnerlukas.sqldsl.dsl.statements.Dir
import de.ruegnerlukas.sqldsl.dsl.statements.FromElement
import de.ruegnerlukas.sqldsl.dsl.statements.FromStatement
import de.ruegnerlukas.sqldsl.dsl.statements.GroupByStatement
import de.ruegnerlukas.sqldsl.dsl.statements.HavingStatement
import de.ruegnerlukas.sqldsl.dsl.statements.InsertStatement
import de.ruegnerlukas.sqldsl.dsl.statements.ItemsInsertContent
import de.ruegnerlukas.sqldsl.dsl.statements.JoinCondition
import de.ruegnerlukas.sqldsl.dsl.statements.JoinElement
import de.ruegnerlukas.sqldsl.dsl.statements.JoinOp
import de.ruegnerlukas.sqldsl.dsl.statements.LimitStatement
import de.ruegnerlukas.sqldsl.dsl.statements.OnJoinCondition
import de.ruegnerlukas.sqldsl.dsl.statements.OrderByElement
import de.ruegnerlukas.sqldsl.dsl.statements.OrderByStatement
import de.ruegnerlukas.sqldsl.dsl.statements.QueryBuilderEndStep
import de.ruegnerlukas.sqldsl.dsl.statements.QueryStatement
import de.ruegnerlukas.sqldsl.dsl.statements.SelectAllElement
import de.ruegnerlukas.sqldsl.dsl.statements.SelectAllFromTableElement
import de.ruegnerlukas.sqldsl.dsl.statements.SelectElement
import de.ruegnerlukas.sqldsl.dsl.statements.SelectStatement
import de.ruegnerlukas.sqldsl.dsl.statements.UpdateStatement
import de.ruegnerlukas.sqldsl.dsl.statements.UsingJoinCondition
import de.ruegnerlukas.sqldsl.dsl.statements.WhereStatement

class BaseGenerator {

	fun update(update: UpdateStatement): Token {
		return ListToken()
			.add("UPDATE")
			.addIf(update.onConflict != OnConflict.ABORT, mapOr(update.onConflict))
			.add(tableIdentifier(update.target))
			.add("SET")
			.add(CsvListToken(update.set.map {
				ListToken()
					.add(columnIdentifier(it.column))
					.add("=")
					.add(expression(it.value))
			}))
			.addIf(update.from != null) { from(update.from!!) }
			.addIf(update.where != null, "WHERE")
			.addIf(update.where != null) { expression(update.where!!) }
			.addIf(update.returning != null) { returning(update.returning!!) }
	}

	fun insert(insert: InsertStatement): Token {
		return ListToken()
			.add("INSERT")
			.addIf(insert.onConflict != OnConflict.ABORT, mapOr(insert.onConflict))
			.add("INTO")
			.add(tableIdentifier(insert.target))
			.add(
				when (insert.fields.size) {
					0 -> NoOpToken()
					else -> GroupToken(CsvListToken(insert.fields.map { StringToken(it.columnName) }))
				}
			)
			.add("VALUES")
			.add(
				when (insert.content) {
					is QueryStatement<*> -> GroupToken(query(insert.content))
					is QueryBuilderEndStep<*> -> GroupToken(query(insert.content.build<Any>()))
					is ItemsInsertContent -> CsvListToken(insert.content.items.map { item ->
						GroupToken(
							CsvListToken(
								insert.fields.map { field ->
									val value = item.getValue(field)
									if (value == null) {
										StringToken("NULL")
									} else {
										literal(value)
									}
								}
							)
						)
					})
					else -> throwUnknown(insert.content)
				}
			)
			.addIf(insert.returning != null) { returning(insert.returning!!) }
	}

	fun delete(delete: DeleteStatement): Token {
		return ListToken()
			.add("DELETE FROM")
			.add(delete.target.tableName)
			.addIf(delete.where != null, "WHERE")
			.addIf(delete.where != null) { expression(delete.where!!) }
			.addIf(delete.returning != null) { returning(delete.returning!!) }
	}

	fun returning(e: Returning): Token {
		return ListToken()
			.add("RETURNING")
			.add(
				when (e) {
					is ReturnAllColumns -> StringToken("*")
					is ReturnColumns -> CsvListToken(e.columns.map { expression(it) })
					else -> throwUnknown(e)
				}
			)
	}

	fun create(create: CreateTableStatement): Token {
		return ListToken()
			.add("CREATE TABLE")
			.addIf(create.onlyIfNotExists, "IF NOT EXISTS")
			.add(create.table.tableName)
			.add(GroupToken(CsvListToken(create.table.getColumns().map { column(it, create.table) })))
			.add(tableProperties(create.table))
	}

	private fun column(column: Column<*>, table: Table): Token {
		return ListToken()
			.add(column.columnName)
			.add(columnType(column.type))
			.addAll(column.getProperties().map { columnProperty(it, column, table) })
	}

	private fun columnType(type: ColumnType): String {
		return when (type) {
			ColumnType.INT -> "INT"
			ColumnType.FLOAT -> "FLOAT"
			ColumnType.TEXT -> "TEXT"
			ColumnType.BOOL -> "BOOL"
		}
	}

	private fun columnProperty(e: ColumnProperty, column: Column<*>, table: Table): Token {
		val numPrimaryKeys = table.getColumns().count { it.getProperties().filterIsInstance<PrimaryKeyProperty>().isNotEmpty() }
		return when (e) {
			is PrimaryKeyProperty -> {
				if (numPrimaryKeys == 1) {
					val tokens = ListToken().add("PRIMARY KEY")
					if (column.getProperties().filterIsInstance<AutoIncrementProperty>().isNotEmpty()) {
						tokens.add("AUTOINCREMENT")
					}
					if (e.onConflict != OnConflict.ABORT) {
						tokens.add(mapOnConflict(e.onConflict))
					}
					return tokens
				} else {
					NoOpToken()
				}
			}
			is DefaultIntValueProperty -> ListToken().add("DEFAULT").add(e.value.toString())
			is DefaultFloatValueProperty -> ListToken().add("DEFAULT").add(e.value.toString())
			is DefaultStringValueProperty -> ListToken().add("DEFAULT").add("'${e.value}'")
			is DefaultBooleanValueProperty -> ListToken().add("DEFAULT").add(if (e.value) "TRUE" else "FALSE")
			is NotNullProperty -> ListToken()
				.add("NOT NULL")
				.addIf(e.onConflict != OnConflict.ABORT, mapOnConflict(e.onConflict))
			is UniqueProperty -> ListToken()
				.add("UNIQUE")
				.addIf(e.onConflict != OnConflict.ABORT, mapOnConflict(e.onConflict))
			is ForeignKeyConstraint -> ListToken()
				.add("REFERENCES")
				.add(e.table.tableName)
				.addIf(e.column != null, "(${e.column!!.columnName})")
				.addIf(e.onDelete != OnDelete.NO_ACTION, mapOnDelete(e.onDelete))
				.addIf(e.onUpdate != OnUpdate.NO_ACTION, mapOnUpdate(e.onUpdate))
			is AutoIncrementProperty -> NoOpToken()
			else -> throwUnknown(e)
		}
	}

	private fun tableProperties(table: Table): Token {
		val primaryKeys = table.getColumns().filter { it.getProperties().filterIsInstance<PrimaryKeyProperty>().isNotEmpty() }
		if (primaryKeys.size > 1) {
			val onConflict = primaryKeys[0].getProperties().filterIsInstance<PrimaryKeyProperty>().first().onConflict
			return ListToken()
				.add("PRIMARY KEY")
				.add(GroupToken(CsvListToken(primaryKeys.map { StringToken(it.columnName) })))
				.addIf(onConflict != OnConflict.ABORT, mapOnConflict(onConflict))
		}
		return NoOpToken()
	}

	private fun mapOr(onConflict: OnConflict): String {
		return when (onConflict) {
			OnConflict.ROLLBACK -> "OR ROLLBACK"
			OnConflict.ABORT -> "OR ABORT"
			OnConflict.FAIL -> "OR FAIL"
			OnConflict.IGNORE -> "OR IGNORE"
			OnConflict.REPLACE -> "OR REPLACE"
		}
	}

	private fun mapOnConflict(onConflict: OnConflict): String {
		return when (onConflict) {
			OnConflict.ROLLBACK -> "ON CONFLICT ROLLBACK"
			OnConflict.ABORT -> "ON CONFLICT ABORT"
			OnConflict.FAIL -> "ON CONFLICT FAIL"
			OnConflict.IGNORE -> "ON CONFLICT IGNORE"
			OnConflict.REPLACE -> "ON CONFLICT REPLACE"
		}
	}

	private fun mapOnDelete(onDelete: OnDelete): String {
		return when (onDelete) {
			OnDelete.NO_ACTION -> "ON DELETE NO ACTION"
			OnDelete.RESTRICT -> "ON DELETE RESTRICT"
			OnDelete.SET_NULL -> "ON DELETE SET NULL"
			OnDelete.SET_DEFAULT -> "ON DELETE SET DEFAULT"
			OnDelete.CASCADE -> "ON DELETE CASCADE"
		}
	}

	private fun mapOnUpdate(onUpdate: OnUpdate): String {
		return when (onUpdate) {
			OnUpdate.NO_ACTION -> "ON UPDATE NO ACTION"
			OnUpdate.RESTRICT -> "ON UPDATE RESTRICT"
			OnUpdate.SET_NULL -> "ON UPDATE SET NULL"
			OnUpdate.SET_DEFAULT -> "ON UPDATE SET DEFAULT"
			OnUpdate.CASCADE -> "ON UPDATE CASCADE"
		}
	}

	fun query(query: QueryStatement<*>): Token {
		return ListToken()
			.add(select(query.select))
			.add(from(query.from))
			.addIf(query.where != null) { where(query.where!!) }
			.addIf(query.groupBy != null) { groupBy(query.groupBy!!) }
			.addIf(query.having != null) { having(query.having!!) }
			.addIf(query.orderBy != null) { orderBy(query.orderBy!!) }
			.addIf(query.limit != null) { limit(query.limit!!) }
	}

	private fun select(stmt: SelectStatement): Token {
		return ListToken()
			.add("SELECT")
			.addIf(stmt.distinct, "DISTINCT")
			.add(CsvListToken(stmt.elements.map { selectElement(it) }))
	}

	private fun selectElement(e: SelectElement): Token {
		return when (e) {
			is SelectAllElement -> StringToken("*")
			is SelectAllFromTableElement -> StringToken("${tableIdentifier(e.table)}.*")
			is AliasExpr<*> -> ListToken().add(GroupToken(expression(e.getExpr()))).add("AS").add(e.alias)
			is Expr<*> -> expression(e)
			else -> throwUnknown(e)
		}
	}


	private fun from(stmt: FromStatement): Token {
		return ListToken()
			.add("FROM")
			.add(CsvListToken(stmt.elements.map { fromElement(it) }))
	}

	private fun fromElement(e: FromElement): Token {
		return when (e) {
			is AliasTable -> ListToken().add(tableIdentifier(e.table)).add("AS").add(e.alias)
			is DerivedTable -> ListToken().add(GroupToken(fromElement(e.getContent()))).add("AS").add(e.tableName)
			is Table -> StringToken(tableIdentifier(e))
			is JoinElement -> join(e)
			is QueryStatement<*> -> query(e)
			is QueryBuilderEndStep<*> -> query(e.build<Any>())
			else -> throwUnknown(e)
		}
	}

	private fun where(stmt: WhereStatement): Token {
		return ListToken()
			.add("WHERE")
			.add(expression(stmt.condition))
	}

	private fun groupBy(stmt: GroupByStatement): Token {
		return ListToken()
			.add("GROUP BY")
			.add(CsvListToken(stmt.elements.map { GroupToken(expression(it)) }))
	}

	private fun having(stmt: HavingStatement): Token {
		return ListToken()
			.add("HAVING")
			.add(expression(stmt.condition))
	}

	private fun orderBy(stmt: OrderByStatement): Token {
		return ListToken()
			.add("ORDER BY")
			.add(CsvListToken(stmt.elements.map { orderByElement(it) }))
	}

	private fun limit(stmt: LimitStatement): Token {
		return ListToken()
			.add("LIMIT")
			.add(stmt.limit.toString())
			.addIf(stmt.offset != 0, "OFFSET ${stmt.offset}")
	}

	private fun orderByElement(e: OrderByElement): Token {
		return ListToken()
			.add(expression(e.expr))
			.add(orderByDirection(e.dir))
	}

	private fun orderByDirection(dir: Dir): String {
		return when (dir) {
			Dir.ASC -> "ASC"
			Dir.DESC -> "DESC"
		}
	}

	private fun join(e: JoinElement): Token {
		return ListToken()
			.add(GroupToken(fromElement(e.left)))
			.add(joinOp(e.op))
			.add(GroupToken(fromElement(e.right)))
			.add(joinCondition(e.condition))
	}

	private fun joinCondition(e: JoinCondition): Token {
		return when (e) {
			is OnJoinCondition -> ListToken()
				.add("ON")
				.add(GroupToken(expression(e.condition)))
			is UsingJoinCondition -> ListToken()
				.add("USING")
				.add(GroupToken(CsvListToken(e.columns.map { expression(it) })))
			else -> throwUnknown(e)
		}
	}

	private fun joinOp(op: JoinOp): String {
		return when (op) {
			JoinOp.LEFT -> "JOIN"
			JoinOp.RIGHT -> "JOIN RIGHT"
			JoinOp.INNER -> "JOIN INNER"
		}
	}

	private fun expression(e: Expr<*>): Token {
		return when (e) {
			is EqualExpr<*> -> ListToken()
				.add(GroupToken(expression(e.left)))
				.add("=")
				.add(GroupToken(expression(e.right)))
			is NotEqualExpr<*> -> ListToken()
				.add(GroupToken(expression(e.left)))
				.add("!=")
				.add(GroupToken(expression(e.right)))
			is LessThanExpr<*> -> ListToken()
				.add(GroupToken(expression(e.left)))
				.add("<")
				.add(GroupToken(expression(e.right)))
			is LessEqualThanExpr<*> -> ListToken()
				.add(GroupToken(expression(e.left)))
				.add("<=")
				.add(GroupToken(expression(e.right)))
			is GreaterThanExpr<*> -> ListToken()
				.add(GroupToken(expression(e.left)))
				.add(">")
				.add(GroupToken(expression(e.right)))
			is GreaterEqualThanExpr<*> -> ListToken()
				.add(GroupToken(expression(e.left)))
				.add(">=")
				.add(GroupToken(expression(e.right)))
			is NotNullExpr -> ListToken()
				.add(GroupToken(expression(e.expr)))
				.add("IS NOT NULL")
			is NullExpr -> ListToken()
				.add(GroupToken(expression(e.expr)))
				.add("IS NULL")
			is NotExpr<*> -> ListToken()
				.add("NOT")
				.add(GroupToken(expression(e.value)))
			is AndExpr -> ListToken()
				.add(GroupToken(expression(e.left)))
				.add("AND")
				.add(GroupToken(expression(e.right)))
			is OrExpr -> ListToken()
				.add(GroupToken(expression(e.left)))
				.add("OR")
				.add(GroupToken(expression(e.right)))
			is AndChainExpr -> ListToken().then {
				e.expressions.forEachIndexed { index, expr ->
					if (index > 0) {
						add("AND")
					}
					add(GroupToken(expression(expr)))
				}
			}
			is OrChainExpr -> ListToken().then {
				e.expressions.forEachIndexed { index, expr ->
					if (index > 0) {
						add("OR")
					}
					add(GroupToken(expression(expr)))
				}
			}
			is LikeExpr -> ListToken()
				.add(GroupToken(expression(e.value)))
				.add("LIKE")
				.add("'${e.pattern}'")
			is BetweenExpr<*> -> ListToken()
				.add(GroupToken(expression(e.value)))
				.add("BETWEEN")
				.add(GroupToken(expression(e.lower)))
				.add("AND")
				.add(GroupToken(expression(e.upper)))
			is InListExpr<*> -> ListToken()
				.add(GroupToken(expression(e.expr)))
				.add("IN")
				.add(literal(e.list))
			is NotInListExpr<*> -> ListToken()
				.add(GroupToken(expression(e.expr)))
				.add("NOT IN")
				.add(literal(e.list))
			is InQueryExpr<*> -> ListToken()
				.add(GroupToken(expression(e.expr)))
				.add("IN")
				.add(GroupToken(query(e.query)))
			is NotInQueryExpr<*> -> ListToken()
				.add(GroupToken(expression(e.expr)))
				.add("NOT IN")
				.add(GroupToken(query(e.query)))
			is AddExpr<*> -> ListToken()
				.add(GroupToken(expression(e.left)))
				.add("+")
				.add(GroupToken(expression(e.right)))
			is SubExpr<*> -> ListToken()
				.add(GroupToken(expression(e.left)))
				.add("-")
				.add(GroupToken(expression(e.right)))
			is MulExpr<*> -> ListToken()
				.add(GroupToken(expression(e.left)))
				.add("*")
				.add(GroupToken(expression(e.right)))
			is DivExpr<*> -> ListToken()
				.add(GroupToken(expression(e.left)))
				.add("/")
				.add(GroupToken(expression(e.right)))
			is AddAllExpr<*> -> ListToken().then {
				e.expr.forEachIndexed { index, expr ->
					if (index > 0) {
						add("+")
					}
					add(GroupToken(expression(expr)))
				}
			}
			is CountAllExpr -> StringToken("COUNT(*)")
			is CountAllDistinctExpr -> StringToken("COUNT( DISTINCT * )")
			is CountExpr -> ListToken().add("COUNT(${expression(e.value).buildString()})")
			is CountDistinctExpr -> ListToken().add("COUNT(DISTINCT ${expression(e.value).buildString()})")
			is MinExpr -> ListToken().add("MIN(${expression(e.value).buildString()})")
			is MaxExpr -> ListToken().add("MAX(${expression(e.value).buildString()})")
			is SumExpr -> ListToken().add("SUM(${expression(e.value).buildString()})")
			is SubQueryExpr<*> -> when (e) {
				is QueryStatement<*> -> query(e)
				is QueryBuilderEndStep<*> -> query(e.build<Any>())
				else -> throwUnknown(e)
			}
			is AliasExpr<*> -> StringToken(e.alias)
			is LiteralExpr<*> -> literal(e)
			is Column<*> -> columnIdentifier(e)
			is DerivedColumn<*> -> StringToken("${e.table.tableName}.${e.columnName}")
			else -> throwUnknown(e)
		}
	}

	private fun literal(e: LiteralExpr<*>): Token {
		return when (e) {
			is IntLiteralExpr -> StringToken(e.value.toString())
			is FloatLiteralExpr -> StringToken(e.value.toString())
			is BooleanLiteralExpr -> StringToken(if (e.value) "TRUE" else "FALSE")
			is StringLiteralExpr -> StringToken("'${e.value}'")
			is ListLiteralExpr<*> -> GroupToken(CsvListToken(e.values.map { expression(it) }))
			else -> throwUnknown(e)
		}
	}

	private fun tableIdentifier(e: TableLike): String {
		return when (e) {
			is AliasTable -> e.alias
			is Table -> e.tableName
			else -> throwUnknown(e)
		}
	}

	private fun columnIdentifier(e: Column<*>): Token {
		return when (e.table) {
			is AliasTable -> StringToken("${e.table.alias}.${e.columnName}")
			is Table -> StringToken("${tableIdentifier(e.table)}.${e.columnName}")
			else -> throwUnknown(e.table)
		}
	}

	private fun <T> throwUnknown(e: Any?): T {
		throw Exception("Unknown element $e")
	}

}