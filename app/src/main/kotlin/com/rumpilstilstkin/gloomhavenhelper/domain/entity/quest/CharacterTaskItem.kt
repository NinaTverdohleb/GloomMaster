package com.rumpilstilstkin.gloomhavenhelper.domain.entity.quest

import kotlinx.serialization.KSerializer
import kotlinx.serialization.PolymorphicSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonEncoder
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

@Serializable(with = CharacterTaskItem.Serializer::class)
sealed interface CharacterTaskItem {
    val id: Int
    val type: String
    val completed: Boolean
    val priority: Int
    val text: String

    @Serializable
    data class Check(
        override val id: Int,
        override val priority: Int,
        override val text: String,
        val isChecked: Boolean = false
    ) : CharacterTaskItem {
        override val completed: Boolean
            get() = isChecked
        override val type: String
            get() = TaskType.Check.name
    }

    @Serializable
    data class Count(
        override val id: Int,
        override val priority: Int,
        override val text: String,
        val count: Int = 0,
        val currentCount: Int = 0,
        val step: Int = 1,
    ) : CharacterTaskItem {
        override val completed: Boolean
            get() = count <= currentCount
        override val type: String
            get() = TaskType.Count.name
    }

    object Serializer : KSerializer<CharacterTaskItem> {
        val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
            encodeDefaults = true
            prettyPrint = true
            coerceInputValues = true
        }
        override val descriptor: SerialDescriptor
            get() = PolymorphicSerializer(CharacterTaskItem::class).descriptor

        override fun serialize(encoder: Encoder, value: CharacterTaskItem) {
            require(encoder is JsonEncoder) { "This class can be serialized only by Json" }

            // Сериализуем объект в JsonObject
            val jsonObject = when (value) {
                is Check -> encoder.json.encodeToJsonElement(Check.serializer(), value).jsonObject
                is Count -> encoder.json.encodeToJsonElement(Count.serializer(), value).jsonObject
            }.toMutableMap()

            // Добавляем поле "type"
            jsonObject["type"] = JsonPrimitive(value.type)

            // Сериализуем JsonObject обратно
            encoder.encodeJsonElement(JsonObject(jsonObject))
        }

        override fun deserialize(decoder: Decoder): CharacterTaskItem {
            val jsonElement = (decoder as JsonDecoder).decodeJsonElement()
            return when (val itemType = jsonElement.jsonObject["type"]?.jsonPrimitive?.content) {
                TaskType.Count.name -> json.decodeFromJsonElement(Count.serializer(), jsonElement)
                TaskType.Check.name -> json.decodeFromJsonElement(Check.serializer(), jsonElement)
                else -> throw SerializationException("Unknown itemType: $itemType")
            }
        }
    }
}