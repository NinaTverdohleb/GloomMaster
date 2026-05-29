package com.rumpilstilstkin.gloomhavenhelper.bd.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.ScenarioGameStateBd
import kotlinx.coroutines.flow.Flow

@Dao
interface ScenarioGameStateDao {

    @Query("SELECT * FROM ScenarioGameStateBd LIMIT 1")
    suspend fun get(): ScenarioGameStateBd?

    @Query("SELECT * FROM ScenarioGameStateBd LIMIT 1")
    fun getFlow(): Flow<ScenarioGameStateBd?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(state: ScenarioGameStateBd)

    @Update
    suspend fun update(state: ScenarioGameStateBd)

    @Query("DELETE FROM ScenarioGameStateBd")
    suspend fun deleteAll()
}
