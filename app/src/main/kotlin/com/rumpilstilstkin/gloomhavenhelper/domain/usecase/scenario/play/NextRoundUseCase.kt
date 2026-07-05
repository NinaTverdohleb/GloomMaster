package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.play

import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterCard
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.MonsterItem
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.MonsterUnit
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.ScenarioBattleState
import jakarta.inject.Inject

class NextRoundUseCase @Inject constructor() {
    operator fun invoke(state: ScenarioBattleState): ScenarioBattleState {
        var newDeck = state.deck
        val newActive =
            state.activeMonsters.mapValues { (_, active) ->
                val monster = state.monsters.getValue(active.slug)
                val draw = newDeck.drawCard(monster.deckName)
                newDeck = draw.newState
                active.withCardAndClearedIsNew(draw.card)
            }
        return state.copy(
            round = state.round + 1,
            activeMonsters = newActive,
            deck = newDeck,
            magicState = state.magicState.decreaseAll(),
        )
    }
}

private fun MonsterItem.withCardAndClearedIsNew(card: MonsterCard?): MonsterItem {
    val newUnits = units.withClearedIsNew()
    if (newUnits === units && card == currentCard) return this
    return copy(currentCard = card, units = newUnits)
}

private fun Map<Int, MonsterUnit>.withClearedIsNew(): Map<Int, MonsterUnit> = mapValues { (_, unit) -> unit.copy(isNew = false) }
