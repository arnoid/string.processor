package org.arnoid.string.processor

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class StringProcessorValueForKeyTest {

    lateinit var stringProcessor: StringProcessor
    lateinit var stringProviderMock: StringProvider

    @Before
    fun before() {
        stringProcessor = StringProcessor()
        stringProviderMock = mock {
            on { get(KEY_A) } doReturn VALUE_A
            on { get(KEY_B) } doReturn VALUE_B
            on { get(KEY_C) } doReturn VALUE_C
        }
    }

    @Test
    fun testTagValueForKey() {
        assertEquals(RESULT_STR, stringProcessor.process(INPUT_STR, stringProviderMock))
    }

    companion object {
        const val KEY_A = "a"
        const val KEY_B = "b"
        const val KEY_C = "c"
        const val VALUE_A = "VALUE_A"
        const val VALUE_B = "\${c}"
        const val VALUE_C = "VALUE_C"
        const val INPUT_STR = "\${a} $$ \${b} \${c}"
        const val RESULT_STR = "VALUE_A \$ VALUE_C VALUE_C"
    }
}