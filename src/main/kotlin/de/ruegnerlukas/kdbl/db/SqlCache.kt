package de.ruegnerlukas.kdbl.db

class SqlCache(initCache: SqlCache? = null) {

	private val statementCache: MutableMap<String, String> = mutableMapOf()
	private val placeholderCache: MutableMap<String, List<String>> = mutableMapOf()

	init {
		initCache?.let { cache -> put(cache) }
	}


	fun getKeys() = statementCache.keys.toList()


	fun getOrPut(key: String, default: () -> Pair<String, List<String>>): Pair<String, List<String>> {
		if (statementCache.containsKey(key)) {
			return statementCache[key]!! to placeholderCache[key]!!
		} else {
			val pair = default()
			statementCache[key] = pair.first
			placeholderCache[key] = pair.second
			return pair
		}
	}


	fun put(other: SqlCache) {
		other.getKeys().forEach { key ->
			put(key, other.statementCache[key]!!, other.placeholderCache[key]!!)
		}
	}

	fun put(key: String, sql: String, placeholders: List<String>) {
		statementCache[key] = sql
		placeholderCache[key] = placeholders
	}

}