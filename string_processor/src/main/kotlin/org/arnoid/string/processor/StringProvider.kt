package org.arnoid.string.processor

interface StringProvider {
    fun get(key: String): String
    fun set(key: String, value: String)
}