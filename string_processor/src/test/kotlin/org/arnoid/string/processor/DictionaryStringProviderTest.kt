package org.arnoid.string.processor

import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`

internal class DictionaryStringProviderTest {

    private lateinit var stringProvider: DictionaryStringProvider
    private lateinit var mockDictionary: MutableMap<String, String>

    @Before
    fun before() {
        mockDictionary = mock()
        stringProvider = DictionaryStringProvider(mockDictionary, "DEFAULT_EMPTY_VALUE")
    }

    @Test
    fun testClear() {
        stringProvider.clear()
        verify(mockDictionary).clear()
        verifyNoMoreInteractions(mockDictionary)
    }

    @Test
    fun testGetHasValue() {
        val key = "key"
        val value = "value"

        `when`(mockDictionary[eq(key)]).thenReturn(value)

        assertEquals(value, stringProvider.get(key))
        verify(mockDictionary)[eq(key)]
        verifyNoMoreInteractions(mockDictionary)
    }

    @Test
    fun testGetHasNoValue() {
        val key = "key"

        assertEquals(DEFAULT_EMPTY_VALUE, stringProvider.get(key))
        verify(mockDictionary)[eq(key)]
        verifyNoMoreInteractions(mockDictionary)
    }

    @Test
    fun testSet() {
        val key = "key"
        val value = "value"

        stringProvider.set(key, value)
        verify(mockDictionary)[eq(key)] = eq(value)
        verifyNoMoreInteractions(mockDictionary)
    }

    private companion object {
        private const val DEFAULT_EMPTY_VALUE = "DEFAULT_EMPTY_VALUE"
    }
}