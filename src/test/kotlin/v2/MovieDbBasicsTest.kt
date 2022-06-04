package v2

import de.ruegnerlukas.sqldsl2.generators.generic.GenericGeneratorContext
import de.ruegnerlukas.sqldsl2.generators.generic.GenericQueryGenerator
import de.ruegnerlukas.sqldsl2.grammar.expr.AndCondition
import de.ruegnerlukas.sqldsl2.grammar.expr.EqualCondition
import de.ruegnerlukas.sqldsl2.grammar.expr.GreaterOrEqualThanCondition
import de.ruegnerlukas.sqldsl2.grammar.expr.InListCondition
import de.ruegnerlukas.sqldsl2.grammar.expr.InSubQueryCondition
import de.ruegnerlukas.sqldsl2.grammar.expr.IntLiteral
import de.ruegnerlukas.sqldsl2.grammar.expr.IsNotNullCondition
import de.ruegnerlukas.sqldsl2.grammar.expr.LessThanCondition
import de.ruegnerlukas.sqldsl2.grammar.expr.LikeCondition
import de.ruegnerlukas.sqldsl2.grammar.expr.ListLiteral
import de.ruegnerlukas.sqldsl2.grammar.expr.NotCondition
import de.ruegnerlukas.sqldsl2.grammar.expr.StringLiteral
import de.ruegnerlukas.sqldsl2.grammar.from.FromStatement
import de.ruegnerlukas.sqldsl2.grammar.orderby.Dir
import de.ruegnerlukas.sqldsl2.grammar.orderby.OrderByExpression
import de.ruegnerlukas.sqldsl2.grammar.orderby.OrderByStatement
import de.ruegnerlukas.sqldsl2.grammar.query.QueryStatement
import de.ruegnerlukas.sqldsl2.grammar.select.SelectStatement
import de.ruegnerlukas.sqldsl2.grammar.where.WhereStatement

fun main() {
	MovieDbBasicsTest().all()
}


/**
 * https://www.w3resource.com/sql-exercises/movie-database-exercise/basic-exercises-on-movie-database.php
 */
class MovieDbBasicsTest {

	private val generator = GenericQueryGenerator(GenericGeneratorContext())

	fun all() {
		println()
		printQuery("1", query1())
		printQuery("2", query2())
		printQuery("3", query3())
		printQuery("4", query4())
		printQuery("5", query5())
		printQuery("6", query6())
		printQuery("7", query7())
		printQuery("8", query8())
		printQuery("9", query9())
		printQuery("10", query10())
	}


	private fun printQuery(name: String, query: QueryStatement?) {
		println("QUERY $name:")
		if (query != null) {
			val str = generator.buildString(query)
			println(str)
		} else {
			println("-")
		}
		println()
	}


	/**
	 * SELECT mov_title, mov_year
	 * FROM movie;
	 */
	private fun query1() = QueryStatement(
		select = SelectStatement(
			listOf(
				Movie.title,
				Movie.year
			)
		),
		from = FromStatement(
			listOf(
				Movie
			)
		)
	)


	/**
	 * SELECT mov_year
	 * FROM movie
	 * WHERE mov_title='American Beauty';
	 */
	private fun query2() = QueryStatement(
		select = SelectStatement(
			listOf(
				Movie.year
			)
		),
		from = FromStatement(
			listOf(
				Movie
			)
		),
		where = WhereStatement(
			EqualCondition(Movie.title, StringLiteral("American Beauty"))
		)
	)


	/**
	 * SELECT mov_title
	 * FROM movie
	 * WHERE mov_year=1999;
	 */
	private fun query3() = QueryStatement(
		select = SelectStatement(
			listOf(
				Movie.title
			)
		),
		from = FromStatement(
			listOf(
				Movie
			)
		),
		where = WhereStatement(
			EqualCondition(Movie.year, IntLiteral(1999))
		)
	)


