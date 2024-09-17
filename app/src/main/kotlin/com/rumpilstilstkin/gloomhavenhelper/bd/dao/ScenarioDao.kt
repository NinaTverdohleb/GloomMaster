package com.rumpilstilstkin.gloomhavenhelper.bd.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.ScenarioBd

@Dao
interface ScenarioDao {
    @Query("SELECT * FROM ScenarioBd")
    suspend fun getAll(): List<ScenarioBd>

    @Query("SELECT * FROM ScenarioBd WHERE scenarioNumber LIKE :scenarioNumber LIMIT 1")
    suspend fun getScenario(scenarioNumber: Int): ScenarioBd

    @Insert
    suspend fun insertAll(vararg scenarios: ScenarioBd)
}