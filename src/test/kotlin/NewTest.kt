import de.ruegnerlukas.sqldsl.core.actions.query.alias
import de.ruegnerlukas.sqldsl.core.actions.query.col
import de.ruegnerlukas.sqldsl.core.actions.query.eq
import de.ruegnerlukas.sqldsl.core.actions.query.innerJoin
import de.ruegnerlukas.sqldsl.core.actions.query.query
import de.ruegnerlukas.sqldsl.db.Address
import de.ruegnerlukas.sqldsl.db.Book
import de.ruegnerlukas.sqldsl.db.BookAuthor
import de.ruegnerlukas.sqldsl.db.BookLanguage
import de.ruegnerlukas.sqldsl.sqlite.SQLiteQueryGenerator

fun main() {

	val builder = SQLiteQueryGenerator()


	println(
		builder.build(
			query()
				.from(
					Book,
					Address,
					BookAuthor.alias("my_author"),
				)
		)
	)

	val aliasBook = Book.alias("b")
	val aliasBookLanguage = BookLanguage.alias("lang")

	println(
		builder.build(
			query()
				.from(
					innerJoin(
						aliasBook,
						aliasBookLanguage,
						aliasBook.col(Book.languageId) eq aliasBookLanguage.col(BookLanguage.languageId)
					)
				)
		)
	)


}