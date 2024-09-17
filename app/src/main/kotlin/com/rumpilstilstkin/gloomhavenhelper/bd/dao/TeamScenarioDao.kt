package com.rumpilstilstkin.gloomhavenhelper.bd.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.TeamScenarioBd

@Dao
interface TeamScenarioDao {
    @Insert
    suspend fun insertAll(vararg scenarios: TeamScenarioBd)

    @Update
    suspend fun update(team: TeamScenarioBd)

    @Query("SELECT * FROM TeamScenarioBd WHERE teamId = :teamId AND scenarioNumber = :scenarioNumber LIMIT 1")
    suspend fun getTeamScenario(teamId: Int, scenarioNumber: Int): TeamScenarioBd

    @Query("SELECT * FROM TeamScenarioBd WHERE teamId = :teamId")
    suspend fun getTeamScenarios(teamId: Int): List<TeamScenarioBd>
}