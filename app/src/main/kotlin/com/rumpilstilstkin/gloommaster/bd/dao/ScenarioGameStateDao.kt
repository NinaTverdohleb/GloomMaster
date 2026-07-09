package com.rumpilstilstkin.gloommaster.bd.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.rumpilstilstkin.gloommaster.bd.entity.ScenarioGameStateBd
import kotlinx.coroutines.flow.Flow

@Dao
interface ScenarioGameStateDao {
    @Query("SELECT * FROM ScenarioGameStateBd LIMIT 1")
    suspend fun get(): ScenarioGameStateBd?

    @Query("SELECT * FROM ScenarioGameStateBd LIMIT 1")
    fun getFlow(): Flow<ScenarioGameStateBd?>

    @Insert
    suspend fun insert(entity: ScenarioGameStateBd)

    @Transaction
    suspend fun clearAndInsert(entity: ScenarioGameStateBd) {
        deleteAll()
        insert(entity)
    }

    @Query("DELETE FROM ScenarioGameStateBd")
    suspend fun deleteAll()
}
