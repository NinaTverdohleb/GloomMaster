package com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.builders

import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioGameStateMonsterItem
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioGameStateMonsterUnit
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.Monster
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterCard
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.MonsterItem
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.MonsterUnit
import kotlinx.collections.immutable.toImmutableList

class ActiveMonstersBuilder {
    private var scenarioMonsters: Map<String, Monster> = emptyMap()
    private var activeMonsters: List<ScenarioGameStateMonsterItem> = emptyList()
    private var cards: Map<Pair<String, Int>, MonsterCard> = emptyMap()
    private var getMonster: suspend (level: Int, slug: String) -> Monster? = { _, _ -> null }
    private var lastLevel: Int = 0
    private var newLevel: Int = 0

    fun scenarioMonsters(monsters: Map<String, Monster>) =
        apply { this.scenarioMonsters = monsters }

    fun activeMonsters(monsters: List<ScenarioGameStateMonsterItem>) =
        apply { this.activeMonsters = monsters }

    fun cards(cards: Map<Pair<String, Int>, MonsterCard>) =
        apply { this.cards = cards }

    fun levels(levels: Pair<Int, Int>) =
        apply {
            this.lastLevel = levels.first
            this.newLevel = levels.second
        }

    fun additionalMonster(getMonster: suspend (level: Int, slug: String) -> Monster?) {
        this.getMonster = getMonster
    }

    suspend fun build(
        gamersCount: Int,
    ): List<MonsterItem> {
        return activeMonsters.map { item ->
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
                            stateUnit.createUnit(
                                monster = monster,
                                lastLevel = lastLevel,
                                newLevel = newLevel,
                                gamersCount = gamersCount,
                                getMonster = getMonster
                            )
                        }.toImmutableList(),
                deck = monster.deckName,
            )
        }
    }

    private suspend inline fun ScenarioGameStateMonsterUnit.createUnit(
        monster: Monster,
        lastLevel: Int,
        newLevel: Int,
        gamersCount: Int,
        getMonster: suspend (level: Int, slug: String) -> Monster?,
    ): MonsterUnit {
        val unitMonster =
            if (lastLevel == level) {
                monster
            } else {
                val levelC = maxOf((newLevel + (level - lastLevel)), 0)
                getMonster(levelC, monster.slug) ?: monster
            }

        return restore(unitMonster, gamersCount)
    }

    private fun ScenarioGameStateMonsterUnit.restore(
        monster: Monster,
        gamersCount: Int
    ): MonsterUnit {
        val maxMonsterLife = if (isElite) {
            monster.eliteLife
        } else if (monster.lifeMultiple) {
            monster.life.times(gamersCount)
        } else {
            monster.life
        }
        val newCurrentLife = maxMonsterLife - maxOf(maxLife - currentLife, 0)
        val stats = if (isElite) monster.eliteStats else monster.stats
        return MonsterUnit(
            number = number,
            maxLife = maxMonsterLife,
            currentLife = newCurrentLife,
            stats = stats.toImmutableList(),
            isSpecial = isElite,
            level = monster.level,
            effects = effects.toImmutableList(),
            immunity =
                monster
                    .immunity
                    .toImmutableList(),
            isNew = isNew,
        )
    }
}