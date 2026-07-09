package com.rumpilstilstkin.gloommaster.domain.usecase.team

import com.rumpilstilstkin.gloommaster.data.CharacterClassRepository
import com.rumpilstilstkin.gloommaster.data.ScenarioRepository
import com.rumpilstilstkin.gloommaster.data.TeamRepository
import com.rumpilstilstkin.gloommaster.domain.entity.CharacterClassType
import com.rumpilstilstkin.gloommaster.domain.entity.TeamInfoForSave
import com.rumpilstilstkin.gloommaster.domain.usecase.goods.AddGoodsToTeamByNumbersUseCase
import com.rumpilstilstkin.gloommaster.domain.usecase.goods.GetGoodNumbersForLevelUseCase
import com.rumpilstilstkin.gloommaster.domain.usecase.team.SaveTeamUseCase
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SaveTeamUseCaseTest {
    private val teamRepository: TeamRepository = mockk()
    private val scenarioRepository: ScenarioRepository = mockk()
    private val characterClassRepository: CharacterClassRepository = mockk()
    private val addGoodsToTeamUseCase: AddGoodsToTeamByNumbersUseCase = mockk(relaxed = true)
    private val getGoodsForLevelUseCase: GetGoodNumbersForLevelUseCase = GetGoodNumbersForLevelUseCase()

    @Test
    fun `given team when invoked then seeds scenarios goods and starter classes`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val team = TeamInfoForSave.fixture()
        coEvery { teamRepository.saveTeam(team) } returns 42
        coJustRun { scenarioRepository.saveTeamScenario(1, 42) }
        coJustRun {
            characterClassRepository.addAvailableClasses(
                42,
                listOf(
                    CharacterClassType.Brute,
                    CharacterClassType.Tinkerer,
                    CharacterClassType.Spellweaver,
                    CharacterClassType.Scoundrel,
                    CharacterClassType.Cragheart,
                    CharacterClassType.Mindthief,
                ),
            )
        }
        val sut =
            SaveTeamUseCase(
                teamRepository,
                scenarioRepository,
                characterClassRepository,
                addGoodsToTeamUseCase,
                getGoodsForLevelUseCase,
            )

        // When
        sut(team)

        // Then — level 1 starter goods range 1..14
        coVerify(exactly = 1) { teamRepository.saveTeam(team) }
        coVerify(exactly = 1) { scenarioRepository.saveTeamScenario(1, 42) }
        coVerify(exactly = 1) {
            characterClassRepository.addAvailableClasses(
                42,
                listOf(
                    CharacterClassType.Brute,
                    CharacterClassType.Tinkerer,
                    CharacterClassType.Spellweaver,
                    CharacterClassType.Scoundrel,
                    CharacterClassType.Cragheart,
                    CharacterClassType.Mindthief,
                ),
            )
        }
        coVerify(exactly = 1) { addGoodsToTeamUseCase(42, (1..14).toList()) }
    }
}
