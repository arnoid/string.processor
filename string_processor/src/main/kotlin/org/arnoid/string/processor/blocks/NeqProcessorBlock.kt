package org.arnoid.string.processor.blocks

import org.arnoid.string.processor.InputIterator
import org.arnoid.string.processor.StringProcessor
import org.arnoid.string.processor.StringProvider

class NeqProcessorBlock : AbstractProcessorBlock() {
    override fun tagName(): String = TAG_NEQ

    override fun process(inputIterator: InputIterator, stringProcessor: StringProcessor, stringProvider: StringProvider): String {
        inputIterator.skip(TAG_NEQ)

        val leftStatement = stringProcessor.process(readTagContent(inputIterator), stringProvider)
        val rightStatement = stringProcessor.process(readTagContent(inputIterator), stringProvider)

        return (leftStatement != rightStatement).toString()
    }

    companion object {
        const val TAG_NEQ = "neq"
    }
}