package com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.game

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.rumpilstilstkin.gloomhavenhelper.designsystem.R
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.ActionIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.AreaIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.EffectIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.GloomIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.MagicIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.ModifierIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.onError
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.onSurfaceVariant

enum class GameIcon(
    val textCode: IconCode,
    @param:StringRes val titleRes: Int,
    val image: GloomIcon,
    val color: Color,
) {
    SUN(
        textCode = IconCode.SUN,
        titleRes = R.string.design_system_icon_light,
        image = MagicIcon.Sun,
        color = MagicIcon.Sun.tintColor,
    ),
    MOON(
        textCode = IconCode.MOON,
        titleRes = R.string.design_system_icon_light,
        image = MagicIcon.Moon,
        color = MagicIcon.Moon.tintColor,
    ),
    FIRE(
        textCode = IconCode.FIRE,
        titleRes = R.string.design_system_icon_light,
        image = MagicIcon.Fire,
        color = MagicIcon.Fire.tintColor,
    ),
    AIR(
        textCode = IconCode.AIR,
        titleRes = R.string.design_system_icon_light,
        image = MagicIcon.Air,
        color = MagicIcon.Air.tintColor,
    ),
    FROST(
        textCode = IconCode.FROST,
        titleRes = R.string.design_system_icon_light,
        image = MagicIcon.Frost,
        color = MagicIcon.Frost.tintColor,
    ),
    EARTH(
        textCode = IconCode.EARTH,
        titleRes = R.string.design_system_icon_light,
        image = MagicIcon.Earth,
        color = MagicIcon.Earth.tintColor,
    ),
    AREA_0(
        textCode = IconCode.AREA_0,
        titleRes = R.string.design_system_icon_area,
        image = AreaIcon.Area0,
        color = Color.Unspecified,
    ),
    AREA_1(
        textCode = IconCode.AREA_1,
        titleRes = R.string.design_system_icon_area,
        image = AreaIcon.Area1,
        color = Color.Unspecified,
    ),
    AREA_2(
        textCode = IconCode.AREA_2,
        titleRes = R.string.design_system_icon_area,
        image = AreaIcon.Area2,
        color = Color.Unspecified,
    ),
    AREA_3(
        textCode = IconCode.AREA_3,
        titleRes = R.string.design_system_icon_area,
        image = AreaIcon.Area3,
        color = Color.Unspecified,
    ),
    AREA_4(
        textCode = IconCode.AREA_4,
        titleRes = R.string.design_system_icon_area,
        image = AreaIcon.Area4,
        color = Color.Unspecified,
    ),
    AREA_5(
        textCode = IconCode.AREA_5,
        titleRes = R.string.design_system_icon_area,
        image = AreaIcon.Area5,
        color = Color.Unspecified,
    ),
    AREA_6(
        textCode = IconCode.AREA_6,
        titleRes = R.string.design_system_icon_area,
        image = AreaIcon.Area6,
        color = Color.Unspecified,
    ),
    PLUS_1(
        textCode = IconCode.PLUS1,
        titleRes = R.string.design_system_icon_plus_1,
        image = ModifierIcon.Plus1,
        color = Color(0xFF7EC16F),
    ),
    PLUS_2(
        textCode = IconCode.PLUS2,
        titleRes = R.string.design_system_icon_plus_2,
        image = ModifierIcon.Plus2,
        color = Color(0xFF7EC16F),
    ),
    PLUS_3(
        textCode = IconCode.PLUS3,
        titleRes = R.string.design_system_icon_plus_3,
        image = ModifierIcon.Plus3,
        color = Color(0xFF7EC16F),
    ),
    PLUS_4(
        textCode = IconCode.PLUS4,
        titleRes = R.string.design_system_icon_plus_4,
        image = ModifierIcon.Plus4,
        color = Color(0xFF7EC16F),
    ),
    PLUS_0(
        textCode = IconCode.ZERO,
        titleRes = R.string.design_system_icon_plus_0,
        image = ModifierIcon.Plus0,
        color = Color(0xFFA78B64),
    ),
    MINUS_1(
        textCode = IconCode.MINUS1,
        titleRes = R.string.design_system_icon_minus_1,
        image = ModifierIcon.Minus1,
        color = Color(0xFF9E5252),
    ),
    MINUS_2(
        textCode = IconCode.MINUS2,
        titleRes = R.string.design_system_icon_minus_2,
        image = ModifierIcon.Minus2,
        color = Color(0xFF9E5252),
    ),
    NEXT(
        textCode = IconCode.NEXT,
        titleRes = R.string.design_system_icon_next,
        image = EffectIcon.Next,
        color = Color(0xFF407538),
    ),
    PUSH(
        textCode = IconCode.PUSH,
        titleRes = R.string.design_system_icon_push,
        image = EffectIcon.Push,
        color = Color(0xFF585151),
    ),
    PIERCE(
        textCode = IconCode.PIERCE,
        titleRes = R.string.design_system_icon_pierce,
        image = EffectIcon.Pierce,
        color = Color(0xFFC29240),
    ),
    STUN(
        textCode = IconCode.STUN,
        titleRes = R.string.design_system_icon_stun,
        image = EffectIcon.Stun,
        color = Color(0xFF2F4C81),
    ),
    DISARM(
        textCode = IconCode.DISARM,
        titleRes = R.string.design_system_icon_disarm,
        image = EffectIcon.Disarm,
        color = Color(0xFF527A72),
    ),
    CONFUSE(
        textCode = IconCode.CONFUSE,
        titleRes = R.string.design_system_icon_confuse,
        image = EffectIcon.Muddle,
        color = Color(0xFF70574A),
    ),
    TARGET(
        textCode = IconCode.TARGET,
        titleRes = R.string.design_system_icon_target,
        image = EffectIcon.Target,
        color = Color(0xFFA43737),
    ),
    WOUND(
        textCode = IconCode.WOUND,
        titleRes = R.string.design_system_icon_wound,
        image = EffectIcon.Wound,
        color = Color(0xFFDC7331),
    ),
    PULL(
        textCode = IconCode.PULL,
        titleRes = R.string.design_system_icon_pull,
        image = EffectIcon.Pull,
        color = Color(0xFF585151),
    ),
    IMMOBILIZE(
        textCode = IconCode.PARALYZE,
        titleRes = R.string.design_system_icon_immobilize,
        image = EffectIcon.Immobilize,
        color = Color(0xFF903E3E),
    ),
    POISON(
        textCode = IconCode.POISON,
        titleRes = R.string.design_system_icon_poison,
        image = EffectIcon.Poison,
        color = Color(0xFF6A7B57),
    ),
    CURSE(
        textCode = IconCode.CURSE,
        titleRes = R.string.design_system_icon_curse,
        image = EffectIcon.Curse,
        color = Color(0xFF7D3194),
    ),
    INVISIBILITY(
        textCode = IconCode.INVISIBILITY,
        titleRes = R.string.design_system_icon_invisibility,
        image = EffectIcon.Invisible,
        color = Color(0xFF111113),
    ),
    BLESS(
        textCode = IconCode.BLESS,
        titleRes = R.string.design_system_icon_bless,
        image = EffectIcon.Bless,
        color = Color(0xFFC49D27),
    ),
    STRENGTH(
        textCode = IconCode.STRENGTH,
        titleRes = R.string.design_system_icon_strength,
        image = EffectIcon.Strengthen,
        color = Color(0xFF3B9FC6),
    ),
    RETALIATE(
        textCode = IconCode.RETALIATE,
        titleRes = R.string.design_system_icon_retaliate,
        image = ActionIcon.Retaliate,
        color = Color.White,
    ),
    RANGED_ATTACK(
        textCode = IconCode.RANGED_ATTACK,
        titleRes = R.string.design_system_icon_ranged_attack,
        image = ActionIcon.Range,
        color = Color.White,
    ),
    MOVE(
        textCode = IconCode.MOVE,
        titleRes = R.string.design_system_icon_move,
        image = ActionIcon.Move,
        color = onSurfaceVariant,
    ),
    SHIELD(
        textCode = IconCode.SHIELD,
        titleRes = R.string.design_system_icon_shield,
        image = ActionIcon.Shield,
        color = onSurfaceVariant,
    ),
    HEAL(
        textCode = IconCode.HEAL,
        titleRes = R.string.design_system_icon_heal,
        image = ActionIcon.Heal,
        color = onSurfaceVariant,
    ),
    ATTACK(
        textCode = IconCode.ATTACK,
        titleRes = R.string.design_system_icon_attack,
        image = ActionIcon.Attack,
        color = onSurfaceVariant,
    ),
    AREA_7(
        textCode = IconCode.AREA_7,
        titleRes = R.string.design_system_icon_area,
        image = AreaIcon.Area7,
        color = Color.Unspecified,
    ),
    SPEND_SUN(
        textCode = IconCode.SPEND_SUN,
        titleRes = R.string.design_system_icon_light,
        image = MagicIcon.SpendSun,
        color = Color.Unspecified,
    ),
    SPEND_MOON(
        textCode = IconCode.SPEND_MOON,
        titleRes = R.string.design_system_icon_light,
        image = MagicIcon.SpendMoon,
        color = Color.Unspecified,
    ),
    SPEND_FIRE(
        textCode = IconCode.SPEND_FIRE,
        titleRes = R.string.design_system_icon_light,
        image = MagicIcon.Fire,
        color = Color.Unspecified,
    ),
    SPEND_AIR(
        textCode = IconCode.SPEND_AIR,
        titleRes = R.string.design_system_icon_light,
        image = MagicIcon.SpendAir,
        color = Color.Unspecified,
    ),
    SPEND_FROST(
        textCode = IconCode.SPEND_FROST,
        titleRes = R.string.design_system_icon_light,
        image = MagicIcon.SpendFrost,
        color = Color.Unspecified,
    ),
    SPEND_EARTH(
        textCode = IconCode.SPEND_EARTH,
        titleRes = R.string.design_system_icon_light,
        image = MagicIcon.Earth,
        color = Color.Unspecified,
    ),
    AREA_8(
        textCode = IconCode.AREA_8,
        titleRes = R.string.design_system_icon_area,
        image = AreaIcon.Area8,
        color = Color.Unspecified,
    ),
    AREA_9(
        textCode = IconCode.AREA_9,
        titleRes = R.string.design_system_icon_area,
        image = AreaIcon.Area9,
        color = Color.Unspecified,
    ),
    AREA_10(
        textCode = IconCode.AREA_10,
        titleRes = R.string.design_system_icon_area,
        image = AreaIcon.Area10,
        color = Color.Unspecified,
    ),
    AREA_11(
        textCode = IconCode.AREA_11,
        titleRes = R.string.design_system_icon_area,
        image = AreaIcon.Area11,
        color = Color.Unspecified,
    ),
    AREA_12(
        textCode = IconCode.AREA_12,
        titleRes = R.string.design_system_icon_area,
        image = AreaIcon.Area12,
        color = Color.Unspecified,
    ),
    LOOT(
        textCode = IconCode.LOOT,
        titleRes = R.string.design_system_icon_loot,
        image = ActionIcon.Loot,
        color = onSurfaceVariant,
    ),
    SPEND_ANY(
        textCode = IconCode.SPEND_ANY,
        titleRes = R.string.design_system_icon_light,
        image = MagicIcon.SpendAny,
        color = Color.Unspecified,
    ),
    AREA_13(
        textCode = IconCode.AREA_13,
        titleRes = R.string.design_system_icon_area,
        image = AreaIcon.Area13,
        color = Color.Unspecified,
    ),
    REGENERATE(
        textCode = IconCode.REGENERATE,
        titleRes = R.string.design_system_icon_regenerate,
        image = EffectIcon.Regenerate,
        color = Color(0xFFC23BC6),
    ),
    ;

    companion object {
        fun IconCode.toGameIcon(): GameIcon = entries.first { it.textCode == this }
    }
}
