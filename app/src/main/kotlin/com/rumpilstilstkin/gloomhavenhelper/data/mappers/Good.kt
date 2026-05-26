package com.rumpilstilstkin.gloomhavenhelper.data.mappers

import com.rumpilstilstkin.gloomhavenhelper.bd.entity.CharacterGoodDetailsBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.GoodBd
import com.rumpilstilstkin.gloomhavenhelper.data.TextResolver
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Good
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.GoodType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.PackType
import com.rumpilstilstkin.gloomhavenhelper.localization.TranslationKeys

fun GoodBd.toDomain() = Good(
    id = this.goodId,
    number = this.number,
    name = this.name,
    type = GoodType.valueOf(this.type),
    cost = this.cost,
    image = this.image,
    pack = PackType.valueOf(this.pack)
)

fun CharacterGoodDetailsBd.toDomain() = Good(
    id = this.good.goodId,
    number = this.good.number,
    name = this.good.name,
    type = GoodType.valueOf(this.good.type),
    cost = this.good.cost,
    characterGoodId = this.characterGood.id,
    image = this.good.image,
    pack = PackType.valueOf(this.good.pack)
)

/**
 * Replaces the display-only [Good.name] with text for the active locale, keyed by the stable
 * item [Good.number]. Identity fields (id, number, cost, type, image) keep their canonical
 * values, so owned-item matching and saved progress are unaffected by language.
 */
fun Good.localized(resolver: TextResolver) = copy(
    name = resolver.resolve(TranslationKeys.GOOD, number.toString(), TranslationKeys.FIELD_NAME)
)