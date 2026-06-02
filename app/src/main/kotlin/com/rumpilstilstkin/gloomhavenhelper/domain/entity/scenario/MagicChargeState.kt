package com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario

import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Magic
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioGameStateMagic

@ConsistentCopyVisibility
data class MagicChargeState private constructor(
    val charges: Map<Magic, Int> = Magic.entries.associateWith { 0 },
) {
    fun decreaseAll(): MagicChargeState {
        if (charges.values.all { it == 0 }) return this
        return copy(charges = charges.mapValues { (_, value) -> if (value > 0) value - 1 else 0 })
    }

    fun toggle(magic: Magic): MagicChargeState {
        val current = charges[magic] ?: return this
        val next = if (current == 0) 2 else current - 1
        return copy(charges = charges + (magic to next))
    }

    fun toSaveState(): List<ScenarioGameStateMagic> =
        charges.map { (magic, value) -> ScenarioGameStateMagic(name = magic.name, value = value) }

    companion object {
        fun restore(charges: Map<Magic, Int>): MagicChargeState {
            val withDefaults = Magic.entries.associateWith { charges[it] ?: 0 } +
                    charges.filterKeys { key -> Magic.entries.none { it == key } }
            return MagicChargeState(charges = withDefaults)
        }

        fun initial(): MagicChargeState = MagicChargeState()
    }
}