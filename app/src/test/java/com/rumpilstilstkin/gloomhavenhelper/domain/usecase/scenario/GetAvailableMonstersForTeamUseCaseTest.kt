package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario

import com.rumpilstilstkin.gloomhavenhelper.data.LocaleRepository
import com.rumpilstilstkin.gloomhavenhelper.data.MonsterRepository
import com.rumpilstilstkin.gloomhavenhelper.data.ScenarioGameStateRepository
import com.rumpilstilstkin.gloomhavenhelper.data.TeamRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.PackType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioGameState
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ShortTeamInfo
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterName
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEmpty
import strikt.assertions.isEqualTo

@OptIn(ExperimentalCoroutinesApi::class)
class GetAvailableMonstersForTeamUseCaseTest {
    private val teamRepository: TeamRepository = mockk()
    private val monsterRepository: MonsterRepository = mockk()
    private val scenarioGameStateRepository: ScenarioGameStateRepository = mockk()
    private val localeRepository: LocaleRepository = mockk()

    @Test
    fun `given team and active scenario when invoked then excludes scenario monsters`() =
        runTest(UnconfinedTestDispatcher()) {
            // Given
            val team = ShortTeamInfo.fixture(teamId = 7, packs = listOf(PackType.MAIN))
            val state =
                ScenarioGameState(
                    level = 1,
                    scenarioNumber = 1,
                    monsterSlugs = listOf("excluded"),
                    round = 0,
                    availableCards = emptyList(),
                    activeMonsters = emptyList(),
                    magicCharges = emptyList(),
                )
            every { teamRepository.currentTeam } returns flowOf(team)
            coEvery { localeRepository.getCurrentLocale() } returns "en"
            coEvery { scenarioGameStateRepository.get() } returns state
            coEvery {
                monsterRepository.getMonstersForPacks(packs = listOf("MAIN"), locale = "en")
            } returns listOf(MonsterName("excluded", "Excluded"), MonsterName("bandit", "Bandit"))
            val sut =
                GetAvailableMonstersForTeamUseCase(
                    teamRepository,
                    monsterRepository,
                    scenarioGameStateRepository,
                    localeRepository,
                )

            // When
            val result = sut()

            // Then
            expectThat(result).isEqualTo(listOf(MonsterName("bandit","Bandit")))
        }

    @Test
    fun `given null current team when invoked then returns empty map`() =
        runTest(UnconfinedTestDispatcher()) {
            // Given
            every { teamRepository.currentTeam } returns flowOf(null)
            val sut =
                GetAvailableMonstersForTeamUseCase(
                    teamRepository,
                    monsterRepository,
                    scenarioGameStateRepository,
                    localeRepository,
                )

            // When
            val result = sut()

            // Then
            expectThat(result).isEmpty()
        }
}
