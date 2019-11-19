# StringProcessor

## What is StringProcessor
[![](https://jitpack.io/v/arnoid/string.processor.svg)](https://jitpack.io/#arnoid/string.processor)

StringProcessor is a tool for procedural string generation.

### Features
* storing generated values
* conditional statements
* recursive processing

## How to

### Gradle

* Add the JitPack repository to your build file
```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
* Add the dependency
```
dependencies {
    implementation 'com.github.arnoid:string.processor:1.0.2'
}
```

### Coding

`StringProcessor` has following functions:
* `init(initArray: Array<String>, stringProvider: StringProcessorValueProvider)` - this method should be used for initialization of the `StringValueValueProvider`. All strings will be processed in given sequence. It is advised to use this function to init variables. After processing of any single string returned result is disbanded.
* `process(input: String, stringProvider: StringProcessorValueProvider): String` - this function will return result of the processing of the `input` string.

### Supported tags

All tags start with `$` sign.

#### Escaped $

* Format: `$$`
* Description: this tag is used to insert `$` and avoid processing.

#### Random Array Item

* Format: `$rnd{value1|value2|...|valueN}`
* Description: tag will split its content by `|` char, pick random item evaluate it and return it.

#### Set `value` for `key`
* Format: `$key:{value}`
* Description: results of `value` processing will be put into `StringProcessorValueProvider.set(key, value)`.

#### Get `value` for `key`
* Format: `${key}`
* Description: processor will process `key` and will call `StringProcessorValueProvider.get(key)` with evaluated key. Any value returned by `StringProcessorValueProvider` will be processed and injected into output. 

#### IF tag
* Format: `$if{if_condition}{left_expression}{right_expression}`
* Description: processor will do following steps:
    1. read `if_condition`
    2. read `left_expression`
    3. read `right_expression`
    4. processor will evaluate `if_condition`. Evaluation result will be equal `true` if evaluation result will be a word `true` ignoring case and `false` otherwise.
        1. if `if_condition` evaluation result is `true` then `left_expression` will be processed and injected into result 
        2. if `if_condition` evaluation result is `false` then `right_expression` will be processed and injected into result

#### EQ tag
* Format: `$eq{left_expression}{right_expression}`
* Description: this will return `true` if evaluated results of `left_expression` and `right_expression` statements are equal, `false` otherwise.

#### NEQ tag
* Format: `$neq{left_expression}{right_expression}`
* Description: this will return `true` if evaluated results of `left_expression` and `right_expression` statements are not equal, `false` otherwise. 

#### GT tag
* Format: `$gt{left_expression}{right_expression}`
* Works for numeric evaluation results only. If result of evaluation of `left_expression` or `right_expression` is a string, then tag will return `false`. 
* Description: this will return `true` if evaluated result of `left_expression` is greater than evaluated result of `right_expression`, `false` otherwise.

#### LT tag
* Format: `$lt{left_expression}{right_expression}`
* Works for numeric evaluation results only. If result of evaluation of `left_expression` or `right_expression` is a string, then tag will return `false`. 
* Description: this will return `true` if evaluated result of `left_expression` is lesser than evaluated result of `right_expression`, `false` otherwise.

#### GEQ tag
* Format: `$geq{left_expression}{right_expression}`
* Works for numeric evaluation results only. If result of evaluation of `left_expression` or `right_expression` is a string, then tag will return `false`. 
* Description: this will return `true` if evaluated result of `left_expression` is greater than or equal to evaluated result of `right_expression`, `false` otherwise.

#### LEQ tag
* Format: `$leq{left_expression}{right_expression}`
* Works for numeric evaluation results only. If result of evaluation of `left_expression` or `right_expression` is a string, then tag will return `false`. 
* Description: this will return `true` if evaluated result of `left_expression` is lesser than or equal to evaluated result of `right_expression`, `false` otherwise.

#### AND tag
* Format: `$and{left_expression}{right_expression}`
* Description: this will return the result of applying logical `AND` operator to evaluated results of `left_expression` and `right_expression`. Any of expressions are evaluated to `true` if evaluation result is a word `true`, `false` otherwise.

#### OR tag
* Format: `$or{left_expression}{right_expression}`
* Description: this will return the result of applying logical `OR` operator to evaluated results of `left_expression` and `right_expression`. Any of expressions are evaluated to `true` if evaluation result is a word `true`, `false` otherwise.

### Nesting and Recursion

It is possible to use tags recursively. For example:

```
$gender:{${random_gender}}
$gender_proform: {
    $if {
        $eq {${gender}} {female}
    }
    {She}
    {He}
}
${gender_proform} was processed.
```

This example will return `She was processed.` or `He was processed`. 
