package sqldsl

import de.ruegnerlukas.sqldsl.dsl.expression.AliasTable
import de.ruegnerlukas.sqldsl.dsl.expression.Table
import de.ruegnerlukas.sqldsl.dsl.expression.TableLike


object Actor : ActorTableDef()

sealed class ActorTableDef : Table("actor") {
	val id = integer("act_id").primaryKey()
	val fName = text("act_fname").notNull()
	val lName = text("act_lname").notNull()
	val gender = text("act_gender").notNull()

	companion object {
		class ActorTableDefAlias(override val table: TableLike, override val alias: String) : ActorTableDef(), AliasTable
	}

	override fun alias(alias: String): ActorTableDefAlias {
		return ActorTableDefAlias(this, alias)
	}
}


object MovieCast : MovieCastTableDef()

sealed class MovieCastTableDef : Table("movie_cast") {
	val actorId = integer("act_id").notNull().foreignKey(Actor.id)
	val movieId = integer("mov_id").notNull().foreignKey(Movie.id)
	val role = text("role").notNull()

	companion object {
		class MovieCastTableDefAlias(override val table: TableLike, override val alias: String) : MovieCastTableDef(), AliasTable
	}

	override fun alias(alias: String): MovieCastTableDefAlias {
		return MovieCastTableDefAlias(this, alias)
	}
}


object Movie : MovieTableDef()

sealed class MovieTableDef : Table("movie") {
	val id = integer("mov_id").primaryKey()
	val title = text("mov_title").notNull()
	val year = integer("mov_year").notNull()
	val time = integer("mov_time").notNull()
	val lang = text("mov_lang").notNull()
	val dateRelease = text("mov_dt_rel").notNull()
	val releaseCountry = text("mov_rel_country").notNull()

	companion object {
		class MovieTableDefAlias(override val table: TableLike, override val alias: String) : MovieTableDef(), AliasTable
	}

	override fun alias(alias: String): MovieTableDefAlias {
		return MovieTableDefAlias(this, alias)
	}
}


object MovieDirection : MovieDirectionTableDef()

sealed class MovieDirectionTableDef : Table("movie_direction") {
	val directorId = integer("dir_id").foreignKey(Director.id)
	val movieId = integer("mov_id").foreignKey(Movie.id)

	companion object {
		class MovieDirectionTableDefTableDefAlias(override val table: TableLike, override val alias: String) : MovieDirectionTableDef(), AliasTable
	}

	override fun alias(alias: String): MovieDirectionTableDefTableDefAlias {
		return MovieDirectionTableDefTableDefAlias(this, alias)
	}
}


object Director : DirectorTableDef()

sealed class DirectorTableDef : Table("director") {
	val id = integer("dir_id").primaryKey()
	val fName = text("dir_fname").notNull()
	val lName = text("dir_lname").notNull()

	companion object {
		class DirectorTableDefTableDefAlias(override val table: TableLike, override val alias: String) : DirectorTableDef(), AliasTable
	}

	override fun alias(alias: String): DirectorTableDefTableDefAlias {
		return DirectorTableDefTableDefAlias(this, alias)
	}
}


object Reviewer : ReviewerTableDef()

sealed class ReviewerTableDef : Table("reviewer") {
	val id = integer("rev_id").primaryKey()
	val name = text("rev_name").notNull()

	companion object {
		class ReviewerTableDefAlias(override val table: TableLike, override val alias: String) : ReviewerTableDef(), AliasTable
	}

	override fun alias(alias: String): ReviewerTableDefAlias {
		return ReviewerTableDefAlias(this, alias)
	}
}

object Rating : RatingTableDef()

sealed class RatingTableDef : Table("rating") {
	val movieId = integer("mov_id").foreignKey(Movie.id)
	val reviewerId = integer("rev_id").foreignKey(Reviewer.id)
	val stars = integer("rev_stars").notNull()
	val numRatings = integer("num_o_ratings").notNull()

	companion object {
		class RatingTableDefAlias(override val table: TableLike, override val alias: String) : RatingTableDef(), AliasTable
	}

	override fun alias(alias: String): RatingTableDefAlias {
		return RatingTableDefAlias(this, alias)
	}
}


object Genre : GenreTableDef()

sealed class GenreTableDef : Table("genres") {
	val id = integer("gen_id").primaryKey()
	val title = text("gen_title").notNull()

	companion object {
		class GenreTableDefAlias(override val table: TableLike, override val alias: String) : GenreTableDef(), AliasTable
	}

	override fun alias(alias: String): GenreTableDefAlias {
		return GenreTableDefAlias(this, alias)
	}
}

object MovieGenre : MovieGenreTableDef()

sealed class MovieGenreTableDef : Table("movie_genres") {
	val movieId = integer("mov_id").foreignKey(Movie.id)
	val genreId = integer("gen_id").foreignKey(Genre.id)

	companion object {
		class MovieGenreTableDefAlias(override val table: TableLike, override val alias: String) : MovieGenreTableDef(), AliasTable
	}

	override fun alias(alias: String): MovieGenreTableDefAlias {
		return MovieGenreTableDefAlias(this, alias)
	}
}