package com.rumpilstilstkin.gloommaster.bd.typeconverters

import androidx.room.TypeConverter
import com.rumpilstilstkin.gloommaster.domain.entity.Achievement
import kotlinx.serialization.json.Json

class AchievementConverter {
    @TypeConverter
    fun fromList(list: List<Achievement>): String = Json.encodeToString(list)

    @TypeConverter
    fun toList(value: String): List<Achievement> = Json.decodeFromString(value)
}
