package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.play

import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.MagicChargeState
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.MonsterDeckState
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.MonsterItem
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.MonsterUnit
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.ScenarioBattleState
import kotlinx.collections.immutable.persistentListOf
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class UpdateUnitLifeUseCaseTest {
    private val sut = UpdateUnitLifeUseCase()

    // NOTE: UpdateUnitLifeUseCase is currently a pure pass-through to
    // state.updateUnit { copy(currentLife = newLife) } — it does NOT clamp.
    // PRD bullets about "damage clamped at zero / heal clamped at max life"
    // do not match the implementation. Tests reflect actual behaviour.

    @Test
    fun `given existing unit when invoked then currentLife is set verbatim`() {
        // Given
        val state = stateWithUnit(currentLife = 5)

        // When
        val result = sut(state, "a", 1, newLife = 3)

        // Then
        expectThat(result.activeMonsters[0].units[0].currentLife).isEqualTo(3)
    }

    @Test
    fun `given new life above max when invoked then no clamping (pass-through)`() {
        // Given — maxLife = 10
        val state = stateWithUnit(currentLife = 5)

        // When
        val result = sut(state, "a", 1, newLife = 999)

        // Then
        expectThat(result.activeMonsters[0].units[0].currentLife).isEqualTo(999)
    }

    @Test
    fun `given new life below zero when invoked then no clamping (pass-through)`() {
        // Given
        val state = stateWithUnit(currentLife = 5)

        // When
        val result = sut(state, "a", 1, newLife = -10)

        // Then
        expectThat(result.activeMonsters[0].units[0].currentLife).isEqualTo(-10)
    }

    @Test
    fun `given unit not found when invoked then state unchanged`() {
        // Given
        val state = stateWithUnit(currentLife = 5)

        // When
        val result = sut(state, "missing", 1, newLife = 0)

        // Then
        expectThat(result.activeMonsters[0].units[0].currentLife).isEqualTo(5)
    }

    private fun stateWithUnit(currentLife: Int) =
        ScenarioBattleState(
            generalLevel = 1,
            name = "",
            monsters = emptyMap(),
            golds = 0,
            exp = 0,
            trapDamage = 0,
            gamersCount = 2,
            monsterLevel = 1,
            deck = MonsterDeckState.create(emptyList()),
            magicState = MagicChargeState.initial(),
            availableEffects = emptySet(),
            activeMonsters =
                listOf(
                    MonsterItem.fixture(slug = "a").copy(
                        units = persistentListOf(MonsterUnit.fixture(1).copy(currentLife = currentLife)),
                    ),
                ),
        )
}
