package com.rumpilstilstkin.gloommaster.domain.entity.scenario

import com.rumpilstilstkin.gloommaster.domain.entity.Magic
import com.rumpilstilstkin.gloommaster.domain.entity.ScenarioGameStateMagic

enum class ChargeLevel(
    val level: Int,
) {
    Zero(0),
    One(1),
    Two(2),
    ;

    fun down(): ChargeLevel =
        when (this) {
            Zero -> Zero
            One -> Zero
            Two -> One
        }

    fun toggle(): ChargeLevel =
        when (this) {
            Zero -> Two
            One -> Zero
            Two -> One
        }

    companion object {
        fun of(value: Int): ChargeLevel = entries.firstOrNull { it.level == value } ?: Zero
    }
}

@ConsistentCopyVisibility
data class MagicChargeState private constructor(
    val charges: Map<Magic, ChargeLevel> = Magic.entries.associateWith { ChargeLevel.Zero },
) {
    fun decreaseAll(): MagicChargeState {
        if (charges.values.all { it == ChargeLevel.Zero }) return this
        return copy(
            charges =
                charges.mapValues { (_, value) ->
                    value.down()
                },
        )
    }

    fun toggle(magic: Magic): MagicChargeState {
        val current = charges[magic] ?: return this
        return copy(charges = charges + (magic to current.toggle()))
    }

    fun toSaveState(): List<ScenarioGameStateMagic> =
        charges.map { (magic, value) ->
            ScenarioGameStateMagic(
                name = magic.name,
                value = value.level,
            )
        }

    companion object {
        fun restore(charges: Map<Magic, Int>): MagicChargeState {
            val withDefaults =
                Magic.entries.associateWith {
                    charges[it]?.let { value -> ChargeLevel.of(value) } ?: ChargeLevel.Zero
                }
            return MagicChargeState(charges = withDefaults)
        }

        fun initial(): MagicChargeState = MagicChargeState()
    }
}
