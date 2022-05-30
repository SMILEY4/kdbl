import de.ruegnerlukas.sqldsl.core.actions.query.alias
import de.ruegnerlukas.sqldsl.core.actions.query.eq
import de.ruegnerlukas.sqldsl.core.actions.query.get
import de.ruegnerlukas.sqldsl.core.actions.query.innerJoin
import de.ruegnerlukas.sqldsl.core.actions.query.query
import de.ruegnerlukas.sqldsl.db.Address
import de.ruegnerlukas.sqldsl.db.Book
import de.ruegnerlukas.sqldsl.db.BookAuthor
import de.ruegnerlukas.sqldsl.db.BookLanguage
import de.ruegnerlukas.sqldsl.sqlite.SQLiteQueryGenerator

fun main() {

	val builder = SQLiteQueryGenerator()

//
//	println(
//		builder.build(
//			query()
//				.select(
//					Book.bookId,
//					BookAuthor.alias("my_author")[BookAuthor.authorId].alias("my_author_id")
//				)
//				.from(
//					Book,
//					Address,
//					BookAuthor.alias("my_author"),
//				)
//		)
//	)
//
//	val aliasBook = Book.alias("b")
//	val aliasBookLanguage = BookLanguage.alias("lang")
//
//	println(
//		builder.build(
//			query()
//				.select(
//					aliasBook[Book.bookId].alias("my_book_id")
//				)
//				.from(
//					innerJoin(
//						aliasBook,
//						aliasBookLanguage,
//						aliasBook[Book.languageId] eq aliasBookLanguage[BookLanguage.languageId]
//					)
//				)
//		)
//	)


}