package com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario

import com.rumpilstilstkin.gloomhavenhelper.domain.entity.AvailableCard
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterCard

@ConsistentCopyVisibility
data class MonsterDeckState private constructor(
    private val byDeck: Map<String, List<MonsterCard>>,
) {
    fun drawCard(
        deckName: String,
        picker: CardPicker = CardPicker.Random,
    ): DrawResult {
        val deckCards = byDeck[deckName] ?: return DrawResult(card = null, newState = this)
        if (deckCards.isEmpty()) return DrawResult(card = null, newState = this)

        val card = picker.pick(deckCards)
        val newDeck =
            if (card.needsShuffle) {
                this
            } else {
                val index = deckCards.indexOfFirst { it.cardId == card.cardId }
                if (index < 0) {
                    this
                } else {
                    MonsterDeckState(
                        byDeck + (
                            deckName to (
                                deckCards.subList(
                                    0,
                                    index,
                                ) + deckCards.subList(index + 1, deckCards.size)
                            )
                        ),
                    )
                }
            }
        return DrawResult(card = card, newState = newDeck)
    }

    fun toAvailableCards(): List<AvailableCard> = getRemainingCards().map { AvailableCard(deck = it.deckName, cardId = it.cardId) }

    fun getRemainingCards(): List<MonsterCard> = byDeck.values.flatten()

    fun cardsForDeck(deckName: String): List<MonsterCard> = byDeck[deckName].orEmpty()

    companion object {
        fun create(cards: List<MonsterCard>): MonsterDeckState = MonsterDeckState(byDeck = cards.groupBy { it.deckName })
    }
}

data class DrawResult(
    val card: MonsterCard?,
    val newState: MonsterDeckState,
)
