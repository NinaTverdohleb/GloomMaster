package com.rumpilstilstkin.gloomhavenhelper.data

import com.rumpilstilstkin.gloomhavenhelper.bd.dao.GoodsDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.TeamGoodDao
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.TeamGoodBd
import com.rumpilstilstkin.gloomhavenhelper.data.mappers.toDomain
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Good
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GoodsRepository @Inject constructor(
    private val goodsDao: GoodsDao,
    private val teamGoodDao: TeamGoodDao,
) {
    suspend fun getGoods(): List<Good> = goodsDao.getAll().map { it.toDomain() }

    suspend fun getGoodByIds(ids: List<Int>) = goodsDao.getGoodsByIds(ids)

    fun getGoodsForTeam(teamId: Int): Flow<List<Good>> =
        teamGoodDao.getGoodNumbersForTeam(teamId)
            .map { numbers ->
                if (numbers.isEmpty()) {
                    emptyList()
                } else {
                    goodsDao.getGoodsByNumbers(numbers).map { it.toDomain() }
                }
            }

    suspend fun addGoodToTeam(teamId: Int, goodNumber: Int) {
        teamGoodDao.insert(TeamGoodBd(teamId = teamId, goodNumber = goodNumber))
    }

    suspend fun removeGoodFromTeam(teamId: Int, goodNumber: Int) {
        teamGoodDao.delete(teamId, goodNumber)
    }

    suspend fun addGoodsToTeam(teamId: Int, goodNumbers: List<Int>) {
        val entities = goodNumbers.map { TeamGoodBd(teamId = teamId, goodNumber = it) }
        teamGoodDao.insertAll(entities)
    }
}