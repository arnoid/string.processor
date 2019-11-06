package org.arnoid.string.processor

open class DictionaryStringProvider(
        val dictionary: MutableMap<String, String> = HashMap(),
        val emptyValue: String = DEFAULT_EMPTY_VALUE
) : StringProvider {
    fun clear() {
        dictionary.clear()
    }

    override fun get(key: String): String {
        return dictionary[key] ?: emptyValue
    }

    override fun set(key: String, value: String) {
        dictionary[key] = value
    }

    companion object {
        const val DEFAULT_EMPTY_VALUE = ""
    }
}