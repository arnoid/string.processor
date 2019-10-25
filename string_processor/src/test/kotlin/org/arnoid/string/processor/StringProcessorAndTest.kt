package org.arnoid.string.processor

import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.mock
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers

class StringProcessorAndTest {

    lateinit var stringProcessor: StringProcessor
    lateinit var stringProviderMock: StringProvider

    @Before
    fun before() {
        stringProcessor = StringProcessor()
        stringProviderMock = mock {
            on { get(ArgumentMatchers.anyString()) } doThrow RuntimeException("This should not happened")
        }
    }

    @Test
    fun testTagAnd() {
        assertEquals(RESULT_STR, stringProcessor.process(INPUT_STR, stringProviderMock))
    }

    companion object {
        const val INPUT_STR = "\$and{true}{true} $$ \$and{true}{false} $$ \$and{10}{10} $$ \$and{a}{b} $$ \$and{b}{a}"
        const val RESULT_STR = "true \$ false \$ false \$ false \$ false"
    }
}