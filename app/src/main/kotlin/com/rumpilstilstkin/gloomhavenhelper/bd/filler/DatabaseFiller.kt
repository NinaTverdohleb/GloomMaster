package com.rumpilstilstkin.gloomhavenhelper.bd.filler

import android.content.SharedPreferences
import androidx.core.content.edit
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.AchievementDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.GameLevelInfoDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.GoodsDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.MonsterDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.PerksDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.PersonalQuestDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.ScenarioDao
import com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.AchievementJsonFiller
import com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.GameLevelInfoJsonFiller
import com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.JsonDataLoader
import javax.inject.Inject

//TODO fill from json
class DatabaseFiller @Inject constructor(
    private val scenarioDao: ScenarioDao,
    private val preferences: SharedPreferences,
    private val goodsDao: GoodsDao,
    private val perksDao: PerksDao,
    private val personalQuestDao: PersonalQuestDao,
    private val monsterDao: MonsterDao,
    private val gameLevelInfoJsonFiller: GameLevelInfoJsonFiller,
    private val achievementJsonFiller: AchievementJsonFiller,
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
        ScenariosFiller.fill_v1(scenarioDao)
        GoodsFiller.fill_v1(goodsDao)
        PerksFiller.fill_v1(perksDao)
        QuestsFiller.fill_v1(personalQuestDao)
        MonstersFiller.fill_v1(monsterDao)
        achievementJsonFiller.fill(1)
    }

    companion object {
        private const val VERSION = 1
        private const val PREFS_VERSION = "filler_version"
    }
}