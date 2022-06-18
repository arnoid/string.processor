package org.arnoid.string.processor.blocks

import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.mock
import junit.framework.TestCase.assertEquals
import org.arnoid.string.processor.StringProcessor
import org.arnoid.string.processor.StringProvider
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString

class OrTest {

    lateinit var stringProcessor: StringProcessor
    lateinit var stringProviderMock: StringProvider

    @Before
    fun before() {
        stringProcessor = StringProcessor()
        stringProviderMock = mock {
            on { get(anyString()) } doThrow RuntimeException("This should not happened")
        }
    }

    @Test
    fun testTagOr() {
        assertEquals(RESULT_STR, stringProcessor.process(INPUT_STR, stringProviderMock))
    }

    companion object {
        const val INPUT_STR = "\$or{true}{true} $$ \$or{true}{false} $$ \$or{false}{false} $$ \$or{10}{10} $$ \$or{a}{b} $$ \$or{b}{a}"
        const val RESULT_STR = "true \$ true \$ false \$ false \$ false \$ false"
    }
}