package v2

import de.ruegnerlukas.sqldsl2.grammar.Table
import de.ruegnerlukas.sqldsl2.grammar.aggregate.CountAllAggFunction
import de.ruegnerlukas.sqldsl2.grammar.aggregate.MaxAggFunction
import de.ruegnerlukas.sqldsl2.grammar.aggregate.MinAggFunction
import de.ruegnerlukas.sqldsl2.grammar.expr.AndChainCondition
import de.ruegnerlukas.sqldsl2.grammar.expr.AndCondition
import de.ruegnerlukas.sqldsl2.grammar.expr.Column
import de.ruegnerlukas.sqldsl2.grammar.expr.EqualCondition
import de.ruegnerlukas.sqldsl2.grammar.expr.GreaterThanCondition
import de.ruegnerlukas.sqldsl2.grammar.expr.InSubQueryCondition
import de.ruegnerlukas.sqldsl2.grammar.expr.IntLiteral
import de.ruegnerlukas.sqldsl2.grammar.expr.NotEqualCondition
import de.ruegnerlukas.sqldsl2.grammar.expr.NotInSubQueryCondition
import de.ruegnerlukas.sqldsl2.grammar.expr.IsNotNullCondition
import de.ruegnerlukas.sqldsl2.grammar.expr.NullLiteral
import de.ruegnerlukas.sqldsl2.grammar.expr.QualifiedColumn
import de.ruegnerlukas.sqldsl2.grammar.expr.StringLiteral
import de.ruegnerlukas.sqldsl2.grammar.expr.SubQueryLiteral
import de.ruegnerlukas.sqldsl2.grammar.from.AliasTableFromExpression
import de.ruegnerlukas.sqldsl2.grammar.from.FromStatement
import de.ruegnerlukas.sqldsl2.grammar.groupby.GroupByStatement
import de.ruegnerlukas.sqldsl2.grammar.having.HavingStatement
import de.ruegnerlukas.sqldsl2.grammar.join.ConditionJoinConstraint
import de.ruegnerlukas.sqldsl2.grammar.join.JoinClause
import de.ruegnerlukas.sqldsl2.grammar.join.JoinOp
import de.ruegnerlukas.sqldsl2.grammar.orderby.Dir
import de.ruegnerlukas.sqldsl2.grammar.orderby.OrderByExpression
import de.ruegnerlukas.sqldsl2.grammar.orderby.OrderByStatement
import de.ruegnerlukas.sqldsl2.grammar.query.QueryStatement
import de.ruegnerlukas.sqldsl2.grammar.select.AliasSelectExpression
import de.ruegnerlukas.sqldsl2.grammar.select.AllSelectExpression
import de.ruegnerlukas.sqldsl2.grammar.select.SelectDistinctStatement
import de.ruegnerlukas.sqldsl2.grammar.select.SelectStatement
import de.ruegnerlukas.sqldsl2.grammar.where.WhereStatement

/**
 * https://www.w3resource.com/sql-exercises/movie-database-exercise/subqueries-exercises-on-movie-database.php
 */
class MovieDbSubQueriesTest {

	/**
	 * SELECT
	 * FROM actor
	 * WHERE act_id IN(
	 * 		SELECT act_id
	 * 		FROM movie_cast
	 * 		WHERE mov_id IN (
	 * 			SELECT mov_id
	 * 			FROM movie
	 * 			WHERE mov_title='Annie Hall'
	 * 		)
	 * );
	 */
	private fun query1() = QueryStatement(
		select = SelectStatement(
			listOf(
				AllSelectExpression()
			)
		),
		from = FromStatement(
			listOf(
				Table("actor")
			)
		),
		where = WhereStatement(
			InSubQueryCondition(
				Column("act_id"),
				QueryStatement(
					select = SelectStatement(
						listOf(
							Column("act_id")
						)
					),
					from = FromStatement(
						listOf(
							Table("movie_cast")
						)
					),
					where = WhereStatement(
						InSubQueryCondition(
							Column("mov_id"),
							QueryStatement(
								select = SelectStatement(
									listOf(
										Column("mov_id")
									)
								),
								from = FromStatement(
									listOf(
										Table("movie")
									)
								),
								where = WhereStatement(
									EqualCondition(
										Column("movie_title"),
										StringLiteral("Annie Hall")
									)
								)
							)
						)
					)
				)
			)
		)
	)


