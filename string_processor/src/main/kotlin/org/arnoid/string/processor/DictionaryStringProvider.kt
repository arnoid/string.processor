package org.arnoid.string.processor

/**
 * A basic implementation of [StringProvider] backed by a [MutableMap].
 *
 * @property dictionary The underlying map storing key-value pairs.
 * @property emptyValue The value returned by [get] if a key is not found. Defaults to
 * [DEFAULT_EMPTY_VALUE].
 */
open class DictionaryStringProvider(
        private val dictionary: MutableMap<String, String> = mutableMapOf(),
        private val emptyValue: String = DEFAULT_EMPTY_VALUE
) : StringProvider {

    /** Clears all stored key-value pairs from the dictionary. */
    fun clear() {
        dictionary.clear()
    }

    override fun get(key: String): String {
        return dictionary[key] ?: emptyValue
    }

    override operator fun set(key: String, value: String) {
        dictionary[key] = value
    }

    override fun has(key: String): Boolean {
        return dictionary.containsKey(key)
    }

    companion object {
        fun from(
                vararg pairs: Pair<String, String>,
                emptyValue: String = DEFAULT_EMPTY_VALUE
        ): DictionaryStringProvider {
            return DictionaryStringProvider(mutableMapOf(*pairs), emptyValue)
        }

        const val DEFAULT_EMPTY_VALUE = ""
    }
}
