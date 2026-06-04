package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.play

import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.MagicChargeState
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.MonsterDeckState
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.MonsterItem
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.MonsterUnit
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.ScenarioBattleState
import kotlinx.collections.immutable.persistentListOf
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.containsExactly
import strikt.assertions.hasSize
import strikt.assertions.isEqualTo

class RemoveUnitUseCaseTest {
    private val sut = RemoveUnitUseCase()

    @Test
    fun `given unit present when invoked then it is removed leaving the rest`() {
        // Given
        val state =
            baseState().copy(
                activeMonsters =
                    listOf(
                        MonsterItem.fixture(slug = "a").copy(
                            units = persistentListOf(MonsterUnit.fixture(1), MonsterUnit.fixture(2)),
                        ),
                    ),
            )

        // When
        val result = sut(state, "a", 1)

        // Then
        expectThat(result.activeMonsters[0].units.map { it.number }).containsExactly(2)
    }

    @Test
    fun `given unit not present when invoked then monster units list is unchanged`() {
        // Given
        val state =
            baseState().copy(
                activeMonsters =
                    listOf(
                        MonsterItem.fixture(slug = "a").copy(
                            units = persistentListOf(MonsterUnit.fixture(1)),
                        ),
                    ),
            )

        // When
        val result = sut(state, "a", 999)

        // Then
        expectThat(result.activeMonsters[0].units).hasSize(1)
        expectThat(result.activeMonsters[0].units[0].number).isEqualTo(1)
    }

    @Test
    fun `given monster not present when invoked then no units are touched`() {
        // Given
        val state =
            baseState().copy(
                activeMonsters =
                    listOf(
                        MonsterItem.fixture(slug = "a").copy(
                            units = persistentListOf(MonsterUnit.fixture(1)),
                        ),
                    ),
            )

        // When
        val result = sut(state, "missing", 1)

        // Then
        expectThat(result.activeMonsters[0].units).hasSize(1)
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
