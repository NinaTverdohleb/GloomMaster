package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.play

import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.Monster
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

// NOTE: AddMonsterUnitsUseCase does NOT check for unit-number conflicts with
// existing units (or duplicates within `numbers`). PRD bullet about
// "rejecting duplicate unit numbers" does not match implementation —
// tests reflect actual behaviour (units are appended verbatim).
class AddMonsterUnitsUseCaseTest {
    private val sut = AddMonsterUnitsUseCase()

    @Test
    fun `given monster with no units when invoked then numbers are appended as new units`() {
        // Given
        val m = monster(slug = "a", life = 6)
        val state =
            baseState(monsters = mapOf("a" to m)).copy(
                activeMonsters =
                    listOf(
                        MonsterItem.fixture(slug = "a").copy(units = persistentListOf()),
                    ),
            )

        // When
        val result = sut(state, slug = "a", numbers = listOf(1, 2), isElite = false)

        // Then
        expectThat(result.activeMonsters[0].units.map { it.number }).containsExactly(1, 2)
        expectThat(result.activeMonsters[0].units[0].currentLife).isEqualTo(6)
        expectThat(result.activeMonsters[0].units[0].maxLife).isEqualTo(6)
        expectThat(result.activeMonsters[0].units[0].isSpecial).isEqualTo(false)
    }

    @Test
    fun `given isElite true when invoked then eliteLife is used and isSpecial is true`() {
        // Given
        val m = monster(slug = "a", life = 6, eliteLife = 9)
        val state =
            baseState(monsters = mapOf("a" to m)).copy(
                activeMonsters =
                    listOf(MonsterItem.fixture(slug = "a").copy(units = persistentListOf())),
            )

        // When
        val result = sut(state, slug = "a", numbers = listOf(1), isElite = true)

        // Then
        expectThat(result.activeMonsters[0].units[0].maxLife).isEqualTo(9)
        expectThat(result.activeMonsters[0].units[0].isSpecial).isEqualTo(true)
    }

    @Test
    fun `given lifeMultiple monster when invoked then non-elite life is gamersCount times base`() {
        // Given
        val m = monster(slug = "a", life = 4, lifeMultiple = true)
        val state =
            baseState(monsters = mapOf("a" to m)).copy(
                gamersCount = 3,
                activeMonsters =
                    listOf(MonsterItem.fixture(slug = "a").copy(units = persistentListOf())),
            )

        // When
        val result = sut(state, slug = "a", numbers = listOf(1), isElite = false)

        // Then
        expectThat(result.activeMonsters[0].units[0].maxLife).isEqualTo(12)
    }

    @Test
    fun `given monster with existing units when invoked then new units are appended after`() {
        // Given
        val m = monster(slug = "a", life = 5)
        val state =
            baseState(monsters = mapOf("a" to m)).copy(
                activeMonsters =
                    listOf(
                        MonsterItem.fixture(slug = "a").copy(
                            units = persistentListOf(MonsterUnit.fixture(7)),
                        ),
                    ),
            )

        // When
        val result = sut(state, slug = "a", numbers = listOf(8), isElite = false)

        // Then
        expectThat(result.activeMonsters[0].units).hasSize(2)
        expectThat(result.activeMonsters[0].units.map { it.number }).containsExactly(7, 8)
    }

    private fun baseState(monsters: Map<String, Monster> = emptyMap()) =
        ScenarioBattleState(
            generalLevel = 1,
            name = "",
            monsters = monsters,
            golds = 0,
            exp = 0,
            trapDamage = 0,
            gamersCount = 2,
            monsterLevel = 1,
            deck = MonsterDeckState.create(emptyList()),
            magicState = MagicChargeState.initial(),
            availableEffects = emptySet(),
        )

    private fun monster(
        slug: String,
        life: Int = 5,
        eliteLife: Int = life + 2,
        lifeMultiple: Boolean = false,
    ) = Monster(
        slug = slug,
        name = slug,
        life = life,
        stats = emptyList(),
        eliteLife = eliteLife,
        eliteStats = emptyList(),
        deckName = "boss",
        cards = emptyList(),
        isBoss = false,
        immunity = emptyList(),
        isFly = false,
        level = 1,
        lifeMultiple = lifeMultiple,
    )
}
