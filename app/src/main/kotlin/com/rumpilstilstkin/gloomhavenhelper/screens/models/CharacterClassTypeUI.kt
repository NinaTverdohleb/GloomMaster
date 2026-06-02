package com.rumpilstilstkin.gloomhavenhelper.screens.models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterClassType

enum class CharacterClassTypeUI(
    @param:StringRes val titleRes: Int,
    @param:DrawableRes val image: Int,
    val type: CharacterClassType,
) {
    Brute(R.string.class_brute, R.drawable.ic_br, CharacterClassType.Brute),
    Tinkerer(R.string.class_tinkerer, R.drawable.ic_ti, CharacterClassType.Tinkerer),
    Spellweaver(R.string.class_spellweaver, R.drawable.ic_sw, CharacterClassType.Spellweaver),
    Scoundrel(R.string.class_scoundrel, R.drawable.ic_sc, CharacterClassType.Scoundrel),
    Cragheart(R.string.class_cragheart, R.drawable.ic_ch, CharacterClassType.Cragheart),
    Mindthief(R.string.class_mindthief, R.drawable.ic_mt, CharacterClassType.Mindthief),
    Diviner(R.string.class_diviner, R.drawable.ic_dr, CharacterClassType.Diviner),
    BeastTyrant(R.string.class_beast_tyrant, R.drawable.ic_bt, CharacterClassType.BeastTyrant),
    Doomstalker(R.string.class_doomstalker, R.drawable.ic_ds, CharacterClassType.Doomstalker),
    Elementalist(R.string.class_elementalist, R.drawable.ic_el, CharacterClassType.Elementalist),
    Soothsinger(R.string.class_soothsinger, R.drawable.ic_ss, CharacterClassType.Soothsinger),
    Sawbones(R.string.class_sawbones, R.drawable.ic_sb, CharacterClassType.Sawbones),
    Plagueherald(R.string.class_plagueherald, R.drawable.ic_ph, CharacterClassType.Plagueherald),
    Nightshroud(R.string.class_nightshroud, R.drawable.ic_ns, CharacterClassType.Nightshroud),
    Summoner(R.string.class_summoner, R.drawable.ic_su, CharacterClassType.Summoner),
    Sunkeeper(R.string.class_sunkeeper, R.drawable.ic_sk, CharacterClassType.Sunkeeper),
    Quartermaster(R.string.class_quartermaster, R.drawable.ic_qm, CharacterClassType.Quartermaster),
    Berserker(R.string.class_berserker, R.drawable.ic_be, CharacterClassType.Berserker),
    ;

    companion object {
        fun CharacterClassType.toCharacterClassTypeUI(): CharacterClassTypeUI = CharacterClassTypeUI.entries.first { it.type == this }
    }
}
