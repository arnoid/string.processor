package org.arnoid.string.processor

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString

class StringProcessorArrayRandomTest {

    lateinit var stringProcessor: StringProcessor
    lateinit var stringProviderMock: StringProvider

    @Before
    fun before() {
        stringProcessor = StringProcessor()
        stringProviderMock = mock {
            on { get(eq("a")) } doReturn "A"
            on { get(eq("b")) } doReturn "B"
        }
    }

    @Test
    fun testTagRnd() {
        val processResult = stringProcessor.process("\$rnd{a|b|c|d}", stringProviderMock)
        assertTrue(processResult == "a" ||
                processResult == "b" ||
                processResult == "c" ||
                processResult == "d")
    }

    @Test
    fun testTagRndSingle() {
        val processResult = stringProcessor.process("\$rnd{a}", stringProviderMock)
        assertEquals("a", processResult)
    }

    @Test
    fun testTagRndEmpty() {
        val processResult = stringProcessor.process("\$rnd{}", stringProviderMock)
        assertTrue(processResult.isEmpty())
    }

    @Test
    fun testTagRndDeep() {
        val processResult = stringProcessor.process("\$rnd{\${a}|\${b}}", stringProviderMock)
        assertTrue(processResult == "A" ||
                processResult == "B")

        verify(stringProviderMock).get(anyString())
    }
}