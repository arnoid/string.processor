package org.arnoid.string.processor

import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.mock
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers

class StringProcessorIfTagTest {

    lateinit var stringProcessor: StringProcessor
    lateinit var valueProviderMock: StringProcessorValueProvider

    @Before
    fun before() {
        stringProcessor = StringProcessor()
        valueProviderMock = mock {
            on { get(ArgumentMatchers.anyString()) } doThrow RuntimeException("This should not happened")
        }
    }

    @Test
    fun testTagIf() {
        assertEquals(RESULT_STR_TRUE, stringProcessor.process(INPUT_STR_TRUE, valueProviderMock))
        assertEquals(RESULT_STR_FALSE, stringProcessor.process(INPUT_STR_FALSE, valueProviderMock))
    }

    companion object {
        const val INPUT_STR_TRUE = "\$if{true} " +
                "" +
                "" +
                "{b} " +
                "" +
                "{c}"
        const val RESULT_STR_TRUE = "b"

        const val INPUT_STR_FALSE = "\$if{false} " +
                "" +
                "" +
                "{b} " +
                "" +
                "{c}"
        const val RESULT_STR_FALSE = "c"
    }
}