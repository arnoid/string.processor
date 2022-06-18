package org.arnoid.string.processor.blocks

import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.mock
import junit.framework.TestCase.assertEquals
import org.arnoid.string.processor.StringProcessor
import org.arnoid.string.processor.StringProvider
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers

class GeqTest {

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
    fun testTagGeq() {
        assertEquals(RESULT_STR, stringProcessor.process(INPUT_STR, stringProviderMock))
    }

    companion object {
        const val INPUT_STR = "\$geq{10}{5} $$ \$geq{5}{10} $$ \$geq{10}{10} $$ \$geq{a}{b} $$ \$geq{b}{a}"
        const val RESULT_STR = "true \$ false \$ true \$ false \$ false"
    }
}