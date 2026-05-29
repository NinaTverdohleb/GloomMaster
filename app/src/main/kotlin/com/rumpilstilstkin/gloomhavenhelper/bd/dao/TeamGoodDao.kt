package com.rumpilstilstkin.gloomhavenhelper.bd.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.GoodWithTranslation
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.TeamGoodBd
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

    @Query(
        """
        SELECT 
            g.*, 
            COALESCE(t1.name, t2.name, CAST(g.displayNumber AS TEXT)) AS translated_name
        FROM TeamGoodBd tg
        INNER JOIN GoodBd g ON tg.goodId = g.goodId
        LEFT JOIN GoodTranslationsBd t1 ON g.displayNumber = t1.displayNumber AND t1.locale = :targetLocale
        LEFT JOIN GoodTranslationsBd t2 ON g.displayNumber = t2.displayNumber AND t2.locale = :defaultLocale
        WHERE tg.teamId = :teamId
    """
    )
    fun getGoodsForTeam(
        teamId: Int,
        targetLocale: String,
        defaultLocale: String
    ): Flow<List<GoodWithTranslation>>

    @Transaction
    @Query(
        """
        SELECT 
            g.displayNumber 
        FROM TeamGoodBd tg
        INNER JOIN GoodBd g ON tg.goodId = g.goodId
        WHERE tg.teamId = :teamId
    """
    )
    suspend fun getTeamGoodNumbers(teamId: Int): List<Int>
}
