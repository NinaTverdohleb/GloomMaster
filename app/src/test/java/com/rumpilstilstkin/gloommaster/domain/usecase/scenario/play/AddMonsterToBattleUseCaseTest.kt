package com.rumpilstilstkin.gloommaster.domain.usecase.scenario.play

import com.rumpilstilstkin.gloommaster.domain.entity.monster.Monster
import com.rumpilstilstkin.gloommaster.domain.entity.monster.MonsterCard
import com.rumpilstilstkin.gloommaster.domain.entity.scenario.MagicChargeState
import com.rumpilstilstkin.gloommaster.domain.entity.scenario.MonsterDeckState
import com.rumpilstilstkin.gloommaster.domain.entity.scenario.ScenarioBattleState
import com.rumpilstilstkin.gloommaster.domain.usecase.scenario.play.AddMonsterToBattleUseCase
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.hasSize
import strikt.assertions.isEqualTo
import strikt.assertions.isNotNull
import strikt.assertions.isNull
import strikt.assertions.isSameInstanceAs

class AddMonsterToBattleUseCaseTest {
    private val sut = AddMonsterToBattleUseCase()

    @Test
    fun `given empty slug list when invoked then state unchanged`() {
        // Given
        val state = baseState(monsters = mapOf("a" to monster(slug = "a")))

        // When
        val result = sut(state, emptyList())

        // Then
        expectThat(result).isSameInstanceAs(state)
    }

    @Test
    fun `given non-boss slug and round 0 when invoked then added with no units and no card`() {
        // Given
        val state =
            baseState(monsters = mapOf("a" to monster(slug = "a", isBoss = false)))
                .copy(round = 0)

        // When
        val result = sut(state, listOf("a"))

        // Then
        expectThat(result.activeMonsters).hasSize(1)
        expectThat(result.activeMonsters["a"]?.slug).isEqualTo("a")
        expectThat(result.activeMonsters["a"]?.units?.size).isEqualTo(0)
        expectThat(result.activeMonsters["a"]?.currentCard).isNull()
    }

    @Test
    fun `given boss slug and round 0 when invoked then unit is created and life is set`() {
        // Given
        val boss = monster(slug = "boss", isBoss = true, life = 10, lifeMultiple = false)
        val state =
            baseState(monsters = mapOf("boss" to boss))
                .copy(round = 0, gamersCount = 2)

        // When
        val result = sut(state, listOf("boss"))

        // Then
        expectThat(result.activeMonsters["boss"]?.units?.size).isEqualTo(1)
        val unit = result.activeMonsters["boss"]?.units?.values?.first()
        expectThat(unit?.maxLife).isEqualTo(10)
        expectThat(unit?.currentLife).isEqualTo(10)
    }

    @Test
    fun `given boss with lifeMultiple and round 0 when invoked then life is multiplied by gamers`() {
        // Given
        val boss = monster(slug = "boss", isBoss = true, life = 10, lifeMultiple = true)
        val state =
            baseState(monsters = mapOf("boss" to boss))
                .copy(round = 0, gamersCount = 3)

        // When
        val result = sut(state, listOf("boss"))

        // Then
        expectThat(result.activeMonsters["boss"]?.units?.values?.first()?.maxLife).isEqualTo(30)
    }

    @Test
    fun `given round greater than 0 when invoked then a card is drawn for the added monster`() {
        // Given
        val card = MonsterCard(deckName = "deckA", cardId = 1, actions = emptyList(), initiative = 5)
        val deck = MonsterDeckState.create(listOf(card))
        val m = monster(slug = "a", deckName = "deckA")
        val state =
            baseState(monsters = mapOf("a" to m))
                .copy(round = 1, deck = deck)

        // When
        val result = sut(state, listOf("a"))

        // Then
        expectThat(result.activeMonsters).hasSize(1)
        expectThat(result.activeMonsters["a"]?.currentCard).isNotNull()
        expectThat(result.activeMonsters["a"]?.currentCard?.cardId).isEqualTo(1)
    }

    @Test
    fun `given multiple slugs when invoked then all are appended in order`() {
        // Given
        val state =
            baseState(
                monsters =
                    mapOf(
                        "a" to monster(slug = "a"),
                        "b" to monster(slug = "b"),
                    ),
            ).copy(round = 0)

        // When
        val result = sut(state, listOf("a", "b"))

        // Then
        expectThat(result.activeMonsters["a"]).isNotNull()
        expectThat(result.activeMonsters["b"]).isNotNull()
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
        isBoss: Boolean = false,
        life: Int = 5,
        lifeMultiple: Boolean = false,
        deckName: String = "boss",
    ) = Monster(
        slug = slug,
        name = slug,
        life = life,
        stats = emptyList(),
        eliteLife = life,
        eliteStats = emptyList(),
        deckName = deckName,
        cards = emptyList(),
        isBoss = isBoss,
        immunity = emptySet(),
        isFly = false,
        level = 1,
        lifeMultiple = lifeMultiple,
    )
}
