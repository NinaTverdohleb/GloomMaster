package com.rumpilstilstkin.gloommaster.domain.entity.scenario.builders

import com.rumpilstilstkin.gloommaster.domain.entity.ScenarioGameStateMonsterItem
import com.rumpilstilstkin.gloommaster.domain.entity.monster.Monster
import com.rumpilstilstkin.gloommaster.domain.entity.monster.MonsterCard
import com.rumpilstilstkin.gloommaster.domain.entity.monster.MonsterStatType
import com.rumpilstilstkin.gloommaster.domain.entity.scenario.MonsterItem

class ActiveMonstersBuilder {
    private var scenarioMonsters: Map<String, Monster> = emptyMap()
    private var activeMonsters: List<ScenarioGameStateMonsterItem> = emptyList()
    private var cards: Map<Pair<String, Int>, MonsterCard> = emptyMap()
    private var levels: Pair<Int, Int> = 0 to 0

    fun scenarioMonsters(monsters: Map<String, Monster>) = apply { this.scenarioMonsters = monsters }

    fun activeMonsters(monsters: List<ScenarioGameStateMonsterItem>) = apply { this.activeMonsters = monsters }

    fun cards(cards: Map<Pair<String, Int>, MonsterCard>) = apply { this.cards = cards }

    fun levels(levels: Pair<Int, Int>) = apply { this.levels = levels }

    suspend fun build(
        availableEffects: Set<MonsterStatType>,
        gamersCount: Int,
        getMonster: suspend (level: Int, slug: String) -> Monster?,
    ): Map<String, MonsterItem> =
        activeMonsters
            .map { item ->
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
                                    .build(availableEffects, getMonster)
                            }.associateBy { it.number },
                    deck = monster.deckName,
                )
            }.associateBy { it.slug }
}
