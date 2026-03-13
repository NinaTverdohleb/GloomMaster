package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team

import com.rumpilstilstkin.gloomhavenhelper.data.TeamRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.goods.AddGoodsToTeamByNumbersUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.goods.GetGoodNumbersForLevelUseCase
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UpdateTeamProsperityUseCase @Inject constructor(
    private val teamRepository: TeamRepository,
    private val getTeamProsperityUseCase: GetTeamProsperityUseCase,
    private val getGoodsForLevelUseCase: GetGoodNumbersForLevelUseCase,
    private val addGoodsToTeamUseCase: AddGoodsToTeamByNumbersUseCase,
) {
    suspend operator fun invoke(
        newProsperityLevelValue: Int
    ) {
        val team = teamRepository.currentTeam.first() ?: return
        val prosperity = getTeamProsperityUseCase(team.prosperity)
        if (prosperity.isStartValue && newProsperityLevelValue == 0 || prosperity.isMax) {
            return
        }

        val prosperityValue = if (prosperity.prosperityLevelValue == newProsperityLevelValue) {
            if (prosperity.prosperityLevelValue == 0) {
                prosperity.prosperitySource.minus(1)
            } else {
                prosperity.prosperitySource.plus(1)
            }
        } else {
            prosperity.prosperitySource.minus(prosperity.prosperityLevelValue)
                .plus(newProsperityLevelValue)
        }

        teamRepository.updateProsperity(team.teamId, prosperityValue)
        val newProsperity = getTeamProsperityUseCase(prosperityValue)
        if (newProsperity.prosperityLevel > prosperity.prosperityLevel) {
            addGoodsToTeamUseCase(team.teamId, getGoodsForLevelUseCase(newProsperity.prosperityLevel))
        }
    }
}