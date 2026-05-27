package com.rumpilstilstkin.gloomhavenhelper.data

import com.rumpilstilstkin.gloomhavenhelper.localization.TranslationKeys
import org.junit.Assert.assertEquals
import org.junit.Test

class TextResolverTest {

    @Test
    fun `resolve returns the active-locale text when present`() {
        val resolver = TextResolver(
            byKey = mapOf(
                TextResolver.key(TranslationKeys.SCENARIO, "1", TranslationKeys.FIELD_NAME) to "Black Barrow",
            ),
        )

        assertEquals("Black Barrow", resolver.resolve(TranslationKeys.SCENARIO, "1", TranslationKeys.FIELD_NAME))
    }

    @Test
    fun `debug build shows a key marker when the translation is missing`() {
        val resolver = TextResolver(
            byKey = emptyMap(),
            fallbackByKey = mapOf(
                TextResolver.key(TranslationKeys.SCENARIO, "1", TranslationKeys.FIELD_NAME) to "Чёрный курган",
            ),
            markMissingKeys = true,
        )

        // Even though the source has the text, debug surfaces the gap rather than hiding it.
        assertEquals("⟦scenario:1:name⟧", resolver.resolve(TranslationKeys.SCENARIO, "1", TranslationKeys.FIELD_NAME))
    }

    @Test
    fun `release build falls back to the russian source when the translation is missing`() {
        val resolver = TextResolver(
            byKey = emptyMap(),
            fallbackByKey = mapOf(
                TextResolver.key(TranslationKeys.SCENARIO, "1", TranslationKeys.FIELD_NAME) to "Чёрный курган",
            ),
            markMissingKeys = false,
        )

        assertEquals("Чёрный курган", resolver.resolve(TranslationKeys.SCENARIO, "1", TranslationKeys.FIELD_NAME))
    }

    @Test
    fun `release build shows a key marker only when even the russian source is missing`() {
        val resolver = TextResolver(byKey = emptyMap(), fallbackByKey = emptyMap(), markMissingKeys = false)

        assertEquals("⟦monster:x:name⟧", resolver.resolve(TranslationKeys.MONSTER, "x", TranslationKeys.FIELD_NAME))
    }

    @Test
    fun `resolveAchievement resolves by catalog key`() {
        val resolver = TextResolver(
            byKey = mapOf(
                TextResolver.key(TranslationKeys.ACHIEVEMENT, "city_rule_economy", TranslationKeys.FIELD_NAME)
                    to "City Rule: Economy",
            ),
        )

        assertEquals("City Rule: Economy", resolver.resolveAchievement("city_rule_economy"))
    }

    @Test
    fun `resolveAchievement falls back to the russian source in release`() {
        val resolver = TextResolver(
            byKey = emptyMap(),
            fallbackByKey = mapOf(
                TextResolver.key(TranslationKeys.ACHIEVEMENT, "city_rule_economy", TranslationKeys.FIELD_NAME)
                    to "Управление городом: Экономика",
            ),
            markMissingKeys = false,
        )

        assertEquals("Управление городом: Экономика", resolver.resolveAchievement("city_rule_economy"))
    }

    @Test
    fun `resolveMonsterText returns the canonical content when it is not in the catalog`() {
        val resolver = TextResolver(byKey = emptyMap())

        assertEquals("неизвестный текст", resolver.resolveMonsterText("неизвестный текст"))
    }
}
