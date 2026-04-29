package com.rumpilstilstkin.gloomhavenhelper.bd.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.TeamCharacterClassBd
import kotlinx.coroutines.flow.Flow

@Dao
interface TeamCharacterClassDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(teamCharacterClass: TeamCharacterClassBd)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg teamCharacterClasses: TeamCharacterClassBd)

    @Query("DELETE FROM TeamCharacterClassBd WHERE teamId = :teamId AND characterType = :characterType")
    suspend fun delete(teamId: Int, characterType: String)

    @Query("DELETE FROM TeamCharacterClassBd WHERE teamId = :teamId")
    suspend fun deleteAllForTeam(teamId: Int)

    @Query("SELECT * FROM TeamCharacterClassBd WHERE teamId = :teamId")
    fun getClassesForTeam(teamId: Int): Flow<List<TeamCharacterClassBd>>

    @Query("SELECT * FROM TeamCharacterClassBd WHERE teamId = :teamId")
    suspend fun getClassesForTeamSync(teamId: Int): List<TeamCharacterClassBd>

    @Query("SELECT characterType FROM TeamCharacterClassBd WHERE teamId = :teamId")
    fun getClassTypesForTeam(teamId: Int): Flow<List<String>>
}
