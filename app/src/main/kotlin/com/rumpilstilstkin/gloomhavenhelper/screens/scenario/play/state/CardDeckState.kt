package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.state

import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterCard
import kotlinx.serialization.Serializable

@Serializable
data class CardDeckState(
    private val cards: List<MonsterCard>
) {
    fun drawCard(deckName: String): DrawResult {
        val deckCards = cards.filter { it.deckName == deckName }
        if (deckCards.isEmpty()) return DrawResult(card = null, newState = this)

        val card = deckCards.random()
        val newCards = if (card.needsShuffle) {
            cards
        } else {
            cards.filter { it.cardId != card.cardId }
        }
        return DrawResult(card = card, newState = copy(cards = newCards))
    }

    fun getCards(): List<MonsterCard> = cards

    companion object {
        fun create(cards: List<MonsterCard>): CardDeckState = CardDeckState(cards)
    }
}

data class DrawResult(
    val card: MonsterCard?,
    val newState: CardDeckState
)
