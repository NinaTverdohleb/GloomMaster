package com.rumpilstilstkin.gloomhavenhelper.localization

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf

/**
 * Active content locale for the composition. Provided at the app root from the reactive
 * [LocaleSource]; reading it makes a composable re-resolve when the user switches language.
 */
val LocalContentLocale: ProvidableCompositionLocal<String> =
    compositionLocalOf { SupportedLanguages.DEFAULT }

/** The app's [LocalizedImageResolver], provided at the root so leaf composables can resolve art. */
val LocalImageResolver: ProvidableCompositionLocal<LocalizedImageResolver?> =
    staticCompositionLocalOf { null }

/**
 * Resolves [assetUrl] (a flat `file:///android_asset/image/...` URL) to the artwork for the
 * active [LocalContentLocale], falling back to the original Russian asset. Recomputes — and so
 * reloads the image — whenever the active locale changes.
 */
@Composable
fun rememberLocalizedAsset(assetUrl: String): String {
    val locale = LocalContentLocale.current
    val resolver = LocalImageResolver.current
    return remember(assetUrl, locale, resolver) {
        resolver?.localize(assetUrl, locale) ?: assetUrl
    }
}
