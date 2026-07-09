package com.rumpilstilstkin.gloommaster.domain.usecase.scenario.play

import com.rumpilstilstkin.gloommaster.domain.entity.scenario.MagicChargeState
import com.rumpilstilstkin.gloommaster.domain.entity.scenario.MonsterDeckState
import com.rumpilstilstkin.gloommaster.domain.entity.scenario.MonsterItem
import com.rumpilstilstkin.gloommaster.domain.entity.scenario.MonsterUnit
import com.rumpilstilstkin.gloommaster.domain.entity.scenario.ScenarioBattleState
import com.rumpilstilstkin.gloommaster.domain.usecase.scenario.play.UpdateUnitLifeUseCase
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNull

class UpdateUnitLifeUseCaseTest {
    private val sut = UpdateUnitLifeUseCase()
    private val monsterSlug = "oozy"
    private val unitNumber = 1

    @Test
    fun `given existing unit when invoked then currentLife is set verbatim`() {
        // Given
        val state = stateWithUnit(maxLife = 5)

        // When
        val result = sut(state, monsterSlug, unitNumber, newLife = 3)

        // Then
        expectThat(result.activeMonsters[monsterSlug]?.units[unitNumber]?.currentLife).isEqualTo(3)
    }

    @Test
    fun `given new life above max when invoked then no clamping`() {
        // Given — maxLife = 10
        val state = stateWithUnit(maxLife = 10)

        // When
        val result = sut(state, monsterSlug, unitNumber, newLife = 999)

        // Then
        expectThat(result.activeMonsters[monsterSlug]?.units[unitNumber]?.currentLife).isEqualTo(10)
    }

    @Test
    fun `given new life below zero when invoked then no clamping (pass-through)`() {
        // Given
        val state = stateWithUnit(maxLife = 5)

        // When
        val result = sut(state, monsterSlug, unitNumber, newLife = -10)

        // Then
        expectThat(result.activeMonsters[monsterSlug]?.units[unitNumber]?.currentLife).isEqualTo(0)
    }

    @Test
    fun `given unit not found when invoked then state unchanged`() {
        // Given
        val state = stateWithUnit(maxLife = 5)

        // When
        val result = sut(state, "missing", unitNumber, newLife = 0)

        // Then
        expectThat(result.activeMonsters["missing"]?.units[unitNumber]?.currentLife).isNull()
    }

    private fun stateWithUnit(maxLife: Int) =
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
                mapOf(
                    monsterSlug to
                            MonsterItem.fixture(slug = monsterSlug).copy(
                                units = mapOf(
                                    unitNumber to MonsterUnit.fixture(
                                        number = unitNumber,
                                        maxLife = maxLife
                                    )
                                ),
                            ),
                ),
        )
}
