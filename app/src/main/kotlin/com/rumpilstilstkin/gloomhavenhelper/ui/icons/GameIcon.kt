package com.rumpilstilstkin.gloomhavenhelper.ui.icons

import androidx.compose.ui.graphics.Color
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.IconCode
import java.util.EnumSet

enum class GameIcon(val textCode: IconCode, val title: String, val imageRes: Int, val color: Color) {
    SUN(textCode = IconCode.SUN, title = "Свет", imageRes = R.drawable.ic_magic_sun, color = Color(0xFFD2A21E)),
    MOON(textCode = IconCode.MOON, title = "Свет", imageRes = R.drawable.ic_magic_moon, color = Color(0xFF324C5C)),
    FIRE(textCode = IconCode.FIRE, title = "Свет", imageRes = R.drawable.ic_magic_fire, color = Color(0xFFBF3A0A)),
    AIR(textCode = IconCode.AIR, title = "Свет", imageRes = R.drawable.ic_magic_air, color = Color(0xFF2C568C)),
    FROST(textCode = IconCode.FROST, title = "Свет", imageRes = R.drawable.ic_magic_frost, color = Color(0xFF67BFDF)),
    EARTH(textCode = IconCode.EARTH, title = "Свет", imageRes = R.drawable.ic_magic_earth, color = Color(0xFF539155)),
    ARIA_0(textCode = IconCode.AREA_0, title = "Область:", imageRes = R.drawable.ic_aria_0, color = Color.White),
    ARIA_1(textCode = IconCode.AREA_1, title = "Область:", imageRes = R.drawable.ic_aria_1, color = Color.White),
    ARIA_2(textCode = IconCode.AREA_2, title = "Область:", imageRes = R.drawable.ic_aria_2, color = Color.White),
    ARIA_3(textCode = IconCode.AREA_3, title = "Область:", imageRes = R.drawable.ic_aria_3, color = Color.White),
    ARIA_4(textCode = IconCode.AREA_4, title = "Область:", imageRes = R.drawable.ic_aria_4, color = Color.White),
    ARIA_5(textCode = IconCode.AREA_5, title = "Область:", imageRes = R.drawable.ic_aria_5, color = Color.White),
    ARIA_6(textCode = IconCode.AREA_6, title = "Область:", imageRes = R.drawable.ic_aria_6, color = Color.White),
    PLUS_1(textCode = IconCode.PLUS1, title = "+1", imageRes = R.drawable.ic_plus_one, color = Color.White),
    PLUS_2(textCode = IconCode.PLUS2, title = "+2", imageRes = R.drawable.ic_plus_two, color = Color.White),
    PLUS_3(textCode = IconCode.PLUS3, title = "+3", imageRes = R.drawable.ic_plus_three, color = Color.White),
    PLUS_4(textCode = IconCode.PLUS4, title = "+4", imageRes = R.drawable.ic_plus_four, color = Color.White),
    PLUS_0(textCode = IconCode.ZERO, title = "+0", imageRes = R.drawable.ic_plus_zero, color = Color.White),
    MINUS_1(textCode = IconCode.MINUS1, title = "-1", imageRes = R.drawable.ic_minus_one, color = Color.White),
    MINUS_2(textCode = IconCode.MINUS2, title = "-2", imageRes = R.drawable.ic_minus_two, color = Color.White),
    NEXT(textCode = IconCode.NEXT, title = "Следующий", imageRes = R.drawable.ic_next, color = Color(0xFF407538)),
    PUSH(textCode = IconCode.PUSH, title = "Толкнуть", imageRes = R.drawable.ic_push, color = Color(0xFF585151)),
    PIERCE(textCode = IconCode.PIERCE, title = "Пробитие", imageRes = R.drawable.ic_pierce, color = Color(0xFFC29240)),
    STUN(textCode = IconCode.STUN, title = "Оглушение", imageRes = R.drawable.ic_stun, color = Color(0xFF2F4C81)),
    DISARM(textCode = IconCode.DISARM, title = "Разоружить", imageRes = R.drawable.ic_disarm, color = Color(0xFF527A72)),
    CONFUSE(textCode = IconCode.CONFUSE, title = "Смятение", imageRes = R.drawable.ic_confused, color = Color(0xFF70574A)),
    TARGET(textCode = IconCode.TARGET, title = "Цель", imageRes = R.drawable.ic_target, color = Color(0xFFA43737)),
    WOUND(textCode = IconCode.WOUND, title = "Рана", imageRes = R.drawable.ic_wound, color = Color(0xFFDC7331)),
    PULL(textCode = IconCode.PULL, title = "Притянуть", imageRes = R.drawable.ic_pull, color = Color(0xFF585151)),
    IMMOBILIZE(textCode = IconCode.PARALYZE, title = "Обездвижать", imageRes = R.drawable.ic_immobilize, color = Color(0xFF903E3E)),
    POISON(textCode = IconCode.POISON, title = "Отравление", imageRes = R.drawable.ic_poison, color = Color(0xFF6A7B57)),
    CURSE(textCode = IconCode.CURSE, title = "Проклятие", imageRes = R.drawable.ic_curse, color = Color(0xFF7D3194)),
    INVISIBILITY(textCode = IconCode.INVISIBILITY, title = "Невидимость", imageRes = R.drawable.ic_invisibility, color = Color(0xFF111113)),
    BLESS(textCode = IconCode.BLESS, title = "Благословение", imageRes = R.drawable.ic_bless, color = Color(0xFFC49D27)),
    STRENGTH(textCode = IconCode.STRENGTH, title = "Усиление", imageRes = R.drawable.ic_strength, color = Color(0xFF3B9FC6)),
    RETALIATE(textCode = IconCode.RETALIATE, title = "Ответный удар", imageRes = R.drawable.ic_retaliate, color = Color.White),
    RANGED_ATTACK(textCode = IconCode.RANGED_ATTACK, title = "Дальний удар", imageRes = R.drawable.ic_range, color = Color.White),
    MOVE(textCode = IconCode.MOVE, title = "Движение", imageRes = R.drawable.ic_move, color = Color.White),
    SHIELD(textCode = IconCode.SHIELD, title = "Защита", imageRes = R.drawable.ic_shield, color = Color.White),
    HEAL(textCode = IconCode.HEAL, title = "Лечение", imageRes = R.drawable.ic_heal, color = Color.White),
    ATTACK(textCode = IconCode.ATTACK, title = "Атака", imageRes = R.drawable.ic_attack, color = Color.White)

    ;

    companion object {
        fun IconCode.toGameIcon(): GameIcon = GameIcon.entries.first { it.textCode == this }
    }
}

object GameIconGroups {
    val magic = EnumSet.of(
        GameIcon.SUN,
        GameIcon.MOON,
        GameIcon.FIRE,
        GameIcon.FROST,
        GameIcon.AIR,
        GameIcon.EARTH,
    )

    val arias = EnumSet.of(
        GameIcon.ARIA_0,
        GameIcon.ARIA_1,
        GameIcon.ARIA_2,
        GameIcon.ARIA_3,
        GameIcon.ARIA_4,
        GameIcon.ARIA_5,
        GameIcon.ARIA_6,
    )

    val effects = EnumSet.of(
        GameIcon.STUN,
        GameIcon.DISARM,
        GameIcon.CONFUSE,
        GameIcon.WOUND,
        GameIcon.IMMOBILIZE,
        GameIcon.POISON,
        GameIcon.INVISIBILITY,
    )
}