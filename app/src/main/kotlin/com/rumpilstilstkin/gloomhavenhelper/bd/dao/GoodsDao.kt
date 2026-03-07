package com.rumpilstilstkin.gloomhavenhelper.bd.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.CharacterGoodDetailsBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.GoodBd
import kotlinx.coroutines.flow.Flow

@Dao
interface GoodsDao {

    @Query("SELECT * FROM GoodBd")
    suspend fun getAll(): List<GoodBd>

    @Insert
    suspend fun insertAll(vararg users: GoodBd)

    @Transaction
    @Query("SELECT * FROM GoodBd WHERE goodId IN (:goodId)")
    suspend fun getGoodsByIds(goodId: List<Int>): List<GoodBd>

    @Query("SELECT * FROM GoodBd WHERE number IN (:numbers)")
    suspend fun getGoodsByNumbers(numbers: List<Int>): List<GoodBd>
}