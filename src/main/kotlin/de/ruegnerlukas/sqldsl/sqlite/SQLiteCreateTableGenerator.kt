package de.ruegnerlukas.sqldsl.sqlite

import de.ruegnerlukas.sqldsl.core.generators.CreateTableGenerator
import de.ruegnerlukas.sqldsl.core.schema.AutoIncrementPseudoConstraint
import de.ruegnerlukas.sqldsl.core.schema.Column
import de.ruegnerlukas.sqldsl.core.schema.ColumnConstraint
import de.ruegnerlukas.sqldsl.core.schema.Create
import de.ruegnerlukas.sqldsl.core.schema.DataType
import de.ruegnerlukas.sqldsl.core.schema.DefaultValueConstraint
import de.ruegnerlukas.sqldsl.core.schema.ForeignKeyConstraint
import de.ruegnerlukas.sqldsl.core.schema.NotNullConstraint
import de.ruegnerlukas.sqldsl.core.schema.OnConflict
import de.ruegnerlukas.sqldsl.core.schema.OnDelete
import de.ruegnerlukas.sqldsl.core.schema.OnUpdate
import de.ruegnerlukas.sqldsl.core.schema.PrimaryKeyConstraint
import de.ruegnerlukas.sqldsl.core.schema.Table
import de.ruegnerlukas.sqldsl.core.schema.UniqueConstraint
import de.ruegnerlukas.sqldsl.core.tokens.CsvListToken
import de.ruegnerlukas.sqldsl.core.tokens.GroupToken
import de.ruegnerlukas.sqldsl.core.tokens.ListToken
import de.ruegnerlukas.sqldsl.core.tokens.NoOpToken
import de.ruegnerlukas.sqldsl.core.tokens.StringToken
import de.ruegnerlukas.sqldsl.core.tokens.Token

class SQLiteCreateTableGenerator(private val prettyPrint: Boolean) : CreateTableGenerator {

    override fun build(table: Table): String {
        return ListToken()
            .add("CREATE TABLE")
            .addIf("IF NOT EXISTS") { table.getCreatePolicy() == Create.IF_NOT_EXISTS }
            .add(table.getTableName())
            .add(
                GroupToken(
                    CsvListToken(
                        listOf(
                            columnDefinitionsBlock(table),
                            tableConstraintsBlock(table)
                        ),
                        prettyPrint
                    ),
                    prettyPrint,
                    prettyPrint
                )
            )
            .buildString()
    }


    private fun columnDefinitionsBlock(table: Table): Token {
        return CsvListToken(oneEntryPerLine = prettyPrint)
            .then {
                table.getTableColumns().forEach { add(columnDefinition(it)) }
            }
    }


    private fun columnDefinition(column: Column<*,*>): Token {
        return ListToken()
            .add(column.getColumnName())
            .add(mapDataType(column.getDataType()))
            .then {
                column.getConstraints().forEach {
                    add(columnConstraint(column, it))
                }
            }
    }


    private fun columnConstraint(column: Column<*,*>, constraint: ColumnConstraint): Token {
        return when (constraint) {
            is UniqueConstraint -> {
                ListToken()
                    .add("UNIQUE")
                    .addIf("ON CONFLICT ${mapOnConflict(constraint.onConflict)}") { constraint.onConflict != OnConflict.ABORT }
            }
            is NotNullConstraint -> {
                ListToken()
                    .add("NOT NULL")
                    .addIf("ON CONFLICT ${mapOnConflict(constraint.onConflict)}") { constraint.onConflict != OnConflict.ABORT }
            }
            is PrimaryKeyConstraint -> {
                if (countPrimaryKeys(column.getParentTable()) == 1) {
                    ListToken()
                        .add("PRIMARY KEY")
                        .addIf("ON CONFLICT ${mapOnConflict(constraint.onConflict)}") { constraint.onConflict != OnConflict.ABORT }
                        .addIf("AUTOINCREMENT") { column.hasConstraint<AutoIncrementPseudoConstraint>() }
                } else {
                    NoOpToken()
                }
            }
            is ForeignKeyConstraint -> {
                ListToken()
                    .add("REFERENCES")
                    .add(constraint.table.getTableName())
                    .addIf("(${constraint.column?.getColumnName()})") { constraint.column != null }
                    .addIf("ON DELETE ${mapOnDelete(constraint.onDelete)}") { constraint.onDelete !== OnDelete.NO_ACTION }
                    .addIf("ON UPDATE ${mapOnUpdate(constraint.onUpdate)}") { constraint.onUpdate !== OnUpdate.NO_ACTION }
            }
            is DefaultValueConstraint<*> -> {
                ListToken()
                    .add("DEFAULT")
                    .add(constraint.getValueAsString())
            }
            is AutoIncrementPseudoConstraint -> NoOpToken()
            else -> throw Exception("Unknown sqlite-column-constraint: $constraint for column ${column.getColumnName()}")
        }
    }


    private fun tableConstraintsBlock(table: Table): Token {
        return if (countPrimaryKeys(table) > 1) {
            ListToken()
                .add("PRIMARY KEY")
                .add(
                    GroupToken(
                        CsvListToken(
                            getPrimaryKeyColumns(table).map { StringToken(it.getColumnName()) }
                        )
                    )
                )
        } else {
            NoOpToken()
        }
    }


    private fun mapDataType(type: DataType): String {
        return when (type) {
            DataType.TEXT -> "TEXT"
            DataType.INTEGER -> "INTEGER"
            DataType.BOOLEAN -> "BOOLEAN"
        }
    }


    private fun mapOnDelete(action: OnDelete): String {
        return when (action) {
            OnDelete.NO_ACTION -> "NO ACTION"
            OnDelete.CASCADE -> "CASCADE"
            OnDelete.RESTRICT -> "RESTRICT"
            OnDelete.SET_NULL -> "SET NULL"
            OnDelete.SET_DEFAULT -> "SET DEFAULT"
        }
    }


    private fun mapOnUpdate(action: OnUpdate): String {
        return when (action) {
            OnUpdate.NO_ACTION -> "NO ACTION"
            OnUpdate.CASCADE -> "CASCADE"
            OnUpdate.RESTRICT -> "RESTRICT"
            OnUpdate.SET_NULL -> "SET NULL"
            OnUpdate.SET_DEFAULT -> "SET DEFAULT"
        }
    }


    private fun mapOnConflict(action: OnConflict): String {
        return when (action) {
            OnConflict.ROLLBACK -> "ROLLBACK"
            OnConflict.ABORT -> "ABORT"
            OnConflict.FAIL -> "FAIL"
            OnConflict.IGNORE -> "IGNORE"
            OnConflict.REPLACE -> "REPLACE"
        }
    }


    private fun countPrimaryKeys(table: Table): Int {
        return table.getTableColumns().count { it.hasConstraint<PrimaryKeyConstraint>() }
    }


    private fun getPrimaryKeyColumns(table: Table): List<Column<*,*>> {
        return table.getTableColumns().filter { it.hasConstraint<PrimaryKeyConstraint>() }
    }

}