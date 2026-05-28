package com.rumpilstilstkin.gloomhavenhelper.data.mappers

import com.rumpilstilstkin.gloomhavenhelper.bd.entity.CharacterGoodDetailsBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.GoodBd
import com.rumpilstilstkin.gloomhavenhelper.data.TextResolver
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Good
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.GoodType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.PackType
import com.rumpilstilstkin.gloomhavenhelper.localization.TranslationKeys

// [Good.name] is display-only and always populated by [localized]; it starts blank because the
// canonical (display) name no longer lives in the catalog.
fun GoodBd.toDomain() = Good(
    id = this.goodId,
    key = this.key,
    number = this.number,
    name = "",
    type = GoodType.valueOf(this.type),
    cost = this.cost,
    image = this.image,
    pack = PackType.valueOf(this.pack)
)

fun CharacterGoodDetailsBd.toDomain() = Good(
    id = this.good.goodId,
    key = this.good.key,
    number = this.good.number,
    name = "",
    type = GoodType.valueOf(this.good.type),
    cost = this.good.cost,
    characterGoodId = this.characterGood.id,
    image = this.good.image,
    pack = PackType.valueOf(this.good.pack)
)

/**
 * Replaces the display-only [Good.name] with text for the active locale, keyed by the stable
 * item [Good.key]. Identity fields (id, key, number, cost, type, image) keep their canonical
 * values, so owned-item matching and saved progress are unaffected by language.
 */
fun Good.localized(resolver: TextResolver) = copy(
    name = resolver.resolve(TranslationKeys.GOOD, key, TranslationKeys.FIELD_NAME)
)