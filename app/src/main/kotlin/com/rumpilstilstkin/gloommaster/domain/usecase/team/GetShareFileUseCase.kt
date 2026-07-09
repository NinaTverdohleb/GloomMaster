package com.rumpilstilstkin.gloommaster.domain.usecase.team

import com.rumpilstilstkin.gloommaster.data.ExportTeamRepository
import com.rumpilstilstkin.gloommaster.data.TeamRepository
import com.rumpilstilstkin.gloommaster.utils.flatMap
import jakarta.inject.Inject
import kotlinx.coroutines.flow.first
import java.io.File

class GetShareFileUseCase @Inject constructor(
    private val teamRepository: TeamRepository,
    private val exportTeamRepository: ExportTeamRepository,
) {
    suspend operator fun invoke(exportDir: File): Result<File> {
        val currentTeam =
            teamRepository.currentTeam.first()
                ?: return Result.failure(IllegalStateException())

        return exportTeamRepository
            .getExportTeamData(currentTeam.teamId)
            .flatMap { jsonContent ->
                try {
                    if (!exportDir.exists()) exportDir.mkdirs()
                    val file = File(exportDir, "${currentTeam.name}.json")
                    file.writeText(jsonContent)
                    Result.success(file)
                } catch (e: Exception) {
                    Result.failure(e)
                }
            }
    }
}
