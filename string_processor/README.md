# StringProcessor

## How to

### `StringProcessor`

`StringProcessor` has following functions:
* `init(initArray: Array<String>, stringProvider: StringProvider)` - this method should be used for initialization of the `StringProvider`. All strings will be processed in given sequence. It is advised to use this function to init variables. After processing of any single string returned result is disbanded.
* `process(input: String, stringProvider: StringProvider): String` - this function will return result of the processing of the `input` string.

### StringProvider

You can instantiate `DictionaryStringProvider` by calling a constructor:
```kotlin
DictionaryStringProvider(
    dictionary = mutableMapOf<String,String>(), // optional, defaults to empty map,
    emptyValue = "empty"  // optional, defaults to DictionaryStringProvider.DEFAULT_EMPTY_VALUE
)
```

Alternatively, you can use factory function:
```kotlin
DictionaryStringProvider.from(
    pairs = "a" to "A", // vararg pairs list 
            "b" to "B",
    emptyValue = "empty"// empty value, defaults to DictionaryStringProvider.DEFAULT_EMPTY_VALUE
    
)
```

## Supported tags

All tags start with `$` sign.

### Escaped $

* Format: `$$`
* Description: this tag is used to insert `$` and avoid processing.
* Example:
  * Template: 
    ```text
    $${abcd}$$
    ```
  * Result:
    ```text
    ${abcd}$
    ```

### Random Array Item

* Format: `$rnd{value1|value2|...|valueN}`
* Description: tag will split its content by `|` char, pick random item, evaluate it and return it.
* Example:
  * Template:
    ```text
    $rnd{a|b|c|d}
    ```
  * Result: random item from array will be selected
    ```text
    c
    ```

### Function
* Format: `$funName={{value}}`
* Description: value will be put into `StringProvider.set(key, value)` without processing. This will lead to behavior when function body will be evaluated whenever function will be called.
* Example:
    * Template:
      ```text
      $function1={{abcd}}
      $function2={{${function1}}}
      ${function2}
      ```
    * Result: 
      ```text
      abcd
      ```

### Set `value` for `key`
* Format: `$key={value}`
* Description: results of `value` processing will be put into `StringProvider.set(key, value)`.
* Example:
    * Template:
      ```text
      $key={abcd}
      ```
    * Result: No output.
      ```text
      ```

### Get `value` for `key`
* Format: `${key}`
* Description: processor will process `key` and will call `StringProvider.get(key)` with evaluated key. Any value returned by `StringProvider` will be processed and injected into output.
* Example:
    * Template:
      ```text
      $key={abcd}
      ${key}
      ```
    * Result: 
      ```text
      abcd
      ```

### IF tag
* :no_entry: tag was modified in `1.3`
* Format: 
  * ```
    $if{if_condition}{statement}
    ```
  * ```
    $if{if_condition}{statement}
    $else{else_statement}
    ```
  * ```
    $if{if_condition}{statement}
    $elseIf{if_condition}{else_if_statement}
    $else{else_statement}
    ```
  * `$else` is optional
* Description: processor will do following steps:
    1. read initial `$if` tag
       1. read `if_condition`
       2. read `statement`
    2. for each `$elseif` tag repeat step 1
    3. read `$else` `statement`
    4. iterate over collected pairs of `if_condition` and `statement`
       1. if pair `if_condition` evaluation result is `true` then this pair `statement` will be processed and returned as result
       2. if pair `if_condition` evaluation result is `false` then iterate to next pair
    5. if no `if_condition`s were evaluated as `true`, then `$else` statement will be evaluated and returned as result
* Example - regular `if`:
    * Template:
      ```text
      $if{true}{if_statement}
      ```
    * Result:
      ```text
      if_statement
      ```
* Example - `if-else`:
  * Template:
    ```text
    $if{false}{if_statement}
    $else{else_statement}
    ```
  * Result:
    ```text
    else_statement
    ```
* Example - `if-elseif-else`:
    * Template:
      ```text
      $if{false}{if_statement}
      $elseif{true}{elseif_statement}
      $else{else_statement}
      ```
    * Result:
      ```text
      elseif_statement
      ```

### WHEN tag
* Format:
    ```
    $when{when_value_statement}
    $case{case_value_condition}{case_statement}
    $else{else_statement}
    ```
  `$else` is optional
* Description: processor will do following steps:
    1. read initial `$when` tag `when_value_statement`
    2. for each `$case` tag
       1. read `case_value_condition`
       2. read `case_statement`
    3. read `$else` `statement`
    4. evaluate `when_value_statement` statement
    5. iterate over collected pairs of `casecase_value_condition` and `case_statement`
        1. if pair `casecase_value_condition` evaluation result equals to result from step 4 then `case_statement` will be evaluated and returned as result
        2. if pair `casecase_value_condition` evaluation result not equals to  from step 4 then iterate to next pair
    6. if no `case`s evaluation results were equal to `when_value_statement` evaluation result , then `$else` statement will be evaluated and returned as result
