package com.rumpilstilstkin.gloomhavenhelper.bd.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.CharacterPerkBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.CharacterPerkDetailsBd
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterPerksDao {
    @Transaction
    @Query("SELECT * FROM CharacterPerkBd WHERE characterId = :characterId")
    fun getCharacterPerksFlow(characterId: Int): Flow<List<CharacterPerkDetailsBd>>

    @Transaction
    @Query("SELECT * FROM CharacterPerkBd WHERE characterId = :characterId")
    suspend fun getCharacterPerks(characterId: Int): List<CharacterPerkDetailsBd>

    @Insert
    suspend fun insert(characterPerk: CharacterPerkBd)

    @Delete
    suspend fun delete(characterPerk: CharacterPerkBd)

    @Transaction
    @Query("DELETE FROM CharacterPerkBd WHERE id = :characterPerkId")
    suspend fun deleteById(characterPerkId: Int)
}