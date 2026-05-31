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
            // Example for a future language: when "de" assets are added, bump VERSION and seed
            // only its translations for the packs that have them — base data is left untouched.
            // 5 -> fillLocale(locale = "de", packs = listOf("main", "forgottenCircles"))
            else -> {}
        }
    }

    suspend fun fillLocale(locale: String, packs: List<String>) {
        packs.forEach { pack ->
            scenarioJsonFiller.fillTranslations(pack, locale)
            goodJsonFiller.fillTranslations(pack, locale)
            perkJsonFiller.fillTranslations(pack, locale)
            achievementJsonFiller.fillTranslations(pack, locale)
            monsterJsonFiller.fillTranslations(pack, locale)
            locationJsonFiller.fillTranslations(pack, locale)
            questJsonFiller.fillTranslations(pack, locale)
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
        monsterJsonFiller.fill(pack)
    }

    private suspend fun fillForgottenCircles() {
        val pack = "forgottenCircles"
        scenarioJsonFiller.fill(pack)
        goodJsonFiller.fill(pack)
        perkJsonFiller.fill(pack)
        monsterJsonFiller.fill(pack)
        achievementJsonFiller.fill(pack)
    }

    companion object {
        private const val VERSION = 5
        private const val PREFS_VERSION = "filler_version"
    }
}
