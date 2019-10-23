package org.arnoid.string.processor

interface StringProcessorValueProvider {
    fun get(key: String): String
    fun set(key: String, value: String)
}