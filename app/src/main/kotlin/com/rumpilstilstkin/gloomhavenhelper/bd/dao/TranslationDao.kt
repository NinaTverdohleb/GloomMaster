package com.rumpilstilstkin.gloomhavenhelper.bd.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.TranslationBd
import kotlinx.coroutines.flow.Flow

@Dao
interface TranslationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg translations: TranslationBd)

    @Query("SELECT * FROM TranslationBd WHERE locale = :locale")
    suspend fun getForLocale(locale: String): List<TranslationBd>

    @Query("SELECT * FROM TranslationBd WHERE locale = :locale")
    fun getForLocaleFlow(locale: String): Flow<List<TranslationBd>>

    @Query("DELETE FROM TranslationBd")
    suspend fun deleteAll()
}