	/**
	 * SELECT dir_fname, dir_lname
	 * FROM  director
	 * WHERE dir_id in (
	 * 		SELECT dir_id
	 * 		FROM movie_direction
	 * 		WHERE mov_id in(
	 * 			SELECT mov_id
	 * 			FROM movie_cast WHERE role = ANY (
	 * 				SELECT role
	 * 				FROM movie_cast
	 * 				WHERE mov_id IN (
	 * 					SELECT  mov_id
	 * 					FROM movie
	 * 					WHERE mov_title='Eyes Wide Shut'
	 * 				)
	 * 			)
	 * 		)
	 * 	);
	 */
	private fun query2(): QueryStatement? {
		// TODO
		return null
	}


	/**
	 * SELECT
	 *		mov_title,
	 *		mov_year,
	 *		mov_time,
	 *		mov_dt_rel AS Date_of_Release,
	 *		mov_rel_country AS Releasing_Country
	 * FROM movie
	 * WHERE mov_rel_country<>'UK';
	 */
	private fun query3() = QueryStatement(
		select = SelectStatement(
			listOf(
				Column("mov_title"),
				Column("mov_year"),
				Column("mov_time"),
				AliasSelectExpression(Column("mov_dt_rel"), "date_of_release"),
				AliasSelectExpression(Column("mov_rel_country"), "releasing_country"),
			)
		),
		from = FromStatement(
			listOf(
				Table("movie")
			)
		),
		where = WhereStatement(
			NotEqualCondition(
				Column("mov_rel_country"),
				StringLiteral("UK")
			)
		)
	)


	/**
	 * SELECT
	 * 		mov_title,
	 * 		mov_year,
	 * 		mov_dt_rel,
	 * 		dir_fname,
	 * 		dir_lname,
	 * 		act_fname,
	 * 		act_lname
	 * FROM
	 * 		movie a,
	 * 		movie_direction b,
	 * 		director c,
	 * 		rating d,
	 * 		reviewer e,
	 * 		actor f,
	 * 		movie_cast g
	 * WHERE a.mov_id=b.mov_id
	 * 		AND b.dir_id = c.dir_id
	 *  	AND a.mov_id = d.mov_id
	 *   	AND d.rev_id = e.rev_id
	 *    	AND a.mov_id = g.mov_id
	 *      AND g.act_id = f.act_id
	 * 	 	AND e.rev_name IS NULL;
	 */
	private fun query4() = QueryStatement(
		select = SelectStatement(
			listOf(
				Column("mov_title"),
				Column("mov_year"),
				Column("mov_dt_rel"),
				Column("dir_fname"),
				Column("dir_lname"),
				Column("act_fname"),
				Column("act_lname"),
			)
		),
		from = FromStatement(
			listOf(
				AliasTableFromExpression(Table("movie"), "a"),
				AliasTableFromExpression(Table("movie_direction"), "b"),
				AliasTableFromExpression(Table("director"), "c"),
				AliasTableFromExpression(Table("rating"), "d"),
				AliasTableFromExpression(Table("reviewer"), "e"),
				AliasTableFromExpression(Table("actor"), "f"),
				AliasTableFromExpression(Table("movie_cast"), "g")
			)
		),
		where = WhereStatement(
			AndChainCondition(
				listOf(
					EqualCondition(
						QualifiedColumn(Table("a"), Column("mov_id")),
						QualifiedColumn(Table("b"), Column("mov_id"))
					),
					EqualCondition(
						QualifiedColumn(Table("b"), Column("dir_id")),
						QualifiedColumn(Table("c"), Column("dir_id"))
					),
					EqualCondition(
						QualifiedColumn(Table("a"), Column("mov_id")),
						QualifiedColumn(Table("d"), Column("mov_id"))
					),
					EqualCondition(
						QualifiedColumn(Table("d"), Column("rev_id")),
						QualifiedColumn(Table("e"), Column("rev_id"))
					),
					EqualCondition(
						QualifiedColumn(Table("a"), Column("mov_id")),
						QualifiedColumn(Table("g"), Column("mov_id"))
					),
					EqualCondition(
						QualifiedColumn(Table("g"), Column("act_id")),
						QualifiedColumn(Table("f"), Column("act_id"))
					),
					EqualCondition(
						QualifiedColumn(Table("e"), Column("rev_name")),
						NullLiteral()
					)
				)
			)
		)
	)


