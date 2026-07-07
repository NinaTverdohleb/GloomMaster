package com.rumpilstilstkin.gloomhavenhelper.data

import app.cash.turbine.test
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.CharacterDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.TeamDao
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.CharacterBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.TeamBd
import com.rumpilstilstkin.gloomhavenhelper.data.datasource.CurrentTeamDatasource
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterClassType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterForSave
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.DifficultyLevel
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.PackType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ShortTeamInfo
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.TeamInfoForSave
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.hasSize
import strikt.assertions.isEqualTo
import strikt.assertions.isNotNull
import strikt.assertions.isNull

@OptIn(ExperimentalCoroutinesApi::class)
class TeamRepositoryTest {
    private val currentTeamDatasource: CurrentTeamDatasource = mockk(relaxUnitFun = true)
    private val teamDao: TeamDao = mockk(relaxUnitFun = true)
    private val characterDao: CharacterDao = mockk(relaxUnitFun = true)
    private val scenarioGameStateRepository: ScenarioGameStateRepository = mockk(relaxUnitFun = true)

    private fun newSut(externalScope: CoroutineScope): TeamRepository =
        TeamRepository(
            externalScope = externalScope,
            currentTeamDatasource = currentTeamDatasource,
            teamDao = teamDao,
            characterDao = characterDao,
            scenarioGameStateRepository = scenarioGameStateRepository,
        )

    @Test
    fun `given prefs holds a valid team id when init runs then currentTeam emits the loaded ShortTeamInfo`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        every { currentTeamDatasource.currentTeam } returns 5
        coEvery { teamDao.findById(5) } returns teamBdFixture(teamId = 5)
        every { teamDao.getTeamFlow(5) } returns flowOf(teamBdFixture(teamId = 5))
        every { characterDao.findByTeamIdFlow(5) } returns flowOf(
            listOf(characterBdFixture(characterId = 1, teamId = 5, isAlive = true)),
        )
        val sut = newSut(TestScope(UnconfinedTestDispatcher(testScheduler)))
        advanceUntilIdle()

