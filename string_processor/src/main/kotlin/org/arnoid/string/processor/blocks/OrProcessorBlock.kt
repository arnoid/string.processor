package org.arnoid.string.processor.blocks

import org.arnoid.string.processor.InputIterator
import org.arnoid.string.processor.StringProcessor
import org.arnoid.string.processor.StringProvider

class OrProcessorBlock : AbstractProcessorBlock() {
    override fun tagName(): String = TAG_OR

    override fun process(
        inputIterator: InputIterator,
        stringProcessor: StringProcessor,
        stringProvider: StringProvider
    ): String {
        inputIterator.skip(TAG_OR)

        val leftStatement = stringProcessor.process(readTagContent(inputIterator), stringProvider).toBoolean()
        val rightStatement = stringProcessor.process(readTagContent(inputIterator), stringProvider).toBoolean()

        return (leftStatement || rightStatement).toString()
    }

    companion object {
        const val TAG_OR = "or"
    }
}