	/**
	 * SELECT mov_title
	 * FROM movie
	 * WHERE mov_id=(
	 * 		SELECT mov_id
	 * 		FROM movie_direction
	 * 		WHERE dir_id=(
	 * 			SELECT dir_id
	 * 			FROM director
	 * 			WHERE dir_fname='Woody' AND dir_lname='Allen'
	 * 		)
	 * );
	 */
	private fun query5(): QueryStatement? {
		// TODO
		return null
	}


	/**
	 * SELECT DISTINCT mov_year
	 * FROM movie
	 * WHERE mov_id IN (
	 * 		SELECT mov_id
	 * 		FROM rating
	 * 		WHERE rev_stars>3
	 * )
	 * ORDER BY mov_year;
	 */
	private fun query6() = QueryStatement(
		select = SelectDistinctStatement(
			listOf(
				Column("mov_year")
			)
		),
		from = FromStatement(
			listOf(
				Table("movie")
			)
		),
		where = WhereStatement(
			InSubQueryCondition(
				Column("mov_id"),
				QueryStatement(
					select = SelectStatement(
						listOf(
							Column("mov_id")
						)
					),
					from = FromStatement(
						listOf(
							Table("rating")
						)
					),
					where = WhereStatement(
						GreaterThanCondition(
							Column("rev_stars"),
							IntLiteral(3)
						)
					)
				)
			)
		),
		orderBy = OrderByStatement(
			listOf(
				OrderByExpression(Column("mov_year"), Dir.ASC)
			)
		)
	)


	/**
	 * SELECT DISTINCT mov_title
	 * FROM movie
	 * WHERE mov_id IN (
	 * 		SELECT mov_id
	 * 		FROM movie
	 * 		WHERE mov_id NOT IN (
	 * 			SELECT mov_id FROM Rating
	 * 		)
	 * );
	 */
	private fun query7() = QueryStatement(
		select = SelectDistinctStatement(
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
			InSubQueryCondition(
				Column("mov_id"),
				QueryStatement(
					select = SelectStatement(
						listOf(
							Column("mov_id")
						)
					),
					from = FromStatement(
						listOf(
							Table("movie")
						)
					),
					where = WhereStatement(
						NotInSubQueryCondition(
							Column("mov_id"),
							QueryStatement(
								select = SelectDistinctStatement(
									listOf(
										Column("mov_id")
									)
								),
								from = FromStatement(
									listOf(
										Table("rating")
									)
								)
							)
						)
					)
				)
			)
		),
		orderBy = OrderByStatement(
			listOf(
				OrderByExpression(Column("mov_year"), Dir.ASC)
			)
		)
	)


	/**
	 * SELECT DISTINCT rev_name
	 * FROM reviewer
	 * WHERE rev_id IN (
	 * 		SELECT rev_id
	 * 		FROM rating
	 * 		WHERE rev_stars IS NULL
	 * );
	 */
	private fun query8(): QueryStatement? {
		// TODO
		return null
	}


