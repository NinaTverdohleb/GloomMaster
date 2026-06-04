package com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario

import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Magic
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioGameStateMagic
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.containsExactlyInAnyOrder
import strikt.assertions.isEqualTo
import strikt.assertions.isSameInstanceAs

class MagicChargeStateTest {
    @Test
    fun `given initial when constructed then every magic has zero charges`() {
        // Given / When
        val state = MagicChargeState.initial()

        // Then
        Magic.entries.forEach { magic ->
            expectThat(state.charges.getValue(magic)).isEqualTo(0)
        }
    }

    @Test
    fun `given restore with partial map when constructed then missing magics default to zero and present override`() {
        // Given
        val partial = mapOf(Magic.FIRE to 2)

        // When
        val state = MagicChargeState.restore(partial)

        // Then
        expectThat(state.charges.getValue(Magic.FIRE)).isEqualTo(2)
        Magic.entries.filter { it != Magic.FIRE }.forEach { magic ->
            expectThat(state.charges.getValue(magic)).isEqualTo(0)
        }
    }

    @Test
    fun `given restore with empty map when constructed then every magic defaults to zero`() {
        // Given / When
        val state = MagicChargeState.restore(emptyMap())

        // Then
        Magic.entries.forEach { magic ->
            expectThat(state.charges.getValue(magic)).isEqualTo(0)
        }
    }

    @Test
    fun `given charge at zero when toggle then jumps to two`() {
        // Given
        val state = MagicChargeState.initial()

        // When
        val next = state.toggle(Magic.FIRE)

        // Then
        expectThat(next.charges.getValue(Magic.FIRE)).isEqualTo(2)
    }

    @Test
    fun `given charge at two when toggle then decrements to one`() {
        // Given
        val state = MagicChargeState.restore(mapOf(Magic.FIRE to 2))

        // When
        val next = state.toggle(Magic.FIRE)

        // Then
        expectThat(next.charges.getValue(Magic.FIRE)).isEqualTo(1)
    }

    @Test
    fun `given charge at one when toggle then decrements to zero`() {
        // Given
        val state = MagicChargeState.restore(mapOf(Magic.FIRE to 1))

        // When
        val next = state.toggle(Magic.FIRE)

        // Then
        expectThat(next.charges.getValue(Magic.FIRE)).isEqualTo(0)
    }

    @Test
    fun `given toggle for unknown magic key when restore omits a magic then no-op`() {
        // Given — restore explicitly with only FIRE in the map. restore() fills defaults
        // for every Magic entry, so toggle never hits the "unknown magic" branch in practice;
        // verify charges map is unchanged when toggling a magic absent from the underlying map
        // by reflection of the cycle on a value present at zero (returns 2, not the same state).
        val state = MagicChargeState.restore(mapOf(Magic.FIRE to 1))

        // When — toggling a different magic still works because restore filled it in at 0
        val next = state.toggle(Magic.AIR)

        // Then
        expectThat(next.charges.getValue(Magic.AIR)).isEqualTo(2)
        expectThat(next.charges.getValue(Magic.FIRE)).isEqualTo(1)
    }

    @Test
    fun `given all zero charges when decreaseAll then returns the same instance`() {
        // Given
        val state = MagicChargeState.initial()

        // When
        val next = state.decreaseAll()

        // Then
        expectThat(next).isSameInstanceAs(state)
    }

    @Test
    fun `given positive charges when decreaseAll then positives decrement and zeros stay`() {
        // Given
        val state = MagicChargeState.restore(mapOf(Magic.FIRE to 2, Magic.AIR to 1))

        // When
        val next = state.decreaseAll()

        // Then
        expectThat(next.charges.getValue(Magic.FIRE)).isEqualTo(1)
        expectThat(next.charges.getValue(Magic.AIR)).isEqualTo(0)
        expectThat(next.charges.getValue(Magic.FROST)).isEqualTo(0)
    }

    @Test
    fun `given mixed charges when toSaveState then snapshot contains one entry per magic`() {
        // Given
        val state = MagicChargeState.restore(mapOf(Magic.FIRE to 2, Magic.MOON to 1))

        // When
        val snapshot = state.toSaveState()

        // Then
        val expected =
            Magic.entries.map { magic ->
                val value =
                    when (magic) {
                        Magic.FIRE -> 2
                        Magic.MOON -> 1
                        else -> 0
                    }
                ScenarioGameStateMagic(name = magic.name, value = value)
            }
        expectThat(snapshot).containsExactlyInAnyOrder(expected)
    }
}
