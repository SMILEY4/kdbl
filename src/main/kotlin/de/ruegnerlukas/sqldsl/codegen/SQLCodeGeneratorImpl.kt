package de.ruegnerlukas.sqldsl.codegen

import de.ruegnerlukas.sqldsl.codegen.dialects.SQLDialect
import de.ruegnerlukas.sqldsl.codegen.tokens.CsvListToken
import de.ruegnerlukas.sqldsl.codegen.tokens.GroupToken
import de.ruegnerlukas.sqldsl.codegen.tokens.ListToken
import de.ruegnerlukas.sqldsl.codegen.tokens.NamedGroupToken
import de.ruegnerlukas.sqldsl.codegen.tokens.NoOpToken
import de.ruegnerlukas.sqldsl.codegen.tokens.PlaceholderToken
import de.ruegnerlukas.sqldsl.codegen.tokens.StringToken
import de.ruegnerlukas.sqldsl.codegen.tokens.Token
import de.ruegnerlukas.sqldsl.dsl.expression.AddAllExpr
import de.ruegnerlukas.sqldsl.dsl.expression.AddExpr
import de.ruegnerlukas.sqldsl.dsl.expression.AliasExpr
import de.ruegnerlukas.sqldsl.dsl.expression.AliasTable
import de.ruegnerlukas.sqldsl.dsl.expression.AndChainExpr
import de.ruegnerlukas.sqldsl.dsl.expression.AndExpr
import de.ruegnerlukas.sqldsl.dsl.expression.AutoIncrementProperty
import de.ruegnerlukas.sqldsl.dsl.expression.BetweenExpr
import de.ruegnerlukas.sqldsl.dsl.expression.BooleanLiteralExpr
import de.ruegnerlukas.sqldsl.dsl.expression.Column
import de.ruegnerlukas.sqldsl.dsl.expression.ColumnProperty
import de.ruegnerlukas.sqldsl.dsl.expression.ConditionExpr
import de.ruegnerlukas.sqldsl.dsl.expression.DateLiteralExpr
import de.ruegnerlukas.sqldsl.dsl.expression.DefaultBooleanValueProperty
import de.ruegnerlukas.sqldsl.dsl.expression.DefaultDateValueProperty
import de.ruegnerlukas.sqldsl.dsl.expression.DefaultDoubleValueProperty
import de.ruegnerlukas.sqldsl.dsl.expression.DefaultFloatValueProperty
import de.ruegnerlukas.sqldsl.dsl.expression.DefaultIntValueProperty
import de.ruegnerlukas.sqldsl.dsl.expression.DefaultLongValueProperty
import de.ruegnerlukas.sqldsl.dsl.expression.DefaultShortValueProperty
import de.ruegnerlukas.sqldsl.dsl.expression.DefaultStringValueProperty
import de.ruegnerlukas.sqldsl.dsl.expression.DefaultTimeValueProperty
import de.ruegnerlukas.sqldsl.dsl.expression.DerivedColumn
import de.ruegnerlukas.sqldsl.dsl.expression.DerivedTable
import de.ruegnerlukas.sqldsl.dsl.expression.DivExpr
import de.ruegnerlukas.sqldsl.dsl.expression.DoubleLiteralExpr
import de.ruegnerlukas.sqldsl.dsl.expression.EqualExpr
import de.ruegnerlukas.sqldsl.dsl.expression.Expr
import de.ruegnerlukas.sqldsl.dsl.expression.FloatLiteralExpr
import de.ruegnerlukas.sqldsl.dsl.expression.ForeignKeyConstraint
import de.ruegnerlukas.sqldsl.dsl.expression.FunctionExpr
import de.ruegnerlukas.sqldsl.dsl.expression.GreaterEqualThanExpr
import de.ruegnerlukas.sqldsl.dsl.expression.GreaterThanExpr
import de.ruegnerlukas.sqldsl.dsl.expression.InListExpr
import de.ruegnerlukas.sqldsl.dsl.expression.InQueryExpr
import de.ruegnerlukas.sqldsl.dsl.expression.IntLiteralExpr
import de.ruegnerlukas.sqldsl.dsl.expression.JoinCondition
import de.ruegnerlukas.sqldsl.dsl.expression.JoinElement
import de.ruegnerlukas.sqldsl.dsl.expression.LessEqualThanExpr
import de.ruegnerlukas.sqldsl.dsl.expression.LessThanExpr
import de.ruegnerlukas.sqldsl.dsl.expression.LikeExpr
import de.ruegnerlukas.sqldsl.dsl.expression.ListLiteralExpr
import de.ruegnerlukas.sqldsl.dsl.expression.LiteralExpr
import de.ruegnerlukas.sqldsl.dsl.expression.LongLiteralExpr
import de.ruegnerlukas.sqldsl.dsl.expression.MulExpr
import de.ruegnerlukas.sqldsl.dsl.expression.NotEqualExpr
import de.ruegnerlukas.sqldsl.dsl.expression.NotExpr
import de.ruegnerlukas.sqldsl.dsl.expression.NotInListExpr
import de.ruegnerlukas.sqldsl.dsl.expression.NotInQueryExpr
import de.ruegnerlukas.sqldsl.dsl.expression.NotNullExpr
import de.ruegnerlukas.sqldsl.dsl.expression.NotNullProperty
import de.ruegnerlukas.sqldsl.dsl.expression.NullExpr
import de.ruegnerlukas.sqldsl.dsl.expression.OnJoinCondition
import de.ruegnerlukas.sqldsl.dsl.expression.OperationExpr
import de.ruegnerlukas.sqldsl.dsl.expression.OrChainExpr
import de.ruegnerlukas.sqldsl.dsl.expression.OrExpr
import de.ruegnerlukas.sqldsl.dsl.expression.PlaceholderLiteralExpr
import de.ruegnerlukas.sqldsl.dsl.expression.PrimaryKeyProperty
import de.ruegnerlukas.sqldsl.dsl.expression.RefAction
import de.ruegnerlukas.sqldsl.dsl.expression.ReturnAllColumns
import de.ruegnerlukas.sqldsl.dsl.expression.ReturnColumns
import de.ruegnerlukas.sqldsl.dsl.expression.Returning
import de.ruegnerlukas.sqldsl.dsl.expression.ShortLiteralExpr
import de.ruegnerlukas.sqldsl.dsl.expression.StringLiteralExpr
import de.ruegnerlukas.sqldsl.dsl.expression.SubExpr
import de.ruegnerlukas.sqldsl.dsl.expression.SubQueryExpr
import de.ruegnerlukas.sqldsl.dsl.expression.Table
import de.ruegnerlukas.sqldsl.dsl.expression.TableLike
import de.ruegnerlukas.sqldsl.dsl.expression.TimeLiteralExpr
import de.ruegnerlukas.sqldsl.dsl.expression.TypecastExpr
import de.ruegnerlukas.sqldsl.dsl.expression.UniqueProperty
import de.ruegnerlukas.sqldsl.dsl.expression.UsingJoinCondition
import de.ruegnerlukas.sqldsl.dsl.statements.CreateTableStatement
import de.ruegnerlukas.sqldsl.dsl.statements.DeleteStatement
import de.ruegnerlukas.sqldsl.dsl.statements.Dir
import de.ruegnerlukas.sqldsl.dsl.statements.FromElement
import de.ruegnerlukas.sqldsl.dsl.statements.FromStatement
import de.ruegnerlukas.sqldsl.dsl.statements.GroupByStatement
import de.ruegnerlukas.sqldsl.dsl.statements.HavingStatement
import de.ruegnerlukas.sqldsl.dsl.statements.InsertStatement
import de.ruegnerlukas.sqldsl.dsl.statements.ItemsInsertContent
import de.ruegnerlukas.sqldsl.dsl.statements.LimitStatement
import de.ruegnerlukas.sqldsl.dsl.statements.OrderByStatement
import de.ruegnerlukas.sqldsl.dsl.statements.QueryBuilderEndStep
import de.ruegnerlukas.sqldsl.dsl.statements.QueryStatement
import de.ruegnerlukas.sqldsl.dsl.statements.SelectAllElement
import de.ruegnerlukas.sqldsl.dsl.statements.SelectAllFromTableElement
import de.ruegnerlukas.sqldsl.dsl.statements.SelectElement
import de.ruegnerlukas.sqldsl.dsl.statements.SelectStatement
import de.ruegnerlukas.sqldsl.dsl.statements.UpdateStatement
import de.ruegnerlukas.sqldsl.dsl.statements.WhereStatement

