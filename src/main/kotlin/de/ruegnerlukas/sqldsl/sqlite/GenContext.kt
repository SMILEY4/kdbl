package de.ruegnerlukas.sqldsl.sqlite

enum class GenContext {
	SELECT,
	FROM,
	WHERE,
	GROUP_BY,
	HAVING,
	ORDER_BY,
	LIMIT,
	JOIN_USING,
	UNKNOWN
}