package com.rumpilstilstkin.gloomhavenhelper.bd.filler.json

import android.content.Context
import com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.models.AchievementJson
import com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.models.CharacterPerksJson
import com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.models.DeckJson
import com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.models.GameLevelJson
import com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.models.GoodJson
import com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.models.MonsterJson
import com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.models.MonsterStatsJson
import com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.models.PersonalQuestJson
import com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.models.QuestTranslationJson
import com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.models.ScenarioJson
import com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.models.ScenarioTranslationJson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JsonDataLoader @Inject constructor(
    @param:ApplicationContext private val context: Context
) {
    private val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
        coerceInputValues = true
    }

    private inline fun <reified T> load(fileName: String): T {
        val jsonString = context.assets
            .open("data/$fileName")
            .bufferedReader()
            .use { it.readText() }
        return json.decodeFromString(jsonString)
    }

    fun loadGameLevels(version: Int): List<GameLevelJson> = load("v${version}_game_levels.json")

    fun loadAchievements(version: Int): List<AchievementJson> = load("v${version}_achievements.json")

    fun loadScenarios(version: Int): List<ScenarioJson> = load("v${version}_scenarios.json")

    fun loadGoods(version: Int): List<GoodJson> = load("v${version}_goods.json")

    fun loadPerks(version: Int): List<CharacterPerksJson> = load("v${version}_perks.json")

    fun loadQuests(version: Int): List<PersonalQuestJson> = load("v${version}_quests.json")

    fun loadMonsters(version: Int): List<MonsterJson> = load("v${version}_monsters.json")

    fun loadMonsterStats(version: Int, pack: String, type: String): List<MonsterStatsJson> = load("v${version}_${pack}_${type}_stats.json")

    fun loadMonsterDeck(version: Int): List<DeckJson> = load("v${version}_ability_decks.json")

    fun loadScenarioTranslations(locale: String): Map<String, ScenarioTranslationJson> =
        load("translations/$locale/scenarios.json")

    fun loadGoodTranslations(locale: String): Map<String, String> =
        load("translations/$locale/goods.json")

    fun loadQuestTranslations(locale: String): Map<String, QuestTranslationJson> =
        load("translations/$locale/quests.json")
}
