package com.rumpilstilstkin.gloomhavenhelper.bd.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.CharacterGoodBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.CharacterGoodDetailsBd
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterGoodsDao {
    @Transaction
    @Query("SELECT * FROM CharacterGoodBd WHERE characterId LIKE :characterId")
    fun getCharacterGoodsFlow(characterId: Int): Flow<List<CharacterGoodDetailsBd>>

    @Transaction
    @Query("SELECT * FROM CharacterGoodBd WHERE characterId LIKE :characterId")
    suspend fun getCharacterGoods(characterId: Int): List<CharacterGoodDetailsBd>

    @Transaction
    @Query("SELECT * FROM CharacterGoodBd WHERE id LIKE :characterGoodId")
    suspend fun getCharacterGoodById(characterGoodId: Int): CharacterGoodDetailsBd

    @Insert
    suspend fun insert(characterGood: CharacterGoodBd)

    @Delete
    suspend fun delete(characterGood: CharacterGoodBd)

    @Transaction
    @Query("DELETE FROM CharacterGoodBd WHERE id LIKE :characterGoodId")
    suspend fun deleteById(characterGoodId: Int)

}