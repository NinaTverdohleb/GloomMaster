package com.rumpilstilstkin.gloomhavenhelper.localization

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Resolves a card/goods image asset URL for the active locale, falling back to the original
 * Russian artwork when a localized file is absent.
 *
 * Layout convention (driven by the [SupportedLanguages] registry):
 * - Russian / fallback art: `image/<category>/<file>` (the existing flat folders).
 * - Localized art: `image/<locale>/<category>/<file>`.
 *
 * Given a flat asset URL such as `file:///android_asset/image/goods/item_1.webp`, [localize]
 * returns the `image/<locale>/...` variant when that file ships for the locale, otherwise the
 * input URL unchanged. This lets English (or any future-locale) artwork land incrementally
 * without ever showing a blank card.
 */
@Singleton
class LocalizedImageResolver @Inject constructor(
    @param:ApplicationContext private val context: Context,
) {

    /** Cached set of file names available under `image/<locale>/<category>`, keyed "locale/category". */
    private val available = ConcurrentHashMap<String, Set<String>>()

    fun localize(assetUrl: String, locale: String): String {
        if (locale == SupportedLanguages.FALLBACK_IMAGE_LOCALE) return assetUrl
        val (category, file) = parse(assetUrl) ?: return assetUrl
        val present = file in filesFor(locale, category)
        return localizedUrl(assetUrl, locale, category, file, present)
    }

    private fun filesFor(locale: String, category: String): Set<String> =
        available.getOrPut("$locale/$category") {
            runCatching { context.assets.list("$IMAGE_DIR/$locale/$category") }
                .getOrNull()
                ?.toSet()
                .orEmpty()
        }

    companion object {
        private const val ASSET_PREFIX = "file:///android_asset/"
        private const val IMAGE_DIR = "image"

        /**
         * Splits a flat asset URL into its `category` and `file` parts, or null if it is not an
         * `image/<category>/<file>` asset URL. Pure — no Android dependencies — so the parsing
         * and fallback contract can be unit tested.
         */
        internal fun parse(assetUrl: String): Pair<String, String>? {
            if (!assetUrl.startsWith(ASSET_PREFIX)) return null
            val rel = assetUrl.removePrefix(ASSET_PREFIX)
            if (!rel.startsWith("$IMAGE_DIR/")) return null
            val body = rel.removePrefix("$IMAGE_DIR/")
            val slash = body.indexOf('/')
            if (slash <= 0 || slash == body.lastIndex) return null
            return body.substring(0, slash) to body.substring(slash + 1)
        }

        /** Pure resolution step: the localized URL when [present], otherwise the original. */
        internal fun localizedUrl(
            assetUrl: String,
            locale: String,
            category: String,
            file: String,
            present: Boolean,
        ): String =
            if (present) "$ASSET_PREFIX$IMAGE_DIR/$locale/$category/$file" else assetUrl
    }
}
