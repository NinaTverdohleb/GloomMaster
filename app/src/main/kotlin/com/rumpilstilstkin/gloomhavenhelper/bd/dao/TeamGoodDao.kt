package com.rumpilstilstkin.gloomhavenhelper.bd.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.GoodWithTranslation
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.TeamGoodBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.TeamGoodBdDetailsBd
import kotlinx.coroutines.flow.Flow

@Dao
interface TeamGoodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(teamGood: TeamGoodBd)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(teamGoods: List<TeamGoodBd>)

    @Query("DELETE FROM TeamGoodBd WHERE teamId = :teamId AND goodNumber = :goodNumber")
    suspend fun delete(teamId: Int, goodNumber: Int)

    @Query("DELETE FROM TeamGoodBd WHERE teamId = :teamId")
    suspend fun deleteAllForTeam(teamId: Int)

    @Query("""
        SELECT 
            g.*, 
            COALESCE(t1.name, t2.name, 'not found') AS translated_name 
        FROM TeamGoodBd tg
        INNER JOIN GoodBd g ON tg.goodNumber = g.goodNumber
        LEFT JOIN GoodTranslationsBd t1 ON g.goodNumber = t1.goodNumber AND t1.locale = :targetLocale
        LEFT JOIN GoodTranslationsBd t2 ON g.goodNumber = t2.goodNumber AND t2.locale = :defaultLocale
        WHERE tg.teamId = :teamId
    """)
    fun getGoodsForTeam(
        teamId: Int,
        targetLocale: String,
        defaultLocale: String
    ): Flow<List<GoodWithTranslation>>

    // Export/Import methods
    @Query("SELECT * FROM TeamGoodBd WHERE teamId = :teamId")
    suspend fun getGoodsForTeamSync(teamId: Int): List<TeamGoodBd>
}
