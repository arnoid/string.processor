package org.arnoid.string.processor

import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.mock
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers

class StringProcessorNotTest {

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
    fun testTagNot() {
        assertEquals(RESULT_STR, stringProcessor.process(INPUT_STR, valueProviderMock))
    }

    companion object {
        const val INPUT_STR = "\$not{true} $$ \$not{false} $$ \$not{10} $$ \$not{a}"
        const val RESULT_STR = "false \$ true \$ true \$ true"
    }
}