class SQLCodeGeneratorImpl(private val dialect: SQLDialect) : SQLCodeGenerator {

	override fun update(update: UpdateStatement): Token {
		return ListToken()
			.add("UPDATE")
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

	override fun insert(insert: InsertStatement): Token {
		return ListToken()
			.add("INSERT INTO")
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

	override fun delete(delete: DeleteStatement): Token {
		return ListToken()
			.add("DELETE FROM")
			.add(delete.target.tableName)
			.addIf(delete.where != null, "WHERE")
			.addIf(delete.where != null) { expression(delete.where!!) }
			.addIf(delete.returning != null) { returning(delete.returning!!) }
	}

	private fun returning(e: Returning): Token {
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

	override fun create(create: CreateTableStatement): Token {
		return ListToken()
			.add("CREATE TABLE")
			.addIf(create.onlyIfNotExists, "IF NOT EXISTS")
			.add(create.table.tableName)
			.add(GroupToken(CsvListToken(create.table.getColumns().map { column(it, create.table) })))
			.add(tableProperties(create.table))
	}

	private fun column(column: Column<*>, table: Table): Token {
		var strDataType = dialect.dataType(column.type)
		val constraints = column
			.getProperties()
			.filter { it !is AutoIncrementProperty }
			.map { columnProperty(it, table.getColumns()) }
			.toMutableList()
		val isAutoIncrement = column
			.getProperties()
			.filterIsInstance<AutoIncrementProperty>()
			.isNotEmpty()
		val indexPrimaryKey = column
			.getProperties()
			.indexOfFirst { it is PrimaryKeyProperty }
		if (isAutoIncrement) {
			dialect.autoIncrementColumn(
				column.type,
				indexPrimaryKey != -1,
				{ v -> strDataType = v },
				{ v -> constraints.add(if (indexPrimaryKey == -1) constraints.size else indexPrimaryKey, StringToken(v)) },
				{ throw IllegalStateException("Auto increment not allowed for column ${table.tableName}#${column.columnName}") }
			)
		}
		return ListToken()
			.add(column.columnName)
			.add(strDataType)
			.addAll(constraints)
	}

	private fun columnProperty(e: ColumnProperty, columns: List<Column<*>>): Token {
		val numPrimaryKeys = columns.count { it.getProperties().filterIsInstance<PrimaryKeyProperty>().isNotEmpty() }
		return when (e) {
			is PrimaryKeyProperty -> {
				if (numPrimaryKeys == 1) {
					StringToken("PRIMARY KEY")
				} else {
					NoOpToken()
				}
			}
			is DefaultBooleanValueProperty -> ListToken().add("DEFAULT").add(dialect.booleanLiteral(e.value))
			is DefaultShortValueProperty -> ListToken().add("DEFAULT").add(e.value.toString())
			is DefaultIntValueProperty -> ListToken().add("DEFAULT").add(e.value.toString())
			is DefaultLongValueProperty -> ListToken().add("DEFAULT").add(e.value.toString())
			is DefaultFloatValueProperty -> ListToken().add("DEFAULT").add(e.value.toString())
			is DefaultDoubleValueProperty -> ListToken().add("DEFAULT").add(e.value.toString())
			is DefaultStringValueProperty -> ListToken().add("DEFAULT").add(dialect.stringLiteral(e.value))
			is DefaultDateValueProperty -> ListToken().add("DEFAULT").add(dialect.dateLiteral(e.value))
			is DefaultTimeValueProperty -> ListToken().add("DEFAULT").add(dialect.timeLiteral(e.value))
			is NotNullProperty -> StringToken("NOT NULL")
			is UniqueProperty -> StringToken("UNIQUE")
			is ForeignKeyConstraint -> ListToken()
				.add("REFERENCES")
				.add(e.table.tableName)
				.addIf(e.column != null, "(${e.column!!.columnName})")
				.add(mapOnDelete(e.onDelete))
				.add(mapOnUpdate(e.onUpdate))
			is AutoIncrementProperty -> NoOpToken()
			else -> throwUnknown(e)
		}
	}

	private fun tableProperties(table: Table): Token {
		val primaryKeys = table.getColumns().filter { it.getProperties().filterIsInstance<PrimaryKeyProperty>().isNotEmpty() }
		if (primaryKeys.size > 1) {
			return ListToken()
				.add("PRIMARY KEY")
				.add(GroupToken(CsvListToken(primaryKeys.map { StringToken(it.columnName) })))
		}
		return NoOpToken()
	}

	private fun mapOnDelete(action: RefAction) = ListToken().add("ON DELETE").add(mapReferentialAction(action))

	private fun mapOnUpdate(action: RefAction) = ListToken().add("ON UPDATE").add(mapReferentialAction(action))

	private fun mapReferentialAction(action: RefAction): String {
		return when (action) {
			RefAction.NO_ACTION -> "NO ACTION"
			RefAction.RESTRICT -> "RESTRICT"
			RefAction.SET_NULL -> "SET NULL"
			RefAction.SET_DEFAULT -> "SET DEFAULT"
			RefAction.CASCADE -> "CASCADE"
		}
	}

	override fun query(query: QueryStatement<*>): Token {
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
			.add(CsvListToken(stmt.elements.map { dialect.orderField(expression(it.expr), orderByDirection(it.dir), it.dir) }))
	}

	private fun orderByDirection(dir: Dir): String {
		return when (dir) {
			Dir.ASC -> "ASC"
			Dir.DESC -> "DESC"
		}
	}

	private fun limit(stmt: LimitStatement): Token {
		return ListToken()
			.add("LIMIT")
			.add(stmt.limit.toString())
			.addIf(stmt.offset != 0, "OFFSET ${stmt.offset}")
	}

	private fun join(e: JoinElement): Token {
		val strOp = dialect.joinOperation(e.op) ?: throw java.lang.IllegalStateException("Join operation ${e.op} is not supported")
		return ListToken()
			.add(GroupToken(fromElement(e.left)))
			.add(strOp)
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

	private fun expression(e: Expr<*>): Token {
		return when (e) {
			is FunctionExpr<*> -> functionExpr(e)
			is ConditionExpr -> conditionExpression(e)
			is OperationExpr<*> -> operationExpression(e)
			is LiteralExpr<*> -> literal(e)
			is SubQueryExpr<*> -> when (e) {
				is QueryStatement<*> -> query(e)
				is QueryBuilderEndStep<*> -> query(e.build<Any>())
				else -> throwUnknown(e)
			}
			is AliasExpr<*> -> StringToken(e.alias)
			is Column<*> -> columnIdentifier(e)
			is DerivedColumn<*> -> StringToken("${e.table.tableName}.${e.columnName}")
			is TypecastExpr<*, *> -> expression(e.expr)
			else -> throwUnknown(e)
		}
	}

	private fun functionExpr(e: FunctionExpr<*>): Token {
		val fnOverwrite = dialect.function(e.type, e.arguments) { expression(it) }
		if (fnOverwrite != null) {
			return fnOverwrite
		} else {
			val fnName = dialect.functionName(e.type) ?: throw IllegalStateException("Function '${e.type}' not supported.")
			if (e.arguments.isEmpty()) {
				return StringToken("$fnName()")
			} else {
				return NamedGroupToken(fnName, CsvListToken(e.arguments.map { expression(it) }))
			}
		}
	}

	private fun conditionExpression(e: ConditionExpr): Token {
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
			else -> throwUnknown(e)
		}
	}

	private fun operationExpression(e: OperationExpr<*>): Token {
		return when (e) {
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
			else -> throwUnknown(e)
		}
	}

	private fun literal(e: LiteralExpr<*>): Token {
		return when (e) {
			is BooleanLiteralExpr -> StringToken(dialect.booleanLiteral(e.value))
			is ShortLiteralExpr -> StringToken(e.value.toString())
			is IntLiteralExpr -> StringToken(e.value.toString())
			is LongLiteralExpr -> StringToken(e.value.toString())
			is FloatLiteralExpr -> StringToken(e.value.toString())
			is DoubleLiteralExpr -> StringToken(e.value.toString())
			is StringLiteralExpr -> StringToken(dialect.stringLiteral(e.value))
			is DateLiteralExpr -> StringToken(dialect.dateLiteral(e.value))
			is TimeLiteralExpr -> StringToken(dialect.timeLiteral(e.value))
			is ListLiteralExpr<*> -> GroupToken(CsvListToken(e.values.map { expression(it) }))
			is PlaceholderLiteralExpr<*> -> PlaceholderToken(e.name)
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