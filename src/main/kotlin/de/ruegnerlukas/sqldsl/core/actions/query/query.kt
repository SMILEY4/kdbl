package de.ruegnerlukas.sqldsl.core.actions.query


fun query() = Step1QueryBuilder()


class Step1QueryBuilder {

	fun select(vararg columns: SelectColumn): Step2QueryBuilder {
		return Step2QueryBuilder(columns.toList())
	}

}

class Step2QueryBuilder(private val selectColumns: List<SelectColumn>) {

	fun from(vararg source: QuerySource): Step3QueryBuilder {
		return Step3QueryBuilder(selectColumns, source.toList())
	}

}


class Step3QueryBuilder(private val selectColumns: List<SelectColumn>, private val querySources: List<QuerySource>) {

	private val orderByFields = mutableListOf<Pair<ColumnRef<*,*>, Dir>>()

	fun build() = QueryStatement(selectColumns, querySources, orderByFields)

	fun orderBy(vararg fields: Pair<ColumnRef<*,*>, Dir>): Step3QueryBuilder {
		orderByFields.addAll(fields.toList())
		return this
	}

}

