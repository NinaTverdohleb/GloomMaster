package com.rumpilstilstkin.gloomhavenhelper.bd.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.PerkBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.PerkTranslationBd

@Dao
interface PerksDao {
    @Insert
    suspend fun insertAll(vararg users: PerkBd)

    @Insert
    suspend fun insert(perk: PerkBd): Long

    @Insert
    suspend fun insertAll(vararg translations: PerkTranslationBd)

    @Query(
        """
        SELECT * FROM PerkTranslationBd as t
            WHERE characterType = :characterType 
            AND (t.locale = :targetLocale
              OR (
                  t.locale = :defaultLocale
                  AND NOT EXISTS (
                      SELECT 1 FROM PerkTranslationBd
                      WHERE characterType = t.characterType AND locale = :targetLocale
                  )
              ))
        """,
    )
    suspend fun getPerksByCharacterClass(
        characterType: String,
        targetLocale: String,
        defaultLocale: String,
    ): List<PerkTranslationBd>
}
