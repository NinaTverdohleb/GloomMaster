package com.rumpilstilstkin.gloomhavenhelper.bd.typeconverters

import androidx.room.TypeConverter
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.CardAction
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterStat
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private val json = Json {
    ignoreUnknownKeys = true
    encodeDefaults = true
}

class MonsterStatsTypeConverter {
    @TypeConverter
    fun fromList(list: List<MonsterStat>): String = json.encodeToString(list)

    @TypeConverter
    fun toList(value: String): List<MonsterStat> = json.decodeFromString(value)
}

class CardActionsTypeConverter {
    @TypeConverter
    fun fromList(list: List<CardAction>): String = json.encodeToString(list)

    @TypeConverter
    fun toList(value: String): List<CardAction> = json.decodeFromString(value)
}