	/**
	 * SELECT
	 * 		rev_name,
	 * 		mov_title,
	 * 		rev_stars
	 * FROM
	 * 		reviewer,
	 * 		rating,
	 * 		movie
	 * WHERE reviewer.rev_id = rating.rev_id
	 * 		AND movie.mov_id=rating.mov_id
	 *      AND reviewer.rev_name IS NOT NULL
	 *      AND rating.rev_stars IS NOT NULL
	 * ORDER BY rev_name, mov_title, rev_stars;
	 */
	private fun query9(): QueryStatement? {
		// TODO
		return null
	}


	/**
	 * SELECT
	 * 		rev_name,
	 * 		mov_title
	 * FROM
	 * 		reviewer,
	 * 		movie,
	 * 		rating,
	 * 		rating r2
	 * WHERE rating.mov_id=movie.mov_id
	 *   AND reviewer.rev_id = rating.rev_ID
	 *   AND rating.rev_id = r2.rev_id
	 * GROUP BY rev_name, mov_title
	 * HAVING count(*) > 1;
	 */
	private fun query10() = QueryStatement(
		select = SelectDistinctStatement(
			listOf(
				Column("rev_name"),
				Column("mov_title")
			)
		),
		from = FromStatement(
			listOf(
				Table("reviewer"),
				Table("movie"),
				Table("rating"),
				AliasTableFromExpression(Table("rating"), "r2")
			)
		),
		where = WhereStatement(
			AndChainCondition(
				listOf(
					EqualCondition(
						QualifiedColumn(Table("rating"), Column("mov_id")),
						QualifiedColumn(Table("movie"), Column("mov_id"))
					),
					EqualCondition(
						QualifiedColumn(Table("reviewer"), Column("rev_id")),
						QualifiedColumn(Table("rating"), Column("rev_id"))
					),
					EqualCondition(
						QualifiedColumn(Table("rating"), Column("rev_id")),
						QualifiedColumn(Table("r2"), Column("rev_id"))
					)
				)
			)
		),
		groupBy = GroupByStatement(
			listOf(
				Column("rev_name"),
				Column("mov_title")
			)
		),
		having = HavingStatement(
			GreaterThanCondition(
				CountAllAggFunction(),
				IntLiteral(1)
			)
		)
	)


	/**
	 * SELECT mov_title, MAX(rev_stars)
	 * FROM movie, rating
	 * WHERE movie.mov_id=rating.mov_id
	 * 		AND rating.rev_stars IS NOT NULL
	 * GROUP BY mov_title
	 * ORDER BY mov_title;
	 */
	private fun query11() = QueryStatement(
		select = SelectStatement(
			listOf(
				Column("mov_title"),
				MaxAggFunction(Column("rev_stars"))
			)
		),
		from = FromStatement(
			listOf(
				Table("movie"),
				Table("rating")
			)
		),
		where = WhereStatement(
			AndCondition(
				EqualCondition(
					QualifiedColumn(Table("movie"), Column("mov_id")),
					QualifiedColumn(Table("rating"), Column("mov_id")),
				),
				IsNotNullCondition(
					QualifiedColumn(Table("rating"), Column("rev_stars")),
				)
			)
		),
		groupBy = GroupByStatement(
			listOf(
				Column("mov_title")
			)
		),
		orderBy = OrderByStatement(
			listOf(
				OrderByExpression(Column("mov_title"), Dir.ASC)
			)
		)
	)


	/**
	 * SELECT DISTINCT reviewer.rev_name
	 * FROM reviewer, rating, movie
	 * WHERE reviewer.rev_id = rating.rev_id
	 * 		AND movie.mov_id = rating.mov_id
	 * 		AND movie.mov_title = 'American Beauty';
	 */
	private fun query12(): QueryStatement? {
		// TODO
		return null
	}


	/**
	 * SELECT movie.mov_title
	 * FROM movie
	 * WHERE movie.mov_id IN(
	 * 		SELECT mov_id
	 * 		FROM rating
	 * 		WHERE rev_id NOT IN (
	 * 			SELECT rev_id
	 * 			FROM reviewer
	 * 			WHERE rev_name='Paul Monks'
	 * 		)
	 * );
	 */
	private fun query13(): QueryStatement? {
		// TODO
		return null
	}


