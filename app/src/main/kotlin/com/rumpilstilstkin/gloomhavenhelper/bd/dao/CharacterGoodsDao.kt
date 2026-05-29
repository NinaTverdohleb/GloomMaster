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
    @Query("SELECT goodId FROM CharacterGoodBd WHERE characterId IN (:characterIds)")
    fun getCharactersGoodIds(characterIds: List<Int>): Flow<List<Int>>

    @Transaction
    @Query(
        """
        SELECT 
            g.displayNumber 
        FROM CharacterGoodBd tg
        INNER JOIN GoodBd g ON tg.goodId = g.goodId
        WHERE tg.characterId = :characterId
    """
    )
    fun getCharactersGoodNumbers(characterId: Int): Flow<List<Int>>

    @Query(
        """
        SELECT 
            g.*, 
            COALESCE(t1.name, t2.name, 'not found') AS translated_name 
        FROM CharacterGoodBd tg
        INNER JOIN GoodBd g ON tg.goodId = g.goodId
        LEFT JOIN GoodTranslationsBd t1 ON g.displayNumber = t1.displayNumber AND t1.locale = :targetLocale
        LEFT JOIN GoodTranslationsBd t2 ON g.displayNumber = t2.displayNumber AND t2.locale = :defaultLocale
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

    @Query("DELETE FROM CharacterGoodBd WHERE characterId = :characterId AND goodId = :goodId")
    suspend fun delete(characterId: Int, goodId: Int)

    @Query("DELETE FROM CharacterGoodBd WHERE characterId = :characterId")
    suspend fun deleteCharacterGoods(characterId: Int)
}