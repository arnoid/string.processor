package org.arnoid.string.processor

abstract class AbstractResourceBackedStringProvider(
        private val provideResourcePath: (key: String) -> String,
        private val extractResourceValues: String
) : StringProvider {
    val cachedValues = HashMap<String, CachedValue<*>>()

    
}

open class CachedValue<T>(
        open val value: T,
        val type: CachedValueType
)

data class StringCachedValue(override val value: String) : CachedValue<String>(value, CachedValueType.STRING)
data class StringArrayCachedValue(override val value: Array<String>) : CachedValue<Array<String>>(value, CachedValueType.STRING_ARRAY) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as StringArrayCachedValue

        if (!value.contentEquals(other.value)) return false

        return true
    }

    override fun hashCode(): Int {
        return value.contentHashCode()
    }
}

enum class CachedValueType {
    STRING,
    STRING_ARRAY
}