package de.ruegnerlukas.sqldsl.db.actions

import de.ruegnerlukas.sqldsl.db.Database

abstract class DbAction<T>(protected val db: Database, protected val sql: String, protected val placeholders: List<String>) {

	private val parameters: MutableMap<String, Any> = mutableMapOf()

	fun parameter(name: String, value: Short) = this.apply { parameters[name] = value }
	fun parameter(name: String, value: Int) = this.apply { parameters[name] = value }
	fun parameter(name: String, value: Long) = this.apply { parameters[name] = value }
	fun parameter(name: String, value: Float) = this.apply { parameters[name] = value }
	fun parameter(name: String, value: Double) = this.apply { parameters[name] = value }
	fun parameter(name: String, value: Boolean) = this.apply { parameters[name] = value }
	fun parameter(name: String, value: String) = this.apply { parameters[name] = value }

	protected fun getParameters(): List<Any> {
		val paramList = mutableListOf<Any>()
		placeholders.forEach {
			paramList.add(parameters[it] ?: throw Exception("No Parameter for placeholder '$it' found."))
		}
		return paramList
	}

	abstract fun execute(): T

}