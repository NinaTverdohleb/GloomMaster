package com.rumpilstilstkin.gloomhavenhelper.data.mappers

import com.rumpilstilstkin.gloomhavenhelper.bd.entity.CharacterPerkWithNameBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.PerkTranslationBd
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Perk

fun CharacterPerkWithNameBd.toDomain() =
    Perk(
        id = this.perk.perkId,
        text = this.text,
    )

fun PerkTranslationBd.toDomain() =
    Perk(
        id = this.perkId,
        text = this.text,
    )
