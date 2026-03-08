package com.rumpilstilstkin.gloomhavenhelper.domain.entity

import java.util.EnumSet

enum class PackType {
    MAIN,
    FORGOTTEN_CIRCLES;
}

val selectablePacks = EnumSet.of(
    PackType.FORGOTTEN_CIRCLES
)