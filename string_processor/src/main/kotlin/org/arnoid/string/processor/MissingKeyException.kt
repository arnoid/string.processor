package org.arnoid.string.processor

/**
 * Exception thrown when a requested key is missing from the [StringProvider] in strict mode.
 *
 * @property key The key that was missing.
 */
class MissingKeyException(val key: String) : RuntimeException("Key '$key' not found in provider")
