package org.arnoid.string.processor.blocks

import org.arnoid.string.processor.InputIterator
import org.arnoid.string.processor.StringProcessor
import org.arnoid.string.processor.StringProvider

class GeqProcessorBlock : AbstractProcessorBlock() {
    override fun tagName(): String = TAG_GEQ

    override fun process(
        inputIterator: InputIterator,
        stringProcessor: StringProcessor,
        stringProvider: StringProvider
    ): String {
        inputIterator.skip(TAG_GEQ)

        val leftStatement = stringProcessor.process(readTagContent(inputIterator), stringProvider).toIntOrNull()
        val rightStatement = stringProcessor.process(readTagContent(inputIterator), stringProvider).toIntOrNull()

        return if (leftStatement != null && rightStatement != null) {
            (leftStatement >= rightStatement).toString()
        } else {
            false.toString()
        }
    }

    companion object {
        const val TAG_GEQ = "geq"
    }
}