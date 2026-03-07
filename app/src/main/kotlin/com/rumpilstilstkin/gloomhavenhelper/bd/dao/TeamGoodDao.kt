package com.rumpilstilstkin.gloomhavenhelper.bd.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.TeamGoodBd
import kotlinx.coroutines.flow.Flow

@Dao
interface TeamGoodDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(teamGood: TeamGoodBd)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(teamGoods: List<TeamGoodBd>)

    @Query("DELETE FROM TeamGoodBd WHERE teamId = :teamId AND goodNumber = :goodNumber")
    suspend fun delete(teamId: Int, goodNumber: Int)

    @Query("DELETE FROM TeamGoodBd WHERE teamId = :teamId")
    suspend fun deleteAllForTeam(teamId: Int)

    @Query("SELECT goodNumber FROM TeamGoodBd WHERE teamId = :teamId")
    fun getGoodNumbersForTeam(teamId: Int): Flow<List<Int>>

    @Query("SELECT * FROM TeamGoodBd WHERE teamId = :teamId")
    fun getGoodsForTeam(teamId: Int): Flow<List<TeamGoodBd>>

    @Query("SELECT EXISTS(SELECT 1 FROM TeamGoodBd WHERE teamId = :teamId AND goodNumber = :goodNumber)")
    suspend fun hasGood(teamId: Int, goodNumber: Int): Boolean
}
