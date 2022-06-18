package org.arnoid.string.processor.blocks

import org.arnoid.string.processor.InputIterator
import org.arnoid.string.processor.StringProcessor
import org.arnoid.string.processor.StringProvider

class AndProcessorBlock : AbstractProcessorBlock() {
    override fun tagName(): String = TAG_AND

    override fun process(
        inputIterator: InputIterator,
        stringProcessor: StringProcessor,
        stringProvider: StringProvider
    ): String {
        inputIterator.skip(TAG_AND)

        val leftStatement = stringProcessor.process(readTagContent(inputIterator), stringProvider).toBoolean()
        val rightStatement = stringProcessor.process(readTagContent(inputIterator), stringProvider).toBoolean()

        return (leftStatement && rightStatement).toString()
    }

    companion object {
        const val TAG_AND = "and"
    }
}