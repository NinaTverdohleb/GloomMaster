package com.rumpilstilstkin.gloommaster.bd.dao

import androidx.room.Dao
import androidx.room.Insert
import com.rumpilstilstkin.gloommaster.bd.entity.LocationBd
import com.rumpilstilstkin.gloommaster.bd.entity.LocationTranslateBd

@Dao
interface LocationsDao {
    @Insert
    suspend fun insertAll(vararg achievements: LocationBd)

    @Insert
    suspend fun insertAll(vararg achievements: LocationTranslateBd)
}
