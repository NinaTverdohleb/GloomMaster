package com.rumpilstilstkin.gloomhavenhelper.bd.filler

import android.content.SharedPreferences
import androidx.core.content.edit
import com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.AchievementJsonFiller
import com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.GameLevelInfoJsonFiller
import com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.GoodJsonFiller
import com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.MonsterJsonFiller
import com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.PerkJsonFiller
import com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.QuestJsonFiller
import com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.ScenarioJsonFiller
import javax.inject.Inject

class DatabaseFiller @Inject constructor(
    private val preferences: SharedPreferences,
    private val gameLevelInfoJsonFiller: GameLevelInfoJsonFiller,
    private val achievementJsonFiller: AchievementJsonFiller,
    private val scenarioJsonFiller: ScenarioJsonFiller,
    private val goodJsonFiller: GoodJsonFiller,
    private val perkJsonFiller: PerkJsonFiller,
    private val questJsonFiller: QuestJsonFiller,
    private val monsterJsonFiller: MonsterJsonFiller,
) {
    val version = preferences.getInt(PREFS_VERSION, 0)

    suspend fun fillDatabase() {
        var version = preferences.getInt(PREFS_VERSION, 0)
        while (version < VERSION) {
            update(version)
            version++
        }
        preferences.edit { putInt(PREFS_VERSION, VERSION) }
    }

    private suspend fun update(version: Int) {
        when (version) {
            0 -> update0()
            1 -> update1()
            else -> {}
        }
    }

    private suspend fun update0() {
        gameLevelInfoJsonFiller.fill(1)
        scenarioJsonFiller.fill(1)
        goodJsonFiller.fill(1)
        perkJsonFiller.fill(1)
        questJsonFiller.fill(1)
        monsterJsonFiller.fillDecks(1)
        monsterJsonFiller.fillMonsters(1)
        monsterJsonFiller.fillStats(
            version = 1,
            pack = "main",
            type = "base"
        )
        monsterJsonFiller.fillStats(
            version = 1,
            pack = "main",
            type = "boss"
        )
        monsterJsonFiller.fillStats(
            version = 1,
            pack = "forgotten_circles",
            type = "base"
        )
        monsterJsonFiller.fillStats(
            version = 1,
            pack = "forgotten_circles",
            type = "boss"
        )
        achievementJsonFiller.fill(1)
    }

    private suspend fun update1() {
        monsterJsonFiller.fixLivingSpiritDeck()
    }

    companion object {
        private const val VERSION = 2
        private const val PREFS_VERSION = "filler_version"
    }
}
