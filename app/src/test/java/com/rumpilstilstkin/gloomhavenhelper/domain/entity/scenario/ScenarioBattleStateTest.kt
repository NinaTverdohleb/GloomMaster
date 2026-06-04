package com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario

import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterStatType
import kotlinx.collections.immutable.persistentListOf
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.containsExactly
import strikt.assertions.isEqualTo

class ScenarioBattleStateTest {
    @Test
    fun `given matching slug and unit number when updateUnit then unit is transformed`() {
        // Given
        val state = state(
            activeMonsters = listOf(
                monsterItem("brute", units = persistentListOf(unit(number = 1, currentLife = 5))),
            ),
        )

        // When
        val updated = state.updateUnit(slug = "brute", number = 1) { copy(currentLife = 1) }

        // Then
        expectThat(updated.activeMonsters[0].units[0].currentLife).isEqualTo(1)
    }

    @Test
    fun `given missing slug when updateUnit then state is unchanged`() {
        // Given
        val state = state(
            activeMonsters = listOf(
                monsterItem("brute", units = persistentListOf(unit(number = 1, currentLife = 5))),
            ),
        )

        // When
        val updated = state.updateUnit(slug = "unknown", number = 1) { copy(currentLife = 1) }

        // Then
        expectThat(updated.activeMonsters[0].units[0].currentLife).isEqualTo(5)
        expectThat(updated.activeMonsters[0].slug).isEqualTo("brute")
    }

    @Test
    fun `given matching slug but missing unit number when updateUnit then state is unchanged`() {
        // Given
        val state = state(
            activeMonsters = listOf(
                monsterItem("brute", units = persistentListOf(unit(number = 1, currentLife = 5))),
            ),
        )

        // When
        val updated = state.updateUnit(slug = "brute", number = 999) { copy(currentLife = 1) }

        // Then
        expectThat(updated.activeMonsters[0].units).containsExactly(unit(number = 1, currentLife = 5))
    }

    @Test
    fun `given transform returns identical unit when updateUnit then unit is preserved`() {
        // Given
        val original = unit(number = 1, currentLife = 5)
        val state = state(
            activeMonsters = listOf(monsterItem("brute", units = persistentListOf(original))),
        )

        // When
        val updated = state.updateUnit(slug = "brute", number = 1) { this }

        // Then
        expectThat(updated.activeMonsters[0].units[0]).isEqualTo(original)
    }

    private fun state(
        activeMonsters: List<MonsterItem>,
    ) = ScenarioBattleState(
        generalLevel = 0,
        scenarioNumber = 1,
        name = "Scenario",
        monsters = emptyMap(),
        golds = 0,
        exp = 0,
        trapDamage = 0,
        gamersCount = 2,
        monsterLevel = 0,
        deck = MonsterDeckState.create(emptyList()),
        activeMonsters = activeMonsters,
        round = 0,
        magicState = MagicChargeState.initial(),
        availableEffects = emptySet(),
    )

    private fun monsterItem(
        slug: String,
        units: kotlinx.collections.immutable.ImmutableList<MonsterUnit>,
    ) = MonsterItem(
        slug = slug,
        name = slug,
        isFly = false,
        deck = "deck",
        currentCard = null,
        units = units,
        isBoss = false,
    )

    private fun unit(
        number: Int,
        currentLife: Int,
    ) = MonsterUnit(
        number = number,
        currentLife = currentLife,
        maxLife = 10,
        stats = persistentListOf(),
        isSpecial = false,
        effects = persistentListOf(),
        immunity = persistentListOf<MonsterStatType>(),
        level = 1,
        isNew = false,
        lifeMultiple = false
    )
}
