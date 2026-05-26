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
import com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.TranslationJsonFiller
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
    private val translationJsonFiller: TranslationJsonFiller,
) {
    suspend fun fillDatabase() {
        var version = preferences.getInt(PREFS_VERSION, 0)
        while (version < VERSION) {
            update(version)
            version++
        }
        preferences.edit { putInt(PREFS_VERSION, VERSION) }
    }

    // TODO create effective fun for fix dictionary and don't do anything if dictionary is correct
    private suspend fun update(version: Int) {
        when (version) {
            0 -> fillV1()
            1 -> monsterJsonFiller.fixLivingSpiritDeck()
            2 -> scenarioJsonFiller.fixScenario21()
            3 -> {
                monsterJsonFiller.fillStats(
                    version = 1,
                    pack = "main",
                    type = "base"
                )
            }
            // Bumped from step 4: the translation dictionary key format changed (locations are
            // now keyed by scenario number), so the store must be re-seeded. fill() clears the
            // store first, so devices that ran the old format are corrected.
            5 -> translationJsonFiller.fill()
            // Re-seed: good names were added to the translation store, so existing installs
            // must rebuild it. fill() clears and re-inserts every locale's dictionaries.
            6 -> translationJsonFiller.fill()
            else -> {}
        }
    }

    private suspend fun fillV1() {
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

    companion object {
        private const val VERSION = 7
        private const val PREFS_VERSION = "filler_version"
    }
}
