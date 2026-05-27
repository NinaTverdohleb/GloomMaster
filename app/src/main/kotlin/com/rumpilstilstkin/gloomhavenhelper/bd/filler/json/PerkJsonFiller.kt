package com.rumpilstilstkin.gloomhavenhelper.bd.filler.json

import com.rumpilstilstkin.gloomhavenhelper.bd.dao.PerksDao
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.PerkBd
import javax.inject.Inject

class PerkJsonFiller @Inject constructor(
    private val jsonDataLoader: JsonDataLoader,
    private val perksDao: PerksDao
) {
    suspend fun fill(version: Int) {
        val characterPerks = jsonDataLoader.loadPerks(version)
        val entities = characterPerks.flatMap { characterPerk ->
            characterPerk.perks.map {
                PerkBd(
                    characterType = characterPerk.characterType
                )
            }
        }
        perksDao.insertAll(*entities.toTypedArray())
    }
}
