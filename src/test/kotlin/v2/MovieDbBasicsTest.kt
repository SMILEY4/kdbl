package v2

import de.ruegnerlukas.sqldsl2.grammar.Table
import de.ruegnerlukas.sqldsl2.grammar.expr.AndCondition
import de.ruegnerlukas.sqldsl2.grammar.expr.Column
import de.ruegnerlukas.sqldsl2.grammar.expr.EqualCondition
import de.ruegnerlukas.sqldsl2.grammar.expr.GreaterOrEqualThanCondition
import de.ruegnerlukas.sqldsl2.grammar.expr.InListCondition
import de.ruegnerlukas.sqldsl2.grammar.expr.InSubQueryCondition
import de.ruegnerlukas.sqldsl2.grammar.expr.IntLiteral
import de.ruegnerlukas.sqldsl2.grammar.expr.LessThanCondition
import de.ruegnerlukas.sqldsl2.grammar.expr.LikeCondition
import de.ruegnerlukas.sqldsl2.grammar.expr.ListLiteral
import de.ruegnerlukas.sqldsl2.grammar.expr.NotCondition
import de.ruegnerlukas.sqldsl2.grammar.expr.IsNotNullCondition
import de.ruegnerlukas.sqldsl2.grammar.expr.QualifiedColumn
import de.ruegnerlukas.sqldsl2.grammar.expr.StringLiteral
import de.ruegnerlukas.sqldsl2.grammar.from.FromStatement
import de.ruegnerlukas.sqldsl2.grammar.orderby.Dir
import de.ruegnerlukas.sqldsl2.grammar.orderby.OrderByExpression
import de.ruegnerlukas.sqldsl2.grammar.orderby.OrderByStatement
import de.ruegnerlukas.sqldsl2.grammar.query.QueryStatement
import de.ruegnerlukas.sqldsl2.grammar.select.SelectStatement
import de.ruegnerlukas.sqldsl2.grammar.where.WhereStatement

/**
 * https://www.w3resource.com/sql-exercises/movie-database-exercise/basic-exercises-on-movie-database.php
 */
class MovieDbBasicsTest {

	fun all() {
		query1()
		query2()
		query3()
		query4()
		query5()
		query6()
		query7()
		query8()
		query9()
		query10()
	}


	/**
	 * SELECT mov_title, mov_year
	 * FROM movie;
	 */
	private fun query1() = QueryStatement(
		select = SelectStatement(
			listOf(
				Column("mov_title"),
				Column("mov_year")
			)
		),
		from = FromStatement(
			listOf(
				Table("movie")
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
				Column("mov_year"),
			)
		),
		from = FromStatement(
			listOf(
				Table("movie")
			)
		),
		where = WhereStatement(
			EqualCondition(Column("mov_title"), StringLiteral("American Beauty"))
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
				Column("mov_title")
			)
		),
		from = FromStatement(
			listOf(
				Table("movie")
			)
		),
		where = WhereStatement(
			EqualCondition(Column("mov_year"), IntLiteral(1999))
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
				Column("mov_title")
			)
		),
		from = FromStatement(
			listOf(
				Table("movie")
			)
		),
		where = WhereStatement(
			LessThanCondition(Column("mov_year"), IntLiteral(1999))
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
				QualifiedColumn(Table("reviewer"), Column("rev_name"))
			)
		),
		from = FromStatement(
			listOf(
				Table("reviewer"),
				Table("rating")
			)
		),
		where = WhereStatement(
			AndCondition(
				EqualCondition(
					QualifiedColumn(Table("rating"), Column("rev_id")),
					QualifiedColumn(Table("reviewer"), Column("rev_id"))
				),
				AndCondition(
					GreaterOrEqualThanCondition(
						QualifiedColumn(Table("rating"), Column("rev_stars")),
						IntLiteral(7)
					),
					IsNotNullCondition(
						QualifiedColumn(Table("reviewer"), Column("rev_name"))
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
				Column("mov_title")
			)
		),
		from = FromStatement(
			listOf(
				Table("movie")
			)
		),
		where = WhereStatement(
			NotCondition(
				InSubQueryCondition(
					Column("movie_id"),
					QueryStatement(
						SelectStatement(
							listOf(
								Column("mov_id")
							)
						),
						FromStatement(
							listOf(
								Table("rating")
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
				Column("mov_title")
			)
		),
		from = FromStatement(
			listOf(
				Table("movie")
			)
		),
		where = WhereStatement(
			NotCondition(
				InListCondition(
					Column("movie_id"),
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
				Column("mov_id"),
				Column("mov_title"),
				Column("mov_year")
			)
		),
		from = FromStatement(
			listOf(
				Table("movie")
			)
		),
		where = WhereStatement(
			LikeCondition(
				Column("mov_title"),
				"%Boogie%Nights%"
			)
		),
		orderBy = OrderByStatement(
			listOf(
				OrderByExpression(Column("mov_year"), Dir.ASC)
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
				Column("act_id"),
			)
		),
		from = FromStatement(
			listOf(
				Table("actor")
			)
		),
		where = WhereStatement(
			AndCondition(
				EqualCondition(
					Column("act_fname"),
					StringLiteral("Woddy")
				),
				EqualCondition(
					Column("act_lname"),
					StringLiteral("Allen")
				)
			)
		),
	)

}



















