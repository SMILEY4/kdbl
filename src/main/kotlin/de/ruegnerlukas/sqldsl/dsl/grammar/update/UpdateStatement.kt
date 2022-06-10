package de.ruegnerlukas.sqldsl.dsl.grammar.update

import de.ruegnerlukas.sqldsl.dsl.grammar.column.OnConflict

class UpdateStatement(
	val onConflict: OnConflict,
	val target: de.ruegnerlukas.sqldsl.dsl.grammar.target.CommonTarget,
	val set: de.ruegnerlukas.sqldsl.dsl.grammar.update.UpdateSetStatement,
	val where: de.ruegnerlukas.sqldsl.dsl.grammar.where.WhereStatement? = null,
	val from: de.ruegnerlukas.sqldsl.dsl.grammar.from.FromStatement? = null,
	val returning: de.ruegnerlukas.sqldsl.dsl.grammar.insert.ReturningStatement? = null
)
