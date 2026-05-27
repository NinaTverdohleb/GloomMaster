package com.rumpilstilstkin.gloomhavenhelper.localization

import com.rumpilstilstkin.gloomhavenhelper.localization.LocalizedImageResolver.Companion.localizedUrl
import com.rumpilstilstkin.gloomhavenhelper.localization.LocalizedImageResolver.Companion.parse
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class LocalizedImageResolverTest {

    @Test
    fun `parse splits a goods asset url into category and file`() {
        val parsed = parse("file:///android_asset/image/goods/item_1.webp")

        assertEquals("goods" to "item_1.webp", parsed)
    }

    @Test
    fun `parse splits a monster card asset url into category and file`() {
        val parsed = parse("file:///android_asset/image/monster_cards/ic_deck_ma_aa_1.webp")

        assertEquals("monster_cards" to "ic_deck_ma_aa_1.webp", parsed)
    }

    @Test
    fun `parse returns null for a non-asset url`() {
        assertNull(parse("https://example.com/image/goods/item_1.webp"))
    }

    @Test
    fun `parse returns null when the path is not under image`() {
        assertNull(parse("file:///android_asset/data/goods/item_1.webp"))
    }

    @Test
    fun `parse returns null when there is no file after the category`() {
        assertNull(parse("file:///android_asset/image/goods/"))
        assertNull(parse("file:///android_asset/image/goods"))
    }

    @Test
    fun `localizedUrl points at the per-locale folder when the file is present`() {
        val resolved = localizedUrl(
            assetUrl = "file:///android_asset/image/goods/item_1.webp",
            locale = "en",
            category = "goods",
            file = "item_1.webp",
            present = true,
        )

        assertEquals("file:///android_asset/image/en/goods/item_1.webp", resolved)
    }

    @Test
    fun `localizedUrl falls back to the original asset when the file is absent`() {
        val original = "file:///android_asset/image/goods/item_1.webp"

        val resolved = localizedUrl(
            assetUrl = original,
            locale = "en",
            category = "goods",
            file = "item_1.webp",
            present = false,
        )

        assertEquals(original, resolved)
    }
}
