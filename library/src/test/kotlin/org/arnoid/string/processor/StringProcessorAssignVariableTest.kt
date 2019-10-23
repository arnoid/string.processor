package org.arnoid.string.processor

import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.mock
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.anyString

class StringProcessorAssignVariableTest {

    lateinit var stringProcessor: StringProcessor
    lateinit var valueProviderMock: StringProcessorValueProvider
    private val dictionary = HashMap<String, String>()

    @Before
    fun before() {
        dictionary.clear()
        stringProcessor = StringProcessor()
        valueProviderMock = mock {
            on { set(anyString(), anyString()) } doAnswer { invocationOnMock ->
                dictionary[invocationOnMock.arguments[0] as String] = invocationOnMock.arguments[1] as String
                Unit
            }
        }
    }

    @Test
    fun testTagAssignVariable() {
        stringProcessor.process(INPUT_STR, valueProviderMock)

        assertEquals("a", dictionary["a"])
        assertEquals("b", dictionary["b"])
        assertEquals("c", dictionary["c"])
    }

    companion object {
        const val INPUT_STR = "\$a:{a} $$ \$b:{b} \$c:{c}"
    }
}