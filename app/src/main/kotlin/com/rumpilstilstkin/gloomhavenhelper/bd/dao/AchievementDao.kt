package com.rumpilstilstkin.gloomhavenhelper.bd.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.AchievementBd

@Dao
interface AchievementDao {
    @Query("SELECT * FROM AchievementBd")
    suspend fun getAll(): List<AchievementBd>

    @Query("SELECT COUNT(*) FROM AchievementBd")
    suspend fun count(): Int

    @Query("SELECT * FROM AchievementBd WHERE isGlobal = 1")
    suspend fun getGlobalAchievements(): List<AchievementBd>

    @Query("SELECT * FROM AchievementBd WHERE isGlobal = 0")
    suspend fun getTeamAchievements(): List<AchievementBd>

    @Query("SELECT * FROM AchievementBd WHERE pack IN (:packs) AND isGlobal = 1")
    suspend fun getGlobalAchievementsByPacks(packs: List<String>): List<AchievementBd>

    @Query("SELECT * FROM AchievementBd WHERE pack IN (:packs) AND isGlobal = 0")
    suspend fun getTeamAchievementsByPacks(packs: List<String>): List<AchievementBd>

    @Insert
    suspend fun insertAll(vararg achievements: AchievementBd)
}
