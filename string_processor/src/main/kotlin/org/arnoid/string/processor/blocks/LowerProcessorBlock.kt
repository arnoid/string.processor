package org.arnoid.string.processor.blocks

import org.arnoid.string.processor.InputIterator
import org.arnoid.string.processor.StringProcessor
import org.arnoid.string.processor.StringProvider

/**
 * Lower case conversion processor block. Usage: `$lower{text}` Returns the processed [text] in
 * lower case.
 */
class LowerProcessorBlock : AbstractProcessorBlock() {
    override fun tagName(): String = TAG_LOWER

    override fun process(
            inputIterator: InputIterator,
            stringProcessor: StringProcessor,
            stringProvider: StringProvider
    ): String {
        inputIterator.skip(TAG_LOWER)
        val content = readTagContent(inputIterator)
        return stringProcessor.process(content, stringProvider).lowercase()
    }

    companion object {
        const val TAG_LOWER = "lower"
    }
}
