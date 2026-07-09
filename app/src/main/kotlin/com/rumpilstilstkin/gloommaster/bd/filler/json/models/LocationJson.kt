package com.rumpilstilstkin.gloommaster.bd.filler.json.models

import com.rumpilstilstkin.gloommaster.bd.entity.LocationBd
import com.rumpilstilstkin.gloommaster.bd.entity.LocationTranslateBd
import kotlinx.serialization.Serializable

@Serializable
data class LocationJson(
    val slug: String,
) {
    fun toEntity() =
        LocationBd(
            slug = slug,
        )
}

@Serializable
data class LocationTranslationJson(
    val slug: String,
    val name: String,
) {
    fun toEntity(locale: String) =
        LocationTranslateBd(
            slug = slug,
            locale = locale,
            name = name,
        )
}
