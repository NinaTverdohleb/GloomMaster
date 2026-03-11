package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.state

import androidx.compose.ui.graphics.Color
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.ui.icons.GameIcon
import kotlinx.serialization.Serializable

enum class Magic(val icon: GameIcon) {
    FIRE(GameIcon.FIRE),
    FROST(GameIcon.FROST),
    AIR(GameIcon.AIR),
    EARTH(GameIcon.EARTH),
    SUN(GameIcon.SUN),
    MOON(GameIcon.MOON);

    companion object {
        val ALL: List<Magic> = entries
    }
}

@Serializable
data class MagicValue(
    val value: Int = 0
) {
    fun decrease(): MagicValue =
        copy(value = if (value > 0) value - 1 else 0)

    fun toggle(): MagicValue =
        copy(value = if (value == 0) 2 else value - 1)

    fun getChargeImage(): Int? = when (value) {
        0 -> null
        1 -> R.drawable.ic_magic_half
        else -> R.drawable.ic_magic_full
    }

    fun color(magic: Magic): Color = when (value) {
        0 -> magic.icon.color.copy(alpha = 0.2f)
        1 -> magic.icon.color.copy(alpha = 0.5f)
        else -> magic.icon.color
    }
}

@Serializable
data class MagicState(
    private val charges: Map<Magic, MagicValue> = Magic.ALL.associateWith { MagicValue() }
) {
    fun toggle(magic: Magic): MagicState {
        val currentValue = charges[magic] ?: return this
        return copy(charges = charges + (magic to currentValue.toggle()))
    }

    fun decreaseAll(): MagicState =
        copy(charges = charges.mapValues { (_, value) -> value.decrease() })

    fun toMap(): Map<Magic, MagicValue> = charges

    companion object {
        fun initial(): MagicState = MagicState()

        fun restore(magicCharges: Map<String, Int>): MagicState =
            MagicState(
                charges = Magic.ALL.associateWith { magic ->
                    MagicValue(magicCharges[magic.name] ?: 0)
                }
            )
    }
}
