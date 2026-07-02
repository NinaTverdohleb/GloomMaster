package com.chaosknight.buildlogic

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension

internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension,
) {
    commonExtension.buildFeatures.compose = true

    dependencies {
        val bom = libs.findLibrary("androidx-compose-bom").get()
        add("implementation", platform(bom))
        add("androidTestImplementation", platform(bom))
        add("implementation", libs.findLibrary("androidx-ui-tooling-preview").get())
        add("debugImplementation", libs.findLibrary("androidx-ui-tooling").get())
    }

    extensions.configure<ComposeCompilerGradlePluginExtension> {
        // Enable with: ./gradlew :app:assembleRelease -PenableComposeCompilerReports=true
        // Output lands in <module>/build/compose-compiler/ (metrics + stability reports).
        if (project.providers.gradleProperty("enableComposeCompilerReports").orNull == "true") {
            val outputDir = layout.buildDirectory.dir("compose-compiler")
            reportsDestination.set(outputDir)
            metricsDestination.set(outputDir)
        }
    }
}
