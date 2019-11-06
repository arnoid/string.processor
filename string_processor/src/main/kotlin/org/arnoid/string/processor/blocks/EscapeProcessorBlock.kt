package org.arnoid.string.processor.blocks

import org.arnoid.string.processor.InputIterator
import org.arnoid.string.processor.StringProcessor
import org.arnoid.string.processor.StringProvider

class EscapeProcessorBlock : AbstractProcessorBlock() {
    override fun tagName(): String = TAG_ESCAPE

    override fun process(inputIterator: InputIterator, stringProcessor: StringProcessor, stringProvider: StringProvider): String {
        inputIterator.skip(TAG_ESCAPE)

        return TAG_ESCAPE
    }

    companion object {
        const val TAG_ESCAPE = "$"
    }
}