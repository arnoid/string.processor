package org.arnoid.string.processor.blocks

import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.mock
import junit.framework.TestCase.assertEquals
import org.arnoid.string.processor.StringProcessor
import org.arnoid.string.processor.StringProvider
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers

class LeqTest {

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
    fun testTagLeq() {
        assertEquals(RESULT_STR, stringProcessor.process(INPUT_STR, stringProviderMock))
    }

    companion object {
        const val INPUT_STR = "\$leq{10}{5} $$ \$leq{5}{10} $$ \$leq{10}{10} $$ \$leq{a}{b} $$ \$leq{b}{a}"
        const val RESULT_STR = "false \$ true \$ true \$ false \$ false"
    }
}