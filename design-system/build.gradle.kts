plugins {
    alias(libs.plugins.chaosknight.android.library.compose)
    alias(libs.plugins.roborazzi)
}

android {
    namespace = "com.rumpilstilstkin.gloomhavenhelper.designsystem"
    testOptions.unitTests.isIncludeAndroidResources = true
}

dependencies {
    api(libs.androidx.material3)
    api(libs.androidx.material.icons.extended)
    api(libs.android.material)
    implementation(libs.androidx.core.ktx)
}

