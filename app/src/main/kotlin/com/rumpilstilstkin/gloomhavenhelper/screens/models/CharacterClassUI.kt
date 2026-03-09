package com.rumpilstilstkin.gloomhavenhelper.screens.models

import androidx.annotation.DrawableRes
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterClassType

enum class CharacterClassTypeUI(
    val title: String,
    @DrawableRes val image: Int,
    val type: CharacterClassType,
) {
    Brute("Инокс, дикарь", R.drawable.ic_br, CharacterClassType.Brute),
    Tinkerer("Куатрил, изобретатель", R.drawable.ic_ti, CharacterClassType.Tinkerer),
    Spellweaver("Орхид, плетущая чары", R.drawable.ic_sw, CharacterClassType.Spellweaver),
    Scoundrel("Человек, плутовка", R.drawable.ic_sc, CharacterClassType.Scoundrel),
    Cragheart("Саввас, пустотелый", R.drawable.ic_ch, CharacterClassType.Cragheart),
    Mindthief("Вермлинг, крадущая разум", R.drawable.ic_mt, CharacterClassType.Mindthief),
    Diviner("Эстер, прорицательница", R.drawable.ic_dr, CharacterClassType.Diviner),
    BeastTyrant("Вермлинг, повелитель зверей", R.drawable.ic_bt, CharacterClassType.BeastTyrant),
    Doomstalker("Орхид, обрекающий", R.drawable.ic_ds, CharacterClassType.Doomstalker),
    Elementalist("Саввас, элементалист", R.drawable.ic_el, CharacterClassType.Elementalist),
    Soothsinger("Куатрил, воспевающая", R.drawable.ic_ss, CharacterClassType.Soothsinger),
    Sawbones("Человек, костоправ", R.drawable.ic_sb, CharacterClassType.Sawbones),
    Plagueherald("Жнец, предвестник чумы", R.drawable.ic_ph, CharacterClassType.Plagueherald),
    Nightshroud("Эстер, покров ночи", R.drawable.ic_ns, CharacterClassType.Nightshroud),
    Summoner("Эстер, призывающая", R.drawable.ic_su, CharacterClassType.Summoner),
    Sunkeeper("Валрат, хранящая солнце", R.drawable.ic_sk, CharacterClassType.Sunkeeper),
    Quartermaster("Валрат, интендант", R.drawable.ic_qm, CharacterClassType.Quartermaster),
    Berserker("Инокс, берсерк", R.drawable.ic_be, CharacterClassType.Berserker);

    companion object {
        fun CharacterClassType.toCharacterClassTypeUI(): CharacterClassTypeUI =
            CharacterClassTypeUI.entries.first { it.type == this }
    }
}
