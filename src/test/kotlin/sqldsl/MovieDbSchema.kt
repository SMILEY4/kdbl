package sqldsl

import de.ruegnerlukas.sqldsl.grammar.table.AliasTable
import de.ruegnerlukas.sqldsl.grammar.table.TableBase
import de.ruegnerlukas.sqldsl.schema.IntColumn.Companion.integer
import de.ruegnerlukas.sqldsl.schema.Table
import de.ruegnerlukas.sqldsl.schema.TextColumn.Companion.text

object Actor : ActorTableDef()

sealed class ActorTableDef : Table<ActorTableDef>("actor") {
	val id = integer("act_id").primaryKey()
	val fName = text("act_fname").notNull()
	val lName = text("act_lname").notNull()
	val gender = text("act_gender").notNull()

	companion object {
		class ActorTableDefAlias(override val baseTable: TableBase, override val aliasName: String): ActorTableDef(), AliasTable
	}

	override fun alias(alias: String): ActorTableDefAlias {
		return ActorTableDefAlias(this, alias)
	}
}


object MovieCast : MovieCastTableDef()

sealed class MovieCastTableDef : Table<MovieCastTableDef>("movie_cast") {
	val actorId = integer("act_id").notNull().foreignKey(Actor.id)
	val movieId = integer("mov_id").notNull().foreignKey(Movie.id)
	val role = text("role").notNull()

	companion object {
		class MovieCastTableDefAlias(override val baseTable: TableBase, override val aliasName: String) : MovieCastTableDef(), AliasTable
	}

	override fun alias(alias: String): MovieCastTableDef {
		return MovieCastTableDefAlias(this, alias)
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
		class MovieTableDefAlias(override val baseTable: TableBase, override val aliasName: String) : MovieTableDef(), AliasTable
	}

	override fun alias(alias: String): MovieTableDef {
		return MovieTableDefAlias(this, alias)
	}
}


object MovieDirection : MovieDirectionTableDef()

sealed class MovieDirectionTableDef : Table<MovieDirectionTableDef>("movie_direction") {
	val directorId = integer("dir_id").foreignKey(Director.id)
	val movieId = integer("mov_id").foreignKey(Movie.id)

	companion object {
		class MovieDirectionTableDefTableDefAlias(override val baseTable: TableBase, override val aliasName: String) : MovieDirectionTableDef(), AliasTable
	}

	override fun alias(alias: String): MovieDirectionTableDef {
		return MovieDirectionTableDefTableDefAlias(this, alias)
	}
}


object Director : DirectorTableDef()

sealed class DirectorTableDef : Table<DirectorTableDef>("director") {
	val id = integer("dir_id").primaryKey()
	val fName = text("dir_fname").notNull()
	val lName = text("dir_lname").notNull()

	companion object {
		class DirectorTableDefTableDefAlias(override val baseTable: TableBase, override val aliasName: String) : DirectorTableDef(), AliasTable
	}

	override fun alias(alias: String): DirectorTableDef {
		return DirectorTableDefTableDefAlias(this, alias)
	}
}


object Reviewer : ReviewerTableDef()

sealed class ReviewerTableDef : Table<ReviewerTableDef>("reviewer") {
	val id = integer("rev_id").primaryKey()
	val name = text("rev_name").notNull()

	companion object {
		class ReviewerTableDefAlias(override val baseTable: TableBase, override val aliasName: String) : ReviewerTableDef(), AliasTable
	}

	override fun alias(alias: String): ReviewerTableDef {
		return ReviewerTableDefAlias(this, alias)
	}
}

object Rating : RatingTableDef()

sealed class RatingTableDef : Table<RatingTableDef>("rating") {
	val movieId = integer("mov_id").foreignKey(Movie.id)
	val reviewerId = integer("rev_id").foreignKey(Reviewer.id)
	val stars = integer("rev_stars").notNull()
	val numRatings = integer("num_o_ratings").notNull()

	companion object {
		class RatingTableDefAlias(override val baseTable: TableBase, override val aliasName: String) : RatingTableDef(), AliasTable
	}

	override fun alias(alias: String): RatingTableDef {
		return RatingTableDefAlias(this, alias)
	}
}


object Genre : GenreTableDef()

sealed class GenreTableDef : Table<GenreTableDef>("genres") {
	val id = integer("gen_id").primaryKey()
	val title = text("gen_title").notNull()

	companion object {
		class GenreTableDefAlias(override val baseTable: TableBase, override val aliasName: String) : GenreTableDef(), AliasTable
	}

	override fun alias(alias: String): GenreTableDef {
		return GenreTableDefAlias(this, alias)
	}
}

object MovieGenre : MovieGenreTableDef()

sealed class MovieGenreTableDef : Table<MovieGenreTableDef>("movie_genres") {
	val movieId = integer("mov_id").foreignKey(Movie.id)
	val genreId = integer("gen_id").foreignKey(Genre.id)

	companion object {
		class MovieGenreTableDefAlias(override val baseTable: TableBase, override val aliasName: String) : MovieGenreTableDef(), AliasTable
	}

	override fun alias(alias: String): MovieGenreTableDef {
		return MovieGenreTableDefAlias(this, alias)
	}
}