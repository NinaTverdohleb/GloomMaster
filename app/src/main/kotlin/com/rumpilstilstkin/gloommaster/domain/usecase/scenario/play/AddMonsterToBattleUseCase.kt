package com.rumpilstilstkin.gloommaster.domain.usecase.scenario.play

import com.rumpilstilstkin.gloommaster.domain.entity.monster.Monster
import com.rumpilstilstkin.gloommaster.domain.entity.scenario.CardPicker
import com.rumpilstilstkin.gloommaster.domain.entity.scenario.MonsterItem
import com.rumpilstilstkin.gloommaster.domain.entity.scenario.MonsterUnit
import com.rumpilstilstkin.gloommaster.domain.entity.scenario.ScenarioBattleState
import jakarta.inject.Inject

class AddMonsterToBattleUseCase @Inject constructor() {
    operator fun invoke(
        state: ScenarioBattleState,
        slugs: List<String>,
    ): ScenarioBattleState {
        if (slugs.isEmpty()) return state

        val freshMonsters =
            slugs
                .map { slug ->
                    val monster = state.monsters.getValue(slug)
                    val units =
                        if (monster.isBoss) {
                            mapOf(1 to createBossUnit(monster, state.gamersCount))
                        } else {
                            emptyMap()
                        }
                    MonsterItem(
                        slug = monster.slug,
                        isFly = monster.isFly,
                        name = monster.name,
                        currentCard = null,
                        isBoss = monster.isBoss,
                        units = units,
                        deck = monster.deckName,
                    )
                }.associateBy { it.slug }
        return if (state.round == 0) {
            state.copy(activeMonsters = state.activeMonsters + freshMonsters)
        } else {
            var newDeck = state.deck
            val withCards =
                freshMonsters.mapValues { (_, monster) ->
                    val draw = newDeck.drawCard(monster.deck, CardPicker.Random)
                    newDeck = draw.newState
                    monster.copy(currentCard = draw.card)
                }

            state.copy(
                activeMonsters = state.activeMonsters + withCards,
                deck = newDeck,
            )
        }
    }

    private fun createBossUnit(
        monster: Monster,
        gamersCount: Int,
    ): MonsterUnit {
        val maxLife = if (monster.lifeMultiple) monster.life * gamersCount else monster.life
        return MonsterUnit(
            number = 1,
            maxLife = maxLife,
            currentLife = maxLife,
            stats = monster.stats,
            isSpecial = false,
            level = monster.level,
            immunity = monster.immunity,
            lifeMultiple = monster.lifeMultiple,
        )
    }
}
