package org.arnoid.string.processor

/**
 * A specialized iterator for traversing input strings during processing. Keeps track of the current
 * cursor position and provides methods for looking ahead and skipping content.
 *
 * @property input The source string to iterate over.
 */
class InputIterator(private val input: String) {
    /** The current index of the iterator in the [input] string. */
    var index = -1

    /** Moves the index back by one and returns the character at the new position. */
    fun previous(): Char {
        index--
        return input[index]
    }

    /** Skips the specified [string] length. */
    fun skip(string: String) {
        skip(string.length)
    }

    /** Moves the index forward by the specified [offset]. */
    fun skip(offset: Int) {
        index += offset
    }

    /** Returns true if there is at least one character before the current index. */
    fun hasPrevious(): Boolean {
        return index != 0
    }

    /** Returns true if there are more characters after the current index. */
    fun hasNext(): Boolean {
        return index + 1 < input.length
    }

    /** Moves the index forward and returns the next character. */
    fun next(): Char {
        index++
        return input[index]
    }

    /** Returns the character at the current index. */
    fun current(): Char {
        return input[index]
    }

    /**
     * Looks at the character at a specific [offset] relative to the next position.
     * @param offset The relative offset from the character that would be returned by [next].
     */
    fun lookup(offset: Int = 0): Char {
        return input[index + 1 + offset]
    }

    /**
     * Checks if the [input] starts with the given [lookup] string at the specified [offset]
     * relative to the next position.
     */
    fun lookup(lookup: String, offset: Int = 0): Boolean {
        return input.startsWith(lookup, index + 1 + offset)
    }

    /**
     * Checks if the [input] starts with the given [lookup] character at the specified [offset]
     * relative to the next position.
     */
    fun lookup(lookup: Char, offset: Int = 0): Boolean {
        return input.startsWith(lookup.toString(), index + 1 + offset)
    }

    /** Finds the next index of [tagVariableDelimiter]. */
    fun nextIndexOf(tagVariableDelimiter: Char): Int {
        return input.indexOf(tagVariableDelimiter, index + 1)
    }

    /** Finds the next index of [tagVariableDelimiter] string. */
    fun nextIndexOf(tagVariableDelimiter: String): Int {
        return input.indexOf(tagVariableDelimiter, index + 1)
    }

    /** Skips a single character. */
    fun skip(char: Char) {
        skip(1)
    }
}
