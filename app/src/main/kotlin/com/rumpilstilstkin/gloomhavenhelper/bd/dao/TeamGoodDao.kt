package com.rumpilstilstkin.gloomhavenhelper.bd.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.TeamGoodBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.TeamGoodBdDetailsBd
import kotlinx.coroutines.flow.Flow

@Dao
interface TeamGoodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(teamGood: TeamGoodBd)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(teamGoods: List<TeamGoodBd>)

    @Query("DELETE FROM TeamGoodBd WHERE teamId = :teamId AND goodId = :goodId")
    suspend fun delete(teamId: Int, goodId: Int)

    @Query("DELETE FROM TeamGoodBd WHERE teamId = :teamId")
    suspend fun deleteAllForTeam(teamId: Int)

    @Transaction
    @Query("SELECT * FROM TeamGoodBd WHERE teamId = :teamId")
    fun getGoodsForTeam(teamId: Int): Flow<List<TeamGoodBdDetailsBd>>

    // Export/Import methods
    @Query("SELECT * FROM TeamGoodBd WHERE teamId = :teamId")
    suspend fun getGoodsForTeamSync(teamId: Int): List<TeamGoodBd>
}
