package de.ruegnerlukas.sqldsl.core.syntax.refs.column

import de.ruegnerlukas.sqldsl.core.schema.Table

interface DirectColumnRef<D, T : Table> : ColumnRef<D, T>
