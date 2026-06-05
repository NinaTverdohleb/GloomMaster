package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.settings

import app.cash.turbine.test
import com.rumpilstilstkin.gloomhavenhelper.data.LocaleRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.LanguageItem
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.containsExactly
import strikt.assertions.isEqualTo
import java.util.Locale

@OptIn(ExperimentalCoroutinesApi::class)
class LanguagesListUseCaseTest {
    private val localeRepository: LocaleRepository = mockk()

    private fun nativeName(tag: String): String =
        Locale.forLanguageTag(tag).let { it.getDisplayLanguage(it) }

    @Test
    fun `given app locale is en when invoked then the en item is selected and system is not`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        every { localeRepository.observeLocaleUnic } returns MutableStateFlow("en")
        every { localeRepository.isLanguageDefault } returns false
        every { localeRepository.avaliableLanguages } returns listOf("en", "ru")
        val sut = LanguagesListUseCase(localeRepository)

        // When / Then
        sut().test {
            val (currentLanguage, languages) = awaitItem()

            expectThat(currentLanguage).isEqualTo(nativeName("en"))
            expectThat(languages).containsExactly(
                LanguageItem(
                    languageTag = null,
                    languageName = "",
                    selected = false,
                ),
                LanguageItem(
                    languageTag = "en",
                    languageName = nativeName("en"),
                    selected = true,
                ),
                LanguageItem(
                    languageTag = "ru",
                    languageName = nativeName("ru"),
                    selected = false,
                ),
            )

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `given app locale is null when invoked then the system item is selected and no language item is`() = runTest(UnconfinedTestDispatcher()) {
        // Given the user has not overridden the language, observeLocale resolves to the system locale ("en")
        every { localeRepository.observeLocaleUnic } returns MutableStateFlow("en")
        every { localeRepository.isLanguageDefault } returns true
        every { localeRepository.avaliableLanguages } returns listOf("en", "ru")
        val sut = LanguagesListUseCase(localeRepository)

        // When / Then
        sut().test {
            val (_, languages) = awaitItem()

            expectThat(languages).containsExactly(
                LanguageItem(
                    languageTag = null,
                    languageName = "",
                    selected = true,
                ),
                LanguageItem(
                    languageTag = "en",
                    languageName = nativeName("en"),
                    selected = false,
                ),
                LanguageItem(
                    languageTag = "ru",
                    languageName = nativeName("ru"),
                    selected = false,
                ),
            )

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `given observeLocale changes when collected then a new list with the updated selection is emitted`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val localeFlow = MutableStateFlow("en")
        every { localeRepository.observeLocaleUnic } returns localeFlow
        every { localeRepository.isLanguageDefault } returns false
        every { localeRepository.avaliableLanguages } returns listOf("en", "ru")
        val sut = LanguagesListUseCase(localeRepository)

        // When / Then
        sut().test {
            val first = awaitItem()
            expectThat(first.first).isEqualTo(nativeName("en"))
            expectThat(first.second.single { it.languageTag == "en" }.selected).isEqualTo(true)
            expectThat(first.second.single { it.languageTag == "ru" }.selected).isEqualTo(false)

            localeFlow.value = "ru"

            val second = awaitItem()
            expectThat(second.first).isEqualTo(nativeName("ru"))
            expectThat(second.second.single { it.languageTag == "en" }.selected).isEqualTo(false)
            expectThat(second.second.single { it.languageTag == "ru" }.selected).isEqualTo(true)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `given empty available languages when invoked then only the system item is returned`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        every { localeRepository.observeLocaleUnic } returns MutableStateFlow("en")
        every { localeRepository.isLanguageDefault } returns true
        every { localeRepository.avaliableLanguages } returns emptyList()
        val sut = LanguagesListUseCase(localeRepository)

        // When / Then
        sut().test {
            val (_, languages) = awaitItem()

            expectThat(languages).containsExactly(
                LanguageItem(
                    languageTag = null,
                    languageName = "",
                    selected = true,
                ),
            )

            cancelAndIgnoreRemainingEvents()
        }
    }
}
