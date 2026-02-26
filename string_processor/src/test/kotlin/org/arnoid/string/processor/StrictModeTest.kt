package org.arnoid.string.processor

import junit.framework.TestCase.assertEquals
import org.junit.Test

class StrictModeTest {

    @Test
    fun testStrictModeOff() {
        val provider = DictionaryStringProvider.from("name" to "Arno")
        val processor = StringProcessor(strictMode = false)

        val result = processor.process("Hello \${name} \${missing}", provider)
        assertEquals("Hello Arno", result)
    }

    @Test(expected = MissingKeyException::class)
    fun testStrictModeOn() {
        val provider = DictionaryStringProvider.from("name" to "Arno")
        val processor = StringProcessor(strictMode = true)

        processor.process("Hello \${name} \${missing}", provider)
    }

    @Test
    fun testStrictModeOnSuccess() {
        val provider = DictionaryStringProvider.from("name" to "Arno")
        val processor = StringProcessor(strictMode = true)

        val result = processor.process("Hello \${name}", provider)
        assertEquals("Hello Arno", result)
    }

    @Test(expected = MissingKeyException::class)
    fun testNestedStrictMode() {
        val provider = DictionaryStringProvider.from("a" to "b")
        val processor = StringProcessor(strictMode = true)

        // ${a} returns "b", then it tries to find ${b} which is missing
        processor.process("\${\${a}}", provider)
    }
}
