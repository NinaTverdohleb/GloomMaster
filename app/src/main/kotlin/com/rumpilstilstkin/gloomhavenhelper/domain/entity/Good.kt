package com.rumpilstilstkin.gloomhavenhelper.domain.entity

data class Good(
    val id: Int,
    val number: Int,
    val name: String,
    val type: GoodType,
    val cost: Int,
    val image: String,
    val characterGoodId: Int? = null,
)

enum class GoodType {
    Body,
    Head,
    Foot,
    Arm,
    DoubleArm,
    SmallThing
}