        // When / Then
        sut.currentTeam.test {
            val emitted = awaitItem()
            expectThat(emitted).isNotNull()
            expectThat(emitted?.teamId).isEqualTo(5)
            expectThat(emitted?.aliveCharacterIds).isEqualTo(listOf(1))
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `given prefs holds EMPTY_TEAM when init runs then currentTeam emits null`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        every { currentTeamDatasource.currentTeam } returns CurrentTeamDatasource.EMPTY_TEAM
        coEvery { teamDao.findById(CurrentTeamDatasource.EMPTY_TEAM) } returns null
        val sut = newSut(TestScope(UnconfinedTestDispatcher(testScheduler)))
        advanceUntilIdle()

        // When / Then
        sut.currentTeam.test {
            expectThat(awaitItem()).isNull()
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `given prefs holds an id but the team row is missing when init runs then currentTeam emits null`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        every { currentTeamDatasource.currentTeam } returns 99
        coEvery { teamDao.findById(99) } returns null
        val sut = newSut(TestScope(UnconfinedTestDispatcher(testScheduler)))
        advanceUntilIdle()

        // When / Then
        sut.currentTeam.test {
            expectThat(awaitItem()).isNull()
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `given a new team id when setCurrentTeam then datasource is updated, scenario state is cleared, and currentTeam emits the new team`() = runTest(UnconfinedTestDispatcher()) {
        // Given prefs start empty and become 7 once setCurrentTeam writes them
        val storedTeamId = slot<Int>()
        every { currentTeamDatasource.currentTeam } answers {
            if (storedTeamId.isCaptured) storedTeamId.captured else CurrentTeamDatasource.EMPTY_TEAM
        }
        every { currentTeamDatasource.currentTeam = capture(storedTeamId) } just Runs
        coEvery { teamDao.findById(CurrentTeamDatasource.EMPTY_TEAM) } returns null
        coEvery { teamDao.findById(7) } returns teamBdFixture(teamId = 7)
        every { teamDao.getTeamFlow(7) } returns flowOf(teamBdFixture(teamId = 7))
        every { characterDao.findByTeamIdFlow(7) } returns flowOf(emptyList())
        val sut = newSut(TestScope(UnconfinedTestDispatcher(testScheduler)))
        advanceUntilIdle()

        // When
        sut.setCurrentTeam(7)
        advanceUntilIdle()

        // Then — observable state, not call order
        expectThat(storedTeamId.captured).isEqualTo(7)
        coVerify(exactly = 1) { scenarioGameStateRepository.delete() }
        sut.currentTeam.test {
            val emitted = awaitItem()
            expectThat(emitted).isNotNull()
            expectThat(emitted?.teamId).isEqualTo(7)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `given DAO emits team list when getTeams is collected then each TeamBd is mapped to a Team`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        every { currentTeamDatasource.currentTeam } returns CurrentTeamDatasource.EMPTY_TEAM
        coEvery { teamDao.findById(any()) } returns null
        every { teamDao.getAllFlow() } returns flowOf(
            listOf(
                teamBdFixture(teamId = 1, packs = listOf("MAIN")),
                teamBdFixture(teamId = 2, packs = listOf("FORGOTTEN_CIRCLES"), difficultyLevel = 1),
            ),
        )
        val sut = newSut(TestScope(UnconfinedTestDispatcher(testScheduler)))

        // When / Then
        sut.getTeams().test {
            val emitted = awaitItem()
            expectThat(emitted).hasSize(2)
            expectThat(emitted[0].packs).isEqualTo(listOf(PackType.MAIN))
            expectThat(emitted[1].difficultyLevel).isEqualTo(DifficultyLevel.HARD)
            awaitComplete()
        }
    }

    @Test
    fun `given DAO has a team and characters when getTeamInfo then a ShortTeamInfo is built from them`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        every { currentTeamDatasource.currentTeam } returns CurrentTeamDatasource.EMPTY_TEAM
        coEvery { teamDao.findById(CurrentTeamDatasource.EMPTY_TEAM) } returns null
        coEvery { teamDao.findById(10) } returns teamBdFixture(teamId = 10)
        coEvery { characterDao.findByTeamId(10) } returns listOf(
            characterBdFixture(characterId = 1, teamId = 10, isAlive = true),
            characterBdFixture(characterId = 2, teamId = 10, isAlive = false),
        )
        val sut = newSut(TestScope(UnconfinedTestDispatcher(testScheduler)))

        // When
        val info = sut.getTeamInfo(10)

        // Then
        expectThat(info).isNotNull()
        expectThat(info?.aliveCharacterIds).isEqualTo(listOf(1))
        expectThat(info?.countRetiredCharacters).isEqualTo(1)
    }

    @Test
    fun `given the team is missing when getTeamInfo then null is returned and characterDao is not hit`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        every { currentTeamDatasource.currentTeam } returns CurrentTeamDatasource.EMPTY_TEAM
        coEvery { teamDao.findById(CurrentTeamDatasource.EMPTY_TEAM) } returns null
        coEvery { teamDao.findById(10) } returns null
        val sut = newSut(TestScope(UnconfinedTestDispatcher(testScheduler)))

        // When
        val info = sut.getTeamInfo(10)

        // Then
        expectThat(info).isNull()
        coVerify(exactly = 0) { characterDao.findByTeamId(10) }
    }

    @Test
    fun `given a team with characters when saveTeam then the new id is returned, each character is persisted with that team id, and prefs point at it`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val storedTeamId = slot<Int>()
        val insertedCharacters = mutableListOf<CharacterBd>()
        every { currentTeamDatasource.currentTeam } answers {
            if (storedTeamId.isCaptured) storedTeamId.captured else CurrentTeamDatasource.EMPTY_TEAM
        }
        every { currentTeamDatasource.currentTeam = capture(storedTeamId) } just Runs
        coEvery { teamDao.findById(any()) } returns null
        coEvery { teamDao.insert(any()) } returns 42L
        coEvery { characterDao.insert(capture(insertedCharacters)) } returns 0L
        val sut = newSut(TestScope(UnconfinedTestDispatcher(testScheduler)))
        advanceUntilIdle()

        val source = TeamInfoForSave(
            name = "Squad",
            characters = listOf(
                CharacterForSave(name = "Brute", level = 1, characterType = CharacterClassType.Brute, experience = 0),
                CharacterForSave(name = "Sun", level = 1, characterType = CharacterClassType.Sunkeeper, experience = 0),
            ),
            packs = listOf(PackType.MAIN),
            difficultyLevel = DifficultyLevel.NORMAL,
        )

        // When
        val id = sut.saveTeam(source)

        // Then — observable results, not call order
        expectThat(id).isEqualTo(42)
        expectThat(storedTeamId.captured).isEqualTo(42)
        expectThat(insertedCharacters).hasSize(2)
        expectThat(insertedCharacters.map { it.name }).isEqualTo(listOf("Brute", "Sun"))
        expectThat(insertedCharacters.map { it.teamId }).isEqualTo(listOf(42, 42))
        coVerify(exactly = 1) { teamDao.insert(match { it.name == "Squad" }) }
        coVerify(exactly = 1) { scenarioGameStateRepository.delete() }
    }

    @Test
    fun `given an empty character list when saveTeam then no character insert is performed`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        every { currentTeamDatasource.currentTeam } returns CurrentTeamDatasource.EMPTY_TEAM
        every { currentTeamDatasource.currentTeam = any() } just Runs
        coEvery { teamDao.findById(any()) } returns null
        coEvery { teamDao.insert(any()) } returns 42L
        val sut = newSut(TestScope(UnconfinedTestDispatcher(testScheduler)))

        val source = TeamInfoForSave(
            name = "Squad",
            characters = emptyList(),
            packs = listOf(PackType.MAIN),
        )

        // When
        sut.saveTeam(source)

        // Then
        coVerify(exactly = 0) { characterDao.insert(any()) }
    }

