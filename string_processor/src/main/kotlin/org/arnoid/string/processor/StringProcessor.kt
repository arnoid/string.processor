package org.arnoid.string.processor

import java.io.StringWriter
import java.io.Writer
import org.arnoid.string.processor.blocks.*

/**
 * The main engine for processing templates. It iterates through the input string and delegates to
 * [AbstractProcessorBlock] when it encounters a control character.
 *
 * @property blocks The list of processor blocks available for this processor.
 */
class StringProcessor(
    /**
     * Processor blocks that will be used to process input.
     *
     * Sequence of blocks is important, as processing attempts are done in order provided by list.
     */
    private val blocks: List<AbstractProcessorBlock> =
        listOf(
            EscapeProcessorBlock(),
            WhenProcessorBlock(),
            IfElseProcessorBlock(),
            EqProcessorBlock(),
            NeqProcessorBlock(),
            GtProcessorBlock(),
            LtProcessorBlock(),
            GeqProcessorBlock(),
            LeqProcessorBlock(),
            AndProcessorBlock(),
            OrProcessorBlock(),
            NotProcessorBlock(),
            GetValueForKeyProcessorBlock(),
            ArrayRandomProcessorBlock(),
            StoreFunctionKeyValueProcessorBlock(),
            StoreKeyValueProcessorBlock(),
        )
) {

    /**
     * Processes the [input] string using the [stringProvider] and returns the result as a trimmed
     * string.
     *
     * @param input The template string to process.
     * @param stringProvider The provider for variable resolution and storage.
     * @return The processed and trimmed string.
     */
    fun process(input: String, stringProvider: StringProvider): String = with(StringWriter()) {
        process(input, this, stringProvider)
    }.toString().trim()

    /**
     * Processes the [input] string and writes the output to the [output] Writer.
     *
     * @param input The template string to process.
     * @param output The writer to receive the processed output.
     * @param stringProvider The provider for variable resolution and storage.
     */
    fun process(input: String, output: Writer, stringProvider: StringProvider) {
        val inputIterator = InputIterator(input)

        while (inputIterator.hasNext()) {
            val nextChar = inputIterator.next()
            if (nextChar == AbstractProcessorBlock.CONTROL_CHAR) {
                // control char detected

                for (block in blocks) {
                    if (block.match(inputIterator)) {
                        block.process(output, inputIterator, this, stringProvider)
                        break
                    }
                }
            } else {
                output.append(nextChar)
            }
        }
    }
}
