package org.arnoid.string.processor.blocks

import org.arnoid.string.processor.InputIterator
import org.arnoid.string.processor.StringProcessor
import org.arnoid.string.processor.StringProvider

class NotProcessorBlock : AbstractProcessorBlock() {
    override fun tagName(): String = TAG_NOT

    override fun process(inputIterator: InputIterator, stringProcessor: StringProcessor, stringProvider: StringProvider): String {
        inputIterator.skip(TAG_NOT)

        return stringProcessor.process(readTagContent(inputIterator), stringProvider).toBoolean().not().toString()
    }

    companion object {
        const val TAG_NOT = "not"
    }
}