package org.arnoid.string.processor

open class DictionaryStringProvider(
        private val dictionary: MutableMap<String, String> = HashMap(),
        private val emptyValue: String = ""
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
}