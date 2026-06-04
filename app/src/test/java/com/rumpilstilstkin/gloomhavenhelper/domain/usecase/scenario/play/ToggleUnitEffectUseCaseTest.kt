package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.play

import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterStatType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.MagicChargeState
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.MonsterDeckState
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.MonsterItem
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.MonsterUnit
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.ScenarioBattleState
import kotlinx.collections.immutable.persistentListOf
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.contains
import strikt.assertions.doesNotContain
import strikt.assertions.isEmpty

class ToggleUnitEffectUseCaseTest {
    private val sut = ToggleUnitEffectUseCase()

    @Test
    fun `given effect not present when invoked then it is added`() {
        // Given
        val state = stateWithUnit(effects = persistentListOf())

        // When
        val result = sut(state, "a", 1, MonsterStatType.POISON)

        // Then
        expectThat(result.activeMonsters[0].units[0].effects).contains(MonsterStatType.POISON)
    }

    @Test
    fun `given effect already present when invoked then it is removed`() {
        // Given
        val state = stateWithUnit(effects = persistentListOf(MonsterStatType.POISON))

        // When
        val result = sut(state, "a", 1, MonsterStatType.POISON)

        // Then
        expectThat(result.activeMonsters[0].units[0].effects).doesNotContain(MonsterStatType.POISON)
    }

    @Test
    fun `given unit not found when invoked then state unchanged`() {
        // Given
        val state = stateWithUnit(effects = persistentListOf())

        // When
        val result = sut(state, "missing", 1, MonsterStatType.POISON)

        // Then
        expectThat(result.activeMonsters[0].units[0].effects).isEmpty()
    }

    private fun stateWithUnit(effects: kotlinx.collections.immutable.ImmutableList<MonsterStatType>) =
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
                        units = persistentListOf(MonsterUnit.fixture(1).copy(effects = effects)),
                    ),
                ),
        )
}
