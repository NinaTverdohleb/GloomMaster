package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team

import com.rumpilstilstkin.gloomhavenhelper.data.CharacterClassRepository
import com.rumpilstilstkin.gloomhavenhelper.data.ScenarioRepository
import com.rumpilstilstkin.gloomhavenhelper.data.TeamRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterClassType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.TeamInfoForSave
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.goods.AddGoodsToTeamUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.goods.GetGoodsForLevelUseCase
import javax.inject.Inject

class SaveTeamUseCase @Inject constructor(
    private val teamRepository: TeamRepository,
    private val scenarioRepository: ScenarioRepository,
    private val characterClassRepository: CharacterClassRepository,
    private val addGoodsToTeamUseCase: AddGoodsToTeamUseCase,
    private val getGoodsForLevelUseCase: GetGoodsForLevelUseCase,
) {
    suspend operator fun invoke(team: TeamInfoForSave) {
        val teamId = teamRepository.saveTeam(team)
        val scenario = scenarioRepository.getScenario(1)
        val startAvaliableClasses = listOf(
            CharacterClassType.Brute,
            CharacterClassType.Tinkerer,
            CharacterClassType.Spellweaver,
            CharacterClassType.Scoundrel,
            CharacterClassType.Cragheart,
            CharacterClassType.Mindthief

        )
        val startGoods = getGoodsForLevelUseCase(1)
        scenarioRepository.saveTeamScenario(scenario, teamId)
        characterClassRepository.addAvailableClasses(teamId, startAvaliableClasses)
        addGoodsToTeamUseCase(teamId, startGoods)
    }
}