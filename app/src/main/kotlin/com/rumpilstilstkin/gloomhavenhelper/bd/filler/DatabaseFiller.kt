package com.rumpilstilstkin.gloomhavenhelper.bd.filler

import android.content.SharedPreferences
import androidx.core.content.edit
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.AchievementDao
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
    private val achievementDao: AchievementDao,
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
            // Re-seed: quest title/description/special and per-task text were added, so existing
            // installs must rebuild the translation store. fill() clears and re-inserts all locales.
            7 -> translationJsonFiller.fill()
            // Re-seed: perk text was added to the translation store, so existing installs must
            // rebuild it. fill() clears and re-inserts every locale's dictionaries.
            8 -> translationJsonFiller.fill()
            // Re-seed: achievement names were added to the translation store, so existing installs
            // must rebuild it. fill() clears and re-inserts every locale's dictionaries.
            9 -> translationJsonFiller.fill()
            // Re-seed: achievement dictionaries were re-keyed from canonical name to stable catalog
            // key, so the store must be rebuilt for installs that seeded the earlier key format.
            10 -> translationJsonFiller.fill()
            // Re-seed: monster names and embedded stats text were added to the translation store,
            // so existing installs must rebuild it. fill() clears and re-inserts every locale.
            11 -> translationJsonFiller.fill()
            // Catalog rebuilt from scratch: display names removed from the asset data and the
            // achievement/monster/scenario references rekeyed to stable catalog keys. The Room
            // schema is destructively recreated (see fallbackToDestructiveMigration), so on an
            // upgrade the catalog tables come back empty while this filler pref persists — and the
            // loop runs only this step, never fillV1. Reseed the catalog when empty, and always
            // rebuild the translation store so its keys match the new catalog.
            12 -> {
                if (achievementDao.count() == 0) fillV1()
                translationJsonFiller.fill()
            }
            // v5 added GoodBd.key (item catalog slug) and re-keyed the goods translation dictionaries
            // from item number to that slug. The Room schema is destructively recreated (see
            // fallbackToDestructiveMigration), so on upgrade the catalog tables come back empty while
            // this filler pref persists. Reseed the catalog when empty, and always rebuild the
            // translation store so the goods keys match the new catalog.
            13 -> {
                if (achievementDao.count() == 0) fillV1()
                translationJsonFiller.fill()
            }
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
        private const val VERSION = 14
        private const val PREFS_VERSION = "filler_version"
    }
}
