package sqldsl

import de.ruegnerlukas.sqldsl.builder.SQL
import de.ruegnerlukas.sqldsl.builder.isEqual
import de.ruegnerlukas.sqldsl.builder.placeholder
import de.ruegnerlukas.sqldsl.db.Database


data class MovieRecord(
	val id: Int,
	val title: String
)


class TestRepository(private val db: Database) {

	fun createMovieTable() = db
		.startCreate(
			SQL.create(Movie)
		)
		.execute()

	fun insertActor(fName: String, lName: String, gender: String) = db
		.startInsert(
			SQL
				.insert()
				.into(Actor)
				.columns(Actor.id, Actor.fName, Actor.lName, Actor.gender)
				.items(
					SQL.item()
						.set(Actor.fName, fName)
						.set(Actor.lName, lName)
						.set(Actor.gender, gender),
				)
		)
		.withReturning()
		.execute()

	fun getMoviesByYear(year: Int) = db
		.startQuery(
			SQL
				.select(Movie.id, Movie.title)
				.from(Movie)
				.where(Movie.year.isEqual(year))

		)
		.execute()
		.mapRows { MovieRecord(it.getInt(Movie.id.columnName), it.getString(Movie.title.columnName)) }

	fun deleteTwoMovies(id1: Int, id2: Int) = db.startTransaction(true) { transaction ->
		transaction
			.startDelete(
				SQL
					.delete()
					.from(Movie)
					.where(Movie.id.isEqual(id1))
			)
			.withUpdateCount()
			.execute()
		transaction
			.startDelete(
				SQL
					.delete()
					.from(Movie)
					.where(Movie.id.isEqual(id2))
			)
			.withUpdateCount()
			.execute()
	}

}


class CachedTestRepository(private val db: Database) {

	fun createMovieTable() = db
		.startCreate("createMovieTable") {
			SQL.create(Movie)
		}
		.execute()

	fun insertActor(fName: String, lName: String, gender: String) = db
		.startInsert("insertActor") {
			SQL
				.insert()
				.into(Actor)
				.columns(Actor.id, Actor.fName, Actor.lName, Actor.gender)
				.items(
					SQL.item()
						.set(Actor.fName, placeholder("fName"))
						.set(Actor.lName, placeholder("lName"))
						.set(Actor.gender, placeholder("gender")),
				)
		}
		.withReturning()
		.parameter("fName", fName)
		.parameter("lName", lName)
		.parameter("gender", gender)
		.execute()

	fun getMoviesByYear(year: Int) = db
		.startQuery("getMoviesByYear") {
			SQL
				.select(Movie.id, Movie.title)
				.from(Movie)
				.where(Movie.year.isEqual(placeholder("year")))
		}
		.parameter("year", year)
		.execute()
		.mapRows { MovieRecord(it.getInt(Movie.id.columnName), it.getString(Movie.title.columnName)) }

}

