package com.rumpilstilstkin.gloomhavenhelper.data.mappers

import com.rumpilstilstkin.gloomhavenhelper.bd.entity.GoodWithTranslation
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Good
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.GoodType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.PackType

fun GoodWithTranslation.toDomain() = Good(
    number = this.good.goodNumber,
    name = this.name,
    type = GoodType.valueOf(this.good.type),
    cost = this.good.cost,
    image = this.good.image,
    pack = PackType.valueOf(this.good.pack)
)