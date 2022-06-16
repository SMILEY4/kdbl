package de.ruegnerlukas.sqldsl.dsl.expression

enum class DataType {

	/**
	 * True or false (~1-Bit)
	 */
	BOOL,

	/**
	 * ~2-byte signed integer
	 */
	SMALLINT,

	/**
	 * ~4-byte signed integer
	 */
	INT,

	/**
	 * ~8-byte signed integer
	 */
	BIGINT,

	/**
	 * ~4-byte floating-point number
	 */
	FLOAT,

	/**
	 * ~8-byte floating-point number
	 */
	DOUBLE,

	/**
	 * Variable-length string
	 */
	TEXT,

	/**
	 * Date with year, month, day
	 */
	DATE,

	/**
	 * Time with hours, minutes, seconds
	 */
	TIME,

	/**
	 * Unix timestamp in milliseconds
	 */
	TIMESTAMP,

}