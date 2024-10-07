package com.rumpilstilstkin.gloomhavenhelper.domain.entity

private enum class IconCode(val id: String) {
    MINUS1("01"),
    MINUS2("02"),
    PLUS1("03"),
    PLUS2("04"),
    PLUS3("05"),
    PLUS4("06"),
    NEXT("07"),
    PUSH("08"),
    PROBOY("09"),
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
}


enum class IconVectorCode(val id: String){
    NEXT(IconCode.NEXT.id),
    PUSH(IconCode.PUSH.id),
    PROBOY(IconCode.PROBOY.id),
    STUN(IconCode.STUN.id),
    DISARM(IconCode.DISARM.id),
    CONFUSE(IconCode.CONFUSE.id),
    TARGET(IconCode.TARGET.id),
    WOUND(IconCode.WOUND.id),
    PULL(IconCode.PULL.id),
    PARALYZE(IconCode.PARALYZE.id),
}

enum class IconResCode(val id: String) {
    MINUS1(IconCode.MINUS1.id),
    MINUS2(IconCode.MINUS2.id),
    PLUS1(IconCode.PLUS1.id),
    PLUS2(IconCode.PLUS2.id),
    PLUS3(IconCode.PLUS3.id),
    PLUS4(IconCode.PLUS4.id),
    ZERO(IconCode.ZERO.id),
    FROST(IconCode.FROST.id),
    SUN(IconCode.SUN.id),
    MOON(IconCode.MOON.id),
    AIR(IconCode.AIR.id),
    FIRE(IconCode.FIRE.id),
    EARTH(IconCode.EARTH.id),
}