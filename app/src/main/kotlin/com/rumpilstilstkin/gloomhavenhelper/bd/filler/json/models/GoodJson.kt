package com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.models

import com.rumpilstilstkin.gloomhavenhelper.bd.entity.GoodBd
import kotlinx.serialization.Serializable

@Serializable
data class GoodJson(
    val number: Int,
    val name: String,
    val type: String,
    val image: String,
    val cost: Int,
    val pack: String,
    val count: Int = 1,
    val isDrawing: Boolean = false
){
    fun toEntity() = GoodBd(
        number = number,
        name = name,
        type = type,
        image = image,
        cost = cost,
        pack = pack,
        isDrawing = isDrawing
    )
}
