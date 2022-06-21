package org.arnoid.string.processor.blocks

import org.arnoid.string.processor.InputIterator
import org.arnoid.string.processor.StringProcessor
import org.arnoid.string.processor.StringProvider

class WhenProcessorBlock : AbstractProcessorBlock() {
    override fun tagName(): String = TAG_WHEN

    override fun process(
        inputIterator: InputIterator,
        stringProcessor: StringProcessor,
        stringProvider: StringProvider
    ): String {

        val whenValueStatement = readTagContent(inputIterator)

        val caseConditionsToStatements = mutableListOf<Pair<String, String>>()

        //first `if` condition and statement
        caseConditionsToStatements.add(readTagContent(inputIterator) to readTagContent(inputIterator))

        val caseConditionTAg = "$CHAR_CONTROL$TAG_CASE"
        while (inputIterator.lookup(caseConditionTAg)) {
            //add more elseif
            inputIterator.skip(caseConditionTAg)
            caseConditionsToStatements.add(readTagContent(inputIterator) to readTagContent(inputIterator))
        }

        val elseStatement = readTagContent(inputIterator, "$CHAR_CONTROL$TAG_ELSE")

        val whenValueEvaluationResult = stringProcessor.process(whenValueStatement, stringProvider)

        caseConditionsToStatements.forEach {
            if (stringProcessor.process(it.first, stringProvider) == whenValueEvaluationResult) {
                return stringProcessor.process(it.second, stringProvider)
            }
        }

        return stringProcessor.process(elseStatement, stringProvider)
    }

    companion object {
        const val TAG_WHEN = "when"
        const val TAG_CASE = "case"
        const val TAG_ELSE = "else"
    }
}