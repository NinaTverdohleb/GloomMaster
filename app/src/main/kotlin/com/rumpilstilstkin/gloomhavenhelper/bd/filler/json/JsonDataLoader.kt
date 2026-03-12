package com.rumpilstilstkin.gloomhavenhelper.bd.filler.json

import android.content.Context
import com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.models.AchievementJson
import com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.models.GameLevelJson
import com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.models.GoodJson
import com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.models.ScenarioJson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JsonDataLoader @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
        classDiscriminator = "kind"
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

    fun loadAbilityDecks(): List<AbilityDeckJson> = load("ability_decks.json")

    fun loadMonsters(): List<MonsterJson> = load("monsters.json")

    fun loadScenarioMonsters(): List<ScenarioMonstersJson> = load("scenario_monsters.json")
}
