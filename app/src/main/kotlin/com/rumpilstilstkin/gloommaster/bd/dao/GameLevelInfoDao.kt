package com.rumpilstilstkin.gloommaster.bd.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.rumpilstilstkin.gloommaster.bd.entity.GameLevelInfoBd

@Dao
interface GameLevelInfoDao {
    @Query("SELECT * FROM GameLevelInfoBd")
    suspend fun getAll(): List<GameLevelInfoBd>

    @Insert
    suspend fun insertAll(vararg users: GameLevelInfoBd)
}
