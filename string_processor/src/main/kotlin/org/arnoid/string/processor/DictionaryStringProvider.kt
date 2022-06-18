package org.arnoid.string.processor

open class DictionaryStringProvider(
    private val dictionary: MutableMap<String, String> = HashMap(),
    private val emptyValue: String = DEFAULT_EMPTY_VALUE
) : StringProvider {

    fun clear() {
        dictionary.clear()
    }

    override fun get(key: String): String {
        return dictionary[key] ?: emptyValue
    }

    override operator fun set(key: String, value: String) {
        dictionary[key] = value
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