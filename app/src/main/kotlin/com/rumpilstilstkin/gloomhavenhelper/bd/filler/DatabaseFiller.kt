package com.rumpilstilstkin.gloomhavenhelper.bd.filler

import android.content.SharedPreferences
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.CharacterClassDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.GameLevelInfoDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.ScenarioDao
import javax.inject.Inject

class DatabaseFiller @Inject constructor(
    private val characterClassDao: CharacterClassDao,
    private val gameLevelInfoDao: GameLevelInfoDao,
    private val scenarioDao: ScenarioDao,
    private val preferences: SharedPreferences,
) {
    val version = preferences.getInt(PREFS_VERSION, 0)

    suspend fun fillDatabase() {
        for (i in version + 1..VERSION) {
            when (i) {
                1 -> startFill()
                else -> {}
            }
        }
        preferences.edit().putInt(PREFS_VERSION, VERSION).apply()
    }

    private suspend fun startFill() {
        StartFill.fillCharacterClasses(characterClassDao)
        StartFill.fillGameLevelInfo(gameLevelInfoDao)
        StartFill.fillScenarios(scenarioDao)
    }

    companion object {
        private const val VERSION = 1
        private const val PREFS_VERSION = "filler_version"
    }

}