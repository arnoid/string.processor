package org.arnoid.string.processor.blocks

import org.arnoid.string.processor.DictionaryStringProvider
import org.arnoid.string.processor.StringProcessor
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

internal class IfElseProcessorBlockTest {

    lateinit var processor: StringProcessor

    @Before
    fun before() {
        processor = StringProcessor()
    }

    private val dictionaryStringProvider = DictionaryStringProvider()

    @Test
    fun testProcessIfTrue() {
        dictionaryStringProvider.set("IF", "true")
        assertEquals(
            STATEMENT_IF_TRUE_RESULT,
            processor.process(STATEMENT_IF_TRUE, dictionaryStringProvider)
        )
    }

    @Test
    fun testProcessIfFalse() {
        dictionaryStringProvider.set("IF", "false")
        assertEquals(
            STATEMENT_IF_FALSE_RESULT,
            processor.process(STATEMENT_IF_FALSE, dictionaryStringProvider)
        )
    }

    @Test
    fun testProcessIfFalseElse() {
        dictionaryStringProvider.set("IF", "false")
        assertEquals(
            STATEMENT_IF_FALSE_ELSE_RESULT,
            processor.process(STATEMENT_IF_FALSE_ELSE, dictionaryStringProvider)
        )
    }

    @Test
    fun testProcessIfElseIfTrueElseIfElse() {
        dictionaryStringProvider.set("IF", "true")
        assertEquals(
            STATEMENT_IF_ELSEIF_TRUE_ELSEIF_ELSE_RESULT,
            processor.process(STATEMENT_IF_ELSEIF_TRUE_ELSEIF_ELSE, dictionaryStringProvider)
        )
    }

    @Test
    fun testProcessIfElseIfElseIfTrueElse() {
        dictionaryStringProvider.set("IF", "true")
        assertEquals(
            STATEMENT_IF_ELSEIF_ELSEIF_TRUE_ELSE_RESULT,
            processor.process(STATEMENT_IF_ELSEIF_ELSEIF_TRUE_ELSE, dictionaryStringProvider)
        )
    }

    companion object {
        const val STATEMENT_IF_TRUE = "\$if{\${IF}}{statement}"
        const val STATEMENT_IF_TRUE_RESULT = "statement"

        const val STATEMENT_IF_FALSE = "\$if{\${IF}}{statement}"
        const val STATEMENT_IF_FALSE_RESULT = ""

        const val STATEMENT_IF_FALSE_ELSE = "\$if{\${IF}}{abcd}\$else{else statement}"
        const val STATEMENT_IF_FALSE_ELSE_RESULT = "else statement"

        const val STATEMENT_IF_ELSEIF_TRUE_ELSEIF_ELSE =
            "\$if{false}{1}\$elseif{\${IF}}{2}\$elseif{false}{3}\$else{else statement}"
        const val STATEMENT_IF_ELSEIF_TRUE_ELSEIF_ELSE_RESULT = "2"

        const val STATEMENT_IF_ELSEIF_ELSEIF_TRUE_ELSE =
            "\$if{false}{1}\$elseif{false}{2}\$elseif{\${IF}}{3}\$else{else statement}"
        const val STATEMENT_IF_ELSEIF_ELSEIF_TRUE_ELSE_RESULT = "3"
    }
}