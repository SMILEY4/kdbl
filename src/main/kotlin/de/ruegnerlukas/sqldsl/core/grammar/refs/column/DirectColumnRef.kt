package de.ruegnerlukas.sqldsl.core.grammar.refs.column

import de.ruegnerlukas.sqldsl.core.schema.Table

interface DirectColumnRef<D, T : Table> : ColumnRef<D, T>
