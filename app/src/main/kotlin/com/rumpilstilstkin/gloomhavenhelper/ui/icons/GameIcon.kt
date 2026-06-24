package com.rumpilstilstkin.gloomhavenhelper.ui.icons

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.game.IconCode
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterStatType

enum class GameIcon(
    val textCode: IconCode,
    @param:StringRes val titleRes: Int,
    val imageRes: Int,
    val color: Color?,
) {
    SUN(textCode = IconCode.SUN, titleRes = R.string.icon_light, imageRes = R.drawable.ic_magic_sun, color = Color(0xFFD2A21E)),
    MOON(textCode = IconCode.MOON, titleRes = R.string.icon_light, imageRes = R.drawable.ic_magic_moon, color = Color(0xFF324C5C)),
    FIRE(textCode = IconCode.FIRE, titleRes = R.string.icon_light, imageRes = R.drawable.ic_magic_fire, color = Color(0xFFBF3A0A)),
    AIR(textCode = IconCode.AIR, titleRes = R.string.icon_light, imageRes = R.drawable.ic_magic_air, color = Color(0xFF2C568C)),
    FROST(textCode = IconCode.FROST, titleRes = R.string.icon_light, imageRes = R.drawable.ic_magic_frost, color = Color(0xFF67BFDF)),
    EARTH(textCode = IconCode.EARTH, titleRes = R.string.icon_light, imageRes = R.drawable.ic_magic_earth, color = Color(0xFF539155)),
    ARIA_0(textCode = IconCode.AREA_0, titleRes = R.string.icon_area, imageRes = R.drawable.ic_aria_0, color = null),
    ARIA_1(textCode = IconCode.AREA_1, titleRes = R.string.icon_area, imageRes = R.drawable.ic_aria_1, color = null),
    ARIA_2(textCode = IconCode.AREA_2, titleRes = R.string.icon_area, imageRes = R.drawable.ic_aria_2, color = null),
    ARIA_3(textCode = IconCode.AREA_3, titleRes = R.string.icon_area, imageRes = R.drawable.ic_aria_3, color = null),
    ARIA_4(textCode = IconCode.AREA_4, titleRes = R.string.icon_area, imageRes = R.drawable.ic_aria_4, color = null),
    ARIA_5(textCode = IconCode.AREA_5, titleRes = R.string.icon_area, imageRes = R.drawable.ic_aria_5, color = null),
    ARIA_6(textCode = IconCode.AREA_6, titleRes = R.string.icon_area, imageRes = R.drawable.ic_aria_6, color = null),
    PLUS_1(textCode = IconCode.PLUS1, titleRes = R.string.icon_plus_1, imageRes = R.drawable.ic_plus_one, color = null),
    PLUS_2(textCode = IconCode.PLUS2, titleRes = R.string.icon_plus_2, imageRes = R.drawable.ic_plus_two, color = null),
    PLUS_3(textCode = IconCode.PLUS3, titleRes = R.string.icon_plus_3, imageRes = R.drawable.ic_plus_three, color = null),
    PLUS_4(textCode = IconCode.PLUS4, titleRes = R.string.icon_plus_4, imageRes = R.drawable.ic_plus_four, color = null),
    PLUS_0(textCode = IconCode.ZERO, titleRes = R.string.icon_plus_0, imageRes = R.drawable.ic_plus_zero, color = null),
    MINUS_1(textCode = IconCode.MINUS1, titleRes = R.string.icon_minus_1, imageRes = R.drawable.ic_minus_one, color = null),
    MINUS_2(textCode = IconCode.MINUS2, titleRes = R.string.icon_minus_2, imageRes = R.drawable.ic_minus_two, color = null),
    NEXT(textCode = IconCode.NEXT, titleRes = R.string.icon_next, imageRes = R.drawable.ic_next, color = Color(0xFF407538)),
    PUSH(textCode = IconCode.PUSH, titleRes = R.string.icon_push, imageRes = R.drawable.ic_push, color = Color(0xFF585151)),
    PIERCE(textCode = IconCode.PIERCE, titleRes = R.string.icon_pierce, imageRes = R.drawable.ic_pierce, color = Color(0xFFC29240)),
    STUN(textCode = IconCode.STUN, titleRes = R.string.icon_stun, imageRes = R.drawable.ic_stun, color = Color(0xFF2F4C81)),
    DISARM(textCode = IconCode.DISARM, titleRes = R.string.icon_disarm, imageRes = R.drawable.ic_disarm, color = Color(0xFF527A72)),
    CONFUSE(textCode = IconCode.CONFUSE, titleRes = R.string.icon_confuse, imageRes = R.drawable.ic_confused, color = Color(0xFF70574A)),
    TARGET(textCode = IconCode.TARGET, titleRes = R.string.icon_target, imageRes = R.drawable.ic_target, color = Color(0xFFA43737)),
    WOUND(textCode = IconCode.WOUND, titleRes = R.string.icon_wound, imageRes = R.drawable.ic_wound, color = Color(0xFFDC7331)),
    PULL(textCode = IconCode.PULL, titleRes = R.string.icon_pull, imageRes = R.drawable.ic_pull, color = Color(0xFF585151)),
    IMMOBILIZE(
        textCode = IconCode.PARALYZE,
        titleRes = R.string.icon_immobilize,
        imageRes = R.drawable.ic_immobilize,
        color = Color(0xFF903E3E),
    ),
    POISON(textCode = IconCode.POISON, titleRes = R.string.icon_poison, imageRes = R.drawable.ic_poison, color = Color(0xFF6A7B57)),
    CURSE(textCode = IconCode.CURSE, titleRes = R.string.icon_curse, imageRes = R.drawable.ic_curse, color = Color(0xFF7D3194)),
    INVISIBILITY(
        textCode = IconCode.INVISIBILITY,
        titleRes = R.string.icon_invisibility,
        imageRes = R.drawable.ic_invisibility,
        color = Color(0xFF111113),
    ),
    BLESS(textCode = IconCode.BLESS, titleRes = R.string.icon_bless, imageRes = R.drawable.ic_bless, color = Color(0xFFC49D27)),
    STRENGTH(textCode = IconCode.STRENGTH, titleRes = R.string.icon_strength, imageRes = R.drawable.ic_strength, color = Color(0xFF3B9FC6)),
    RETALIATE(textCode = IconCode.RETALIATE, titleRes = R.string.icon_retaliate, imageRes = R.drawable.ic_retaliate, color = Color.White),
    RANGED_ATTACK(
        textCode = IconCode.RANGED_ATTACK,
        titleRes = R.string.icon_ranged_attack,
        imageRes = R.drawable.ic_range,
        color = Color.White,
    ),
    MOVE(textCode = IconCode.MOVE, titleRes = R.string.icon_move, imageRes = R.drawable.ic_move, color = Color.White),
    SHIELD(textCode = IconCode.SHIELD, titleRes = R.string.icon_shield, imageRes = R.drawable.ic_shield, color = Color.White),
    HEAL(textCode = IconCode.HEAL, titleRes = R.string.icon_heal, imageRes = R.drawable.ic_heal, color = Color.White),
    ATTACK(textCode = IconCode.ATTACK, titleRes = R.string.icon_attack, imageRes = R.drawable.ic_attack, color = Color.White),
    ARIA_7(textCode = IconCode.AREA_7, titleRes = R.string.icon_area, imageRes = R.drawable.ic_aria_7, color = null),
    SPEND_SUN(textCode = IconCode.SPEND_SUN, titleRes = R.string.icon_light, imageRes = R.drawable.ic_magic_sun_spend, color = null),
    SPEND_MOON(textCode = IconCode.SPEND_MOON, titleRes = R.string.icon_light, imageRes = R.drawable.ic_magic_moon_spend, color = null),
    SPEND_FIRE(textCode = IconCode.SPEND_FIRE, titleRes = R.string.icon_light, imageRes = R.drawable.ic_magic_fire_spend, color = null),
    SPEND_AIR(textCode = IconCode.SPEND_AIR, titleRes = R.string.icon_light, imageRes = R.drawable.ic_magic_air_spend, color = null),
    SPEND_FROST(textCode = IconCode.SPEND_FROST, titleRes = R.string.icon_light, imageRes = R.drawable.ic_magic_frost_spend, color = null),
    SPEND_EARTH(textCode = IconCode.SPEND_EARTH, titleRes = R.string.icon_light, imageRes = R.drawable.ic_magic_earth_spend, color = null),
    ARIA_8(textCode = IconCode.AREA_8, titleRes = R.string.icon_area, imageRes = R.drawable.ic_aria_8, color = null),
    AREA_9(textCode = IconCode.AREA_9, titleRes = R.string.icon_area, imageRes = R.drawable.ic_aria_9, color = null),
    AREA_10(textCode = IconCode.AREA_10, titleRes = R.string.icon_area, imageRes = R.drawable.ic_aria_10, color = null),
    AREA_11(textCode = IconCode.AREA_11, titleRes = R.string.icon_area, imageRes = R.drawable.ic_aria_11, color = null),
    AREA_12(textCode = IconCode.AREA_12, titleRes = R.string.icon_area, imageRes = R.drawable.ic_aria_12, color = null),
    LOOT(textCode = IconCode.LOOT, titleRes = R.string.icon_loot, imageRes = R.drawable.ic_loot, color = Color.White),
    SPEND_ANY(textCode = IconCode.SPEND_ANY, titleRes = R.string.icon_light, imageRes = R.drawable.ic_magic_any_spend, color = null),
    AREA_13(textCode = IconCode.AREA_13, titleRes = R.string.icon_area, imageRes = R.drawable.ic_aria_13, color = null),
    REGENERATE(
        textCode = IconCode.REGENERATE,
        titleRes = R.string.icon_regenerate,
        imageRes = R.drawable.ic_regenerate,
        color = Color(0xFFC23BC6),
    ),
    ;

    companion object {
        fun IconCode.toGameIcon(): GameIcon = GameIcon.entries.first { it.textCode == this }

        fun MonsterStatType.toGameIcon(): GameIcon =
            when (this) {
                MonsterStatType.ATTACK -> ATTACK
                MonsterStatType.MOVE -> MOVE
                MonsterStatType.RANGE -> RANGED_ATTACK
                MonsterStatType.SHIELD -> SHIELD
                MonsterStatType.RETALIATE -> RETALIATE
                MonsterStatType.TARGET -> TARGET
                MonsterStatType.POISON -> POISON
                MonsterStatType.WOUND -> WOUND
                MonsterStatType.MUDDLE -> CONFUSE
                MonsterStatType.STUN -> STUN
                MonsterStatType.IMMOBILIZE -> IMMOBILIZE
                MonsterStatType.DISARM -> DISARM
                MonsterStatType.CURSE -> CURSE
                MonsterStatType.STRENGTHEN -> STRENGTH
                MonsterStatType.INVISIBLE -> INVISIBILITY
                MonsterStatType.HEAL -> HEAL
                MonsterStatType.PUSH -> PUSH
                MonsterStatType.BLESS -> BLESS
                MonsterStatType.PULL -> PULL
                MonsterStatType.PIERCE -> PIERCE
                MonsterStatType.REGENERATE -> REGENERATE
            }
    }
}
