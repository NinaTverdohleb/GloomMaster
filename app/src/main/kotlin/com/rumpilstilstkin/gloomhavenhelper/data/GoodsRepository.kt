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
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.contains
import kotlin.collections.map

@Singleton
class GoodsRepository @Inject constructor(
    private val goodsDao: GoodsDao,
    private val teamGoodDao: TeamGoodDao,
    private val characterGoodsDao: CharacterGoodsDao,
) {
    suspend fun getGoods(packs: Set<PackType>): List<Good> =
        goodsDao
            .getAll()
            .map { it.toDomain() }
            .filter { it.pack in packs }

    suspend fun getGood(goodId: Int): Good? =
        goodsDao.getGoodById(goodId)?.toDomain()

    fun getGoodsForTeam(teamId: Int): Flow<List<Good>> =
        teamGoodDao.getGoodsForTeam(teamId)
            .map { goods -> goods.map { it.good.toDomain() } }

    suspend fun getTeamGoodsNumbers(teamId: Int): List<Int> = teamGoodDao.getGoodsForTeamSync(teamId).map { it.goodId }

    suspend fun getCharacterGoodIds(characterId: Int): List<Int> =
        characterGoodsDao.getCharacterGoods(characterId).map { it.good.goodId }

    fun getCharacterGoodIds(characterIds: List<Int>): Flow<List<Int>> =
        characterGoodsDao.getCharactersGoodIds(characterIds)

    suspend fun getGoodsByNumbers(numbers: List<Int>): List<Good> =
        goodsDao.getGoodsByNumbers(numbers).map { it.toDomain() }

    suspend fun delete(teamId: Int, goodId: Int) {
        teamGoodDao.delete(teamId, goodId)
    }

    suspend fun addGoodsToTeam(teamId: Int, goodIds: List<Int>) {
        val entities = goodIds.map { TeamGoodBd(teamId = teamId, goodId = it) }
        teamGoodDao.insertAll(entities)
    }

    suspend fun removeGoodFromTeam(teamId: Int, goodId: Int) {
        teamGoodDao.delete(teamId, goodId)
    }

    suspend fun addCharacterGoods(characterId: Int, goodIds: List<Int>) {
        val entities = goodIds.map { CharacterGoodBd(characterId = characterId, goodId = it) }
        characterGoodsDao.insertAll(entities)
    }

    suspend fun deleteCharacterGood(goodId: Int, characterId: Int) {
        characterGoodsDao.delete(
            characterId = characterId,
            goodId = goodId
        )
    }

    fun getCharacterGoods(characterId: Int) =
        characterGoodsDao.getCharacterGoodsFlow(characterId).map { goods ->
            goods.map { it.toDomain() }
        }
}