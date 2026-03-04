package com.rumpilstilstkin.gloomhavenhelper.screens.models

import androidx.compose.runtime.Immutable
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterClass
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterClassType

@Immutable
data class CharacterClassUI(
    val name: String,
    val classType: CharacterClassType,
    val imageRes: Int,
)

fun CharacterClass.toUi() = CharacterClassUI(
    name = this.name,
    classType = this.type,
    imageRes = this.type.toImage(),
)


fun CharacterClassType.toImage() = when(this) {
    CharacterClassType.Brute -> R.drawable.br
    CharacterClassType.BeastTyrant -> R.drawable.bt
    CharacterClassType.Cragheart -> R.drawable.ch
    CharacterClassType.Doomstalker -> R.drawable.ds
    CharacterClassType.Elementalist -> R.drawable.el
    CharacterClassType.Soothsinger -> R.drawable.ss
    CharacterClassType.Sawbones -> R.drawable.sb
    CharacterClassType.Plagueherald -> R.drawable.ph
    CharacterClassType.Tinkerer -> R.drawable.ti
    CharacterClassType.Nightshroud -> R.drawable.ns
    CharacterClassType.Spellweaver -> R.drawable.sw
    CharacterClassType.Summoner-> R.drawable.su
    CharacterClassType.Sunkeeper -> R.drawable.sk
    CharacterClassType.Mindthief -> R.drawable.mt
    CharacterClassType.Scoundrel -> R.drawable.sc
    CharacterClassType.Quartermaster -> R.drawable.qm
    CharacterClassType.Diviner -> R.drawable.dr
    CharacterClassType.Berserker -> R.drawable.be
}
