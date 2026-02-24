package com.rumpilstilstkin.gloomhavenhelper.bd.filler

import android.content.SharedPreferences
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.CharacterClassDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.GameLevelInfoDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.GoodsDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.PerksDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.PersonalQuestDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.ScenarioDao
import javax.inject.Inject
import androidx.core.content.edit

class DatabaseFiller @Inject constructor(
    private val characterClassDao: CharacterClassDao,
    private val gameLevelInfoDao: GameLevelInfoDao,
    private val scenarioDao: ScenarioDao,
    private val preferences: SharedPreferences,
    private val goodsDao: GoodsDao,
    private val perksDao: PerksDao,
    private val personalQuestDao: PersonalQuestDao,
) {
    val version = preferences.getInt(PREFS_VERSION, 0)

    suspend fun fillDatabase() {
        if (characterClassDao.getAll().isEmpty()) {
            startFill()
        }
        /*for (i in version + 1..VERSION) {
            when (i) {
                1 -> startFill()
                else -> {}
            }
        }*/
        preferences.edit { putInt(PREFS_VERSION, VERSION) }
    }

    private suspend fun startFill() {
        CharacterClassesFiller.fill_v1(characterClassDao)
        GameLevelInfoFiller.fill_v1(gameLevelInfoDao)
        ScenariosFiller.fill_v1(scenarioDao)
        GoodsFiller.fill_v1(goodsDao)
        PerksFiller.fill_v1(perksDao)
        QuestsFiller.fill_v1(personalQuestDao)
    }

    companion object {
        private const val VERSION = 1
        private const val PREFS_VERSION = "filler_version"
    }

}