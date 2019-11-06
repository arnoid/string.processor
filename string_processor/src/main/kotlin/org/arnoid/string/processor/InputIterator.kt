package org.arnoid.string.processor

class InputIterator(val input: String) {
    var index = -1

    fun previous(): Char {
        index--
        return input[index]
    }

    fun skip(string: String) {
        skip(string.length)
    }

    fun skip(offset: Int) {
        index += offset
    }

    fun hasPrevious(): Boolean {
        return index != 0
    }

    fun hasNext(): Boolean {
        return index + 1 < input.length
    }

    fun next(): Char {
        index++
        return input[index]
    }

    fun current(): Char {
        return input[index]
    }

    fun lookup(offset: Int = 0): Char {
        return input[index + 1 + offset]
    }

    fun lookup(lookup: String, offset: Int = 0): Boolean {
        return input.startsWith(lookup, index + 1 + offset)
    }

    fun lookup(lookup: Char, offset: Int = 0): Boolean {
        return input.startsWith(lookup.toString(), index + 1 + offset)
    }

    fun nextIndexOf(tagVariableDelimiter: Char): Int {
        return input.indexOf(tagVariableDelimiter, index + 1)
    }

    fun skip(char: Char) {
        skip(1)
    }

}