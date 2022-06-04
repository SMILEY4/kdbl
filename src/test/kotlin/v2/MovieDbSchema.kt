package v2

import de.ruegnerlukas.sqldsl2.grammar.TableAlias
import de.ruegnerlukas.sqldsl2.schema.IntColumn.Companion.integer
import de.ruegnerlukas.sqldsl2.schema.Table
import de.ruegnerlukas.sqldsl2.schema.TextColumn.Companion.text

object Actor : ActorTableDef()

sealed class ActorTableDef : Table<ActorTableDef>("actor") {
	val id = integer("act_id").primaryKey()
	val fName = text("act_fname").notNull()
	val lName = text("act_lname").notNull()
	val gender = text("act_gender").notNull()

	companion object {
		class ActorTableDefAlias(override val alias: String) : ActorTableDef(), TableAlias
	}

	override fun alias(alias: String): ActorTableDef {
		return ActorTableDefAlias(alias)
	}
}


object MovieCast : MovieCastTableDef()

sealed class MovieCastTableDef : Table<MovieCastTableDef>("movie_cast") {
	val actorId = integer("act_id").notNull().foreignKey(Actor.id)
	val movieId = integer("mov_id").notNull().foreignKey(Movie.id)
	val role = text("role").notNull()

	companion object {
		class MovieCastTableDefAlias(override val alias: String) : MovieCastTableDef(), TableAlias
	}

	override fun alias(alias: String): MovieCastTableDef {
		return MovieCastTableDefAlias(alias)
	}
}


object Movie : MovieTableDef()

sealed class MovieTableDef : Table<MovieTableDef>("movie") {
	val id = integer("mov_id").primaryKey()
	val title = text("mov_title").notNull()
	val year = integer("mov_year").notNull()
	val time = integer("mov_time").notNull()
	val lang = text("mov_lang").notNull()
	val dateRelease = text("mov_dt_rel").notNull()
	val releaseCountry = text("mov_rel_country").notNull()

	companion object {
		class MovieTableDefAlias(override val alias: String) : MovieTableDef(), TableAlias
	}

	override fun alias(alias: String): MovieTableDef {
		return MovieTableDefAlias(alias)
	}
}


object MovieDirection : MovieDirectionTableDef()

sealed class MovieDirectionTableDef : Table<MovieDirectionTableDef>("movie_direction") {
	val directorId = integer("dir_id").foreignKey(Director.id)
	val movieId = integer("mov_id").foreignKey(Movie.id)

	companion object {
		class MovieDirectionTableDefTableDefAlias(override val alias: String) : MovieDirectionTableDef(), TableAlias
	}

	override fun alias(alias: String): MovieDirectionTableDef {
		return MovieDirectionTableDefTableDefAlias(alias)
	}
}


object Director : DirectorTableDef()

sealed class DirectorTableDef : Table<DirectorTableDef>("director") {
	val id = integer("dir_id").primaryKey()
	val fName = text("dir_fname").notNull()
	val lName = text("dir_lname").notNull()

	companion object {
		class DirectorTableDefTableDefAlias(override val alias: String) : DirectorTableDef(), TableAlias
	}

	override fun alias(alias: String): DirectorTableDef {
		return DirectorTableDefTableDefAlias(alias)
	}
}


object Reviewer : ReviewerTableDef()

sealed class ReviewerTableDef : Table<ReviewerTableDef>("reviewer") {
	val id = integer("rev_id").primaryKey()
	val name = text("rev_name").notNull()

	companion object {
		class ReviewerTableDefAlias(override val alias: String) : ReviewerTableDef(), TableAlias
	}

	override fun alias(alias: String): ReviewerTableDef {
		return ReviewerTableDefAlias(alias)
	}
}

object Rating : RatingTableDef()

sealed class RatingTableDef : Table<RatingTableDef>("rating") {
	val movieId = integer("mov_id").foreignKey(Movie.id)
	val reviewerId = integer("rev_id").foreignKey(Reviewer.id)
	val stars = integer("rev_stars").notNull()
	val numRatings = integer("num_o_ratings").notNull()

	companion object {
		class RatingTableDefAlias(override val alias: String) : RatingTableDef(), TableAlias
	}

	override fun alias(alias: String): RatingTableDef {
		return RatingTableDefAlias(alias)
	}
}


object Genre : GenreTableDef()

sealed class GenreTableDef : Table<GenreTableDef>("genres") {
	val id = integer("gen_id").primaryKey()
	val title = text("gen_title").notNull()

	companion object {
		class GenreTableDefAlias(override val alias: String) : GenreTableDef(), TableAlias
	}

	override fun alias(alias: String): GenreTableDef {
		return GenreTableDefAlias(alias)
	}
}

object MovieGenre : MovieGenreTableDef()

sealed class MovieGenreTableDef : Table<MovieGenreTableDef>("movie_genres") {
	val movieId = integer("mov_id").foreignKey(Movie.id)
	val genreId = integer("gen_id").foreignKey(Genre.id)

	companion object {
		class MovieGenreTableDefAlias(override val alias: String) : MovieGenreTableDef(), TableAlias
	}

	override fun alias(alias: String): MovieGenreTableDef {
		return MovieGenreTableDefAlias(alias)
	}
}