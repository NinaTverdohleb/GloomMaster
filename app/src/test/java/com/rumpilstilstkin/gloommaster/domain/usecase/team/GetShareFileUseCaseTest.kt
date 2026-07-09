package com.rumpilstilstkin.gloommaster.domain.usecase.team

import com.rumpilstilstkin.gloommaster.data.ExportTeamRepository
import com.rumpilstilstkin.gloommaster.data.TeamRepository
import com.rumpilstilstkin.gloommaster.domain.entity.ShortTeamInfo
import com.rumpilstilstkin.gloommaster.domain.usecase.team.GetShareFileUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isFalse
import strikt.assertions.isTrue
import java.io.File
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class GetShareFileUseCaseTest {
    @get:Rule
    val tmp = TemporaryFolder()

    private val teamRepository: TeamRepository = mockk()
    private val exportTeamRepository: ExportTeamRepository = mockk()

    @Test
    fun `given current team and successful export when invoked then file is written and returned`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val team = ShortTeamInfo.fixture(teamId = 7, teamName = "MyTeam")
        every { teamRepository.currentTeam } returns flowOf(team)
        coEvery { exportTeamRepository.getExportTeamData(7) } returns Result.success("""{"team":"data"}""")
        val exportDir = tmp.newFolder("export")
        val sut = GetShareFileUseCase(teamRepository, exportTeamRepository)

        // When
        val result = sut(exportDir)

        // Then
        expectThat(result.isSuccess).isTrue()
        val file = result.getOrThrow()
        expectThat(file.name).isEqualTo("MyTeam.json")
        expectThat(file.readText()).isEqualTo("""{"team":"data"}""")
    }

    @Test
    fun `given current team and export dir not yet existing when invoked then dir is created`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val team = ShortTeamInfo.fixture(teamId = 7, teamName = "T")
        every { teamRepository.currentTeam } returns flowOf(team)
        coEvery { exportTeamRepository.getExportTeamData(7) } returns Result.success("json")
        val parent = tmp.newFolder("parent")
        val exportDir = File(parent, "nested/sub")
        val sut = GetShareFileUseCase(teamRepository, exportTeamRepository)

        // When
        val result = sut(exportDir)

        // Then
        expectThat(result.isSuccess).isTrue()
        expectThat(exportDir.exists()).isTrue()
    }

    @Test
    fun `given null current team when invoked then returns failure`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        every { teamRepository.currentTeam } returns flowOf(null)
        val sut = GetShareFileUseCase(teamRepository, exportTeamRepository)

        // When
        val result = sut(tmp.newFolder())

        // Then
        expectThat(result.isFailure).isTrue()
        expectThat(result.isSuccess).isFalse()
    }

    @Test
    fun `given export repository failure when invoked then returns failure`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val team = ShortTeamInfo.fixture(teamId = 7)
        every { teamRepository.currentTeam } returns flowOf(team)
        coEvery { exportTeamRepository.getExportTeamData(7) } returns Result.failure(IOException("bad"))
        val sut = GetShareFileUseCase(teamRepository, exportTeamRepository)

        // When
        val result = sut(tmp.newFolder())

        // Then
        expectThat(result.isFailure).isTrue()
    }
}
