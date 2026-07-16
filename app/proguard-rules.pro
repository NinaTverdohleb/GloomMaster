# Android-specific rules

-keep class androidx.startup.** { *; }

-dontwarn androidx.lifecycle.**

# Generic Obfuscation
-repackageclasses ''
-allowaccessmodification

# Remove debug information
-renamesourcefileattribute ''
-keepattributes Signature,Exceptions,InnerClasses
-keepattributes *Annotation*

# Keep line numbers and source file attribute so Crashlytics can
# deobfuscate stack traces with line numbers on minified release builds.
-keepattributes SourceFile,LineNumberTable

# --- Library-specific rules ---

# Kotlin Coroutines
# https://github.com/Kotlin/kotlinx.coroutines/blob/master/kotlinx-coroutines-core/jvm/resources/META-INF/proguard/coroutines.pro
-keep class kotlinx.coroutines.internal.MainDispatcherFactory { <init>(); }
-keep class kotlinx.coroutines.android.AndroidDispatcherFactory { <init>(); }
-keep class kotlinx.coroutines.android.AndroidExceptionPreHandler { <init>(); }

# Kotlinx Serialization
# https://github.com/Kotlin/kotlinx.serialization/blob/master/docs/proguard.md
-keepclassmembers class ** {
    @kotlinx.serialization.SerialName *;
}
-keepclassmembers class * {
    @kotlinx.serialization.Serializable <fields>;
    kotlinx.serialization.KSerializer serializer(...);
}
-keepclassmembers class *$$serializer {
    <methods>;
}

# Retrofit & OkHttp
# https://github.com/square/retrofit/blob/master/retrofit/src/main/resources/META-INF/proguard-rules.pro
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}
-dontwarn okio.**
-dontwarn org.conscrypt.**

# Jetpack Compose
-keep class androidx.compose.runtime.internal.ComposableLambda { *; }
-keepclassmembers class * {
    @androidx.compose.runtime.Composable <methods>;
}
-keepclassmembers class * {
    @androidx.compose.ui.tooling.preview.Preview <methods>;
    @androidx.compose.ui.tooling.preview.Preview <fields>;
}
-keepclassmembers class **.R$* {
    public static <fields>;
}
