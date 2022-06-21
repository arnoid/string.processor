package org.arnoid.string.processor.blocks

import org.arnoid.string.processor.InputIterator
import org.arnoid.string.processor.StringProcessor
import org.arnoid.string.processor.StringProvider

class IfElseProcessorBlock : AbstractProcessorBlock() {
    override fun tagName(): String = TAG_IF

    override fun process(
        inputIterator: InputIterator,
        stringProcessor: StringProcessor,
        stringProvider: StringProvider
    ): String {
        val ifConditionsToStatements = mutableListOf<Pair<String, String>>()

        //first `if` condition and statement
        ifConditionsToStatements.add(readTagContent(inputIterator) to readTagContent(inputIterator))

        val elseIf = "$CHAR_CONTROL$TAG_ELSE_IF"
        while (inputIterator.lookup(elseIf)) {
            //add more elseif
            inputIterator.skip(elseIf)
            ifConditionsToStatements.add(readTagContent(inputIterator) to readTagContent(inputIterator))
        }

        val elseStatement = readTagContent(inputIterator, "$CHAR_CONTROL$TAG_ELSE")

        for (ifConditionToStatementPair in ifConditionsToStatements) {
            if (stringProcessor.process(ifConditionToStatementPair.first, stringProvider).toBoolean()) {
                return stringProcessor.process(ifConditionToStatementPair.second, stringProvider)
            }
        }

        return stringProcessor.process(elseStatement, stringProvider)
    }

    companion object {
        const val TAG_IF = "if"
        const val TAG_ELSE_IF = "elseif"
        const val TAG_ELSE = "else"
    }
}