	/**
	 * SELECT mov_title
	 * FROM movie
	 * WHERE mov_year<1998;
	 */
	private fun query4() = QueryStatement(
		select = SelectStatement(
			listOf(
				Movie.title
			)
		),
		from = FromStatement(
			listOf(
				Movie
			)
		),
		where = WhereStatement(
			LessThanCondition(Movie.year, IntLiteral(1999))
		)
	)


	/**
	 * SELECT reviewer.rev_name
	 * FROM reviewer
	 * UNION
	 * (SELECT movie.mov_title
	 * FROM movie);
	 */
	private fun query5(): QueryStatement? {
		// TODO
		return null
	}


	/**
	 * SELECT reviewer.rev_name
	 * FROM reviewer, rating
	 * WHERE rating.rev_id = reviewer.rev_id
	 * AND rating.rev_stars>=7
	 * AND reviewer.rev_name IS NOT NULL;
	 */
	private fun query6() = QueryStatement(
		select = SelectStatement(
			listOf(
				Reviewer.name
			)
		),
		from = FromStatement(
			listOf(
				Reviewer,
				Rating
			)
		),
		where = WhereStatement(
			AndCondition(
				EqualCondition(
					Rating.reviewerId,
					Reviewer.id
				),
				AndCondition(
					GreaterOrEqualThanCondition(
						Rating.stars,
						IntLiteral(7)
					),
					IsNotNullCondition(
						Reviewer.name
					)
				)
			)
		)
	)


	/**
	 * SELECT mov_title
	 * FROM movie
	 * WHERE mov_id NOT IN (SELECT mov_id FROM rating);
	 */
	private fun query7() = QueryStatement(
		select = SelectStatement(
			listOf(
				Movie.title
			)
		),
		from = FromStatement(
			listOf(
				Movie
			)
		),
		where = WhereStatement(
			NotCondition(
				InSubQueryCondition(
					Movie.id,
					QueryStatement(
						SelectStatement(
							listOf(
								Rating.movieId
							)
						),
						FromStatement(
							listOf(
								Rating
							)
						)
					)
				)
			)
		)
	)


	/**
	 * SELECT mov_title
	 * FROM movie
	 * WHERE mov_id in (905, 907, 917);
	 */
	private fun query8() = QueryStatement(
		select = SelectStatement(
			listOf(
				Movie.title
			)
		),
		from = FromStatement(
			listOf(
				Movie
			)
		),
		where = WhereStatement(
			InListCondition(
				Movie.id,
				ListLiteral(
					listOf(
						IntLiteral(905),
						IntLiteral(907),
						IntLiteral(917)
					)
				)
			)
		)
	)


	/**
	 * SELECT mov_id, mov_title, mov_year
	 * FROM movie
	 * WHERE mov_title LIKE '%Boogie%Nights%'
	 * ORDER BY mov_year ASC;
	 */
	private fun query9() = QueryStatement(
		select = SelectStatement(
			listOf(
				Movie.id,
				Movie.title,
				Movie.year
			)
		),
		from = FromStatement(
			listOf(
				Movie
			)
		),
		where = WhereStatement(
			LikeCondition(
				Movie.title,
				"%Boogie%Nights%"
			)
		),
		orderBy = OrderByStatement(
			listOf(
				OrderByExpression(Movie.year, Dir.ASC)
			)
		)
	)


	/**
	 * SELECT act_id
	 * FROM actor
	 * WHERE act_fname='Woody'
	 * AND act_lname='Allen';
	 */
	private fun query10() = QueryStatement(
		select = SelectStatement(
			listOf(
				Actor.id
			)
		),
		from = FromStatement(
			listOf(
				Actor
			)
		),
		where = WhereStatement(
			AndCondition(
				EqualCondition(
					Actor.fName,
					StringLiteral("Woddy")
				),
				EqualCondition(
					Actor.lName,
					StringLiteral("Allen")
				)
			)
		),
	)

}



















