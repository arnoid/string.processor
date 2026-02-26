package org.arnoid.string.processor

/** A decorator for [StringProvider] that throws [MissingKeyException] if a key is not found. */
class StrictStringProvider(private val delegate: StringProvider) : StringProvider {
    override fun get(key: String): String {
        if (!has(key)) {
            throw MissingKeyException(key)
        }
        return delegate.get(key)
    }

    override fun set(key: String, value: String) {
        delegate.set(key, value)
    }

    override fun has(key: String): Boolean {
        return delegate.has(key)
    }
}
