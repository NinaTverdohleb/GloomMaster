package com.rumpilstilstkin.gloomhavenhelper.localization

import com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.models.CharacterPerksJson
import com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.models.QuestTranslationJson
import com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.models.ScenarioTranslationJson
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Assume.assumeTrue
import org.junit.Test
import java.io.File

/**
 * Verifies every supported language fully covers the translation store — the Phase 9 completeness
 * + Forgotten Circles check — and doubles as the maintainer report: on a gap the assertion message
 * lists the untranslated keys per locale. Reads the shipped dictionary assets directly (no Android
 * runtime), flattening them exactly as [com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.TranslationJsonFiller] does.
 */
class TranslationDictionaryCoverageTest {

    private val json = Json { ignoreUnknownKeys = true }

    @Test
    fun `untranslatedByLocale flags keys missing or blank against the other locale`() {
        val gaps = TranslationCoverage.untranslatedByLocale(
            mapOf(
                "ru" to mapOf("a" to "ru-a", "b" to "ru-b", "c" to ""),
                "en" to mapOf("a" to "en-a", "b" to ""),
            )
        )

        assertEquals(listOf("b"), gaps.getValue("en"))
        assertEquals(emptyList<String>(), gaps.getValue("ru"))
    }

    @Test
    fun `every supported locale fully covers the translation store including Forgotten Circles`() {
        val root = translationsRoot()
        assumeTrue("translation assets not found from ${File(".").absolutePath}", root != null)

        val byLocale = SupportedLanguages.contentLocales.associateWith { locale ->
            flatten(root!!.resolve(locale))
        }

        val gaps = TranslationCoverage.untranslatedByLocale(byLocale).filterValues { it.isNotEmpty() }

        assertTrue(report(gaps), gaps.isEmpty())
    }

    private fun flatten(dir: File): Map<String, String> = buildMap {
        decode<Map<String, ScenarioTranslationJson>>(dir.resolve("scenarios.json")).forEach { (number, text) ->
            put(key(TranslationKeys.SCENARIO, number, TranslationKeys.FIELD_NAME), text.name)
            if (text.location.isNotBlank()) {
                put(key(TranslationKeys.SCENARIO, number, TranslationKeys.FIELD_LOCATION), text.location)
            }
        }
        decode<Map<String, String>>(dir.resolve("goods.json")).forEach { (number, name) ->
            put(key(TranslationKeys.GOOD, number, TranslationKeys.FIELD_NAME), name)
        }
        decode<Map<String, QuestTranslationJson>>(dir.resolve("quests.json")).forEach { (questId, quest) ->
            put(key(TranslationKeys.QUEST, questId, TranslationKeys.FIELD_TITLE), quest.title)
            if (quest.description.isNotBlank()) {
                put(key(TranslationKeys.QUEST, questId, TranslationKeys.FIELD_DESCRIPTION), quest.description)
            }
            if (quest.special.isNotBlank()) {
                put(key(TranslationKeys.QUEST, questId, TranslationKeys.FIELD_SPECIAL), quest.special)
            }
            quest.tasks.forEach { (taskId, text) ->
                put(key(TranslationKeys.QUEST_TASK, "$questId:$taskId", TranslationKeys.FIELD_TEXT), text)
            }
        }
        decode<List<CharacterPerksJson>>(dir.resolve("perks.json"))
            .flatMap { it.perks }
            .forEachIndexed { index, perk ->
                put(key(TranslationKeys.PERK, (index + 1).toString(), TranslationKeys.FIELD_TEXT), perk.text)
            }
        decode<Map<String, String>>(dir.resolve("achievements.json")).forEach { (catalogKey, name) ->
            put(key(TranslationKeys.ACHIEVEMENT, catalogKey, TranslationKeys.FIELD_NAME), name)
        }
        decode<Map<String, String>>(dir.resolve("monsters.json")).forEach { (catalogKey, name) ->
            put(key(TranslationKeys.MONSTER, catalogKey, TranslationKeys.FIELD_NAME), name)
        }
        decode<Map<String, String>>(dir.resolve("monster_texts.json")).forEach { (catalogKey, text) ->
            put(key(TranslationKeys.MONSTER_TEXT, catalogKey, TranslationKeys.FIELD_TEXT), text)
        }
    }

    private inline fun <reified T> decode(file: File): T = json.decodeFromString(file.readText())

    private fun key(entityType: String, entityKey: String, fieldName: String) = "$entityType|$entityKey|$fieldName"

    private fun translationsRoot(): File? =
        listOf("src/main/assets/data/translations", "app/src/main/assets/data/translations")
            .map(::File)
            .firstOrNull { it.isDirectory }

    private fun report(gaps: Map<String, List<String>>): String = buildString {
        appendLine("Untranslated translation-store keys per locale:")
        gaps.toSortedMap().forEach { (locale, keys) ->
            appendLine("  [$locale] ${keys.size} untranslated:")
            keys.forEach { appendLine("    $it") }
        }
    }
}
