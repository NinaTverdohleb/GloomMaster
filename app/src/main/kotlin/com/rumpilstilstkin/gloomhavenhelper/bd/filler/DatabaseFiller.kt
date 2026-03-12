package com.rumpilstilstkin.gloomhavenhelper.bd.filler

import android.content.SharedPreferences
import androidx.core.content.edit
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.MonsterDao
import com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.AchievementJsonFiller
import com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.GameLevelInfoJsonFiller
import com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.GoodJsonFiller
import com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.PerkJsonFiller
import com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.QuestJsonFiller
import com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.ScenarioJsonFiller
import javax.inject.Inject

//TODO fill from json
class DatabaseFiller @Inject constructor(
    private val preferences: SharedPreferences,
    private val monsterDao: MonsterDao,
    private val gameLevelInfoJsonFiller: GameLevelInfoJsonFiller,
    private val achievementJsonFiller: AchievementJsonFiller,
    private val scenarioJsonFiller: ScenarioJsonFiller,
    private val goodJsonFiller: GoodJsonFiller,
    private val perkJsonFiller: PerkJsonFiller,
    private val questJsonFiller: QuestJsonFiller,
) {
    val version = preferences.getInt(PREFS_VERSION, 0)

    suspend fun fillDatabase() {
        if (version == 0) {
            startFill()
        }
        preferences.edit { putInt(PREFS_VERSION, VERSION) }
    }

    private suspend fun startFill() {
        gameLevelInfoJsonFiller.fill(1)
        scenarioJsonFiller.fill(1)
        goodJsonFiller.fill(1)
        perkJsonFiller.fill(1)
        questJsonFiller.fill(1)
        MonstersFiller.fill_v1(monsterDao)
        achievementJsonFiller.fill(1)
    }

    companion object {
        private const val VERSION = 1
        private const val PREFS_VERSION = "filler_version"
    }
}
