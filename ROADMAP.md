# String Processor Roadmap: Improvements & New Features

This document outlines proposed improvements and new features for the `string.processor` Kotlin project.

---

## 📋 Proposed Improvements

### 1. Architectural & Code Quality
*   **Abstract Common Logic**: Move repetitive logic (like `inputIterator.skip(tagName())`) from individual blocks (e.g., `Eq`, `Gt`, `Lt`) into a template method in `AbstractProcessorBlock` to reduce boilerplate.
*   **Enhanced Error Handling**: Introduce specific exceptions like `MalformedTagException` for cases like unclosed braces or invalid syntax, rather than failing silently or returning empty strings.
*   **Boolean Evaluation Refinement**: Improve `IfElseProcessorBlock` logic to support "truthy" values (non-empty strings, non-zero numbers) instead of strictly relying on `String.toBoolean()`.
*   **Block Matching Optimization**: Transition from linear block iteration in `StringProcessor` to a Map-based lookup or trie for faster tag matching.
*   **KDoc Documentation**: Add comprehensive KDoc comments to all classes and methods to document the DSL syntax and internal API.

### 2. Parser Enhancements
*   **Advanced Iterator**: Upgrade `InputIterator` to support `peek()`, `mark()`, and `reset()`. This will simplify the implementation of complex multi-character tags.
*   **Nested Tag Stability**: Ensure consistent behavior across all blocks when tags are deeply nested inside logic blocks like `$when` or `$if`.

---

## 🚀 Proposed New Features

### 1. Core DSL Features
*   **Default Values**: Support syntax for fallback values, e.g., `${key|default_value}`.
*   **JSON/Nested Access**: Enable accessing properties of stored objects, e.g., `${user.profile.id}`.
*   **Loops (Foreach)**: Implement an iteration block, e.g., `$foreach{item:list}{...content...}`.
*   **Scoped Variables**: Support local variable scopes within blocks to prevent variable leakage.

### 2. Utility Blocks
*   **String Manipulation**:
    *   `$upper{text}` / `$lower{text}`: Case conversion.
    *   `$trim{text}`: Removing surrounding whitespace.
    *   `$replace{original}{search}{replace}`: String replacement.
*   **Mathematical Operations**:
    *   `$calc{expression}` or functional tags like `$add{v1}{v2}`, `$sub{v1}{v2}`.
*   **Date & Time**:
    *   `$now{format}`: Print the current timestamp in a specific format.
*   **Comments**:
    *   `$#{ comment content }`: A block that is completely ignored during processing.

### 3. Integration & Tooling
*   **Environment Provider**: A `StringProvider` implementation that reads from System Environment variables or `.properties` files.
*   **Template Includes**: A `$include{path/to/file}` block to allow modular template design.
*   **Strict Mode**: An optional configuration flag for `StringProcessor` that throws an error when a requested key is missing from the provider.
