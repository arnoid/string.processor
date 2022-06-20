# StringProcessor

## What is StringProcessor
![GitHub tag (latest by date)](https://img.shields.io/github/v/tag/arnoid/string.processor)

StringProcessor is a tool for procedural string generation.

### Features
* key-value storage
* functions
* conditional statements
* recursive processing

## How to

### Gradle

* Add the JitPack repository to your build file
```groovy
repositories {
    maven { url 'https://jitpack.io' }
}
```
Alternatively Github Packages
```groovy
repositories {
    maven {
        url = uri("https://maven.pkg.github.com/arnoid/string.processor")
        credentials {
            username = project.findProperty("gpr.user") ?: System.getenv("USERNAME")
            password = project.findProperty("gpr.key") ?: System.getenv("TOKEN")
        }
    }
}
```
* Add the dependency
```groovy
dependencies {
    implementation 'com.github.arnoid:string.processor:x.x.x'
}
```

### Using

* Check [documentation](string_processor/README.md):