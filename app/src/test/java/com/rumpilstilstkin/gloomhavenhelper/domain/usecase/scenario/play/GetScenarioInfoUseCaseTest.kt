package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.play

import com.rumpilstilstkin.gloomhavenhelper.data.LevelInfoRepository
import com.rumpilstilstkin.gloomhavenhelper.data.LocaleRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.DifficultyLevel
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.LevelInfo
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.PackType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Prosperity
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.TeamInfo
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.MagicChargeState
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.MonsterDeckState
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.ScenarioBattleState
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team.GetCurrentTeamUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isFalse
import strikt.assertions.isTrue

@OptIn(ExperimentalCoroutinesApi::class)
class GetScenarioInfoUseCaseTest {
    private val getCurrentTeamUseCase: GetCurrentTeamUseCase = mockk()
    private val levelInfoRepository: LevelInfoRepository = mockk()
    private val restoreScenarioStateUseCase: RestoreScenarioStateUseCase = mockk()
    private val localeRepository: LocaleRepository = mockk()

    @Test
    fun `given current team when invoked then restored state is returned`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val team = teamInfo(level = 3)
        val levelInfo = LevelInfo(level = 3, monsterLevel = 1, goldCount = 8, trapDamage = 2, experience = 4)
        val restored = battleState()
        every { getCurrentTeamUseCase() } returns flowOf(team)
        coEvery { levelInfoRepository.getLevelInfo(3) } returns Result.success(levelInfo)
        coEvery { localeRepository.getCurrentLocale() } returns "en"
        coEvery { restoreScenarioStateUseCase(team, levelInfo, "en") } returns restored
        val sut =
            GetScenarioInfoUseCase(
                getCurrentTeamUseCase,
                levelInfoRepository,
                restoreScenarioStateUseCase,
                localeRepository,
            )

        // When
        val result = sut()

        // Then
        expectThat(result.isSuccess).isTrue()
        expectThat(result.getOrThrow()).isEqualTo(restored)
    }

    @Test
    fun `given level info fails when invoked then restore is called with null level info`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val team = teamInfo(level = 3)
        val restored = battleState()
        every { getCurrentTeamUseCase() } returns flowOf(team)
        coEvery { levelInfoRepository.getLevelInfo(3) } returns Result.failure(RuntimeException("nope"))
        coEvery { localeRepository.getCurrentLocale() } returns "en"
        coEvery { restoreScenarioStateUseCase(team, null, "en") } returns restored
        val sut =
            GetScenarioInfoUseCase(
                getCurrentTeamUseCase,
                levelInfoRepository,
                restoreScenarioStateUseCase,
                localeRepository,
            )

        // When
        val result = sut()

        // Then
        expectThat(result.isSuccess).isTrue()
        expectThat(result.getOrThrow()).isEqualTo(restored)
    }

    @Test
    fun `given null team when invoked then returns failure`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        every { getCurrentTeamUseCase() } returns flowOf(null)
        val sut =
            GetScenarioInfoUseCase(
                getCurrentTeamUseCase,
                levelInfoRepository,
                restoreScenarioStateUseCase,
                localeRepository,
            )

        // When
        val result = sut()

        // Then
        expectThat(result.isFailure).isTrue()
        expectThat(result.isSuccess).isFalse()
    }

    private fun teamInfo(level: Int) =
        TeamInfo(
            id = 7,
            name = "Team",
            level = level,
            teamAchievement = emptyList(),
            globalAchievement = emptyList(),
            reputation = 0,
            activeScenario = emptyList(),
            aliveCharacters = emptyList(),
            shopDiscount = 0,
            prosperity = Prosperity.fixture(),
            packs = listOf(PackType.MAIN),
            hasActiveScenario = false,
            churchValue = 0,
            difficultyLevel = DifficultyLevel.NORMAL,
        )

    private fun battleState() =
        ScenarioBattleState(
            generalLevel = 0,
            name = "",
            monsters = emptyMap(),
            golds = 0,
            exp = 0,
            trapDamage = 0,
            gamersCount = 0,
            monsterLevel = 0,
            deck = MonsterDeckState.create(emptyList()),
            magicState = MagicChargeState.initial(),
            availableEffects = emptySet(),
        )
}
