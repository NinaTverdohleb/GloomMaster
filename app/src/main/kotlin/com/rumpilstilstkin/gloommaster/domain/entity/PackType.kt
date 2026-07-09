package com.rumpilstilstkin.gloommaster.domain.entity

import java.util.EnumSet

enum class PackType {
    MAIN,
    FORGOTTEN_CIRCLES,
}

val selectablePacks: EnumSet<PackType> =
    EnumSet.of(
        PackType.FORGOTTEN_CIRCLES,
    )
