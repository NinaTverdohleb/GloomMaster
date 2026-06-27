package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.play

import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Magic
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.Monster
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterCard
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.ChargeLevel
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.MagicChargeState
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.MonsterDeckState
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.MonsterItem
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.MonsterUnit
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.ScenarioBattleState
import kotlinx.collections.immutable.persistentListOf
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNotNull

class NextRoundUseCaseTest {
    private val sut = NextRoundUseCase()

    @Test
    fun `given state at round 0 when invoked then round increments to 1`() {
        // Given
        val state = baseState().copy(round = 0)

        // When
        val result = sut(state)

        // Then
        expectThat(result.round).isEqualTo(1)
    }

    @Test
    fun `given state at round 5 when invoked then round increments to 6`() {
        // Given
        val state = baseState().copy(round = 5)

        // When
        val result = sut(state)

        // Then
        expectThat(result.round).isEqualTo(6)
    }

    @Test
    fun `given active monsters when invoked then each receives a drawn card`() {
        // Given
        val card = MonsterCard(deckName = "deckA", cardId = 1, actions = emptyList(), initiative = 5)
        val deck = MonsterDeckState.create(listOf(card))
        val state =
            baseState(monsters = mapOf("a" to monster("a", deckName = "deckA"))).copy(
                deck = deck,
                activeMonsters = listOf(MonsterItem.fixture(slug = "a").copy(deck = "deckA")),
            )

        // When
        val result = sut(state)

        // Then
        expectThat(result.activeMonsters[0].currentCard).isNotNull()
        expectThat(result.activeMonsters[0].currentCard!!.cardId).isEqualTo(1)
    }

    @Test
    fun `given active monsters with isNew units when invoked then isNew is cleared`() {
        // Given
        val card = MonsterCard(deckName = "deckA", cardId = 1, actions = emptyList(), initiative = 5)
        val deck = MonsterDeckState.create(listOf(card))
        val state =
            baseState(monsters = mapOf("a" to monster("a", deckName = "deckA"))).copy(
                deck = deck,
                activeMonsters =
                    listOf(
                        MonsterItem.fixture(slug = "a").copy(
                            deck = "deckA",
                            units = persistentListOf(MonsterUnit.fixture(1).copy(isNew = true)),
                        ),
                    ),
            )

        // When
        val result = sut(state)

        // Then
        expectThat(result.activeMonsters[0].units[0].isNew).isEqualTo(false)
    }

    @Test
    fun `given magic charges when invoked then magic state is decreased`() {
        // Given
        val card = MonsterCard(deckName = "deckA", cardId = 1, actions = emptyList(), initiative = 5)
        val deck = MonsterDeckState.create(listOf(card))
        val state =
            baseState(monsters = mapOf("a" to monster("a", deckName = "deckA")))
                .copy(
                    deck = deck,
                    activeMonsters = listOf(MonsterItem.fixture(slug = "a").copy(deck = "deckA")),
                    magicState = MagicChargeState.initial().toggle(Magic.FIRE),
                )

        // When
        val result = sut(state)

        // Then
        expectThat(result.magicState.charges[Magic.FIRE]).isEqualTo(ChargeLevel.One)
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
        deckName: String = "boss",
    ) = Monster(
        slug = slug,
        name = slug,
        life = 5,
        stats = emptyList(),
        eliteLife = 7,
        eliteStats = emptyList(),
        deckName = deckName,
        cards = emptyList(),
        isBoss = false,
        immunity = emptySet(),
        isFly = false,
        level = 1,
        lifeMultiple = false,
    )
}
