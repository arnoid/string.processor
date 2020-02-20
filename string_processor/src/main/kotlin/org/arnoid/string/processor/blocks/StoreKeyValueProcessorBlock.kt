package org.arnoid.string.processor.blocks

import org.arnoid.string.processor.InputIterator
import org.arnoid.string.processor.StringProcessor
import org.arnoid.string.processor.StringProvider
import java.io.Writer

class StoreKeyValueProcessorBlock : AbstractProcessorBlock() {

    override fun match(inputIterator: InputIterator): Boolean {
        return inputIterator.lookup().isLetter()
    }

    override fun tagName(): String = TAG_NAME

    override fun process(output: Writer, inputIterator: InputIterator, stringProcessor: StringProcessor, stringProvider: StringProvider) {
        val outputBuilder = StringBuilder()

        while (inputIterator.hasNext()) {
            val nextChar = inputIterator.next()

            if (nextChar == VARIABLE_DELIMITER) {
                break
            } else {
                outputBuilder.append(nextChar)
            }
        }

        val value = stringProcessor.process(readTagContent(inputIterator), stringProvider)

        stringProvider.set(outputBuilder.toString(), value)
    }

    override fun process(inputIterator: InputIterator, stringProcessor: StringProcessor, stringProvider: StringProvider): String {
        //not used
        return ""
    }

    companion object {
        const val TAG_NAME = "STORE_VALUE_FOR_KEY"
        const val VARIABLE_DELIMITER = '='
    }

}