package com.rumpilstilstkin.gloomhavenhelper.bd.typeconverters

import androidx.room.TypeConverter
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.AvaliableCard
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioGameStateMagic
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioGameStateMonsterItem
import kotlinx.serialization.json.Json

class ScenarioConverters {
    private val json = Json { ignoreUnknownKeys = true }

    // Для List<Int>
    @TypeConverter
    fun fromIntList(value: List<Int>) = json.encodeToString(value)

    @TypeConverter
    fun toIntList(value: String) = json.decodeFromString<List<Int>>(value)

    // Для List<ScenarioGameStateMagic>
    @TypeConverter
    fun fromMagicList(value: List<ScenarioGameStateMagic>) = json.encodeToString(value)

    @TypeConverter
    fun toMagicList(value: String) = json.decodeFromString<List<ScenarioGameStateMagic>>(value)

    // Для List<ScenarioGameStateMonsterItem>
    @TypeConverter
    fun fromMonsterList(value: List<ScenarioGameStateMonsterItem>) = json.encodeToString(value)

    @TypeConverter
    fun toMonsterList(value: String) = json.decodeFromString<List<ScenarioGameStateMonsterItem>>(value)

    // Для List<AvaliableCard>
    @TypeConverter
    fun fromAvaliableCardList(value: List<AvaliableCard>) = json.encodeToString(value)

    @TypeConverter
    fun toAvaliableCardList(value: String) = json.decodeFromString<List<AvaliableCard>>(value)
}