* Example
    * Template:
      ```text
      $when{1}
      $case{0}{value_0}
      $case{1}{value_1}
      $case{2}{value_2}
      $else{else_value}
      ```
    * Result:
      ```text
      value_1
      ```

### EQ tag
* Format: `$eq{left_expression}{right_expression}`
* Description: this will return `true` if evaluated results of `left_expression` and `right_expression` statements are equal, `false` otherwise.
* Example:
    * Template:
      ```text
      $eq{abcd}{abcd}
      ```
    * Result:
      ```text
      true
      ```

### NEQ tag
* Format: `$neq{left_expression}{right_expression}`
* Description: this will return `true` if evaluated results of `left_expression` and `right_expression` statements are not equal, `false` otherwise.
* Example:
    * Template:
      ```text
      $neq{abcd}{abcd}
      ```
    * Result: 
      ```text
      false
      ```

### AND tag
* Format: `$and{left_expression}{right_expression}`
* :warning: Works for boolean (`true` \ `false`) evaluation results only. If result of evaluation of `left_expression` or `right_expression` is not a boolean string, then tag will return `false`.
* Description: this will return the result of applying logical `AND` operator to evaluated results of `left_expression` and `right_expression`. Any of expressions are evaluated to `true` if evaluation result is a word `true`, `false` otherwise.
* Example:
    * Template:
      ```text
      $and{true}{false}
      ```
    * Result: 
      ```text
      false
      ```

### OR tag
* Format: `$or{left_expression}{right_expression}`
* :warning: Works for boolean (`true` \ `false`) evaluation results only. If result of evaluation of `left_expression` or `right_expression` is not a boolean string, then tag will return `false`.
* Description: this will return the result of applying logical `OR` operator to evaluated results of `left_expression` and `right_expression`. Any of expressions are evaluated to `true` if evaluation result is a word `true`, `false` otherwise.
* Example:
    * Template:
      ```text
      $or{true}{false}
      ```
    * Result: 
      ```text
      true
      ```

### GT tag
* Format: `$gt{left_expression}{right_expression}`
* :warning: Works for integer evaluation results only. If result of evaluation of `left_expression` or `right_expression` is a non-integer string, then tag will return `false`. 
* Description: this will return `true` if evaluated result of `left_expression` is greater than evaluated result of `right_expression`, `false` otherwise.
* Example:
    * Template:
      ```text
      $gt{2}{1}
      ```
    * Result: 
      ```text
      true
      ```

### LT tag
* Format: `$lt{left_expression}{right_expression}`
* :warning: Works for `integer` evaluation results only. If result of evaluation of `left_expression` or `right_expression` is a non-integer string, then tag will return `false`. 
* Description: this will return `true` if evaluated result of `left_expression` is lesser than evaluated result of `right_expression`, `false` otherwise.
* Example:
    * Template:
      ```text
      $lt{2}{1}
      ```
    * Result: 
      ```text
      false
      ```

### GEQ tag
* Format: `$geq{left_expression}{right_expression}`
* :warning: Works for `integer` evaluation results only. If result of evaluation of `left_expression` or `right_expression` is a non-integer string, then tag will return `false`. 
* Description: this will return `true` if evaluated result of `left_expression` is greater than or equal to evaluated result of `right_expression`, `false` otherwise.
* Example:
    * Template:
      ```text
      $geq{2}{1}
      ```
    * Result: 
      ```text
      true
      ```

### LEQ tag
* Format: `$leq{left_expression}{right_expression}`
* :warning: Works for integer evaluation results only. If result of evaluation of `left_expression` or `right_expression` is a non-integer string, then tag will return `false`. 
* Description: this will return `true` if evaluated result of `left_expression` is lesser than or equal to evaluated result of `right_expression`, `false` otherwise.
* Example:
    * Template:
      ```text
      $leq{2}{1}
      ```
    * Result: 
      ```text
      true
      ```

## Nesting and Recursion

It is possible to use tags recursively. For example:

```
$random_gender=$end{male|female}
$gender={${random_gender}}
$gender_proform={
    $if {
        $eq {${gender}} {female}
    }
    {She}
    {He}
}
${gender_proform} was processed.
```

This example will return `She was processed.` or `He was processed`.

## Function or Key-For-Value

Please compare:
* Function statement:
    ```text
    $function={{${key}}}
    ```
  * `StringProvider` will be called 
    ```kotlin
    StringProvider.set("function", "${key}")
    ```
    :warning: Please note, that expression value **was not evaluated** before.
* Key-For-Value statement
    ```text
    $key={value}
    ```
  * `StringProvider` will be called
    ```kotlin
    StringProvider.set("key", "value")
    ```
    :warning: Please note, that expression value **was evaluated** before assignment.


The difference is in fact that `function` body will be stored without evaluation, so its body will be evaluated on each access to that function. While `key-for-value` body will be evaluated before storing to `StringProvider`.