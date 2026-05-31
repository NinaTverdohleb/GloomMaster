package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario

import com.rumpilstilstkin.gloomhavenhelper.data.MonsterRepository
import com.rumpilstilstkin.gloomhavenhelper.data.ScenarioGameStateRepository
import com.rumpilstilstkin.gloomhavenhelper.data.ScenarioRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.LevelInfo
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioBattleInfo
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.TeamInfo
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
    ): ScenarioBattleInfo {
        val state = scenarioGameStateRepository.get()
        return state?.let { gameState ->
            val scenarioName = gameState.scenarioNumber?.let {
                scenarioRepository.getScenario(gameState.scenarioNumber, locale).scenarioName
            } ?: ""
            val scenarioMonsters =
                monsterRepository.getMonstersBySlugs(gameState.monsterSlugs, team.level, locale)
            val allCards = scenarioMonsters.flatMap { it.cards }
            val avaliableCards = gameState.availableCards.mapNotNull { (deck, cardId) ->
                allCards.firstOrNull {
                    it.cardId == cardId && it.deckName == deck
                }
            }
            ScenarioBattleInfo(
                name = scenarioName,
                golds = levelInfo?.goldCount ?: 0,
                exp = levelInfo?.experience ?: 0,
                trapDamage = levelInfo?.trapDamage ?: 0,
                gamersCount = team.aliveCharacters.size,
                monsterLevel = levelInfo?.monsterLevel ?: 0,
                round = state.round,
                monsters = scenarioMonsters,
                availableCards = avaliableCards,
                activeMonsters = state.activeMonsters,
                magicCharges = state.magicCharges.associate {
                    it.name to it.value
                },
                scenarioNumber = state.scenarioNumber
            )
        } ?: ScenarioBattleInfo(
            name = "",
            monsters = emptyList(),
            golds = levelInfo?.goldCount ?: 0,
            exp = levelInfo?.experience ?: 0,
            trapDamage = levelInfo?.trapDamage ?: 0,
            gamersCount = team.aliveCharacters.size,
            monsterLevel = levelInfo?.monsterLevel ?: 0,
            scenarioNumber = null
        )
    }
}