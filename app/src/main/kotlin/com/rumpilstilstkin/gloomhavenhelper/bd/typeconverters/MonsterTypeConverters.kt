package com.rumpilstilstkin.gloomhavenhelper.bd.typeconverters

import androidx.room.TypeConverter
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterAction
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterCardAction
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterStatType
import kotlinx.serialization.json.Json

private val json =
    Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
    }

class CardActionsTypeConverter {
    @TypeConverter
    fun fromList(list: List<MonsterAction>): String = json.encodeToString(list)

    @TypeConverter
    fun toList(value: String): List<MonsterAction> = json.decodeFromString(value)
}

class MonsterStatTypeListConverter {
    @TypeConverter
    fun fromList(list: List<MonsterStatType>): String = json.encodeToString(list)

    @TypeConverter
    fun toList(value: String): List<MonsterStatType> = json.decodeFromString(value)
}

class MonsterStatTypeSetConverter {
    @TypeConverter
    fun fromSet(list: Set<MonsterStatType>): String = json.encodeToString(list)

    @TypeConverter
    fun toSet(value: String): Set<MonsterStatType> = json.decodeFromString(value)
}

class MonsterCardActionTypeConverter {
    @TypeConverter
    fun fromList(list: List<MonsterCardAction>): String = json.encodeToString(list)

    @TypeConverter
    fun toList(value: String): List<MonsterCardAction> = json.decodeFromString(value)
}

class StringListTypeConverter {
    @TypeConverter
    fun fromList(list: List<String>): String = json.encodeToString(list)

    @TypeConverter
    fun toList(value: String): List<String> = json.decodeFromString(value)
}
