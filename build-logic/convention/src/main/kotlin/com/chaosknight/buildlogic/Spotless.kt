package com.chaosknight.buildlogic

import com.diffplug.gradle.spotless.SpotlessExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure

internal fun Project.configureSpotlessForAndroid() {
    apply(plugin = "com.diffplug.spotless")
    extensions.configure<SpotlessExtension> {
        kotlin {
            target("src/**/*.kt")
            targetExclude(
                "**/ui/icons/**/*.kt",
                "src/test/**/*.kt",
                "src/androidTest/**/*.kt"
            )
            ktlint(libs.findVersion("ktlint").get().requiredVersion).editorConfigOverride(
                mapOf(
                    "android" to "true",
                    "ktlint_code_style" to "ktlint_official",
                    "ij_kotlin_allow_trailing_comma" to "true",
                    "ij_kotlin_allow_trailing_comma_on_call_site" to "true",
                    "ktlint_function_naming_ignore_when_annotated_with" to "Composable",
                    "ktlint_standard_annotation" to "disabled",
                    "ij_kotlin_variable_annotation_wrap" to "off",
                    "ij_kotlin_annotation_wrap" to "off",
                    "ktlint_experimental_suppress-annotation" to "enabled",
                    "ktlint_standard_backing-property-naming" to "disabled",
                ),
            )
            endWithNewline()
        }
        format("kts") {
            target("*.kts")
            endWithNewline()
        }
        format("xml") {
            target("src/**/*.xml")
            endWithNewline()
        }
    }
}
