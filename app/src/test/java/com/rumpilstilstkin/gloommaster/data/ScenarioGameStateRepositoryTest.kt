package com.rumpilstilstkin.gloommaster.data

import app.cash.turbine.test
import com.rumpilstilstkin.gloommaster.bd.dao.ScenarioGameStateDao
import com.rumpilstilstkin.gloommaster.bd.entity.ScenarioGameStateBd
import com.rumpilstilstkin.gloommaster.domain.entity.ScenarioGameState
import com.rumpilstilstkin.gloommaster.data.ScenarioGameStateRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNotNull
import strikt.assertions.isNull

@OptIn(ExperimentalCoroutinesApi::class)
class ScenarioGameStateRepositoryTest {
    private val dao: ScenarioGameStateDao = mockk(relaxUnitFun = true)
    private val sut = ScenarioGameStateRepository(dao)

    @Test
    fun `given DAO has a stored state when get is called then it is mapped to the domain entity`() = runTest {
        // Given
        coEvery { dao.get() } returns bdFixture(level = 4, scenarioNumber = 42, round = 3)

        // When
        val state = sut.get()

        // Then
        expectThat(state).isNotNull()
        expectThat(state?.level).isEqualTo(4)
        expectThat(state?.scenarioNumber).isEqualTo(42)
        expectThat(state?.round).isEqualTo(3)
    }

    @Test
    fun `given DAO has no stored state when get is called then null is returned`() = runTest {
        // Given
        coEvery { dao.get() } returns null

        // When
        val state = sut.get()

        // Then
        expectThat(state).isNull()
    }

    @Test
    fun `given DAO emits the stored Bd when getFlow is collected then the Bd reaches the consumer without mapping`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val bd = bdFixture(level = 2, scenarioNumber = 7, round = 1)
        every { dao.getFlow() } returns flowOf(bd)

        // When / Then
        sut.getFlow().test {
            expectThat(awaitItem()).isEqualTo(bd)
            awaitComplete()
        }
    }

    @Test
    fun `given a domain state when save is called then DAO clearAndInsert receives an equivalent Bd`() = runTest {
        // Given
        val state = domainFixture(level = 5, scenarioNumber = 11, round = 2)

        // When
        sut.save(state)

        // Then
        coVerify(exactly = 1) {
            dao.clearAndInsert(
                match { it.level == 5 && it.scenarioNumber == 11 && it.round == 2 },
            )
        }
    }

    @Test
    fun `given any state when delete is called then DAO deleteAll is invoked exactly once`() = runTest {
        // When
        sut.delete()

        // Then
        coVerify(exactly = 1) { dao.deleteAll() }
    }

    companion object {
        fun bdFixture(
            level: Int = 1,
            scenarioNumber: Int? = 1,
            round: Int = 0,
        ): ScenarioGameStateBd = ScenarioGameStateBd(
            scenarioNumber = scenarioNumber,
            monsterSlugs = emptyList(),
            round = round,
            availableCards = emptyList(),
            activeMonsters = emptyList(),
            magicChargeMap = emptyList(),
            level = level,
        )

        fun domainFixture(
            level: Int = 1,
            scenarioNumber: Int? = 1,
            round: Int = 0,
        ): ScenarioGameState = ScenarioGameState(
            level = level,
            scenarioNumber = scenarioNumber,
            monsterSlugs = emptyList(),
            round = round,
            availableCards = emptyList(),
            activeMonsters = emptyList(),
            magicCharges = emptyList(),
        )
    }
}
