package org.arnoid.string.processor.blocks

import org.arnoid.string.processor.DictionaryStringProvider
import org.arnoid.string.processor.StringProcessor
import org.arnoid.string.processor.StringProvider
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

internal class WhenProcessorBlockTest {

    private lateinit var processor: StringProcessor

    private
    lateinit var dictionaryStringProvider: StringProvider

    @Before
    fun before() {
        dictionaryStringProvider = DictionaryStringProvider()
        processor = StringProcessor()
    }

    @Test
    fun testProcessWhen1() {
        dictionaryStringProvider.set("WHEN", "1")

        assertEquals(
            STATEMENT_WHEN_1_RESULT,
            processor.process(STATEMENT_WHEN_1, dictionaryStringProvider)
        )
    }

    @Test
    fun testProcessWhenElse() {
        dictionaryStringProvider.set("WHEN", "0")
        assertEquals(
            STATEMENT_WHEN_ELSE_RESULT,
            processor.process(STATEMENT_WHEN_ELSE, dictionaryStringProvider)
        )
    }

    @Test
    fun testProcessWhen2() {
        dictionaryStringProvider.set("WHEN", "2")
        assertEquals(
            STATEMENT_WHEN_2_RESULT,
            processor.process(STATEMENT_WHEN_2, dictionaryStringProvider)
        )
    }

    companion object {
        const val STATEMENT_WHEN_1 = "\$when{\${WHEN}}\$case{1}{value_1}\$else{else_value}"
        const val STATEMENT_WHEN_1_RESULT = "value_1"

        const val STATEMENT_WHEN_2 = "\$when{2}\$case{1}{value_1}\$case{2}{value_2}\$else{else_value}"
        const val STATEMENT_WHEN_2_RESULT = "value_2"

        const val STATEMENT_WHEN_ELSE = "\$when{2}\$case{1}{value_1}\$else{else_value}"
        const val STATEMENT_WHEN_ELSE_RESULT = "else_value"
    }
}