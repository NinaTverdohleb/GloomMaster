package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team

import com.rumpilstilstkin.gloomhavenhelper.data.TeamRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.DifficultyLevel
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UpdateDifficultyLevelUseCase @Inject constructor(
    private val teamRepository: TeamRepository,
) {
    suspend operator fun invoke(level: DifficultyLevel) {
        val currentTeam = teamRepository.currentTeam.first() ?: return
        if (currentTeam.difficultyLevel == level) return
        teamRepository.updateTeam(currentTeam.copy(difficultyLevel = level))
    }
}
