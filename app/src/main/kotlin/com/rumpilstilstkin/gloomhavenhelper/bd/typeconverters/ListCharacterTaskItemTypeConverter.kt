package com.rumpilstilstkin.gloomhavenhelper.bd.typeconverters

import androidx.room.TypeConverter
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.quest.CharacterTaskItem
import kotlinx.serialization.json.Json

class ListCharacterTaskItemTypeConverter {
    @TypeConverter
    fun fromList(list: List<CharacterTaskItem>): String = Json.encodeToString(list)

    @TypeConverter
    fun toList(value: String): List<CharacterTaskItem> = Json.decodeFromString(value)
}
