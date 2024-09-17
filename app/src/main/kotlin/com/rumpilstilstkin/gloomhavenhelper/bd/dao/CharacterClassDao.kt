package com.rumpilstilstkin.gloomhavenhelper.bd.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.CharacterClassBd

@Dao
interface CharacterClassDao {
    @Query("SELECT * FROM CharacterClassBd")
    suspend fun getAll(): List<CharacterClassBd>

    @Query("SELECT * FROM CharacterClassBd WHERE characterClassId LIKE :id LIMIT 1")
    suspend fun findById(id: Int): CharacterClassBd

    @Insert
    suspend fun insertAll(vararg users: CharacterClassBd)
}