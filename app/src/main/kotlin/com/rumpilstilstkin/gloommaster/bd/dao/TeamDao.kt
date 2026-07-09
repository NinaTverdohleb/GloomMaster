package com.rumpilstilstkin.gloommaster.bd.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import androidx.room.Upsert
import com.rumpilstilstkin.gloommaster.bd.entity.TeamBd
import kotlinx.coroutines.flow.Flow

@Dao
interface TeamDao {
    @Query("SELECT * FROM TeamBd")
    suspend fun getAll(): List<TeamBd>

    @Query("SELECT * FROM TeamBd")
    fun getAllFlow(): Flow<List<TeamBd>>

    @Query("SELECT * FROM TeamBd WHERE teamId = :id LIMIT 1")
    suspend fun findById(id: Int): TeamBd?

    @Query("SELECT * FROM TeamBd WHERE teamId = :id LIMIT 1")
    fun getTeamFlow(id: Int): Flow<TeamBd>

    @Upsert
    suspend fun insert(team: TeamBd): Long

    @Transaction
    @Query("DELETE FROM TeamBd WHERE teamId = :teamId")
    suspend fun delete(teamId: Int)

    @Update
    suspend fun update(team: TeamBd)

    @Transaction
    @Query("UPDATE TeamBd SET reputation=:reputation WHERE teamId = :id")
    suspend fun updateReputation(
        id: Int,
        reputation: Int,
    )

    @Transaction
    @Query("UPDATE TeamBd SET prosperity=:prosperity WHERE teamId = :id")
    suspend fun updateProsperity(
        id: Int,
        prosperity: Int,
    )

    @Transaction
    @Query("UPDATE TeamBd SET churchValue=:value WHERE teamId = :id")
    suspend fun updateDonateValue(
        id: Int,
        value: Int,
    )
}
