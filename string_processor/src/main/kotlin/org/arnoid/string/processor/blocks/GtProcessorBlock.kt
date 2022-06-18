package org.arnoid.string.processor.blocks

import org.arnoid.string.processor.InputIterator
import org.arnoid.string.processor.StringProcessor
import org.arnoid.string.processor.StringProvider

class GtProcessorBlock : AbstractProcessorBlock() {
    override fun tagName(): String = TAG_GT

    override fun process(
        inputIterator: InputIterator,
        stringProcessor: StringProcessor,
        stringProvider: StringProvider
    ): String {
        inputIterator.skip(TAG_GT)

        val leftStatement = stringProcessor.process(readTagContent(inputIterator), stringProvider).toIntOrNull()
        val rightStatement = stringProcessor.process(readTagContent(inputIterator), stringProvider).toIntOrNull()

        return if (leftStatement != null && rightStatement != null) {
            return (leftStatement > rightStatement).toString()
        } else {
            false.toString()
        }
    }

    companion object {
        const val TAG_GT = "gt"
    }
}