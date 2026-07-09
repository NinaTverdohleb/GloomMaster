package com.rumpilstilstkin.gloommaster.domain.usecase.team

import com.rumpilstilstkin.gloommaster.domain.usecase.team.GetDiscountByReputationUseCase
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class GetDiscountByReputationUseCaseTest {
    private val sut = GetDiscountByReputationUseCase()

    @Test
    fun `given reputation -20 when invoked then discount 5`() {
        expectThat(sut(-20)).isEqualTo(5)
    }

    @Test
    fun `given reputation -15 when invoked then discount 4`() {
        expectThat(sut(-15)).isEqualTo(4)
    }

    @Test
    fun `given reputation -11 when invoked then discount 3`() {
        expectThat(sut(-11)).isEqualTo(3)
    }

    @Test
    fun `given reputation -7 when invoked then discount 2`() {
        expectThat(sut(-7)).isEqualTo(2)
    }

    @Test
    fun `given reputation -3 when invoked then discount 1`() {
        expectThat(sut(-3)).isEqualTo(1)
    }

    @Test
    fun `given reputation 0 when invoked then discount 0`() {
        expectThat(sut(0)).isEqualTo(0)
    }

    @Test
    fun `given reputation 3 when invoked then discount -1`() {
        expectThat(sut(3)).isEqualTo(-1)
    }

    @Test
    fun `given reputation 7 when invoked then discount -2`() {
        expectThat(sut(7)).isEqualTo(-2)
    }

    @Test
    fun `given reputation 11 when invoked then discount -3`() {
        expectThat(sut(11)).isEqualTo(-3)
    }

    @Test
    fun `given reputation 15 when invoked then discount -4`() {
        expectThat(sut(15)).isEqualTo(-4)
    }

    @Test
    fun `given reputation 20 when invoked then discount -5`() {
        expectThat(sut(20)).isEqualTo(-5)
    }

    @Test
    fun `given reputation out of range when invoked then discount 0`() {
        expectThat(sut(100)).isEqualTo(0)
        expectThat(sut(-100)).isEqualTo(0)
    }
}
