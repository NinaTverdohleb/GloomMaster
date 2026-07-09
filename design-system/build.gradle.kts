plugins {
    alias(libs.plugins.chaosknight.android.library.compose)
    alias(libs.plugins.roborazzi)
}

android {
    namespace = "com.rumpilstilstkin.gloommaster.designsystem"
    testOptions.unitTests.isIncludeAndroidResources = true
}

dependencies {
    implementation(project(":test-tags"))
    api(libs.androidx.material3)
    api(libs.androidx.material.icons.extended)
    api(libs.android.material)
    implementation(libs.androidx.core.ktx)
}
