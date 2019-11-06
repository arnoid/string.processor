package org.arnoid.string.processor.blocks

import org.arnoid.string.processor.InputIterator
import org.arnoid.string.processor.StringProcessor
import org.arnoid.string.processor.StringProvider
import java.io.Writer

abstract class AbstractProcessorBlock {

    open fun match(inputIterator: InputIterator): Boolean {
        return inputIterator.lookup(tagName())
    }

    abstract fun tagName(): String

    open fun process(output: Writer, inputIterator: InputIterator, stringProcessor: StringProcessor, stringProvider: StringProvider) {
        output.append(process(inputIterator, stringProcessor, stringProvider))
    }

    abstract fun process(inputIterator: InputIterator, stringProcessor: StringProcessor, stringProvider: StringProvider): String

    protected fun readTagContent(inputIterator: InputIterator): String {
        val output = StringBuilder()

        var contentStart = false
        var level = 0

        while (inputIterator.hasNext()) {
            val nextChar = inputIterator.next()

            if (contentStart) {
                if (nextChar == AbstractProcessorBlock.START_TAG) {
                    level++
                } else if (nextChar == AbstractProcessorBlock.END_TAG) {
                    if (level == 0) {
                        break
                    } else {
                        level--
                    }
                }

                output.append(nextChar)
            } else if (nextChar == AbstractProcessorBlock.START_TAG) {
                contentStart = true
            }
        }

        return output.toString()
    }

    companion object {
        const val CHAR_CONTROL = '$'

        const val START_TAG = '{'
        const val END_TAG = '}'
    }
}