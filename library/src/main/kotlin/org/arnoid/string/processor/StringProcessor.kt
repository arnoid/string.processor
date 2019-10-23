package org.arnoid.string.processor

class StringProcessor {

    fun init(initArray: Array<String>, stringProvider: StringProcessorValueProvider) {
        for (initItem in initArray) {
            process(initItem, stringProvider)
        }
    }

    fun process(input: String, stringProvider: StringProcessorValueProvider): String {
        val inputIterator = InputIterator(input)
        val output = StringBuilder()

        while (inputIterator.hasNext()) {
            val nextChar = inputIterator.next()
            if (nextChar == TAG_ESCAPE) {
                //control char detected
                when {
                    inputIterator.lookup(TAG_ESCAPE) -> output.append(TAG_ESCAPE)
                    lookupIf(inputIterator) -> output.append(processIf(inputIterator, stringProvider))
                    lookupEq(inputIterator) -> output.append(processEq(inputIterator, stringProvider))
                    lookupNeq(inputIterator) -> output.append(processNeq(inputIterator, stringProvider))
                    lookupGt(inputIterator) -> output.append(processGt(inputIterator, stringProvider))
                    lookupLt(inputIterator) -> output.append(processLt(inputIterator, stringProvider))
                    lookupGeq(inputIterator) -> output.append(processGeq(inputIterator, stringProvider))
                    lookupLeq(inputIterator) -> output.append(processLeq(inputIterator, stringProvider))
                    lookupAnd(inputIterator) -> output.append(processAnd(inputIterator, stringProvider))
                    lookupOr(inputIterator) -> output.append(processOr(inputIterator, stringProvider))
                    lookupNot(inputIterator) -> output.append(processNot(inputIterator, stringProvider))
                    lookupValueForKey(inputIterator) -> output.append(processValueForKey(inputIterator, stringProvider))
                    inputIterator.lookup().isLetter() -> processVariable(inputIterator, stringProvider)
                }

            } else {
                output.append(nextChar)
            }
        }

        return output.toString().trim()
    }

    private fun lookupNot(inputIterator: InputIterator) = inputIterator.lookup(TAG_NOT)

    private fun lookupOr(inputIterator: InputIterator) = inputIterator.lookup(TAG_OR)

    private fun lookupAnd(inputIterator: InputIterator) = inputIterator.lookup(TAG_AND)

    private fun lookupLeq(inputIterator: InputIterator) = inputIterator.lookup(TAG_LEQ)

    private fun lookupGeq(inputIterator: InputIterator) = inputIterator.lookup(TAG_GEQ)

    private fun lookupLt(inputIterator: InputIterator) = inputIterator.lookup(TAG_LT)

    private fun lookupGt(inputIterator: InputIterator) = inputIterator.lookup(TAG_GT)

    private fun lookupNeq(inputIterator: InputIterator) = inputIterator.lookup(TAG_NEQ)

    private fun lookupEq(inputIterator: InputIterator) = inputIterator.lookup(TAG_EQ)

    private fun lookupIf(inputIterator: InputIterator) = inputIterator.lookup(TAG_IF)

    private fun lookupValueForKey(inputIterator: InputIterator) = inputIterator.lookup(TAG_CONTENT_START)

    private fun readTagContent(inputIterator: InputIterator): String {
        val output = StringBuilder()

        var contentStart = false
        var level = 0

        while (inputIterator.hasNext()) {
            val nextChar = inputIterator.next()

            if (contentStart) {
                if (nextChar == START_TAG) {
                    level++
                } else if (nextChar == END_TAG) {
                    if (level == 0) {
                        break
                    } else {
                        level--
                    }
                }

                output.append(nextChar)
            } else if (nextChar == START_TAG) {
                contentStart = true
            }
        }

        return output.toString()
    }

    private fun processValueForKey(inputIterator: InputIterator, stringProvider: StringProcessorValueProvider): String {
        val tagContent = readTagContent(inputIterator)

        val key = process(tagContent, stringProvider)

        return process(stringProvider.get(key), stringProvider)
    }

    private fun processNot(inputIterator: InputIterator, stringProvider: StringProcessorValueProvider): String {
        inputIterator.skip(TAG_NOT)
        return process(readTagContent(inputIterator), stringProvider).toBoolean().not().toString()
    }

    private fun processOr(inputIterator: InputIterator, stringProvider: StringProcessorValueProvider): String {
        inputIterator.skip(TAG_OR)
        val leftStatement = process(readTagContent(inputIterator), stringProvider).toBoolean()
        val rightStatement = process(readTagContent(inputIterator), stringProvider).toBoolean()

        return (leftStatement || rightStatement).toString()
    }

