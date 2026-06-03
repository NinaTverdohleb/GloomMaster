package com.rumpilstilstkin.gloomhavenhelper.data.mappers

import com.rumpilstilstkin.gloomhavenhelper.bd.entity.MonsterAbilityCardBd
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterCardAction
import kotlinx.collections.immutable.ImmutableList
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.contains
import strikt.assertions.hasSize
import strikt.assertions.isEmpty
import strikt.assertions.isEqualTo
import strikt.assertions.isTrue

class MonstersMapperTest {
    @Test
    fun `given MonsterAbilityCardBd with two actions when toDomain then fields copy and actions become ImmutableList`() {
        // Given
        val bd = abilityCardFixture(
            deckName = "guard",
            cardId = 17,
            initiative = 21,
            needsShuffle = true,
        )
        val actions = listOf(
            MonsterCardAction(text = "Move 2"),
            MonsterCardAction(text = "Attack +1"),
        )

        // When
        val card = bd.toDomain(actions)

        // Then
        expectThat(card.cardId).isEqualTo(17)
        expectThat(card.deckName).isEqualTo("guard")
        expectThat(card.initiative).isEqualTo(21)
        expectThat(card.needsShuffle).isEqualTo(true)
        expectThat(card.actions).hasSize(2)
        expectThat(card.actions).contains(actions[0], actions[1])
        expectThat(card.actions is ImmutableList<*>).isTrue()
    }

    @Test
    fun `given MonsterAbilityCardBd with empty actions when toDomain then actions is an empty ImmutableList`() {
        // Given
        val bd = abilityCardFixture()

        // When
        val card = bd.toDomain(actions = emptyList())

        // Then
        expectThat(card.actions).isEmpty()
        expectThat(card.actions is ImmutableList<*>).isTrue()
    }

    companion object {
        fun abilityCardFixture(
            deckName: String = "deck",
            cardId: Int = 1,
            initiative: Int = 10,
            needsShuffle: Boolean = false,
        ): MonsterAbilityCardBd = MonsterAbilityCardBd(
            deckName = deckName,
            cardId = cardId,
            initiative = initiative,
            needsShuffle = needsShuffle,
        )
    }
}
