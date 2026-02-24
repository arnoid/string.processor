package org.arnoid.string.processor.blocks

import org.arnoid.string.processor.InputIterator
import org.arnoid.string.processor.StringProcessor
import org.arnoid.string.processor.StringProvider

/**
 * Comment processor block. Usage: `$#{ comment content }` The content inside the braces is
 * completely ignored and results in an empty string in the output.
 */
class CommentProcessorBlock : AbstractProcessorBlock() {
    override fun tagName(): String = TAG_COMMENT

    override fun process(
        inputIterator: InputIterator,
        stringProcessor: StringProcessor,
        stringProvider: StringProvider
    ): String {
        inputIterator.skip(TAG_COMMENT)
        readTagContent(inputIterator)
        return ""
    }

    companion object {
        const val TAG_COMMENT = "#"
    }
}
