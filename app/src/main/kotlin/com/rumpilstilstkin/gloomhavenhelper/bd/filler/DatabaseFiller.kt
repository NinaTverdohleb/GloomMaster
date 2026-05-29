package com.rumpilstilstkin.gloomhavenhelper.bd.filler

import android.content.SharedPreferences
import androidx.core.content.edit
import com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.AchievementJsonFiller
import com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.GameLevelInfoJsonFiller
import com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.GoodJsonFiller
import com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.LocationJsonFiller
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
    private val locationJsonFiller: LocationJsonFiller,
) {
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
            4 -> {
                fillMain()
                fillForgottenCircles()
            }
            else -> {}
        }
    }

    private suspend fun fillMain() {
        val pack = "main"
        gameLevelInfoJsonFiller.fill(pack)
        scenarioJsonFiller.fill(pack)
        goodJsonFiller.fill(pack)
        locationJsonFiller.fill(pack)
        achievementJsonFiller.fill(pack)
        perkJsonFiller.fill(pack)
        questJsonFiller.fill(pack)
        monsterJsonFiller.fillDecks(pack)
        monsterJsonFiller.fillMonsters(pack)
        monsterJsonFiller.fillStats(pack)
    }

    private suspend fun fillForgottenCircles() {
        val pack = "forgottenCircles"
        scenarioJsonFiller.fill(pack)
        goodJsonFiller.fill(pack)
        perkJsonFiller.fill(pack)
        monsterJsonFiller.fillDecks(pack)
        monsterJsonFiller.fillMonsters(pack)
        monsterJsonFiller.fillStats(pack)
        achievementJsonFiller.fill(pack)
    }

    companion object {
        private const val VERSION = 5
        private const val PREFS_VERSION = "filler_version"
    }
}
