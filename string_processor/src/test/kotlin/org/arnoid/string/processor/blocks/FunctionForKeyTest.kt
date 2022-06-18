package org.arnoid.string.processor.blocks

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import junit.framework.TestCase.assertEquals
import org.arnoid.string.processor.DictionaryStringProvider
import org.arnoid.string.processor.StringProcessor
import org.arnoid.string.processor.StringProvider
import org.junit.Before
import org.junit.Test

class FunctionForKeyTest {

    lateinit var stringProcessor: StringProcessor
    lateinit var stringProviderMock: StringProvider

    @Before
    fun before() {
        stringProcessor = StringProcessor()
        stringProviderMock = mock {
            on { get(KEY_A) } doReturn VALUE_A
            on { get(FUNCTION_NAME) } doReturn FUNCTION_BODY
        }
    }

    @Test
    fun testTagFunctionForKey() {
        assertEquals("", stringProcessor.process(INPUT_STR, stringProviderMock))
        verify(stringProviderMock).set(FUNCTION_NAME, FUNCTION_BODY)
        verifyNoMoreInteractions((stringProviderMock))
    }

    @Test
    fun testCallFunctionForKey() {
        assertEquals(VALUE_A, stringProcessor.process(INPUT_STR_WITH_FUNCTION, stringProviderMock))
        verify(stringProviderMock).get(FUNCTION_NAME)
        verify(stringProviderMock).get(KEY_A)
        verifyNoMoreInteractions((stringProviderMock))
    }

    @Test
    fun testCallFunctionInFunction() {
        val stringProvider = DictionaryStringProvider()
        stringProvider[KEY_A] = VALUE_A
        stringProcessor.process("\$fun={{ \$fun2={{ \${a} }} }}", stringProvider)
        assertEquals("", stringProcessor.process("\${fun2}", stringProvider))
        assertEquals("", stringProcessor.process("\${fun}", stringProvider))
        assertEquals(VALUE_A, stringProcessor.process("\${fun2}", stringProvider))
    }

    companion object {
        const val KEY_A = "a"
        const val VALUE_A = "VALUE_A"
        const val FUNCTION_NAME = "fun"
        const val FUNCTION_BODY = "\${a}"
        const val INPUT_STR = "\$$FUNCTION_NAME={{$FUNCTION_BODY}}"
        const val INPUT_STR_WITH_FUNCTION = "\${$FUNCTION_NAME}"
    }
}