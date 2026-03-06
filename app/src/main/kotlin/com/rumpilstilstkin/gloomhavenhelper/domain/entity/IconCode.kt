package com.rumpilstilstkin.gloomhavenhelper.domain.entity

enum class IconCode(val value: String) {
    MINUS1("01"),
    MINUS2("02"),
    PLUS1("03"),
    PLUS2("04"),
    PLUS3("05"),
    PLUS4("06"),
    NEXT("07"),
    PUSH("08"),
    PIERCE("09"),
    STUN("10"),
    DISARM("11"),
    CONFUSE("12"),
    TARGET("13"),
    WOUND("14"),
    ZERO("15"),

    FROST("16"),

    PULL("17"),
    PARALYZE("18"),

    SUN("19"),
    MOON("20"),
    AIR("21"),
    FIRE("22"),
    EARTH("23"),

    INVISIBILITY("24"),
    POISON("25"),
    CURSE("26"),

    AREA_0("27"),
    AREA_1("28"),
    AREA_2("29"),
    AREA_3("30"),
    AREA_4("31"),
    AREA_5("32"),
    AREA_6("33"),
}

enum class IconVectorCode(val id: IconCode) {
    NEXT(IconCode.NEXT),
    PUSH(IconCode.PUSH),
    PROBOY(IconCode.PIERCE),
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

enum class IconResCode(val id: IconCode) {
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
}