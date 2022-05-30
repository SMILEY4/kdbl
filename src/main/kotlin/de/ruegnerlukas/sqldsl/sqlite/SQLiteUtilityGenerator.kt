package de.ruegnerlukas.sqldsl.sqlite

import de.ruegnerlukas.sqldsl.core.actions.query.AliasTableColumnRef
import de.ruegnerlukas.sqldsl.core.actions.query.ColumnRef
import de.ruegnerlukas.sqldsl.core.actions.query.TableRef
import de.ruegnerlukas.sqldsl.core.tokens.ListToken
import de.ruegnerlukas.sqldsl.core.tokens.StringToken
import de.ruegnerlukas.sqldsl.core.tokens.Token

class SQLiteUtilityGenerator {

	fun columnRef(ref: ColumnRef<*, *>): Token {
		if (ref is AliasTableColumnRef) {
			val tableName = ref.getTableRef().getTableName()
			val columName = ref.getColumn().getColumnName()
			return StringToken("$tableName.$columName")
		} else {
			return StringToken(ref.getColumn().getColumnName())
		}
	}

	fun tableRef(ref: TableRef): Token {
		val orgName = ref.getTable().getTableName()
		val assignedName = ref.getTableName()
		if (orgName == assignedName) {
			return StringToken(orgName)
		} else {
			return ListToken()
				.add(orgName)
				.add("AS")
				.add(assignedName)
		}
	}

}