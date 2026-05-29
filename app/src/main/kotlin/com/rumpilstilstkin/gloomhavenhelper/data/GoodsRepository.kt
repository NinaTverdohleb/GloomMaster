package com.rumpilstilstkin.gloomhavenhelper.data

import com.rumpilstilstkin.gloomhavenhelper.bd.dao.CharacterGoodsDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.GoodsDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.TeamGoodDao
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.CharacterGoodBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.TeamGoodBd
import com.rumpilstilstkin.gloomhavenhelper.data.mappers.toDomain
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Good
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.PackType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.map

@Singleton
class GoodsRepository @Inject constructor(
    private val goodsDao: GoodsDao,
    private val teamGoodDao: TeamGoodDao,
    private val characterGoodsDao: CharacterGoodsDao,
) {
    suspend fun getGoods(packs: Set<PackType>, locale: String): List<Good> =
        goodsDao
            .getAll(
                targetLocale = locale,
                defaultLocale = LocaleRepository.DEFAULT_LOCALE
            )
            .map { it.toDomain() }
            .filter { it.pack in packs }

    fun getGoodsForTeam(teamId: Int, locale: String): Flow<List<Good>> =
        teamGoodDao.getGoodsForTeam(
            teamId = teamId,
            targetLocale = locale,
            defaultLocale = LocaleRepository.DEFAULT_LOCALE
        )
            .map { goods -> goods.map { it.toDomain() } }

    suspend fun getTeamGoodsNumbers(teamId: Int): List<Int> =
        teamGoodDao.getGoodsForTeamSync(teamId).map { it.goodNumber }

    suspend fun getCharacterGoodNumbers(characterId: Int): List<Int> =
        characterGoodsDao.getCharactersGoodNumbers(listOf(characterId)).first()

    fun getCharacterGoodNumbers(characterIds: List<Int>): Flow<List<Int>> =
        characterGoodsDao.getCharactersGoodNumbers(characterIds)

    suspend fun getGoodsByNumbers(numbers: List<Int>, locale: String): List<Good> =
        goodsDao.getGoodsByNumbers(
            numbers = numbers,
            targetLocale = locale,
            defaultLocale = LocaleRepository.DEFAULT_LOCALE
        ).map { it.toDomain() }

    suspend fun delete(teamId: Int, goodId: Int) {
        teamGoodDao.delete(teamId, goodId)
    }

    suspend fun addGoodsToTeam(teamId: Int, goodNumbers: List<Int>) {
        val entities = goodNumbers.map { TeamGoodBd(teamId = teamId, goodNumber = it) }
        teamGoodDao.insertAll(entities)
    }

    suspend fun removeGoodFromTeam(teamId: Int, goodId: Int) {
        teamGoodDao.delete(teamId, goodId)
    }

    suspend fun addCharacterGoods(characterId: Int, goodNumbers: List<Int>) {
        val entities =
            goodNumbers.map { CharacterGoodBd(characterId = characterId, goodNumber = it) }
        characterGoodsDao.insertAll(entities)
    }

    suspend fun deleteCharacterGood(goodNumber: Int, characterId: Int) {
        characterGoodsDao.delete(
            characterId = characterId,
            goodNumber = goodNumber
        )
    }

    fun getCharacterGoods(characterId: Int, locale: String): Flow<List<Good>> =
        characterGoodsDao.getCharacterGoodsFlow(
            characterId = characterId,
            targetLocale = locale,
            defaultLocale = LocaleRepository.DEFAULT_LOCALE
        ).map { goods ->
            goods.map { it.toDomain() }
        }
}