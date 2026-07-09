package com.rumpilstilstkin.gloommaster.domain.usecase.scenario.play

import com.rumpilstilstkin.gloommaster.domain.entity.Magic
import com.rumpilstilstkin.gloommaster.domain.entity.scenario.ChargeLevel
import com.rumpilstilstkin.gloommaster.domain.entity.scenario.MagicChargeState
import com.rumpilstilstkin.gloommaster.domain.entity.scenario.MonsterDeckState
import com.rumpilstilstkin.gloommaster.domain.entity.scenario.ScenarioBattleState
import com.rumpilstilstkin.gloommaster.domain.usecase.scenario.play.ToggleMagicChargeUseCase
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class ToggleMagicChargeUseCaseTest {
    private val sut = ToggleMagicChargeUseCase()

    @Test
    fun `given charge at zero when invoked then becomes 2`() {
        // Given
        val state = baseState()

        // When
        val result = sut(state, Magic.FIRE)

        // Then
        expectThat(result.magicState.charges[Magic.FIRE]).isEqualTo(ChargeLevel.Two)
    }

    @Test
    fun `given charge at 2 when invoked then becomes 1`() {
        // Given
        val state = baseState().let { it.copy(magicState = it.magicState.toggle(Magic.FIRE)) }

        // When
        val result = sut(state, Magic.FIRE)

        // Then
        expectThat(result.magicState.charges[Magic.FIRE]).isEqualTo(ChargeLevel.One)
    }

    @Test
    fun `given charge at 1 when invoked then becomes 0`() {
        // Given — toggle twice to get 1
        val state =
            baseState().let { s0 ->
                val s1 = s0.copy(magicState = s0.magicState.toggle(Magic.FIRE))
                s1.copy(magicState = s1.magicState.toggle(Magic.FIRE))
            }

        // When
        val result = sut(state, Magic.FIRE)

        // Then
        expectThat(result.magicState.charges[Magic.FIRE]).isEqualTo(ChargeLevel.Zero)
    }

    private fun baseState() =
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
        )
}
