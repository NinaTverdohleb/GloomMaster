package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team

import com.rumpilstilstkin.gloomhavenhelper.data.TeamRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Prosperity
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.goods.AddGoodsToTeamUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.goods.GetGoodsForLevelUseCase
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UpdateTeamProsperityUseCase @Inject constructor(
    private val teamRepository: TeamRepository,
    private val getTeamProsperityUseCase: GetTeamProsperityUseCase,
    private val getGoodsForLevelUseCase: GetGoodsForLevelUseCase,
    private val addGoodsToTeamUseCase: AddGoodsToTeamUseCase,
) {
    suspend operator fun invoke(
        prosperity: Prosperity,
        newProsperityLevelValue: Int
    ) {
        val teamId = teamRepository.currentTeamId.first()

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

        teamRepository.updateProsperity(teamId, prosperityValue)
        val newProsperity = getTeamProsperityUseCase(newProsperityLevelValue)
        if (newProsperity.prosperityLevel > prosperity.prosperityLevel) {
            addGoodsToTeamUseCase(teamId, getGoodsForLevelUseCase(newProsperity.prosperityLevel))
        }
    }
}