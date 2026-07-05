package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.play

import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.MagicChargeState
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.MonsterDeckState
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.MonsterItem
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.MonsterUnit
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.ScenarioBattleState
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNotNull
import strikt.assertions.isNull

class RemoveUnitUseCaseTest {
    private val sut = RemoveUnitUseCase()
    private val monsterSlug = "oozy"

    @Test
    fun `given unit present when invoked then it is removed leaving the rest`() {
        // Given
        val state =
            baseState(
                activeMonsters =
                    mapOf(
                        monsterSlug to
                                MonsterItem.fixture(slug = monsterSlug).copy(
                                    units = mapOf(
                                        1 to MonsterUnit.fixture(1),
                                        2 to MonsterUnit.fixture(2)
                                    ),
                                ),
                    ),
            )

        // When
        val result = sut(state, monsterSlug, 1)

        // Then
        expectThat(result.activeMonsters[monsterSlug]?.units[1]).isNull()
    }

    @Test
    fun `given unit not present when invoked then monster units list is unchanged`() {
        // Given
        val state =
            baseState(
                activeMonsters =
                    mapOf(
                        monsterSlug to
                                MonsterItem.fixture(slug = monsterSlug).copy(
                                    units = mapOf(
                                        1 to MonsterUnit.fixture(1),
                                        2 to MonsterUnit.fixture(2)
                                    ),
                                ),
                    ),
            )

        // When
        val result = sut(state, monsterSlug, 999)

        // Then
        expectThat(result.activeMonsters[monsterSlug]?.units[2]).isNotNull()
        expectThat(result.activeMonsters[monsterSlug]?.units[2]).isNotNull()
        expectThat(result.activeMonsters[monsterSlug]?.units?.count()).isEqualTo(2)
    }

    @Test
    fun `given monster not present when invoked then no units are touched`() {
        // Given
        val state =
            baseState(
                activeMonsters =
                    mapOf(
                        monsterSlug to
                                MonsterItem.fixture(slug = monsterSlug).copy(
                                    units = mapOf(
                                        1 to MonsterUnit.fixture(1)
                                    ),
                                ),
                    ),
            )

        // When
        val result = sut(state, "missing", 1)

        // Then
        expectThat(result.activeMonsters.count()).isEqualTo(1)
        expectThat(result.activeMonsters[monsterSlug]?.units?.count()).isEqualTo(1)
    }

    private fun baseState(
        activeMonsters: Map<String, MonsterItem>
    ) =
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
            activeMonsters = activeMonsters
        )
}
