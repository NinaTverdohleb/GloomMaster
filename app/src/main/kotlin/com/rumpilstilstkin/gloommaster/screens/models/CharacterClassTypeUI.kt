package com.rumpilstilstkin.gloommaster.screens.models

import androidx.annotation.StringRes
import com.rumpilstilstkin.gloommaster.R
import com.rumpilstilstkin.gloommaster.designsystem.icons.CharacterIcon
import com.rumpilstilstkin.gloommaster.domain.entity.CharacterClassType

enum class CharacterClassTypeUI(
    @param:StringRes val titleRes: Int,
    val image: CharacterIcon,
    val type: CharacterClassType,
) {
    Brute(R.string.class_brute, CharacterIcon.Brute, CharacterClassType.Brute),
    Tinkerer(R.string.class_tinkerer, CharacterIcon.Tinkerer, CharacterClassType.Tinkerer),
    Spellweaver(R.string.class_spellweaver, CharacterIcon.Spellweaver, CharacterClassType.Spellweaver),
    Scoundrel(R.string.class_scoundrel, CharacterIcon.Scoundrel, CharacterClassType.Scoundrel),
    Cragheart(R.string.class_cragheart, CharacterIcon.Cragheart, CharacterClassType.Cragheart),
    Mindthief(R.string.class_mindthief, CharacterIcon.Mindthief, CharacterClassType.Mindthief),
    Diviner(R.string.class_diviner, CharacterIcon.Diviner, CharacterClassType.Diviner),
    BeastTyrant(R.string.class_beast_tyrant, CharacterIcon.BeastTyrant, CharacterClassType.BeastTyrant),
    Doomstalker(R.string.class_doomstalker, CharacterIcon.Doomstalker, CharacterClassType.Doomstalker),
    Elementalist(R.string.class_elementalist, CharacterIcon.Elementalist, CharacterClassType.Elementalist),
    Soothsinger(R.string.class_soothsinger, CharacterIcon.Soothsinger, CharacterClassType.Soothsinger),
    Sawbones(R.string.class_sawbones, CharacterIcon.Sawbones, CharacterClassType.Sawbones),
    Plagueherald(R.string.class_plagueherald, CharacterIcon.Plagueherald, CharacterClassType.Plagueherald),
    Nightshroud(R.string.class_nightshroud, CharacterIcon.Nightshroud, CharacterClassType.Nightshroud),
    Summoner(R.string.class_summoner, CharacterIcon.Summoner, CharacterClassType.Summoner),
    Sunkeeper(R.string.class_sunkeeper, CharacterIcon.Sunkeeper, CharacterClassType.Sunkeeper),
    Quartermaster(R.string.class_quartermaster, CharacterIcon.Quartermaster, CharacterClassType.Quartermaster),
    Berserker(R.string.class_berserker, CharacterIcon.Berserker, CharacterClassType.Berserker),
    ;

    companion object {
        fun CharacterClassType.toCharacterClassTypeUI(): CharacterClassTypeUI = entries.first { it.type == this }
    }
}
