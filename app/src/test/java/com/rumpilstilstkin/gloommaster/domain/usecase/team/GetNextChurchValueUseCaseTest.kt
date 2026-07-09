package com.rumpilstilstkin.gloommaster.domain.usecase.team

import com.rumpilstilstkin.gloommaster.domain.usecase.team.GetNextChurchValueUseCase
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class GetNextChurchValueUseCaseTest {
    private val sut = GetNextChurchValueUseCase()

    @Test
    fun `given value below 150 when invoked then returns 150`() {
        expectThat(sut(0)).isEqualTo(150)
        expectThat(sut(149)).isEqualTo(150)
    }

    @Test
    fun `given value 150 when invoked then returns 200`() {
        expectThat(sut(150)).isEqualTo(200)
    }

    @Test
    fun `given value 199 when invoked then returns 200`() {
        expectThat(sut(199)).isEqualTo(200)
    }

    @Test
    fun `given value 250 when invoked then returns 300`() {
        expectThat(sut(250)).isEqualTo(300)
    }

    @Test
    fun `given value 350 when invoked then returns 400`() {
        expectThat(sut(350)).isEqualTo(400)
    }

    @Test
    fun `given value 400 when invoked then returns 500`() {
        expectThat(sut(400)).isEqualTo(500)
    }

    @Test
    fun `given value 600 when invoked then returns 700`() {
        expectThat(sut(600)).isEqualTo(700)
    }

    @Test
    fun `given value 900 when invoked then returns 1000`() {
        expectThat(sut(900)).isEqualTo(1000)
    }

    @Test
    fun `given value 1000 or above when invoked then returns -1`() {
        expectThat(sut(1000)).isEqualTo(-1)
        expectThat(sut(9999)).isEqualTo(-1)
    }
}
