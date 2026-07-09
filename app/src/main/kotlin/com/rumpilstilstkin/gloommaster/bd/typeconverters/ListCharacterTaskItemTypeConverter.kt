package com.rumpilstilstkin.gloommaster.bd.typeconverters

import androidx.room.TypeConverter
import com.rumpilstilstkin.gloommaster.domain.entity.quest.CharacterTaskItem
import kotlinx.serialization.json.Json

class ListCharacterTaskItemTypeConverter {
    @TypeConverter
    fun fromList(list: List<CharacterTaskItem>): String = Json.encodeToString(list)

    @TypeConverter
    fun toList(value: String): List<CharacterTaskItem> = Json.decodeFromString(value)
}
