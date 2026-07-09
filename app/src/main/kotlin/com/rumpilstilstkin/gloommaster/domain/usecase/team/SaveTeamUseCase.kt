package com.rumpilstilstkin.gloommaster.domain.usecase.team

import com.rumpilstilstkin.gloommaster.data.CharacterClassRepository
import com.rumpilstilstkin.gloommaster.data.ScenarioRepository
import com.rumpilstilstkin.gloommaster.data.TeamRepository
import com.rumpilstilstkin.gloommaster.domain.entity.CharacterClassType
import com.rumpilstilstkin.gloommaster.domain.entity.TeamInfoForSave
import com.rumpilstilstkin.gloommaster.domain.usecase.goods.AddGoodsToTeamByNumbersUseCase
import com.rumpilstilstkin.gloommaster.domain.usecase.goods.GetGoodNumbersForLevelUseCase
import javax.inject.Inject

class SaveTeamUseCase @Inject constructor(
    private val teamRepository: TeamRepository,
    private val scenarioRepository: ScenarioRepository,
    private val characterClassRepository: CharacterClassRepository,
    private val addGoodsToTeamUseCase: AddGoodsToTeamByNumbersUseCase,
    private val getGoodsForLevelUseCase: GetGoodNumbersForLevelUseCase,
) {
    suspend operator fun invoke(team: TeamInfoForSave) {
        val teamId = teamRepository.saveTeam(team)
        val startAvaliableClasses =
            listOf(
                CharacterClassType.Brute,
                CharacterClassType.Tinkerer,
                CharacterClassType.Spellweaver,
                CharacterClassType.Scoundrel,
                CharacterClassType.Cragheart,
                CharacterClassType.Mindthief,
            )
        val startGoods = getGoodsForLevelUseCase(1)
        scenarioRepository.saveTeamScenario(1, teamId)
        characterClassRepository.addAvailableClasses(teamId, startAvaliableClasses)
        addGoodsToTeamUseCase(teamId, startGoods)
    }
}
