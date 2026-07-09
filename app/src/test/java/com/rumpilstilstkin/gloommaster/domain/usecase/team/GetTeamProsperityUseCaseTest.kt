package com.rumpilstilstkin.gloommaster.domain.usecase.team

import com.rumpilstilstkin.gloommaster.domain.usecase.team.GetTeamProsperityUseCase
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isFalse
import strikt.assertions.isTrue

class GetTeamProsperityUseCaseTest {
    private val sut = GetTeamProsperityUseCase()

    @Test
    fun `given prosperity 0 when invoked then level 1 with isStartValue true`() {
        // Given / When
        val result = sut(0)

        // Then
        expectThat(result.prosperityLevel).isEqualTo(1)
        expectThat(result.prosperityLevelValue).isEqualTo(0)
        expectThat(result.prosperityRange).isEqualTo(IntRange(0, 3))
        expectThat(result.isStartValue).isTrue()
        expectThat(result.isMax).isFalse()
    }

    @Test
    fun `given prosperity 3 when invoked then level 1 boundary`() {
        val result = sut(3)
        expectThat(result.prosperityLevel).isEqualTo(1)
        expectThat(result.prosperityLevelValue).isEqualTo(3)
        expectThat(result.isStartValue).isFalse()
    }

    @Test
    fun `given prosperity 4 when invoked then level 2 value 0`() {
        val result = sut(4)
        expectThat(result.prosperityLevel).isEqualTo(2)
        expectThat(result.prosperityLevelValue).isEqualTo(0)
        expectThat(result.prosperityRange).isEqualTo(IntRange(0, 4))
    }

    @Test
    fun `given prosperity 9 when invoked then level 3 value 0`() {
        val result = sut(9)
        expectThat(result.prosperityLevel).isEqualTo(3)
        expectThat(result.prosperityLevelValue).isEqualTo(0)
    }

    @Test
    fun `given prosperity 15 when invoked then level 4 value 0`() {
        val result = sut(15)
        expectThat(result.prosperityLevel).isEqualTo(4)
        expectThat(result.prosperityLevelValue).isEqualTo(0)
    }

    @Test
    fun `given prosperity 22 when invoked then level 5 value 0`() {
        val result = sut(22)
        expectThat(result.prosperityLevel).isEqualTo(5)
        expectThat(result.prosperityLevelValue).isEqualTo(0)
    }

    @Test
    fun `given prosperity 30 when invoked then level 6 value 0`() {
        val result = sut(30)
        expectThat(result.prosperityLevel).isEqualTo(6)
        expectThat(result.prosperityLevelValue).isEqualTo(0)
    }

    @Test
    fun `given prosperity 39 when invoked then level 7 value 0`() {
        val result = sut(39)
        expectThat(result.prosperityLevel).isEqualTo(7)
        expectThat(result.prosperityLevelValue).isEqualTo(0)
    }

    @Test
    fun `given prosperity 50 when invoked then level 8 value 0`() {
        val result = sut(50)
        expectThat(result.prosperityLevel).isEqualTo(8)
        expectThat(result.prosperityLevelValue).isEqualTo(0)
        expectThat(result.isMax).isFalse()
    }

    @Test
    fun `given prosperity 63 when invoked then level 8 boundary`() {
        val result = sut(63)
        expectThat(result.prosperityLevel).isEqualTo(8)
        expectThat(result.prosperityLevelValue).isEqualTo(13)
    }

    @Test
    fun `given prosperity 64 when invoked then level 9 with isMax true`() {
        val result = sut(64)
        expectThat(result.prosperityLevel).isEqualTo(9)
        expectThat(result.prosperityLevelValue).isEqualTo(0)
        expectThat(result.prosperityRange).isEqualTo(IntRange(0, 0))
        expectThat(result.isMax).isTrue()
    }
}
