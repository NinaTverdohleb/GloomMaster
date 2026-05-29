package com.rumpilstilstkin.gloomhavenhelper.bd.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.GoodBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.GoodTranslationsBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.GoodWithTranslation

@Dao
interface GoodsDao {

    @Query(
        """
            SELECT 
                g.*, 
                COALESCE(t1.name, t2.name, 'not found') AS translated_name 
            FROM GoodBd g
            LEFT JOIN GoodTranslationsBd t1 ON g.goodNumber = t1.goodNumber AND t1.locale = :targetLocale
            LEFT JOIN GoodTranslationsBd t2 ON g.goodNumber = t2.goodNumber AND t2.locale = :defaultLocale
        """
    )
    suspend fun getAll(
        targetLocale: String,
        defaultLocale: String
    ): List<GoodWithTranslation>

    @Insert
    suspend fun insertAll(vararg users: GoodBd)

    @Insert
    suspend fun insertAll(vararg translations: GoodTranslationsBd)

    @Query(
        """
            SELECT 
                g.*, 
                COALESCE(t1.name, t2.name, 'not found') AS translated_name 
            FROM GoodBd g
            LEFT JOIN GoodTranslationsBd t1 ON g.goodNumber = t1.goodNumber AND t1.locale = :targetLocale
            LEFT JOIN GoodTranslationsBd t2 ON g.goodNumber = t2.goodNumber AND t2.locale = :defaultLocale
            WHERE g.goodNumber IN (:numbers)
        """
    )
    suspend fun getGoodsByNumbers(
        numbers: List<Int>,
        targetLocale: String,
        defaultLocale: String
    ): List<GoodWithTranslation>

    @Query(
        """
            SELECT 
                g.*, 
                COALESCE(t1.name, t2.name, 'not found') AS translated_name 
            FROM GoodBd g
            LEFT JOIN GoodTranslationsBd t1 ON g.goodNumber = t1.goodNumber AND t1.locale = :targetLocale
            LEFT JOIN GoodTranslationsBd t2 ON g.goodNumber = t2.goodNumber AND t2.locale = :defaultLocale
            WHERE g.goodNumber = :goodNumber
             """
    )
    suspend fun getGoodByNumber(
        goodNumber: Int,
        targetLocale: String,
        defaultLocale: String
    ): GoodWithTranslation?
}