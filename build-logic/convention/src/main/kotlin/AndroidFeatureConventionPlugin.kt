import com.android.build.api.dsl.LibraryExtension
import com.chaosknight.buildlogic.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "chaosknight.android.library.compose")
            apply(plugin = "chaosknight.hilt")

            extensions.configure<LibraryExtension> {
                testOptions.animationsDisabled = true
            }

            dependencies {
                "implementation"(project(":core:designsystem"))

                "implementation"(libs.findLibrary("androidx.lifecycle.runtime.ktx").get())
                "implementation"(libs.findLibrary("androidx.lifecycle.viewmodel.compose").get())
                "implementation"(libs.findLibrary("hilt.navigation.compose").get())
            }
        }
    }
}
