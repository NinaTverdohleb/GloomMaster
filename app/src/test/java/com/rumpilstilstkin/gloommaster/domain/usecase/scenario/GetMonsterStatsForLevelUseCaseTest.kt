package com.rumpilstilstkin.gloommaster.domain.usecase.scenario

import com.rumpilstilstkin.gloommaster.data.LocaleRepository
import com.rumpilstilstkin.gloommaster.data.MonsterRepository
import com.rumpilstilstkin.gloommaster.domain.entity.monster.MonsterStats
import com.rumpilstilstkin.gloommaster.domain.usecase.scenario.GetMonsterStatsForLevelUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

@OptIn(ExperimentalCoroutinesApi::class)
class GetMonsterStatsForLevelUseCaseTest {
    private val monsterRepository: MonsterRepository = mockk()
    private val localeRepository: LocaleRepository = mockk()

    @Test
    fun `given monster and level when invoked then returns repository stats`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val stats =
            MonsterStats(
                monsterSlug = "bandit",
                level = 3,
                isElite = true,
                life = 10,
                stats = emptyList(),
            )
        coEvery { localeRepository.getCurrentLocale() } returns "en"
        coEvery {
            monsterRepository.getMonsterStats(monsterSlug = "bandit", level = 3, isElite = true, locale = "en")
        } returns stats
        val sut = GetMonsterStatsForLevelUseCase(monsterRepository, localeRepository)

        // When
        val result = sut(monsterSlug = "bandit", level = 3, isElite = true)

        // Then
        expectThat(result).isEqualTo(stats)
    }
}
