package com.rumpilstilstkin.gloomhavenhelper.bd.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.CharacterGoodBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.GoodWithTranslation
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterGoodsDao {
    @Transaction
    @Query("SELECT goodNumber FROM CharacterGoodBd WHERE characterId IN (:characterIds)")
    fun getCharactersGoodNumbers(characterIds: List<Int>): Flow<List<Int>>

    @Query(
        """
        SELECT 
            g.*, 
            COALESCE(t1.name, t2.name, 'not found') AS translated_name 
        FROM CharacterGoodBd tg
        INNER JOIN GoodBd g ON tg.goodNumber = g.goodNumber
        LEFT JOIN GoodTranslationsBd t1 ON g.goodNumber = t1.goodNumber AND t1.locale = :targetLocale
        LEFT JOIN GoodTranslationsBd t2 ON g.goodNumber = t2.goodNumber AND t2.locale = :defaultLocale
        WHERE tg.characterId = :characterId
    """
    )
    fun getCharacterGoodsFlow(
        characterId: Int,
        targetLocale: String,
        defaultLocale: String
    ): Flow<List<GoodWithTranslation>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(characterGood: CharacterGoodBd)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characterGoods: List<CharacterGoodBd>)

    @Delete
    suspend fun delete(characterGood: CharacterGoodBd)

    @Query("DELETE FROM CharacterGoodBd WHERE characterId = :characterId AND goodNumber = :goodNumber")
    suspend fun delete(characterId: Int, goodNumber: Int)

    @Query("DELETE FROM CharacterGoodBd WHERE characterId = :characterId")
    suspend fun deleteCharacterGoods(characterId: Int)
}