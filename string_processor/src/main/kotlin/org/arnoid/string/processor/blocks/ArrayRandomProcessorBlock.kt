package org.arnoid.string.processor.blocks

import org.arnoid.string.processor.InputIterator
import org.arnoid.string.processor.StringProcessor
import org.arnoid.string.processor.StringProvider

/**
 * Random element selector processor block. Usage: `$rnd{choice1|choice2|choice3}` Splits the
 * content by [ARRAY_DELIMITER] and returns one item at random.
 */
class ArrayRandomProcessorBlock : AbstractProcessorBlock() {

    override fun tagName(): String = TAG_RND

    override fun process(
        inputIterator: InputIterator,
        stringProcessor: StringProcessor,
        stringProvider: StringProvider
    ): String {
        return readTagContent(inputIterator)
            .split(ARRAY_DELIMITER)
            .random()
            .let { randomItem ->
                stringProcessor.process(randomItem, stringProvider)
            }
    }

    companion object {
        const val TAG_RND = "rnd"
        const val ARRAY_DELIMITER = "|"
    }
}
