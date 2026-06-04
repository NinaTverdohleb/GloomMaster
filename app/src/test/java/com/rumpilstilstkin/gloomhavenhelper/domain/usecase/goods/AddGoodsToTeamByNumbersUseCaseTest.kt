package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.goods

import com.rumpilstilstkin.gloomhavenhelper.data.GoodsRepository
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.containsExactlyInAnyOrder
import strikt.assertions.isEmpty

@OptIn(ExperimentalCoroutinesApi::class)
class AddGoodsToTeamByNumbersUseCaseTest {
    private val goodsRepository: GoodsRepository = mockk()

    @Test
    fun `given matching numbers when invoked then addGoodsToTeam called with flattened ids`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        coEvery { goodsRepository.getGoodIdsByNumbers(listOf(1, 2)) } returns
            mapOf(1 to listOf(100), 2 to listOf(200, 201))
        val capturedIds = slot<List<Int>>()
        coJustRun { goodsRepository.addGoodsToTeam(teamId = 5, goodIds = capture(capturedIds)) }
        val sut = AddGoodsToTeamByNumbersUseCase(goodsRepository)

        // When
        sut(teamId = 5, goodNumbers = listOf(1, 2))

        // Then
        expectThat(capturedIds.captured).containsExactlyInAnyOrder(100, 200, 201)
    }

    @Test
    fun `given empty input when invoked then addGoodsToTeam is called with empty list`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        coEvery { goodsRepository.getGoodIdsByNumbers(emptyList()) } returns emptyMap()
        val capturedIds = slot<List<Int>>()
        coJustRun { goodsRepository.addGoodsToTeam(teamId = 5, goodIds = capture(capturedIds)) }
        val sut = AddGoodsToTeamByNumbersUseCase(goodsRepository)

        // When
        sut(teamId = 5, goodNumbers = emptyList())

        // Then — implementation always calls addGoodsToTeam (no short-circuit); list is empty
        expectThat(capturedIds.captured).isEmpty()
        coVerify(exactly = 1) { goodsRepository.addGoodsToTeam(teamId = 5, goodIds = any()) }
    }

    @Test
    fun `given partial matches when invoked then only matched numbers' ids are forwarded`() = runTest(UnconfinedTestDispatcher()) {
        // Given — number 999 has no match (absent from map)
        coEvery { goodsRepository.getGoodIdsByNumbers(listOf(1, 999)) } returns
            mapOf(1 to listOf(100))
        val capturedIds = slot<List<Int>>()
        coJustRun { goodsRepository.addGoodsToTeam(teamId = 5, goodIds = capture(capturedIds)) }
        val sut = AddGoodsToTeamByNumbersUseCase(goodsRepository)

        // When
        sut(teamId = 5, goodNumbers = listOf(1, 999))

        // Then
        expectThat(capturedIds.captured).containsExactlyInAnyOrder(100)
    }
}
