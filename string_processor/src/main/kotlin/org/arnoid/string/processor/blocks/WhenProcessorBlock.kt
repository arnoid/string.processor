package org.arnoid.string.processor.blocks

import org.arnoid.string.processor.InputIterator
import org.arnoid.string.processor.StringProcessor
import org.arnoid.string.processor.StringProvider

/**
 * Switch-like processor block. Usage: `$when{value} $case{val1}{stmt1} $case{val2}{stmt2}
 * $else{default}` Compares the target value against cases and returns the matching statement.
 */
class WhenProcessorBlock : AbstractProcessorBlock() {

    override fun tagName(): String = TAG_WHEN

    override fun process(
        inputIterator: InputIterator,
        stringProcessor: StringProcessor,
        stringProvider: StringProvider
    ): String {

        val whenValueStatement = readTagContent(inputIterator)

        val caseConditionsToStatements = mutableListOf<Pair<String, String>>()

        // first `if` condition and statement
        caseConditionsToStatements.add(
            readTagContent(inputIterator) to readTagContent(inputIterator)
        )

        val caseConditionTAg = "$CONTROL_CHAR$TAG_CASE"
        while (inputIterator.lookup(caseConditionTAg)) {
            // add more elseif
            inputIterator.skip(caseConditionTAg)
            caseConditionsToStatements.add(
                readTagContent(inputIterator) to readTagContent(inputIterator)
            )
        }

        val elseStatement = readTagContent(inputIterator, "$CONTROL_CHAR$TAG_ELSE")

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
