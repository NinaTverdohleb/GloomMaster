package com.rumpilstilstkin.gloommaster.domain.usecase.scenario.play

import com.rumpilstilstkin.gloommaster.domain.entity.monster.Monster
import com.rumpilstilstkin.gloommaster.domain.entity.scenario.MagicChargeState
import com.rumpilstilstkin.gloommaster.domain.entity.scenario.MonsterDeckState
import com.rumpilstilstkin.gloommaster.domain.entity.scenario.MonsterItem
import com.rumpilstilstkin.gloommaster.domain.entity.scenario.MonsterUnit
import com.rumpilstilstkin.gloommaster.domain.entity.scenario.ScenarioBattleState
import com.rumpilstilstkin.gloommaster.domain.usecase.scenario.play.AddMonsterUnitsUseCase
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.containsExactly
import strikt.assertions.hasSize
import strikt.assertions.isEqualTo
import strikt.assertions.isNotNull

class AddMonsterUnitsUseCaseTest {
    private val sut = AddMonsterUnitsUseCase()

    @Test
    fun `given monster with no units when invoked then numbers are added as new units`() {
        // Given
        val m = monster(slug = "a", life = 6)
        val state =
            baseState(monsters = mapOf("a" to m)).copy(
                activeMonsters = mapOf("a" to MonsterItem.fixture(slug = "a")),
            )

        // When
        val result = sut(state, slug = "a", numbers = listOf(1, 2), isElite = false)

        // Then
        val units = result.activeMonsters.getValue("a").units
        expectThat(units[1]).isNotNull()
        expectThat(units[2]).isNotNull()
        expectThat(units.getValue(1).currentLife).isEqualTo(6)
        expectThat(units.getValue(1).maxLife).isEqualTo(6)
        expectThat(units.getValue(1).isSpecial).isEqualTo(false)
    }

    @Test
    fun `given isElite true when invoked then eliteLife is used and isSpecial is true`() {
        // Given
        val m = monster(slug = "a", life = 6, eliteLife = 9)
        val state =
            baseState(monsters = mapOf("a" to m)).copy(
                activeMonsters = mapOf("a" to MonsterItem.fixture(slug = "a")),
            )

        // When
        val result = sut(state, slug = "a", numbers = listOf(1), isElite = true)

        // Then
        val units = result.activeMonsters.getValue("a").units
        expectThat(units.getValue(1).maxLife).isEqualTo(9)
        expectThat(units.getValue(1).isSpecial).isEqualTo(true)
    }

    @Test
    fun `given lifeMultiple monster when invoked then non-elite life is gamersCount times base`() {
        // Given
        val m = monster(slug = "a", life = 4, lifeMultiple = true)
        val state =
            baseState(monsters = mapOf("a" to m)).copy(
                gamersCount = 3,
                activeMonsters = mapOf("a" to MonsterItem.fixture(slug = "a")),
            )

        // When
        val result = sut(state, slug = "a", numbers = listOf(1), isElite = false)

        // Then
        expectThat(result.activeMonsters.getValue("a").units.getValue(1).maxLife).isEqualTo(12)
    }

    @Test
    fun `given monster with existing units when invoked then new units are added to existing`() {
        // Given
        val m = monster(slug = "a", life = 5)
        val state =
            baseState(monsters = mapOf("a" to m)).copy(
                activeMonsters =
                    mapOf(
                        "a" to
                            MonsterItem.fixture(slug = "a").copy(
                                units = mapOf(7 to MonsterUnit.fixture(number = 7)),
                            ),
                    ),
            )

        // When
        val result = sut(state, slug = "a", numbers = listOf(8), isElite = false)

        // Then
        val units = result.activeMonsters.getValue("a").units
        expectThat(units).hasSize(2)
        expectThat(units.keys).containsExactly(7, 8)
        expectThat(units.values.map { it.number }).containsExactly(7, 8)
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
        immunity = emptySet(),
        isFly = false,
        level = 1,
        lifeMultiple = lifeMultiple,
    )
}
