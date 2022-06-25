package kdbl

import de.ruegnerlukas.kdbl.builder.SQL
import de.ruegnerlukas.kdbl.builder.isEqual
import de.ruegnerlukas.kdbl.builder.placeholder
import de.ruegnerlukas.kdbl.db.Database


data class MovieRecord(
	val id: Int,
	val title: String
)

data class NameRecord(
	val firstName: String,
	val lastName: String
)


class TestRepository(private val db: Database) {

	suspend fun createMovieTable() = db
		.startCreate(
			SQL.create(Movie)
		)
		.execute()


	suspend fun insertActor(fName: String, lName: String, gender: String) = db
		.startInsert {
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
		}
		.executeCounting()

	suspend fun updateActor(id: Int, fName: String, lName: String) = db
		.startUpdate {
			SQL
				.update(Actor)
				.set {
					it[Actor.fName] = fName
					it[Actor.lName] = lName
				}
				.where(Actor.id.isEqual(id))
		}


	suspend fun insertActorGetName(fName: String, lName: String, gender: String) = db
		.startInsert {
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
				.returning(Actor.fName, Actor.lName)
		}
		.executeReturning()
		.getOne { NameRecord(it.getString(Actor.fName.columnName), it.getString(Actor.lName.columnName)) }


	suspend fun getMoviesByYear(year: Int) = db
		.startQuery {
			SQL
				.select(Movie.id, Movie.title)
				.from(Movie)
				.where(Movie.year.isEqual(year))

		}
		.execute()
		.getMultiple { MovieRecord(it.getInt(Movie.id.columnName), it.getString(Movie.title.columnName)) }


	suspend fun deleteTwoMovies(id1: Int, id2: Int) = db.startTransaction(true) { transaction ->
		transaction
			.startDelete {
				SQL
					.delete()
					.from(Movie)
					.where(Movie.id.isEqual(id1))
			}
			.executeCounting()
		transaction
			.startDelete {
				SQL
					.delete()
					.from(Movie)
					.where(Movie.id.isEqual(id2))
			}
			.executeCounting()
	}

}


class CachedTestRepository(private val db: Database) {

	suspend fun createMovieTable() = db
		.startCreate("createMovieTable") {
			SQL.create(Movie)
		}
		.execute()

	suspend fun insertActor(fName: String, lName: String, gender: String) = db
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
		.parameters {
			it["fname"] = fName
			it["lName"] = lName
			it["gender"] = gender
		}
		.executeReturning()
		.getMultiple { NameRecord(it.getString(Actor.fName.columnName), it.getString(Actor.lName.columnName)) }

	suspend fun getMoviesByYear(year: Int) = db
		.startQuery("getMoviesByYear") {
			SQL
				.select(Movie.id, Movie.title)
				.from(Movie)
				.where(Movie.year.isEqual(placeholder("year")))
		}
		.parameter("year", year)
		.execute()
		.getMultipleOrNone { MovieRecord(it.getInt(Movie.id.columnName), it.getString(Movie.title.columnName)) }

}

