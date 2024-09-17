package com.rumpilstilstkin.gloomhavenhelper.bd.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.CharacterBd
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {

    @Query("SELECT * FROM CharacterBd")
    fun getAllCharacters(): Flow<List<CharacterBd>>

    @Query("SELECT * FROM CharacterBd WHERE teamId LIKE :teamId")
    suspend fun findByTeamId(teamId: Int): List<CharacterBd>

    @Query("SELECT * FROM CharacterBd WHERE characterId LIKE :characterId LIMIT 1")
    suspend fun getCharacterById(characterId: Int): CharacterBd

    @Query("SELECT * FROM CharacterBd WHERE teamId LIKE :teamId")
    fun findByTeamIdFlow(teamId: Int): Flow<List<CharacterBd>>

    @Insert
    suspend fun insert(character: CharacterBd)

    @Delete
    suspend fun delete(character: CharacterBd)

    @Update
    suspend fun update(character: CharacterBd)

    @Transaction
    @Query("DELETE FROM CharacterBd WHERE characterId = :characterId")
    suspend fun deleteById(characterId: Int)
}