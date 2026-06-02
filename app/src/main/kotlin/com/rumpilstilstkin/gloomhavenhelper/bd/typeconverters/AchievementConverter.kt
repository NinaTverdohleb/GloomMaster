package com.rumpilstilstkin.gloomhavenhelper.bd.typeconverters

import androidx.room.TypeConverter
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Achievement
import kotlinx.serialization.json.Json

class AchievementConverter {
    @TypeConverter
    fun fromList(list: List<Achievement>): String = Json.encodeToString(list)

    @TypeConverter
    fun toList(value: String): List<Achievement> = Json.decodeFromString(value)
}
