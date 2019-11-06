package org.arnoid.string.processor.blocks

import org.arnoid.string.processor.InputIterator
import org.arnoid.string.processor.StringProcessor
import org.arnoid.string.processor.StringProvider

class StoreKeyValueProcessorBlock : AbstractProcessorBlock() {
    override fun match(inputIterator: InputIterator): Boolean {
        return inputIterator.lookup().isLetter()
    }

    override fun tagName(): String = TAG_NAME

    override fun process(inputIterator: InputIterator, stringProcessor: StringProcessor, stringProvider: StringProvider): String {
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

        return ""
    }

    companion object {
        const val TAG_NAME = "STORE_VALUE_FOR_KEY"
        const val VARIABLE_DELIMITER = ':'
    }

}