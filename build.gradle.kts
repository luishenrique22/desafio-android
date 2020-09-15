import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

// Top-level build file where you can add configuration options common to all sub-projects/modules.

ktlint {
    version.set(Versions.ktlint)
    debug.set(true)
    verbose.set(true)
    android.set(false)
    outputToConsole.set(true)
    reporters {
        setOf(ReporterType.PLAIN, ReporterType.CHECKSTYLE)
    }
    ignoreFailures.set(true)
    kotlinScriptAdditionalPaths {
        include(fileTree("scripts/"))
    }
    filter {
        exclude("**/generated/**")
        include("**/kotlin/**")
    }
}

buildscript {

    repositories {
        google()
        jcenter()

    }
    dependencies {
        classpath(Libs.com_android_tools_build_gradle)
        classpath(Libs.kotlin_gradle_plugin)
        classpath(Libs.ktlint_gradle)
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven {
            url = uri("https://plugins.gradle.org/m2/")
        }

    }
}
plugins {
    id("de.fayard.buildSrcVersions") version "0.6.1"
    id("org.jlleitschuh.gradle.ktlint") version "9.0.0"

}
tasks.register("clean",Delete::class){
    delete(rootProject.buildDir)
}

apply(plugin = "org.jlleitschuh.gradle.ktlint")