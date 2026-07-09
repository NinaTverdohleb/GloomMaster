package com.rumpilstilstkin.gloommaster.data.mappers

import com.rumpilstilstkin.gloommaster.bd.entity.CharacterPerkWithNameBd
import com.rumpilstilstkin.gloommaster.bd.entity.PerkTranslationBd
import com.rumpilstilstkin.gloommaster.domain.entity.Perk

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
