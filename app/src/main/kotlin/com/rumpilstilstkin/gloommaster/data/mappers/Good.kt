package com.rumpilstilstkin.gloommaster.data.mappers

import com.rumpilstilstkin.gloommaster.bd.entity.GoodWithTranslation
import com.rumpilstilstkin.gloommaster.domain.entity.Good
import com.rumpilstilstkin.gloommaster.domain.entity.GoodType
import com.rumpilstilstkin.gloommaster.domain.entity.PackType

fun GoodWithTranslation.toDomain() =
    Good(
        id = this.good.goodId,
        displayNumber = this.good.displayNumber,
        name = this.name,
        type = GoodType.valueOf(this.good.type),
        cost = this.good.cost,
        image = this.good.image,
        pack = PackType.valueOf(this.good.pack),
    )
