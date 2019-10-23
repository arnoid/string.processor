package org.arnoid.string.processor

import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.mock
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers

class StringProcessorLtTest {

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
    fun testTagLt() {
        assertEquals(RESULT_STR, stringProcessor.process(INPUT_STR, valueProviderMock))
    }

    companion object {
        const val INPUT_STR = "\$lt{10}{5} $$ \$lt{5}{10} $$ \$lt{10}{10} $$ \$lt{a}{b} $$ \$lt{b}{a}"
        const val RESULT_STR = "false \$ true \$ false \$ false \$ false"
    }
}