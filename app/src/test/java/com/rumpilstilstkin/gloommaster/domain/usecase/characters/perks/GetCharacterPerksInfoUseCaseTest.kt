package com.rumpilstilstkin.gloommaster.domain.usecase.characters.perks

import app.cash.turbine.test
import com.rumpilstilstkin.gloommaster.data.CharacterRepository
import com.rumpilstilstkin.gloommaster.data.LocaleRepository
import com.rumpilstilstkin.gloommaster.data.PerksRepository
import com.rumpilstilstkin.gloommaster.domain.entity.CharacterClassType
import com.rumpilstilstkin.gloommaster.domain.entity.CharacterInfo
import com.rumpilstilstkin.gloommaster.domain.entity.Perk
import com.rumpilstilstkin.gloommaster.domain.usecase.characters.perks.GetCharacterPerksInfoUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.containsExactly
import strikt.assertions.isEmpty
import strikt.assertions.isEqualTo

@OptIn(ExperimentalCoroutinesApi::class)
class GetCharacterPerksInfoUseCaseTest {
    private val characterRepository: CharacterRepository = mockk()
    private val perksRepository: PerksRepository = mockk()
    private val localeRepository: LocaleRepository = mockk()

    @Test
    fun `given character and perks when invoked then info contains acquired plus filtered avaliable plus count`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val character = characterInfo(level = 3, checkMarkCount = 6, additionalContOfPerks = 1)
        val acquired = listOf(Perk(id = 1, text = "one"))
        val all = listOf(
            Perk(id = 1, text = "one"),
            Perk(id = 2, text = "two"),
            Perk(id = 3, text = "three"),
        )
        every { localeRepository.observeLocale } returns flowOf("en")
        every { characterRepository.getCharacterByIdFlow(5) } returns flowOf(character)
        every { characterRepository.getCharacterPerksFlow(5, "en") } returns flowOf(acquired)
        coEvery { perksRepository.getPerksForCharacterClass(CharacterClassType.Brute, "en") } returns all
        val sut =
            GetCharacterPerksInfoUseCase(characterRepository, perksRepository, localeRepository)

        // When / Then — allCount = level + (checkMarks/3) - 1 = 3 + 2 - 1 = 4
        // availablePerksCount = max(0, 4 - 1 + 1) = 4
        sut(5).test {
            val info = awaitItem()
            expectThat(info.characterPerks).containsExactly(Perk(id = 1, text = "one"))
            expectThat(info.avaliablePerks).containsExactly(Perk(id = 2, text = "two"), Perk(id = 3, text = "three"))
            expectThat(info.avaliablePerksCount).isEqualTo(4)
            awaitComplete()
        }
    }

    @Test
    fun `given null character when invoked then returns empty info`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        every { localeRepository.observeLocale } returns flowOf("en")
        every { characterRepository.getCharacterByIdFlow(5) } returns flowOf(null)
        every { characterRepository.getCharacterPerksFlow(5, "en") } returns flowOf(emptyList())
        val sut =
            GetCharacterPerksInfoUseCase(characterRepository, perksRepository, localeRepository)

        // When / Then
        sut(5).test {
            val info = awaitItem()
            expectThat(info.characterPerks).isEmpty()
            expectThat(info.avaliablePerks).isEmpty()
            expectThat(info.avaliablePerksCount).isEqualTo(0)
            awaitComplete()
        }
    }

    private fun characterInfo(
        level: Int,
        checkMarkCount: Int,
        additionalContOfPerks: Int,
    ) = CharacterInfo(
        name = "Brute",
        level = level,
        characterType = CharacterClassType.Brute,
        isAlive = true,
        id = 5,
        team = null,
        experience = 0,
        goldCount = 0,
        checkMarkCount = checkMarkCount,
        notes = "",
        additionalContOfPerks = additionalContOfPerks,
    )
}
