package org.arnoid.string.processor.blocks

import org.arnoid.string.processor.InputIterator
import org.arnoid.string.processor.StringProcessor
import org.arnoid.string.processor.StringProvider

/**
 * Upper case conversion processor block. Usage: `$upper{text}` Returns the processed [text] in
 * UPPER CASE.
 */
class UpperProcessorBlock : AbstractProcessorBlock() {
    override fun tagName(): String = TAG_UPPER

    override fun process(
            inputIterator: InputIterator,
            stringProcessor: StringProcessor,
            stringProvider: StringProvider
    ): String {
        inputIterator.skip(TAG_UPPER)
        val content = readTagContent(inputIterator)
        return stringProcessor.process(content, stringProvider).uppercase()
    }

    companion object {
        const val TAG_UPPER = "upper"
    }
}
