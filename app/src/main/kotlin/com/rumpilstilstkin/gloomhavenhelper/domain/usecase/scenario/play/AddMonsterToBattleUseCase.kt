package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.play

import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.Monster
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.CardPicker
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.MonsterItem
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.MonsterUnit
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.ScenarioBattleState
import jakarta.inject.Inject
import kotlinx.collections.immutable.toImmutableList

class AddMonsterToBattleUseCase @Inject constructor() {
    operator fun invoke(
        state: ScenarioBattleState,
        slugs: List<String>,
    ): ScenarioBattleState {
        if (slugs.isEmpty()) return state

        val freshMonsters =
            slugs.map { slug ->
                val monster = state.monsters.getValue(slug)
                val units =
                    if (monster.isBoss) {
                        listOf(createBossUnit(monster, state.gamersCount))
                    } else {
                        emptyList()
                    }
                MonsterItem(
                    slug = monster.slug,
                    isFly = monster.isFly,
                    name = monster.name,
                    currentCard = null,
                    isBoss = monster.isBoss,
                    units = units.toImmutableList(),
                    deck = monster.deckName,
                )
            }
        return if (state.round == 0) {
            state.copy(activeMonsters = state.activeMonsters + freshMonsters)
        } else {
            var newDeck = state.deck
            val withCards =
                freshMonsters.map { active ->
                    val monster = state.monsters.getValue(active.slug)
                    val draw = newDeck.drawCard(monster.deckName, CardPicker.Random)
                    newDeck = draw.newState
                    active.copy(currentCard = draw.card)
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
            stats = monster.stats.toImmutableList(),
            isSpecial = false,
            level = monster.level,
            immunity = monster.immunity.toImmutableList(),
            lifeMultiple = monster.lifeMultiple,
        )
    }
}
