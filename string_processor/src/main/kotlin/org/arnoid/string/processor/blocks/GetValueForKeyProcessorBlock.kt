package org.arnoid.string.processor.blocks

import org.arnoid.string.processor.InputIterator
import org.arnoid.string.processor.StringProcessor
import org.arnoid.string.processor.StringProvider

class GetValueForKeyProcessorBlock : AbstractProcessorBlock() {

    override fun tagName(): String = START_TAG.toString()

    override fun process(
        inputIterator: InputIterator,
        stringProcessor: StringProcessor,
        stringProvider: StringProvider
    ): String {
        val tagContent = readTagContent(inputIterator)

        val key = stringProcessor.process(tagContent, stringProvider)

        return stringProcessor.process(stringProvider.get(key), stringProvider)
    }

}