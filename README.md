# ck-gradle-plugin
Gradle plugin to calculate CK metrics using https://github.com/mauricioaniche/ck

# How to build it:
    ./gradlew install

# How to use it:

* in build.gradle under buildscript/repositories add

        mavenLocal()

* in buildscript/dependencies add

        classpath 'com.github.jazzmuesli:ck-gradle-plugin:1.0-SNAPSHOT'

* Add in your allprojects:

        apply plugin: 'java-gradle-plugin'
        apply plugin: 'CkGradlePlugin'


# TODO

* Publish this plugin to https://plugins.gradle.org/
* Figure out how to apply it without modifying build.gradle
* Figure out how to avoid apply plugin java-gradle-plugin in client build.gradle
* Pull requests are more than welcome.

