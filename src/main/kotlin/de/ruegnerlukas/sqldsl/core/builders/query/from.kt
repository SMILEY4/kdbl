package de.ruegnerlukas.sqldsl.core.builders.query

import de.ruegnerlukas.sqldsl.core.grammar.from.FromExpression
import de.ruegnerlukas.sqldsl.core.grammar.statements.FromStatement
import de.ruegnerlukas.sqldsl.core.grammar.statements.SelectStatement


class PostSelectQueryBuilder(private val selectStatement: SelectStatement) {

	fun from(vararg expressions: FromExpression): PostFromQueryBuilder {
		return PostFromQueryBuilder(selectStatement, FromStatement(expressions.toList()))
	}


}
//
//class FromQueryBuilder {
//
//	private val expressions = mutableListOf<FromExpression>()
//
//	fun table(table: Table) {
//		expressions.add(TableFromExpression(table, table.getTableName()))
//	}
//
//	fun table(table: Table, alias: String) {
//		expressions.add(TableFromExpression(table, alias))
//	}
//
//	fun subQuery(query: QueryStatement, alias: String) {
//		expressions.add(QueryFromExpression(query, alias))
//	}
//
//	fun joinLeft(block: JoinBuilder.() -> Unit) {
//		expressions.add(JoinBuilder(JoinOp.LEFT).apply(block).build())
//	}
//
//	fun joinInner(block: JoinBuilder.() -> Unit) {
//		expressions.add(JoinBuilder(JoinOp.INNER).apply(block).build())
//	}
//
//	fun joinCross(block: JoinBuilder.() -> Unit) {
//		expressions.add(JoinBuilder(JoinOp.CROSS).apply(block).build())
//	}
//
//	class JoinBuilder(private val op: JoinOp) {
//
//		private var left: FromExpression? = null
//		private var right: FromExpression? = null
//		private var on: Expression? = null
//		private var using: List<LiteralColumnRef>? = null
//
//		fun left(table: Table) {
//			this.left = TableFromExpression(table, table.getTableName())
//		}
//
//		fun left(table: Table, alias: String) {
//			this.left = TableFromExpression(table, alias)
//		}
//
//		fun right(table: Table) {
//			this.right = TableFromExpression(table, table.getTableName())
//		}
//
//		fun right(table: Table, alias: String) {
//			this.right = TableFromExpression(table, alias)
//		}
//
//		fun on(expression: Expression) {
//			this.on = expression
//		}
//
//		fun using(vararg columns: LiteralColumnRef) {
//			this.using = columns.toList()
//		}
//
//		internal fun build(): JoinFromExpression {
//			if (on != null) {
//				return JoinFromExpression(left!!, right!!, op, ConditionJoinConstraint(on!!))
//			} else {
//				return JoinFromExpression(left!!, right!!, op, UsingJoinConstraint(using!!))
//			}
//		}
//	}
//
//	fun buildStatement(): FromStatement {
//		return FromStatement(expressions)
//	}
//
//}
