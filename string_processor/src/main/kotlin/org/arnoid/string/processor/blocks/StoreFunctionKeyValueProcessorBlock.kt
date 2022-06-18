package org.arnoid.string.processor.blocks

import org.arnoid.string.processor.InputIterator
import org.arnoid.string.processor.StringProcessor
import org.arnoid.string.processor.StringProvider
import java.io.Writer

class StoreFunctionKeyValueProcessorBlock : AbstractProcessorBlock() {

    override fun match(inputIterator: InputIterator): Boolean {
        val isNextLetter = inputIterator.lookup().isLetter()
        val functionNameBodyDelimiterIndex = inputIterator.nextIndexOf(NAME_DELIMITER)
        val functionBodyBeginIndex = inputIterator.nextIndexOf(FUNCTION_BODY_BEGIN)

        val isFunctionStartDefined = functionBodyBeginIndex != -1

        return isNextLetter //next is letter
                && (functionNameBodyDelimiterIndex + 1 == functionBodyBeginIndex) //name-body delimiter is before body tag
                && isFunctionStartDefined
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

        val value = readTagContent(inputIterator)

        stringProvider.set(outputBuilder.toString(), value.substring(1, value.length - 1))
    }

    override fun process(
        inputIterator: InputIterator,
        stringProcessor: StringProcessor,
        stringProvider: StringProvider
    ): String {
        //not used
        return ""
    }

    companion object {
        const val TAG_NAME = "STORE_FUNCTION_FOR_KEY"
        const val NAME_DELIMITER = '='
        const val FUNCTION_BODY_BEGIN = "{{"
        const val FUNCTION_BODY_END = "}}"
    }

}