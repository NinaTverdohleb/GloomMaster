package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.goods

import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEmpty
import strikt.assertions.isEqualTo

class GetGoodNumbersForLevelUseCaseTest {
    private val sut = GetGoodNumbersForLevelUseCase()

    @Test
    fun `given level 1 when invoked then returns 1 to 14`() {
        expectThat(sut(1)).isEqualTo((1..14).toList())
    }

    @Test
    fun `given level 2 when invoked then returns 15 to 21`() {
        expectThat(sut(2)).isEqualTo((15..21).toList())
    }

    @Test
    fun `given level 3 when invoked then returns 22 to 28`() {
        expectThat(sut(3)).isEqualTo((22..28).toList())
    }

    @Test
    fun `given level 4 when invoked then returns 29 to 35`() {
        expectThat(sut(4)).isEqualTo((29..35).toList())
    }

    @Test
    fun `given level 5 when invoked then returns 36 to 42`() {
        expectThat(sut(5)).isEqualTo((36..42).toList())
    }

    @Test
    fun `given level 6 when invoked then returns 43 to 49`() {
        expectThat(sut(6)).isEqualTo((43..49).toList())
    }

    @Test
    fun `given level 7 when invoked then returns 50 to 56`() {
        expectThat(sut(7)).isEqualTo((50..56).toList())
    }

    @Test
    fun `given level 8 when invoked then returns 57 to 63`() {
        expectThat(sut(8)).isEqualTo((57..63).toList())
    }

    @Test
    fun `given level 9 when invoked then returns 64 to 70`() {
        expectThat(sut(9)).isEqualTo((64..70).toList())
    }

    @Test
    fun `given level 0 when invoked then returns empty`() {
        expectThat(sut(0)).isEmpty()
    }

    @Test
    fun `given level 10 when invoked then returns empty`() {
        expectThat(sut(10)).isEmpty()
    }
}
