package com.rumpilstilstkin.gloomhavenhelper.data

import com.rumpilstilstkin.gloomhavenhelper.bd.dao.GameLevelInfoDao
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.GameLevelInfoBd
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNotNull
import strikt.assertions.isTrue

@OptIn(ExperimentalCoroutinesApi::class)
class LevelInfoRepositoryTest {
    private val dao: GameLevelInfoDao = mockk()
    private val sut = LevelInfoRepository(dao)

    @Test
    fun `given empty cache when getLevelInfo is called then DAO is hit and the level is returned`() = runTest {
        // Given
        coEvery { dao.getAll() } returns listOf(
            GameLevelInfoBd.fixture(level = 1),
            GameLevelInfoBd.fixture(level = 2),
        )

        // When
        val result = sut.getLevelInfo(2)

        // Then
        coVerify(exactly = 1) { dao.getAll() }
        expectThat(result.isSuccess).isTrue()
        expectThat(result.getOrNull()).isNotNull()
        expectThat(result.getOrNull()?.level).isEqualTo(2)
    }

    @Test
    fun `given the cache has already been filled when getLevelInfo is called a second time then the DAO is not hit again`() = runTest {
        // Given
        coEvery { dao.getAll() } returns listOf(GameLevelInfoBd.fixture(level = 1))

        // When
        sut.getLevelInfo(1)
        sut.getLevelInfo(1)

        // Then
        coVerify(exactly = 1) { dao.getAll() }
    }

    @Test
    fun `given the DAO has no row for the requested level when getLevelInfo is called then Result is failure with Level not found`() = runTest {
        // Given
        coEvery { dao.getAll() } returns listOf(GameLevelInfoBd.fixture(level = 1))

        // When
        val result = sut.getLevelInfo(99)

        // Then
        expectThat(result.isFailure).isTrue()
        expectThat(result.exceptionOrNull()?.message).isEqualTo("Level not found")
    }

    @Test
    fun `given the DAO returns an empty list when getLevelInfo is called twice then DAO is hit on every call because the cache is never populated`() = runTest {
        // Given — documented current behaviour: empty DAO response does not populate the cache,
        // so each call re-hits the DAO. Pinned here to detect any unintended change.
        coEvery { dao.getAll() } returns emptyList()

        // When
        sut.getLevelInfo(1)
        sut.getLevelInfo(1)

        // Then
        coVerify(exactly = 2) { dao.getAll() }
    }
}
