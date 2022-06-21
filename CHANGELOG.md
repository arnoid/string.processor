# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

## [1.3.0] - 2022-06-21
### Added
* WHEN tag implementation `$when{value}$case{case_value}{case_statement}$else{else_statement}`. See documentation for more details.
* IF tag implementation `$if{condition}{statement}$elseif{elseif_condition}{elseif_statement}$else{else_statement}`. See documentation for more details.
### Removed
* IF tag implementation `$if{condition}{left_statement}{right_statement}`

## [1.2.1] - 2022-06-18
### Changed
* Updated dependencies

## [1.2.0] - 2020-02-21
### Added
* Functions support
### Changed
* value for key declaration changed from `:` char to `=` char

## [1.1.2] - 2019-11-26
### Changed
* Moved content trimming to `StringProcessor` level 

## [1.1.1] - 2019-11-19
### Changed
* boolean statements now are trimmed after evaluation 

## [1.1.0] - 2019-11-18
### Changed
* Separated tags processors to separate processor blocks
### Added
* Random array block processor

## [1.0.3] - 2019-10-28
### Changed
* Converted `DictionaryStringProvider` to `open` class

## [1.0.2] - 2019-10-25
### Added
* `DictionaryStringProvider` implementation

## [1.0.1] - 2019-10-25
### Changed
* Renamed `ValuePeovider` to `StringProvider`

## [1.0.0] - 2019-10-24
* Initial release