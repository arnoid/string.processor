package org.arnoid.string.processor

import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.mock
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers

class StringProcessorGtTest {

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
    fun testTagGt() {
        assertEquals(RESULT_STR, stringProcessor.process(INPUT_STR, valueProviderMock))
    }

    companion object {
        const val INPUT_STR = "\$gt{10}{5} $$ \$gt{5}{10} $$ \$gt{10}{10} $$ \$gt{a}{b} $$ \$gt{b}{a}"
        const val RESULT_STR = "true \$ false \$ false \$ false \$ false"
    }
}