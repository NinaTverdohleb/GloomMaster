package com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.builders

import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioGameStateMonsterItem
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.Monster
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterCard
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.MonsterItem
import kotlinx.collections.immutable.toImmutableList

class ActiveMonstersBuilder {
    private var scenarioMonsters: Map<String, Monster> = emptyMap()
    private var activeMonsters: List<ScenarioGameStateMonsterItem> = emptyList()
    private var cards: Map<Pair<String, Int>, MonsterCard> = emptyMap()
    private var levels: Pair<Int, Int> = 0 to 0

    fun scenarioMonsters(monsters: Map<String, Monster>) = apply { this.scenarioMonsters = monsters }

    fun activeMonsters(monsters: List<ScenarioGameStateMonsterItem>) = apply { this.activeMonsters = monsters }

    fun cards(cards: Map<Pair<String, Int>, MonsterCard>) = apply { this.cards = cards }

    fun levels(levels: Pair<Int, Int>) =
        apply { this.levels = levels }

    suspend fun build(
        gamersCount: Int,
        getMonster: suspend (level: Int, slug: String) -> Monster?,
    ): List<MonsterItem> =
        activeMonsters.map { item ->
            val monster = scenarioMonsters.getValue(item.slug)
            MonsterItem(
                slug = item.slug,
                name = monster.name,
                currentCard =
                    item.currentCard?.let { currentCard ->
                        cards[currentCard.deck to currentCard.cardId]
                    },
                isFly = monster.isFly,
                isBoss = monster.isBoss,
                units =
                    item.units
                        .map { stateUnit ->
                            MonsterUnitBuilder(stateUnit, monster)
                                .levels(levels)
                                .gamersCount(gamersCount)
                                .build(getMonster)
                        }.toImmutableList(),
                deck = monster.deckName,
            )
        }
}
