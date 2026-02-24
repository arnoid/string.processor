package org.arnoid.string.processor.blocks

import junit.framework.TestCase.assertEquals
import org.arnoid.string.processor.DictionaryStringProvider
import org.arnoid.string.processor.StringProcessor
import org.junit.Test

class CommentTest {

    private val processor = StringProcessor()
    private val provider = DictionaryStringProvider.from()

    @Test
    fun testComment() {
        val input = "Hello$#{ This is a comment } World"
        assertEquals("Hello World", processor.process(input, provider))
    }

    @Test
    fun testNestedComment() {
        val input = "Hello$#{ Comment with \$upper{nested} tag } World"
        assertEquals("Hello World", processor.process(input, provider))
    }
}
