package com.rumpilstilstkin.gloomhavenhelper.ui.icons

import androidx.compose.ui.graphics.Color
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.IconCode
import java.util.EnumSet

enum class GameIcons(val textCode: IconCode, val title: String, val imageRes: Int, val color: Color) {
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
    FLY(textCode = IconCode.FLY, title = "Полет", imageRes = R.drawable.ic_fly, color = Color.White),
    ;

    companion object {
        fun IconCode.toGameIcon(): GameIcons = GameIcons.entries.first { it.textCode == this }
    }
}

object GameIconGroups {
    val magic = EnumSet.of(
        GameIcons.SUN,
        GameIcons.MOON,
        GameIcons.FIRE,
        GameIcons.FROST,
        GameIcons.AIR,
        GameIcons.EARTH,
    )

    val arias = EnumSet.of(
        GameIcons.ARIA_0,
        GameIcons.ARIA_1,
        GameIcons.ARIA_2,
        GameIcons.ARIA_3,
        GameIcons.ARIA_4,
        GameIcons.ARIA_5,
        GameIcons.ARIA_6,
    )
}