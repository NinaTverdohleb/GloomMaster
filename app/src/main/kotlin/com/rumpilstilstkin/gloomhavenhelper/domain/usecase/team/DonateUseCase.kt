package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team

import com.rumpilstilstkin.gloomhavenhelper.data.TeamRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class DonateUseCase @Inject constructor(
    private val teamRepository: TeamRepository,
    private val getNextChurchValueUseCase: GetNextChurchValueUseCase,
    private val updateTeamProsperityUseCase: UpdateTeamProsperityUseCase,
    private val getTeamProsperityUseCase: GetTeamProsperityUseCase,
) {
    suspend operator fun invoke(oldChurchValue: Int) {
        val team = teamRepository.currentTeam.first() ?: return
        val churchValue = teamRepository.donate(team.teamId)
        val prosperity = getTeamProsperityUseCase(team.prosperity)
        if (churchValue == getNextChurchValueUseCase(oldChurchValue)) {
            val newProsperityLevelValue =
                prosperity.prosperityLevelValue
                    .plus(1)
                    .coerceAtMost(prosperity.prosperityRange.last)

            updateTeamProsperityUseCase(newProsperityLevelValue)
        }
    }
}
