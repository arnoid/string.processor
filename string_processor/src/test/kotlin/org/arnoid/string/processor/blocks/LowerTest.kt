package org.arnoid.string.processor.blocks

import junit.framework.TestCase.assertEquals
import org.arnoid.string.processor.DictionaryStringProvider
import org.arnoid.string.processor.StringProcessor
import org.junit.Test

class LowerTest {

    private val processor = StringProcessor()
    private val provider = DictionaryStringProvider.from("name" to "John Doe")

    @Test
    fun testLower() {
        val input = "\$lower{HELLO WORLD}"
        assertEquals("hello world", processor.process(input, provider))
    }

    @Test
    fun testUpperWithVariable() {
        val input = "\$upper{\${name}}"
        assertEquals("JOHN DOE", processor.process(input, provider))
    }
}
