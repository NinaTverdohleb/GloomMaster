package com.rumpilstilstkin.gloomhavenhelper.ui.icons

import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.CharacterIcon
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterClassType

fun CharacterClassType.toImage() =
    when (this) {
        CharacterClassType.Brute -> CharacterIcon.Brute
        CharacterClassType.BeastTyrant -> CharacterIcon.BeastTyrant
        CharacterClassType.Cragheart -> CharacterIcon.Cragheart
        CharacterClassType.Doomstalker -> CharacterIcon.Doomstalker
        CharacterClassType.Elementalist -> CharacterIcon.Elementalist
        CharacterClassType.Soothsinger -> CharacterIcon.Soothsinger
        CharacterClassType.Sawbones -> CharacterIcon.Sawbones
        CharacterClassType.Plagueherald -> CharacterIcon.Plagueherald
        CharacterClassType.Tinkerer -> CharacterIcon.Tinkerer
        CharacterClassType.Nightshroud -> CharacterIcon.Nightshroud
        CharacterClassType.Spellweaver -> CharacterIcon.Spellweaver
        CharacterClassType.Summoner -> CharacterIcon.Summoner
        CharacterClassType.Sunkeeper -> CharacterIcon.Sunkeeper
        CharacterClassType.Mindthief -> CharacterIcon.Mindthief
        CharacterClassType.Scoundrel -> CharacterIcon.Scoundrel
        CharacterClassType.Quartermaster -> CharacterIcon.Quartermaster
        CharacterClassType.Diviner -> CharacterIcon.Diviner
        CharacterClassType.Berserker -> CharacterIcon.Berserker
    }
