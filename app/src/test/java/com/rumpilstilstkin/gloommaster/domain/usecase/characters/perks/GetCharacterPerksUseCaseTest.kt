package com.rumpilstilstkin.gloommaster.domain.usecase.characters.perks

import app.cash.turbine.test
import com.rumpilstilstkin.gloommaster.data.CharacterRepository
import com.rumpilstilstkin.gloommaster.data.LocaleRepository
import com.rumpilstilstkin.gloommaster.domain.entity.Perk
import com.rumpilstilstkin.gloommaster.domain.usecase.characters.perks.GetCharacterPerksUseCase
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.containsExactly

@OptIn(ExperimentalCoroutinesApi::class)
class GetCharacterPerksUseCaseTest {
    private val characterRepository: CharacterRepository = mockk()
    private val localeRepository: LocaleRepository = mockk()

    @Test
    fun `given current locale when invoked then forwards repo perks flow for character`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val perks = listOf(Perk(id = 1, text = "alpha"), Perk(id = 2, text = "beta"))
        every { localeRepository.observeLocale } returns flowOf("ru")
        every { characterRepository.getCharacterPerksFlow(5, "ru") } returns flowOf(perks)
        val sut = GetCharacterPerksUseCase(characterRepository, localeRepository)

        // When / Then
        sut(5).test {
            expectThat(awaitItem()).containsExactly(Perk(id = 1, text = "alpha"), Perk(id = 2, text = "beta"))
            awaitComplete()
        }
    }
}
