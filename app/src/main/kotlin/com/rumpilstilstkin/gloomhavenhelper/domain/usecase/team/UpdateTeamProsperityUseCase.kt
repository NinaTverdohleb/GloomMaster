package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team

import com.rumpilstilstkin.gloomhavenhelper.data.TeamRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Prosperity
import javax.inject.Inject

class UpdateTeamProsperityUseCase @Inject constructor(
    private val teamRepository: TeamRepository,
) {
    suspend operator fun invoke(
        prosperity: Prosperity,
        newProsperityLevelValue: Int
    ) {
        if (prosperity.isStartValue && newProsperityLevelValue == 0 || prosperity.isMax) {
            return
        }

        val prosperity = if (prosperity.prosperityLevelValue == newProsperityLevelValue) {
            if (prosperity.prosperityLevelValue == 0) {
                prosperity.prosperitySource.minus(1)
            } else {
                prosperity.prosperitySource.plus(1)
            }
        } else {
            prosperity.prosperitySource.minus(prosperity.prosperityLevelValue)
                .plus(newProsperityLevelValue)
        }
        teamRepository.updateProsperity(prosperity)
    }
}