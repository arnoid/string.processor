package org.arnoid.string.processor

import org.arnoid.string.processor.blocks.*
import java.io.StringWriter
import java.io.Writer
import java.util.*

class StringProcessor {
    val blocks = LinkedList<AbstractProcessorBlock>()

    init {
        blocks.add(EscapeProcessorBlock())
        blocks.add(IfProcessorBlock())
        blocks.add(EqProcessorBlock())
        blocks.add(NeqProcessorBlock())
        blocks.add(GtProcessorBlock())
        blocks.add(LtProcessorBlock())
        blocks.add(GeqProcessorBlock())
        blocks.add(LeqProcessorBlock())
        blocks.add(AndProcessorBlock())
        blocks.add(OrProcessorBlock())
        blocks.add(NotProcessorBlock())
        blocks.add(GetValueForKeyProcessorBlock())
        blocks.add(ArrayRandomProcessorBlock())
        blocks.add(StoreKeyValueProcessorBlock())
    }

    fun init(initArray: Array<String>, stringProvider: StringProvider) {
        for (initItem in initArray) {
            process(initItem, stringProvider)
        }
    }

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