package org.arnoid.string.processor.blocks

import java.io.Writer
import org.arnoid.string.processor.InputIterator
import org.arnoid.string.processor.StringProcessor
import org.arnoid.string.processor.StringProvider

/**
 * Variable assignment processor block. Usage: `$varName={value}` Stores the processed [value] in
 * the [StringProvider] under [varName].
 */
class StoreKeyValueProcessorBlock : AbstractProcessorBlock() {

    override fun match(inputIterator: InputIterator): Boolean {
        return inputIterator.lookup().isLetter()
    }

    override fun tagName(): String = TAG_NAME

    override fun process(
            output: Writer,
            inputIterator: InputIterator,
            stringProcessor: StringProcessor,
            stringProvider: StringProvider
    ) {
        val outputBuilder = StringBuilder()

        while (inputIterator.hasNext()) {
            val nextChar = inputIterator.next()

            if (nextChar == NAME_DELIMITER) {
                break
            } else {
                outputBuilder.append(nextChar)
            }
        }

        val value = stringProcessor.process(readTagContent(inputIterator), stringProvider)

        stringProvider.set(outputBuilder.toString(), value)
    }

    override fun process(
            inputIterator: InputIterator,
            stringProcessor: StringProcessor,
            stringProvider: StringProvider
    ): String {
        // not used
        return ""
    }

    companion object {
        const val TAG_NAME = "STORE_VALUE_FOR_KEY"
        const val NAME_DELIMITER = '='
    }
}
