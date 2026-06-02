package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.play

import com.rumpilstilstkin.gloomhavenhelper.data.MonsterRepository
import com.rumpilstilstkin.gloomhavenhelper.data.ScenarioGameStateRepository
import com.rumpilstilstkin.gloomhavenhelper.data.ScenarioRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.LevelInfo
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Magic
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.PackType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioGameStateMonsterUnit
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.TeamInfo
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.Monster
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterStatType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.MagicChargeState
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.MonsterDeckState
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.MonsterItem
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.MonsterUnit
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.ScenarioBattleState
import kotlinx.collections.immutable.toImmutableList
import javax.inject.Inject

class RestoreScenarioStateUseCase @Inject constructor(
    private val scenarioGameStateRepository: ScenarioGameStateRepository,
    private val monsterRepository: MonsterRepository,
    private val scenarioRepository: ScenarioRepository
) {
    suspend operator fun invoke(
        team: TeamInfo,
        levelInfo: LevelInfo?,
        locale: String,
    ): ScenarioBattleState {
        val state = scenarioGameStateRepository.get()
        return state?.let { gameState ->
            val scenarioName = gameState.scenarioNumber?.let {
                scenarioRepository.getScenario(gameState.scenarioNumber, locale).scenarioName
            } ?: ""
            val scenarioMonsters =
                monsterRepository.getMonstersBySlugs(gameState.monsterSlugs, team.level, locale)

            val monsterBySlug = scenarioMonsters.associateBy { it.slug }
            val cardByKey = scenarioMonsters
                .asSequence()
                .flatMap { it.cards.asSequence() }
                .associateBy { it.deckName to it.cardId }

            val availableCard = gameState.availableCards
                .mapNotNull { (deck, cardId) -> cardByKey[deck to cardId] }
            ScenarioBattleState(
                name = scenarioName,
                golds = levelInfo?.goldCount ?: 0,
                exp = levelInfo?.experience ?: 0,
                trapDamage = levelInfo?.trapDamage ?: 0,
                gamersCount = team.aliveCharacters.size,
                monsterLevel = levelInfo?.monsterLevel ?: 0,
                round = state.round,
                monsters = scenarioMonsters,
                deck = MonsterDeckState.create(availableCard),
                activeMonsters = state.activeMonsters.map { item ->
                    val monster = monsterBySlug.getValue(item.slug)
                    MonsterItem(
                        slug = item.slug,
                        name = monster.name,
                        currentCard = item.currentCard?.let { currentCard ->
                            cardByKey[currentCard.deck to currentCard.cardId]
                        },
                        isFly = monster.isFly,
                        isBoss = monster.isBoss,
                        units = item.units.map { stateUnit ->
                            stateUnit.createUnit(
                                locale = locale,
                                monster = monster,
                                lastLevel = state.level,
                                newLevel = team.level
                            )
                        }.toImmutableList(),
                        deck = monster.deckName
                    )
                },
                magicState = MagicChargeState.restore(state.magicCharges.associate {
                    Magic.valueOf(it.name) to it.value
                }
                ),
                scenarioNumber = state.scenarioNumber,
                availableEffects = team.packs.flatMapTo(mutableSetOf()) { pack ->
                    when (pack) {
                        PackType.MAIN -> MonsterStatType.Companion.mainEffectsPack
                        PackType.FORGOTTEN_CIRCLES -> MonsterStatType.Companion.fcEffectsPack
                    }
                }.toSet(),
                generalLevel = team.level
            )
        } ?: ScenarioBattleState(
            name = "",
            monsters = emptyList(),
            golds = levelInfo?.goldCount ?: 0,
            exp = levelInfo?.experience ?: 0,
            trapDamage = levelInfo?.trapDamage ?: 0,
            gamersCount = team.aliveCharacters.size,
            monsterLevel = levelInfo?.monsterLevel ?: 0,
            availableEffects = MonsterStatType.mainEffectsPack,
            deck = MonsterDeckState.create(emptyList()),
            generalLevel = team.level
        )
    }

    private suspend fun ScenarioGameStateMonsterUnit.createUnit(
        locale: String,
        monster: Monster,
        lastLevel: Int,
        newLevel: Int
    ): MonsterUnit {
        val unitMonster = if (lastLevel == level) (
                monster
                ) else {
            val levelC = maxOf((newLevel + (level - lastLevel)), 0)
            monsterRepository.getMonstersBySlugs(
                listOf(monster.slug),
                levelC,
                locale
            ).firstOrNull() ?: monster
        }

        return restore(unitMonster)
    }

    private fun ScenarioGameStateMonsterUnit.restore(
        monster: Monster,
    ): MonsterUnit {
        val maxMonsterLife = if (isElite) monster.eliteLife else monster.life
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
            immunity = monster
                .immunity
                .toImmutableList(),
            isNew = isNew
        )
    }
}