	/**
	 * SELECT
	 * 		reviewer.rev_name,
	 * 		movie.mov_title,
	 * 		rating.rev_stars
	 * FROM reviewer, movie, rating
	 * WHERE rating.rev_stars = (
	 * 			SELECT MIN(rating.rev_stars)
	 * 			FROM rating
	 * 		)
	 * 		AND rating.rev_id = reviewer.rev_id
	 * 		AND rating.mov_id = movie.mov_id;
	 */
	private fun query14() = QueryStatement(
		select = SelectStatement(
			listOf(
				QualifiedColumn(Table("reviewer"), Column("rev_name")),
				QualifiedColumn(Table("movie"), Column("mov_title")),
				QualifiedColumn(Table("rating"), Column("rev_stars"))
			)
		),
		from = FromStatement(
			listOf(
				Table("reviewer"),
				Table("movie"),
				Table("rating")
			)
		),
		where = WhereStatement(
			AndChainCondition(
				listOf(
					EqualCondition(
						QualifiedColumn(Table("rating"), Column("rev_stars")),
						SubQueryLiteral(
							QueryStatement(
								select = SelectStatement(
									listOf(
										MinAggFunction(QualifiedColumn(Table("rating"), Column("rev_stars")))
									)
								),
								from = FromStatement(
									listOf(
										Table("rating")
									)
								)
							)
						)
					),
					EqualCondition(
						QualifiedColumn(Table("rating"), Column("rev_id")),
						QualifiedColumn(Table("reviewer"), Column("rev_id"))
					),
					EqualCondition(
						QualifiedColumn(Table("rating"), Column("mov_id")),
						QualifiedColumn(Table("movie"), Column("mov_id"))
					)
				)
			)
		)
	)


	/**
	 * SELECT mov_title
	 * FROM movie
	 * 		JOIN  movie_direction
	 *  	ON movie.mov_id=movie_direction.mov_id
	 * 			JOIN  director
	 *  		ON movie_direction.dir_id=director.dir_id
	 * WHERE dir_fname = 'James' AND dir_lname='Cameron';
	 */
	private fun query15() = QueryStatement(
		select = SelectStatement(
			listOf(
				Column("mov_title")
			)
		),
		from = FromStatement(
			listOf(
				JoinClause(
					JoinOp.LEFT,
					JoinClause(
						JoinOp.LEFT,
						Table("movie"),
						Table("movie_direction"),
						ConditionJoinConstraint(
							EqualCondition(
								QualifiedColumn(Table("movie"), Column("mov_id")),
								QualifiedColumn(Table("movie_direction"), Column("mov_id"))
							)
						)
					),
					Table("director"),
					ConditionJoinConstraint(
						EqualCondition(
							QualifiedColumn(Table("movie_direction"), Column("dir_id")),
							QualifiedColumn(Table("director"), Column("dir_id"))
						)
					)
				)
			)
		),
		where = WhereStatement(
			AndCondition(
				EqualCondition(
					Column("dir_fname"),
					StringLiteral("James")
				),
				EqualCondition(
					Column("dir_lname"),
					StringLiteral("Cameron")
				)
			)
		)
	)


	/**
	 * SELECT mov_title
	 * FROM movie
	 * WHERE mov_id IN (
	 * 		SELECT mov_id
	 * 		FROM movie_cast
	 * 		WHERE act_id IN (
	 * 			SELECT act_id
	 * 			FROM actor
	 * 			WHERE act_id IN (
	 * 				SELECT act_id
	 * 				FROM movie_cast
	 * 				GROUP BY act_id
	 * 				HAVING COUNT(act_id)>1
	 * 			)
	 * 		)
	 * 	);
	 */
	private fun query16(): QueryStatement? {
		// TODO
		return null
	}

}