package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.play

import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.MagicChargeState
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.MonsterDeckState
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.MonsterItem
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.ScenarioBattleState
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.containsExactly
import strikt.assertions.doesNotContain
import strikt.assertions.hasSize

class RemoveMonsterUseCaseTest {
    private val sut = RemoveMonsterUseCase()

    @Test
    fun `given monster present when invoked then it is removed`() {
        // Given
        val state =
            baseState(
                activeMonsters = mapOf(
                    "a" to MonsterItem.fixture(slug = "a"),
                    "b" to MonsterItem.fixture(slug = "b")
                ),
            )

        // When
        val result = sut(state, "a")

        // Then
        expectThat(result.activeMonsters.keys).containsExactly("b")
        expectThat(result.activeMonsters.keys).doesNotContain("a")
    }

    @Test
    fun `given monster not present when invoked then state is unchanged`() {
        // Given
        val state =
            baseState(
                activeMonsters = mapOf(
                    "a" to MonsterItem.fixture(slug = "a"),
                ),
            )

        // When
        val result = sut(state, "missing")

        // Then
        expectThat(result.activeMonsters).hasSize(1)
        expectThat(result.activeMonsters.keys).containsExactly("a")
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
