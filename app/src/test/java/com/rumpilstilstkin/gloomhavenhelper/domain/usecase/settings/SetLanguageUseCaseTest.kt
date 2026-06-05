package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.settings

import com.rumpilstilstkin.gloomhavenhelper.data.LocaleRepository
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SetLanguageUseCaseTest {
    private val localeRepository: LocaleRepository = mockk()

    @Test
    fun `given a language tag when invoked then localeRepository setAppLocale is called with the same tag`() = runTest {
        // Given
        justRun { localeRepository.setAppLocale(any()) }
        val sut = SetLanguageUseCase(localeRepository)

        // When
        sut("ru")

        // Then
        verify(exactly = 1) { localeRepository.setAppLocale("ru") }
    }

    @Test
    fun `given a null language tag when invoked then localeRepository setAppLocale is called with null to reset to system`() = runTest {
        // Given
        justRun { localeRepository.setAppLocale(any()) }
        val sut = SetLanguageUseCase(localeRepository)

        // When
        sut(null)

        // Then
        verify(exactly = 1) { localeRepository.setAppLocale(null) }
    }

    @Test
    fun `given multiple invocations when invoked then every call is forwarded to the repository in order`() = runTest {
        // Given
        justRun { localeRepository.setAppLocale(any()) }
        val sut = SetLanguageUseCase(localeRepository)

        // When
        sut("en")
        sut("ru")
        sut(null)

        // Then
        verify(exactly = 1) { localeRepository.setAppLocale("en") }
        verify(exactly = 1) { localeRepository.setAppLocale("ru") }
        verify(exactly = 1) { localeRepository.setAppLocale(null) }
    }
}