    @Test
    fun `given currentTeam is loaded when updateReputation then DAO is called with the loaded teamId`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        every { currentTeamDatasource.currentTeam } returns 5
        coEvery { teamDao.findById(5) } returns teamBdFixture(teamId = 5)
        val sut = newSut(TestScope(UnconfinedTestDispatcher(testScheduler)))
        advanceUntilIdle()

        // When
        sut.updateReputation(7)

        // Then
        coVerify(exactly = 1) { teamDao.updateReputation(5, 7) }
    }

    @Test
    fun `given currentTeam is in failure state when updateReputation then DAO is not called`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        every { currentTeamDatasource.currentTeam } returns CurrentTeamDatasource.EMPTY_TEAM
        coEvery { teamDao.findById(any()) } returns null
        val sut = newSut(TestScope(UnconfinedTestDispatcher(testScheduler)))
        advanceUntilIdle()

        // When
        sut.updateReputation(7)

        // Then
        coVerify(exactly = 0) { teamDao.updateReputation(any(), any()) }
    }

    @Test
    fun `given a teamId and prosperity when updateProsperity then DAO is called with that pair`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        every { currentTeamDatasource.currentTeam } returns CurrentTeamDatasource.EMPTY_TEAM
        coEvery { teamDao.findById(any()) } returns null
        val sut = newSut(TestScope(UnconfinedTestDispatcher(testScheduler)))

        // When
        sut.updateProsperity(teamId = 5, prosperity = 12)

        // Then
        coVerify(exactly = 1) { teamDao.updateProsperity(5, 12) }
    }

    @Test
    fun `given a team with churchValue 30 when donate then DAO is updated with 40 and 40 is returned`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        every { currentTeamDatasource.currentTeam } returns CurrentTeamDatasource.EMPTY_TEAM
        coEvery { teamDao.findById(CurrentTeamDatasource.EMPTY_TEAM) } returns null
        coEvery { teamDao.findById(5) } returns teamBdFixture(teamId = 5, churchValue = 30)
        val sut = newSut(TestScope(UnconfinedTestDispatcher(testScheduler)))

        // When
        val result = sut.donate(5)

        // Then
        expectThat(result).isEqualTo(40)
        coVerify(exactly = 1) { teamDao.updateDonateValue(5, 40) }
    }

    @Test
    fun `given the team is missing when donate then DAO is updated with 0 and 0 is returned`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        every { currentTeamDatasource.currentTeam } returns CurrentTeamDatasource.EMPTY_TEAM
        coEvery { teamDao.findById(any()) } returns null
        val sut = newSut(TestScope(UnconfinedTestDispatcher(testScheduler)))

        // When
        val result = sut.donate(5)

        // Then
        expectThat(result).isEqualTo(0)
        coVerify(exactly = 1) { teamDao.updateDonateValue(5, 0) }
    }

    @Test
    fun `given a ShortTeamInfo when updateTeam then DAO update is called with the equivalent TeamBd`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        every { currentTeamDatasource.currentTeam } returns CurrentTeamDatasource.EMPTY_TEAM
        coEvery { teamDao.findById(any()) } returns null
        val sut = newSut(TestScope(UnconfinedTestDispatcher(testScheduler)))
        val info = ShortTeamInfo.fixture(teamId = 7, reputation = 3)

        // When
        sut.updateTeam(info)

        // Then
        coVerify(exactly = 1) { teamDao.update(match { it.teamId == 7 && it.reputation == 3 }) }
    }

    @Test
    fun `given the deleted team is the current one and another team exists as a fallback when deleteTeam then prefs switch to the fallback and the original team row is removed`() = runTest(UnconfinedTestDispatcher()) {
        // Given prefs point at team 7, so team 7 is the current team
        val storedTeamId = slot<Int>()
        every { currentTeamDatasource.currentTeam } answers {
            if (storedTeamId.isCaptured) storedTeamId.captured else 7
        }
        every { currentTeamDatasource.currentTeam = capture(storedTeamId) } just Runs
        coEvery { teamDao.findById(any()) } returns null
        coEvery { teamDao.getAll() } returns listOf(
            teamBdFixture(teamId = 7),
            teamBdFixture(teamId = 8),
        )
        val sut = newSut(TestScope(UnconfinedTestDispatcher(testScheduler)))
        advanceUntilIdle()

        // When
        sut.deleteTeam(7)
        advanceUntilIdle()

        // Then — observable state, not call order
        expectThat(storedTeamId.captured).isEqualTo(8)
        coVerify(exactly = 1) { characterDao.deleteByTeamId(7) }
        coVerify(exactly = 1) { teamDao.delete(7) }
        coVerify(exactly = 1) { scenarioGameStateRepository.delete() }
    }

    @Test
    fun `given the deleted team is the current and only team when deleteTeam then prefs reset to EMPTY_TEAM and the team row is removed`() = runTest(UnconfinedTestDispatcher()) {
        // Given prefs point at team 7, so team 7 is the current team
        val storedTeamId = slot<Int>()
        every { currentTeamDatasource.currentTeam } answers {
            if (storedTeamId.isCaptured) storedTeamId.captured else 7
        }
        every { currentTeamDatasource.currentTeam = capture(storedTeamId) } just Runs
        coEvery { teamDao.findById(any()) } returns null
        coEvery { teamDao.getAll() } returns listOf(teamBdFixture(teamId = 7))
        val sut = newSut(TestScope(UnconfinedTestDispatcher(testScheduler)))
        advanceUntilIdle()

        // When
        sut.deleteTeam(7)
        advanceUntilIdle()

        // Then — observable state, not call order
        expectThat(storedTeamId.captured).isEqualTo(CurrentTeamDatasource.EMPTY_TEAM)
        coVerify(exactly = 1) { characterDao.deleteByTeamId(7) }
        coVerify(exactly = 1) { teamDao.delete(7) }
        coVerify(exactly = 1) { scenarioGameStateRepository.delete() }
    }

    companion object {
        fun teamBdFixture(
            teamId: Int = 1,
            name: String = "Squad",
            packs: List<String> = listOf("MAIN"),
            difficultyLevel: Int = 0,
            churchValue: Int = 100,
            reputation: Int = 0,
        ): TeamBd = TeamBd(
            teamId = teamId,
            name = name,
            packs = packs,
            difficultyLevel = difficultyLevel,
            churchValue = churchValue,
            reputation = reputation,
        )

        fun characterBdFixture(
            characterId: Int = 1,
            teamId: Int? = 1,
            isAlive: Boolean = true,
        ): CharacterBd = CharacterBd(
            characterId = characterId,
            name = "Brute",
            level = 1,
            experience = 0,
            goldCount = 30,
            characterType = "Brute",
            teamId = teamId,
            isAlive = isAlive,
        )
    }
}
