package com.rumpilstilstkin.gloomhavenhelper.domain.entity

private object IconCode {
    const val MINUS1 = "01"
    const val MINUS2 = "02"
    const val PLUS1 = "03"
    const val PLUS2 = "04"
    const val PLUS3 = "05"
    const val PLUS4 = "06"
    const val NEXT = "07"
    const val PUSH = "08"
    const val PROBOY = "09"
    const val STUN = "10"
    const val DISARM = "11"
    const val CONFUSE = "12"
    const val TARGET = "13"
    const val WOUND = "14"
    const val ZERO = "15"
    const val FROST = "16"
    const val PULL = "17"
    const val PARALYZE = "18"
    const val SUN = "19"
    const val MOON = "20"
    const val AIR = "21"
    const val FIRE = "22"
    const val EARTH = "23"
    const val INVISIBILITY = "24"
    const val POISON = "25"
    const val CURSE = "26"
    const val AREA_0 = "27"
    const val SPEND_FROST = "28"
    const val SPEND_SUN = "29"
    const val SPEND_MOON = "30"
    const val SPEND_AIR = "31"
    const val SPEND_FIRE = "32"
    const val SPEND_EARTH = "33"
    const val SPEND_ANY = "34"
    const val AREA_1 = "35"
    const val AREA_2 = "36"
    const val AREA_3 = "37"
    const val AREA_4 = "38"
    const val AREA_5 = "39"
    const val AREA_6 = "40"
}

enum class IconVectorCode(val id: String) {
    NEXT(IconCode.NEXT),
    PUSH(IconCode.PUSH),
    PROBOY(IconCode.PROBOY),
    STUN(IconCode.STUN),
    DISARM(IconCode.DISARM),
    CONFUSE(IconCode.CONFUSE),
    TARGET(IconCode.TARGET),
    WOUND(IconCode.WOUND),
    PULL(IconCode.PULL),
    PARALYZE(IconCode.PARALYZE),
    INVISIBILITY(IconCode.INVISIBILITY),
    POISON(IconCode.POISON),
    CURSE(IconCode.CURSE),
}

enum class IconResCode(val id: String) {
    MINUS1(IconCode.MINUS1),
    MINUS2(IconCode.MINUS2),
    PLUS1(IconCode.PLUS1),
    PLUS2(IconCode.PLUS2),
    PLUS3(IconCode.PLUS3),
    PLUS4(IconCode.PLUS4),
    ZERO(IconCode.ZERO),
    FROST(IconCode.FROST),
    SUN(IconCode.SUN),
    MOON(IconCode.MOON),
    AIR(IconCode.AIR),
    FIRE(IconCode.FIRE),
    EARTH(IconCode.EARTH),
    SPEND_FROST(IconCode.SPEND_FROST),
    SPEND_SUN(IconCode.SPEND_SUN),
    SPEND_MOON(IconCode.SPEND_MOON),
    SPEND_AIR(IconCode.SPEND_AIR),
    SPEND_FIRE(IconCode.SPEND_FIRE),
    SPEND_EARTH(IconCode.SPEND_EARTH),
    SPEND_ANY(IconCode.SPEND_ANY),
    AREA_0(IconCode.AREA_0),
    AREA_1(IconCode.AREA_1)
}