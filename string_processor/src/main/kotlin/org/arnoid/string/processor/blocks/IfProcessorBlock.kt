package org.arnoid.string.processor.blocks

import org.arnoid.string.processor.InputIterator
import org.arnoid.string.processor.StringProcessor
import org.arnoid.string.processor.StringProvider

class IfProcessorBlock : AbstractProcessorBlock() {
    override fun tagName(): String = TAG_IF

    override fun process(inputIterator: InputIterator, stringProcessor: StringProcessor, stringProvider: StringProvider): String {
        val ifCondition = readTagContent(inputIterator)
        val trueCaseStatement = readTagContent(inputIterator)
        val falseCaseStatement = readTagContent(inputIterator)

        val statementToProcess = if (stringProcessor.process(ifCondition, stringProvider).trim().toBoolean()) {
            trueCaseStatement
        } else {
            falseCaseStatement
        }

        return stringProcessor.process(statementToProcess, stringProvider)
    }

    companion object {
        const val TAG_IF = "if"
    }
}