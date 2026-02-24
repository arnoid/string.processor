package org.arnoid.string.processor.blocks

import java.io.Writer
import org.arnoid.string.processor.InputIterator
import org.arnoid.string.processor.StringProcessor
import org.arnoid.string.processor.StringProvider

/**
 * Base class for all processor blocks in the template engine. A processor block is responsible for
 * identifying its tag in the input and processing its content.
 *
 * All tags start with a control character [CONTROL_CHAR] and are usually followed by a tag name and
 * content enclosed in [TAG_START] and [TAG_END].
 */
abstract class AbstractProcessorBlock {

    /**
     * Checks if this block matches the current position in the [inputIterator].
     * @return True if the block should process the current input.
     */
    open fun match(inputIterator: InputIterator): Boolean {
        return inputIterator.lookup(tagName())
    }

    /** Returns the name of the tag this block handles (without the control character). */
    abstract fun tagName(): String

    /** Processes the block's logic and appends the result to the [output] writer. */
    open fun process(
        output: Writer,
        inputIterator: InputIterator,
        stringProcessor: StringProcessor,
        stringProvider: StringProvider
    ) {
        output.append(process(inputIterator, stringProcessor, stringProvider))
    }

    /** Processes the block's logic and returns the result as a string. */
    abstract fun process(
        inputIterator: InputIterator,
        stringProcessor: StringProcessor,
        stringProvider: StringProvider
    ): String

    /** Reads the content of a tag if it matches the [tagName]. */
    protected fun readTagContent(inputIterator: InputIterator, tagName: String): String {
        return readTagContentIf(inputIterator) { inputIterator: InputIterator ->
            inputIterator.lookup(tagName)
        }
    }

    /**
     * Will read content of [InputIterator] after next [TAG_START] until next [TAG_END] ending after
     * [TAG_END] if [precondition] is met.
     */
    protected fun readTagContentIf(
        inputIterator: InputIterator,
        precondition: (inputIterator: InputIterator) -> Boolean
    ): String {
        return if (precondition.invoke(inputIterator)) {
            readTagContent(inputIterator)
        } else {
            ""
        }
    }

    /**
     * Reads everything from the next [TAG_START] to the corresponding [TAG_END], handling nested
     * tags.
     */
    protected fun readTagContent(inputIterator: InputIterator): String {
        val output = StringBuilder()

        var contentStart = false
        var level = 0

        while (inputIterator.hasNext()) {
            val nextChar = inputIterator.next()

            if (contentStart) {
                if (nextChar == TAG_START) {
                    level++
                } else if (nextChar == TAG_END) {
                    if (level == 0) {
                        break
                    } else {
                        level--
                    }
                }

                output.append(nextChar)
            } else if (nextChar == TAG_START) {
                contentStart = true
            }
        }

        return output.toString()
    }

    companion object {
        /** The character that triggers tag processing. Default is '$'. */
        const val CONTROL_CHAR = '$'

        /** The character that marks the start of a tag's content. Default is '{'. */
        const val TAG_START = '{'

        /** The character that marks the end of a tag's content. Default is '}'. */
        const val TAG_END = '}'
    }
}
