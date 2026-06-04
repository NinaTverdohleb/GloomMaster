package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team

import com.rumpilstilstkin.gloomhavenhelper.data.CharacterClassRepository
import com.rumpilstilstkin.gloomhavenhelper.data.CharacterRepository
import com.rumpilstilstkin.gloomhavenhelper.data.GoodsRepository
import com.rumpilstilstkin.gloomhavenhelper.data.QuestsRepository
import com.rumpilstilstkin.gloomhavenhelper.data.ScenarioRepository
import com.rumpilstilstkin.gloomhavenhelper.data.TeamRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ShortTeamInfo
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.export.TeamExportDto
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.perks.AddPerksForCharacterUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.goods.AddGoodForCharacterUseCase
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isFalse
import strikt.assertions.isTrue

@OptIn(ExperimentalCoroutinesApi::class)
class ImportTeamUseCaseTest {
    private val teamRepository: TeamRepository = mockk()
    private val scenarioRepository: ScenarioRepository = mockk()
    private val characterClassRepository: CharacterClassRepository = mockk()
    private val characterRepository: CharacterRepository = mockk()
    private val questsRepository: QuestsRepository = mockk()
    private val addPerksForCharacterUseCase: AddPerksForCharacterUseCase = mockk(relaxed = true)
    private val addGoodForCharacterUseCase: AddGoodForCharacterUseCase = mockk(relaxed = true)
    private val goodsRepository: GoodsRepository = mockk()

    @Test
    fun `given valid empty-state JSON when invoked then team is saved and orchestration runs`() = runTest(UnconfinedTestDispatcher()) {
        // Given — minimal payload: empty characters, empty goods, no unlocked classes
        val team = ShortTeamInfo.fixture(teamId = 0, teamName = "Imported")
        val dto =
            TeamExportDto(
                version = 1,
                team = team,
                teamGoods = emptyList(),
                unlockedClasses = emptyList(),
                teamScenarios = emptyList(),
                characters = emptyList(),
            )
        val jsonString = Json { encodeDefaults = true }.encodeToString(TeamExportDto.serializer(), dto)

        coEvery { teamRepository.saveTeam(any()) } returns 99
        coJustRun { teamRepository.updateTeam(any()) }
        coJustRun { characterClassRepository.addAvailableClasses(99, emptyList()) }
        coJustRun { scenarioRepository.addTeamScenarios(emptyList(), 99) }
        coEvery { goodsRepository.getGoodIdsByNumbers(emptyList()) } returns emptyMap()
        coJustRun { goodsRepository.addGoodsToTeam(99, emptyList()) }

        val sut =
            ImportTeamUseCase(
                teamRepository,
                scenarioRepository,
                characterClassRepository,
                characterRepository,
                questsRepository,
                addPerksForCharacterUseCase,
                addGoodForCharacterUseCase,
                goodsRepository,
            )

        // When
        val result = sut(jsonString)

        // Then
        expectThat(result.isSuccess).isTrue()
        coVerify(exactly = 1) { teamRepository.saveTeam(any()) }
        coVerify(exactly = 1) { teamRepository.updateTeam(team.copy(teamId = 99)) }
        coVerify(exactly = 1) { characterClassRepository.addAvailableClasses(99, emptyList()) }
        coVerify(exactly = 1) { scenarioRepository.addTeamScenarios(emptyList(), 99) }
        coVerify(exactly = 1) { goodsRepository.addGoodsToTeam(99, emptyList()) }
    }

    @Test
    fun `given invalid JSON when invoked then returns failure with no side effects`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val sut =
            ImportTeamUseCase(
                teamRepository,
                scenarioRepository,
                characterClassRepository,
                characterRepository,
                questsRepository,
                addPerksForCharacterUseCase,
                addGoodForCharacterUseCase,
                goodsRepository,
            )

        // When
        val result = sut("this is not json")

        // Then
        expectThat(result.isFailure).isTrue()
        expectThat(result.isSuccess).isFalse()
        coVerify(exactly = 0) { teamRepository.saveTeam(any()) }
    }
}
