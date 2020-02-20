package org.arnoid.string.processor

import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mockito.times

class StringProcessorAssignVariableTest {

    lateinit var stringProcessor: StringProcessor
    lateinit var stringProviderMock: StringProvider
    private val dictionary = HashMap<String, String>()

    @Before
    fun before() {
        dictionary.clear()
        stringProcessor = StringProcessor()
        stringProviderMock = mock {
            on { set(anyString(), anyString()) } doAnswer { invocationOnMock ->
                val key = invocationOnMock.arguments[0] as String
                val value = invocationOnMock.arguments[1] as String
                println("{$key} {$value}")
                dictionary[key] = value
                Unit
            }
        }
    }

    @Test
    fun testTagAssignVariable() {
        stringProcessor.process(INPUT_STR, stringProviderMock)

        verify(stringProviderMock).set("a", "a")
        verify(stringProviderMock).set("b", "b")
        verify(stringProviderMock).set("c", "c")

        assertEquals("a", dictionary["a"])
        assertEquals("b", dictionary["b"])
        assertEquals("c", dictionary["c"])
    }

    companion object {
        const val INPUT_STR = "\$a={a} $$ \$b={b} \$c={c}"
    }
}