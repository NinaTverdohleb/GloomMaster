package com.rumpilstilstkin.gloomhavenhelper.bd.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.CharacterPersonalQuestBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.CharacterPersonalQuestDetailsBd
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterPersonalQuestDao {

    @Transaction
    @Query("SELECT * FROM CharacterPersonalQuestBd WHERE characterId LIKE :characterId LIMIT 1")
    fun getCharacterPersonalQuestFlow(characterId: Int): Flow<CharacterPersonalQuestDetailsBd?>

    @Insert
    suspend fun insert(characterPerk: CharacterPersonalQuestBd)

    @Transaction
    @Query("DELETE FROM CharacterPersonalQuestBd WHERE questId LIKE :questId")
    suspend fun deleteById(questId: Int)

    @Query("DELETE FROM CharacterPersonalQuestBd WHERE characterId LIKE :characterId")
    suspend fun deleteByCharacterId(characterId: Int)
}