package com.rumpilstilstkin.gloommaster.data

import com.rumpilstilstkin.gloommaster.bd.dao.PerksDao
import com.rumpilstilstkin.gloommaster.bd.entity.PerkTranslationBd
import com.rumpilstilstkin.gloommaster.domain.entity.CharacterClassType
import com.rumpilstilstkin.gloommaster.data.PerksRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.hasSize
import strikt.assertions.isEmpty
import strikt.assertions.isEqualTo

@OptIn(ExperimentalCoroutinesApi::class)
class PerksRepositoryTest {
    private val dao: PerksDao = mockk()
    private val sut = PerksRepository(dao)

    @Test
    fun `given DAO returns two translations when getPerksForCharacterClass then mapped Perks are returned and target+default locales are passed through`() = runTest {
        // Given
        coEvery {
            dao.getPerksByCharacterClass(
                characterType = "Brute",
                targetLocale = "ru",
                defaultLocale = "en",
            )
        } returns listOf(
            translationFixture(perkId = 1, text = "Add two +1 cards"),
            translationFixture(perkId = 2, text = "Remove four +0 cards"),
        )

        // When
        val perks = sut.getPerksForCharacterClass(
            characterType = CharacterClassType.Brute,
            locale = "ru",
        )

        // Then
        expectThat(perks).hasSize(2)
        expectThat(perks.map { it.id }).isEqualTo(listOf(1, 2))
        expectThat(perks.map { it.text }).isEqualTo(listOf("Add two +1 cards", "Remove four +0 cards"))
        coVerify(exactly = 1) {
            dao.getPerksByCharacterClass(
                characterType = "Brute",
                targetLocale = "ru",
                defaultLocale = "en",
            )
        }
    }

    @Test
    fun `given DAO returns an empty list when getPerksForCharacterClass then the result is an empty list`() = runTest {
        // Given
        coEvery {
            dao.getPerksByCharacterClass(
                characterType = "Spellweaver",
                targetLocale = "ru",
                defaultLocale = "en",
            )
        } returns emptyList()

        // When
        val perks = sut.getPerksForCharacterClass(
            characterType = CharacterClassType.Spellweaver,
            locale = "ru",
        )

        // Then
        expectThat(perks).isEmpty()
    }

    companion object {
        fun translationFixture(
            perkId: Int = 1,
            locale: String = "ru",
            text: String = "text",
            characterType: String = "Brute",
        ): PerkTranslationBd = PerkTranslationBd(
            perkId = perkId,
            locale = locale,
            text = text,
            characterType = characterType,
        )
    }
}