    private fun processAnd(inputIterator: InputIterator, stringProvider: StringProcessorValueProvider): String {
        inputIterator.skip(TAG_AND)
        val leftStatement = process(readTagContent(inputIterator), stringProvider).toBoolean()
        val rightStatement = process(readTagContent(inputIterator), stringProvider).toBoolean()

        return (leftStatement && rightStatement).toString()
    }

    private fun processLeq(inputIterator: InputIterator, stringProvider: StringProcessorValueProvider): String {
        inputIterator.skip(TAG_LEQ)
        val leftStatement = process(readTagContent(inputIterator), stringProvider).toIntOrNull()
        val rightStatement = process(readTagContent(inputIterator), stringProvider).toIntOrNull()

        return if (leftStatement == null || rightStatement == null) {
            false.toString()
        } else {
            (leftStatement <= rightStatement).toString()
        }
    }

    private fun processGeq(inputIterator: InputIterator, stringProvider: StringProcessorValueProvider): String {
        inputIterator.skip(TAG_GEQ)
        val leftStatement = process(readTagContent(inputIterator), stringProvider).toIntOrNull()
        val rightStatement = process(readTagContent(inputIterator), stringProvider).toIntOrNull()

        return if (leftStatement == null || rightStatement == null) {
            false.toString()
        } else {
            (leftStatement >= rightStatement).toString()
        }
    }

    private fun processLt(inputIterator: InputIterator, stringProvider: StringProcessorValueProvider): String {
        inputIterator.skip(TAG_LT)
        val leftStatement = process(readTagContent(inputIterator), stringProvider).toIntOrNull()
        val rightStatement = process(readTagContent(inputIterator), stringProvider).toIntOrNull()

        return if (leftStatement == null || rightStatement == null) {
            false.toString()
        } else {
            (leftStatement < rightStatement).toString()
        }
    }

    private fun processGt(inputIterator: InputIterator, stringProvider: StringProcessorValueProvider): String {
        inputIterator.skip(TAG_GT)
        val leftStatement = process(readTagContent(inputIterator), stringProvider).toIntOrNull()
        val rightStatement = process(readTagContent(inputIterator), stringProvider).toIntOrNull()

        return if (leftStatement == null || rightStatement == null) {
            false.toString()
        } else {
            (leftStatement > rightStatement).toString()
        }
    }

    private fun processNeq(inputIterator: InputIterator, stringProvider: StringProcessorValueProvider): String {
        inputIterator.skip(TAG_NEQ)

        val leftStatement = process(readTagContent(inputIterator), stringProvider)
        val rightStatement = process(readTagContent(inputIterator), stringProvider)

        return (leftStatement != rightStatement).toString()
    }

    private fun processEq(inputIterator: InputIterator, stringProvider: StringProcessorValueProvider): String {
        inputIterator.skip(TAG_EQ)

        val leftStatement = process(readTagContent(inputIterator), stringProvider)
        val rightStatement = process(readTagContent(inputIterator), stringProvider)

        return (leftStatement == rightStatement).toString()
    }

    private fun processIf(inputIterator: InputIterator, stringProvider: StringProcessorValueProvider): String {
        inputIterator.skip(TAG_IF)

        val ifCondition = readTagContent(inputIterator)
        val leftStatement = readTagContent(inputIterator)
        val rightStatement = readTagContent(inputIterator)

        return if (process(ifCondition, stringProvider).toBoolean()) {
            process(leftStatement, stringProvider)
        } else {
            process(rightStatement, stringProvider)
        }
    }

    private fun processVariable(inputIterator: InputIterator, stringProvider: StringProcessorValueProvider) {
        val outputBuilder = StringBuilder()

        while (inputIterator.hasNext()) {
            val nextChar = inputIterator.next()

            if (nextChar == TAG_VARIABLE_DELIMITER) {
                break
            } else {
                outputBuilder.append(nextChar)
            }
        }

        val unprocessedValue = readTagContent(inputIterator)
        stringProvider.set(outputBuilder.toString(), process(unprocessedValue, stringProvider))
    }

    companion object {
        const val TAG_ESCAPE = '$'
        const val TAG_CONTENT_START = "{"
        const val TAG_IF = "if"
        const val TAG_EQ = "eq"
        const val TAG_NEQ = "neq"
        const val TAG_GT = "gt"
        const val TAG_LT = "lt"
        const val TAG_GEQ = "geq"
        const val TAG_LEQ = "leq"
        const val TAG_AND = "and"
        const val TAG_OR = "or"
        const val TAG_NOT = "not"

        const val TAG_VARIABLE_DELIMITER = ':'

        const val START_TAG = '{'
        const val END_TAG = '}'
    }
}