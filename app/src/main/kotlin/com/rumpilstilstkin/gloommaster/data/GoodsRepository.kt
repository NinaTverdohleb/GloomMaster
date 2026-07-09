package com.rumpilstilstkin.gloommaster.data

import com.rumpilstilstkin.gloommaster.bd.dao.CharacterGoodsDao
import com.rumpilstilstkin.gloommaster.bd.dao.GoodsDao
import com.rumpilstilstkin.gloommaster.bd.dao.TeamGoodDao
import com.rumpilstilstkin.gloommaster.bd.entity.CharacterGoodBd
import com.rumpilstilstkin.gloommaster.bd.entity.TeamGoodBd
import com.rumpilstilstkin.gloommaster.data.mappers.toDomain
import com.rumpilstilstkin.gloommaster.domain.entity.Good
import com.rumpilstilstkin.gloommaster.domain.entity.PackType
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
    suspend fun getGoods(
        packs: Set<PackType>,
        locale: String,
    ): List<Good> =
        goodsDao
            .getAll(
                targetLocale = locale,
                defaultLocale = LocaleRepository.DEFAULT_LOCALE,
            ).map { it.toDomain() }
            .filter { it.pack in packs }

    fun getGoodsForTeam(
        teamId: Int,
        locale: String,
    ): Flow<List<Good>> =
        teamGoodDao
            .getGoodsForTeam(
                teamId = teamId,
                targetLocale = locale,
                defaultLocale = LocaleRepository.DEFAULT_LOCALE,
            ).map { goods -> goods.map { it.toDomain() } }

    suspend fun getTeamGoodsNumbers(teamId: Int): List<Int> = teamGoodDao.getTeamGoodNumbers(teamId)

    suspend fun getCharacterGoodNumbers(characterId: Int): List<Int> = characterGoodsDao.getCharactersGoodNumbers(characterId).first()

    fun getCharacterGoodIds(characterIds: List<Int>): Flow<List<Int>> = characterGoodsDao.getCharactersGoodIds(characterIds)

    suspend fun getGoodIdsByNumbers(numbers: List<Int>): Map<Int, List<Int>> =
        goodsDao
            .getGoodsByNumbers(numbers)
            .groupBy { it.displayNumber }
            .mapValues { entry ->
                entry.value.map { it.goodId }
            }

    suspend fun delete(
        teamId: Int,
        goodId: Int,
    ) {
        teamGoodDao.delete(teamId, goodId)
    }

    suspend fun addGoodsToTeam(
        teamId: Int,
        goodIds: List<Int>,
    ) {
        val entities = goodIds.map { TeamGoodBd(teamId = teamId, goodId = it) }
        teamGoodDao.insertAll(entities)
    }

    suspend fun removeGoodFromTeam(
        teamId: Int,
        goodId: Int,
    ) {
        teamGoodDao.delete(teamId, goodId)
    }

    suspend fun addCharacterGoods(
        characterId: Int,
        goodIds: List<Int>,
    ) {
        val entities =
            goodIds.map { CharacterGoodBd(characterId = characterId, goodId = it) }
        characterGoodsDao.insertAll(entities)
    }

    suspend fun deleteCharacterGood(
        goodId: Int,
        characterId: Int,
    ) {
        characterGoodsDao.delete(
            characterId = characterId,
            goodId = goodId,
        )
    }

    fun getCharacterGoods(
        characterId: Int,
        locale: String,
    ): Flow<List<Good>> =
        characterGoodsDao
            .getCharacterGoodsFlow(
                characterId = characterId,
                targetLocale = locale,
                defaultLocale = LocaleRepository.DEFAULT_LOCALE,
            ).map { goods ->
                goods.map { it.toDomain() }
            }
}
