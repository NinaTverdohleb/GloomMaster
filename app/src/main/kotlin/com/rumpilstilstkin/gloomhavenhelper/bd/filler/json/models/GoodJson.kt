package com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.models

import com.rumpilstilstkin.gloomhavenhelper.bd.entity.GoodBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.GoodTranslationsBd
import kotlinx.serialization.Serializable

@Serializable
data class GoodJson(
    val number: Int,
    val type: String,
    val image: String,
    val cost: Int,
    val pack: String,
    val count: Int = 1,
    val isDrawing: Boolean = false
) {
    fun toEntity() = GoodBd(
        goodNumber = number,
        type = type,
        cost = cost,
        image = image,
        pack = pack,
        isDrawing = isDrawing
    )
}

@Serializable
data class GoodTranslationJson(
    val goodNumber: Int,
    val name: String
) {
    fun toEntity(locale: String) = GoodTranslationsBd(
        goodNumber = goodNumber,
        locale = locale,
        name = name
    )
}
