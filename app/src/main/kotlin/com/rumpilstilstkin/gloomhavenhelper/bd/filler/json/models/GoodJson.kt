package com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.models

import com.rumpilstilstkin.gloomhavenhelper.bd.entity.GoodBd
import kotlinx.serialization.Serializable

@Serializable
data class GoodJson(
    // Stable catalog key (snake_case slug of the English name) — the translation-store key. The
    // numeric [number] is kept as the in-game item card number for display, sort and search.
    val key: String,
    val number: Int,
    val type: String,
    val image: String,
    val cost: Int,
    val pack: String,
    val count: Int = 1,
    val isDrawing: Boolean = false
){
    fun toEntity() = GoodBd(
        key = key,
        number = number,
        type = type,
        image = image,
        cost = cost,
        pack = pack,
        isDrawing = isDrawing
    )
}
