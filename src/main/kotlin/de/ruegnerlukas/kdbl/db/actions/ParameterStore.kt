package de.ruegnerlukas.kdbl.db.actions

abstract class ParameterStore<B>(private val placeholders: List<String>) {

	private val parameters: MutableMap<String, Any?> = mutableMapOf()


	/**
	 * Set the values for placeholders via the given map
	 */
	fun parameters(block: (map: MutableMap<String, Any?>) -> Unit): B {
		parameters.putAll(mutableMapOf<String, Any?>().apply(block))
		return provideThis()
	}


	/**
	 * Set the value for a placeholder with the given value (order does not matter)
	 * @param name the name of the placeholder
	 * @param value the value or null
	 */
	fun parameter(name: String, value: Short?) = this.apply { parameters[name] = value }.provideThis()


	/**
	 * Set the value for a placeholder with the given value (order does not matter)
	 * @param name the name of the placeholder
	 * @param value the value or null
	 */
	fun parameter(name: String, value: Int?) = this.apply { parameters[name] = value }.provideThis()


	/**
	 * Set the value for a placeholder with the given value (order does not matter)
	 * @param name the name of the placeholder
	 * @param value the value or null
	 */
	fun parameter(name: String, value: Long?) = this.apply { parameters[name] = value }.provideThis()


	/**
	 * Set the value for a placeholder with the given value (order does not matter)
	 * @param name the name of the placeholder
	 * @param value the value or null
	 */
	fun parameter(name: String, value: Float?) = this.apply { parameters[name] = value }.provideThis()


	/**
	 * Set the value for a placeholder with the given value (order does not matter)
	 * @param name the name of the placeholder
	 * @param value the value or null
	 */
	fun parameter(name: String, value: Double?) = this.apply { parameters[name] = value }.provideThis()


	/**
	 * Set the value for a placeholder with the given value (order does not matter)
	 * @param name the name of the placeholder
	 * @param value the value or null
	 */
	fun parameter(name: String, value: Boolean?) = this.apply { parameters[name] = value }.provideThis()


	/**
	 * Set the value for a placeholder with the given value (order does not matter)
	 * @param name the name of the placeholder
	 * @param value the value or null
	 */
	fun parameter(name: String, value: String?) = this.apply { parameters[name] = value }.provideThis()


	/**
	 * @return the values of the parameters in the correct order
	 */
	protected fun getParameterValues(): List<Any?> {
		val paramList = mutableListOf<Any?>()
		placeholders.forEach {
			if (parameters.containsKey(it)) {
				paramList.add(parameters[it])
			} else {
				throw Exception("No Parameter for placeholder '$it' found.")
			}
		}
		return paramList
	}


	/**
	 * @return an instance of "this"
	 */
	protected abstract fun provideThis(): B

}