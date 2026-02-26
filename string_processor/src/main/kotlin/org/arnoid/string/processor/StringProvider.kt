package org.arnoid.string.processor

/**
 * Interface for providing and storing string values used during processing. Implementations define
 * how variables are resolved and persisted.
 */
interface StringProvider {
    /**
     * Retrieves a value associated with the given [key].
     * @param key The identifier for the string value.
     * @return The string value associated with the key, or a default value if not found.
     */
    fun get(key: String): String

    /**
     * Stores a [value] for a given [key].
     * @param key The identifier for the string value.
     * @param value The string value to store.
     */
    fun set(key: String, value: String)

    /**
     * Checks if a value is associated with the given [key].
     * @param key The identifier for the string value.
     * @return True if the key is found, false otherwise.
     */
    fun has(key: String): Boolean
}
