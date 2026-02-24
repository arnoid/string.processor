package org.arnoid.string.processor.blocks

import junit.framework.TestCase.assertEquals
import org.arnoid.string.processor.DictionaryStringProvider
import org.arnoid.string.processor.StringProcessor
import org.junit.Test

class UpperTest {

    private val processor = StringProcessor()
    private val provider = DictionaryStringProvider.from("name" to "John Doe")

    @Test
    fun testUpper() {
        val input = "\$upper{hello world}"
        assertEquals("HELLO WORLD", processor.process(input, provider))
    }

    fun testUpperWithVariable() {
        val input = "\$upper{\${name}}"
        assertEquals("JOHN DOE", processor.process(input, provider))
    }
}
