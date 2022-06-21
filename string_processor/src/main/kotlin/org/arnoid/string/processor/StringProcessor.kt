package org.arnoid.string.processor

import org.arnoid.string.processor.blocks.*
import java.io.StringWriter
import java.io.Writer
import java.util.*

class StringProcessor(
    private val blocks: List<AbstractProcessorBlock> = listOf(
        EscapeProcessorBlock(),
        WhenProcessorBlock(),
        IfElseProcessorBlock(),
        EqProcessorBlock(),
        NeqProcessorBlock(),
        GtProcessorBlock(),
        LtProcessorBlock(),
        GeqProcessorBlock(),
        LeqProcessorBlock(),
        AndProcessorBlock(),
        OrProcessorBlock(),
        NotProcessorBlock(),
        GetValueForKeyProcessorBlock(),
        ArrayRandomProcessorBlock(),
        StoreFunctionKeyValueProcessorBlock(),
        StoreKeyValueProcessorBlock(),
    )
) {

    fun process(input: String, stringProvider: StringProvider): String {
        val stringWriter = StringWriter()
        process(input, stringWriter, stringProvider)
        return stringWriter.toString().trim()
    }

    fun process(input: String, output: Writer, stringProvider: StringProvider) {
        val inputIterator = InputIterator(input)

        while (inputIterator.hasNext()) {
            val nextChar = inputIterator.next()
            if (nextChar == AbstractProcessorBlock.CHAR_CONTROL) {
                //control char detected

                for (block in blocks) {
                    if (block.match(inputIterator)) {
                        block.process(output, inputIterator, this, stringProvider)
                        break
                    }
                }
            } else {
                output.append(nextChar)
            }
        }
    }
}