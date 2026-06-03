import com.diffplug.gradle.spotless.SpotlessExtension
import org.gradle.kotlin.dsl.configure

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.hilt)
    alias(libs.plugins.room)
    alias(libs.plugins.spotless)
}

android {
    enableKotlin = true
    namespace = "com.rumpilstilstkin.gloomhavenhelper"
    compileSdk = 37

    defaultConfig {
        applicationId = "com.rumpilstilstkin.gloommaster"
        minSdk = 31
        versionCode = 1
        versionName = "0.0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            applicationIdSuffix = ".debug"
            manifestPlaceholders["appLabelSuffix"] = " (Debug)"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

room {
    schemaDirectory("$projectDir/schemas")
}

kotlin {
    compilerOptions {
        languageVersion = org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_3
    }
}

configure<SpotlessExtension> {
    kotlin {
        target("src/**/*.kt")
        targetExclude(
            "**/ui/icons/**/*.kt",
            "src/test/**/*.kt",
            "src/androidTest/**/*.kt"
        )
        val ktlintVersion = extensions.getByType<VersionCatalogsExtension>()
            .named("libs")
            .findVersion("ktlint")
            .get()
            .requiredVersion

        ktlint(ktlintVersion).editorConfigOverride(
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
            )
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

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.splashscreen)
    implementation(libs.androidx.window.size)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.collections.immutable)
    implementation(libs.coil.compose)

    ksp(libs.hilt.compiler)
    ksp(libs.hilt.ext.compiler)
    implementation(libs.hilt.android)
    implementation(libs.hilt.core)

    ksp(libs.room.compiler)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)

    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.turbine)
    testImplementation(libs.mockk)
    testImplementation(libs.strikt.core)
    testImplementation(libs.hilt.android.testing)

    androidTestImplementation(libs.room.test)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
