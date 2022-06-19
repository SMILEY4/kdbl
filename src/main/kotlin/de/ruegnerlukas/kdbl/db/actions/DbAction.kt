package de.ruegnerlukas.kdbl.db.actions

import de.ruegnerlukas.kdbl.db.Database

/**
 * A single generic operation
 * @param db the database to use
 * @param sql the sql-string with possible placeholders
 * @param placeholders the names of the placeholders in the correct order
 * @param T the type of the expected result
 */
abstract class DbAction<T>(protected val db: Database, protected val sql: String, protected val placeholders: List<String>) {

	private val parameters: MutableMap<String, Any> = mutableMapOf()


	/**
	 * Set the value for a placeholder with the given value (order does not matter)
	 * @param name the name of the placeholder
	 * @param value the value
	 */
	fun parameter(name: String, value: Short) = this.apply { parameters[name] = value }


	/**
	 * Set the value for a placeholder with the given value (order does not matter)
	 * @param name the name of the placeholder
	 * @param value the value
	 */
	fun parameter(name: String, value: Int) = this.apply { parameters[name] = value }


	/**
	 * Set the value for a placeholder with the given value (order does not matter)
	 * @param name the name of the placeholder
	 * @param value the value
	 */
	fun parameter(name: String, value: Long) = this.apply { parameters[name] = value }


	/**
	 * Set the value for a placeholder with the given value (order does not matter)
	 * @param name the name of the placeholder
	 * @param value the value
	 */
	fun parameter(name: String, value: Float) = this.apply { parameters[name] = value }


	/**
	 * Set the value for a placeholder with the given value (order does not matter)
	 * @param name the name of the placeholder
	 * @param value the value
	 */
	fun parameter(name: String, value: Double) = this.apply { parameters[name] = value }


	/**
	 * Set the value for a placeholder with the given value (order does not matter)
	 * @param name the name of the placeholder
	 * @param value the value
	 */
	fun parameter(name: String, value: Boolean) = this.apply { parameters[name] = value }


	/**
	 * Set the value for a placeholder with the given value (order does not matter)
	 * @param name the name of the placeholder
	 * @param value the value
	 */
	fun parameter(name: String, value: String) = this.apply { parameters[name] = value }


	/**
	 * Get the values of the parameters in the correct order
	 */
	protected fun getParameterValues(): List<Any> {
		val paramList = mutableListOf<Any>()
		placeholders.forEach {
			paramList.add(parameters[it] ?: throw Exception("No Parameter for placeholder '$it' found."))
		}
		return paramList
	}


	/**
	 * Execute this operation
	 */
	abstract fun execute(): T

}