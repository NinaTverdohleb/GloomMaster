package com.rumpilstilstkin.gloommaster.bd.filler.json.models

import com.rumpilstilstkin.gloommaster.bd.entity.MonsterAbilityCardBd
import com.rumpilstilstkin.gloommaster.bd.entity.MonsterAbilityCardTranslationBd
import com.rumpilstilstkin.gloommaster.domain.entity.monster.MonsterCardAction
import kotlinx.serialization.Serializable

@Serializable
data class DeckJson(
    val name: String,
    val cards: List<CardJson>,
) {
    fun toEntity(): List<MonsterAbilityCardBd> =
        cards.map {
            MonsterAbilityCardBd(
                deckName = name,
                needsShuffle = it.needsShuffle,
                initiative = it.initiative,
                cardId = it.id,
            )
        }
}

@Serializable
data class CardJson(
    val needsShuffle: Boolean,
    val initiative: Int,
    val id: Int,
)

@Serializable
data class DeckTranslationJson(
    val deck: String,
    val cards: List<TranslationCard>,
) {
    fun toEntity(locale: String): List<MonsterAbilityCardTranslationBd> =
        cards.map {
            MonsterAbilityCardTranslationBd(
                deckName = deck,
                locale = locale,
                cardId = it.id,
                actions = it.actions,
            )
        }
}

@Serializable
data class TranslationCard(
    val id: Int,
    val actions: List<MonsterCardAction>,
)
