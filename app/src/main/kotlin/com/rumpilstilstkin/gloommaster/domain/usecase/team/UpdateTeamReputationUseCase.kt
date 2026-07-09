package com.rumpilstilstkin.gloommaster.domain.usecase.team

import com.rumpilstilstkin.gloommaster.data.TeamRepository
import javax.inject.Inject

class UpdateTeamReputationUseCase @Inject constructor(
    private val teamRepository: TeamRepository,
) {
    suspend operator fun invoke(reputation: Int) {
        if (reputation in -20..20) {
            teamRepository.updateReputation(reputation)
        }
    }
}
