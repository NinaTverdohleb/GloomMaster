// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {

    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.android.test) apply false
    alias(libs.plugins.baselineprofile) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.dependencyGuard) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
    alias(libs.plugins.firebase.perf) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.roborazzi) apply false
    alias(libs.plugins.room) apply false
    alias(libs.plugins.spotless) apply false
}

val globalTasksGroup = "GloomMasterTasks"

val verifyAll = tasks.register("verifyAll") {
    group = globalTasksGroup
    description = "Runs all critical checks: spotless, lint, dependency guard, and tests."

    val allSpotless = allprojects.map { it.tasks.matching { t -> t.name == "spotlessCheck" } }
    val allTests = allprojects.map { it.tasks.matching { t -> t.name.matches(Regex("test(DebugUnitTest)?")) } }
    val allLint = allprojects.map { it.tasks.matching { t -> t.name == "lintDebug" } }
    val allDepGuard = allprojects.map { it.tasks.matching { t -> t.name == "dependencyGuard" } }

    dependsOn(allSpotless, allDepGuard, allLint, allTests)
}!!

subprojects {
    val spotless = tasks.matching { it.name == "spotlessCheck" }
    val lint = tasks.matching { it.name == "lintDebug" }
    val test = tasks.matching { it.name.matches(Regex("test(DebugUnitTest)?")) }

    lint.configureEach { mustRunAfter(spotless) }
    test.configureEach { mustRunAfter(lint) }

    tasks.withType<com.diffplug.gradle.spotless.SpotlessTask>().configureEach {
        notCompatibleWithConfigurationCache("Spotless has configuration cache issues")
    }
}
