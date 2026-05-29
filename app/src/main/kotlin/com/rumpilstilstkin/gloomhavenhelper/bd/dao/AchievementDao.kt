package com.rumpilstilstkin.gloomhavenhelper.bd.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.AchievementBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.AchievementTranslateBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.AchievementWithTranslation

@Dao
interface AchievementDao {
    @Query(
        """
        SELECT * FROM AchievementTranslateBd as t
            WHERE (t.locale = :targetLocale
              OR (
                  t.locale = :defaultLocale
                  AND NOT EXISTS (
                      SELECT 1 FROM PerkTranslationBd
                      WHERE locale = :targetLocale
                  )
              ))
        """
    )
    suspend fun getAllTranslations(
        targetLocale: String,
        defaultLocale: String
    ): List<AchievementTranslateBd>

    @Query("SELECT * FROM AchievementBd")
    suspend fun getAll(): List<AchievementBd>

    @Query("SELECT * FROM AchievementBd WHERE pack IN (:packs) AND isGlobal = 1")
    suspend fun getGlobalAchievementsByPacks(packs: List<String>): List<AchievementBd>

    @Query("SELECT * FROM AchievementBd WHERE pack IN (:packs) AND isGlobal = 0")
    suspend fun getTeamAchievementsByPacks(packs: List<String>): List<AchievementBd>

    @Query(
        """
    SELECT * FROM AchievementTranslateBd AS t
    WHERE t.slug IN (:slugs)
      AND (
          t.locale = :targetLocale
          OR (
              t.locale = :defaultLocale
              AND NOT EXISTS (
                  SELECT 1 FROM AchievementTranslateBd
                  WHERE slug = t.slug AND locale = :targetLocale
              )
          )
      )
"""
    )
    suspend fun getTeamAchievementsBySlugs(
        slugs: List<String>,
        targetLocale: String,
        defaultLocale: String
    ): List<AchievementTranslateBd>

    @Insert
    suspend fun insertAll(vararg achievements: AchievementBd)

    @Insert
    suspend fun insertAll(vararg achievements: AchievementTranslateBd)
}
