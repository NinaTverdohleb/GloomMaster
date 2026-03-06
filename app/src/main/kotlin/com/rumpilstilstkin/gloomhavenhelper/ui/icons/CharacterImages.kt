package com.rumpilstilstkin.gloomhavenhelper.ui.icons

import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterClassType

fun CharacterClassType.toImage() = when(this) {
    CharacterClassType.Brute -> R.drawable.ic_br
    CharacterClassType.BeastTyrant -> R.drawable.ic_bt
    CharacterClassType.Cragheart -> R.drawable.ic_ch
    CharacterClassType.Doomstalker -> R.drawable.ic_ds
    CharacterClassType.Elementalist -> R.drawable.ic_el
    CharacterClassType.Soothsinger -> R.drawable.ic_ss
    CharacterClassType.Sawbones -> R.drawable.ic_sb
    CharacterClassType.Plagueherald -> R.drawable.ic_ph
    CharacterClassType.Tinkerer -> R.drawable.ic_ti
    CharacterClassType.Nightshroud -> R.drawable.ic_ns
    CharacterClassType.Spellweaver -> R.drawable.ic_sw
    CharacterClassType.Summoner-> R.drawable.ic_su
    CharacterClassType.Sunkeeper -> R.drawable.ic_sk
    CharacterClassType.Mindthief -> R.drawable.ic_mt
    CharacterClassType.Scoundrel -> R.drawable.ic_sc
    CharacterClassType.Quartermaster -> R.drawable.ic_qm
    CharacterClassType.Diviner -> R.drawable.ic_dr
    CharacterClassType.Berserker -> R.drawable.ic_be
}
