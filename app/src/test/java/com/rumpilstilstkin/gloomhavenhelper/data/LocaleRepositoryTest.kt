package com.rumpilstilstkin.gloomhavenhelper.data

import app.cash.turbine.test
import com.rumpilstilstkin.gloomhavenhelper.data.datasource.LocaleDatasource
import com.rumpilstilstkin.gloomhavenhelper.data.datasource.SystemLocaleDatasource
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

@OptIn(ExperimentalCoroutinesApi::class)
class LocaleRepositoryTest {
    private val systemDs: SystemLocaleDatasource = mockk()
    private val appDs: LocaleDatasource = mockk(relaxUnitFun = true)

    @Test
    fun `given appLocale is null when observeLocale emits then it falls back to the system locale`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        every { systemDs.observeSystemLocale() } returns MutableStateFlow("ru")
        every { appDs.observeAppLocale() } returns MutableStateFlow<String?>(null)
        val sut = LocaleRepository(systemDs, appDs)

        // When / Then
        sut.observeLocaleUnic.test {
            expectThat(awaitItem()).isEqualTo("ru")
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `given appLocale is set when observeLocale emits then appLocale overrides the system locale`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        every { systemDs.observeSystemLocale() } returns MutableStateFlow("en")
        every { appDs.observeAppLocale() } returns MutableStateFlow<String?>("ru")
        val sut = LocaleRepository(systemDs, appDs)

        // When / Then
        sut.observeLocaleUnic.test {
            expectThat(awaitItem()).isEqualTo("ru")
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `given appLocale changes from null to ru when observeLocale is collected then two emissions are produced`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val appFlow = MutableStateFlow<String?>(null)
        every { systemDs.observeSystemLocale() } returns MutableStateFlow("en")
        every { appDs.observeAppLocale() } returns appFlow
        val sut = LocaleRepository(systemDs, appDs)

        // When / Then
        sut.observeLocaleUnic.test {
            expectThat(awaitItem()).isEqualTo("en")
            appFlow.value = "ru"
            expectThat(awaitItem()).isEqualTo("ru")
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `given identical resolved locales when observeLocale is collected then distinctUntilChanged dedupes them`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val systemFlow = MutableStateFlow("ru")
        every { systemDs.observeSystemLocale() } returns systemFlow
        every { appDs.observeAppLocale() } returns MutableStateFlow<String?>(null)
        val sut = LocaleRepository(systemDs, appDs)

        // When / Then
        sut.observeLocaleUnic.test {
            expectThat(awaitItem()).isEqualTo("ru")
            systemFlow.value = "ru"
            expectNoEvents()
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `given appLocale is set when getCurrentLocale is called then appLocale is returned`() = runTest {
        // Given
        every { systemDs.observeSystemLocale() } returns MutableStateFlow("en")
        every { appDs.observeAppLocale() } returns MutableStateFlow<String?>(null)
        every { appDs.locale } returns "ru"
        val sut = LocaleRepository(systemDs, appDs)

        // When
        val locale = sut.getCurrentLocale()

        // Then
        expectThat(locale).isEqualTo("ru")
    }

    @Test
    fun `given appLocale is null when getCurrentLocale is called then the system locale is returned`() = runTest {
        // Given
        every { systemDs.observeSystemLocale() } returns MutableStateFlow("en")
        every { appDs.observeAppLocale() } returns MutableStateFlow<String?>(null)
        every { appDs.locale } returns null
        every { systemDs.getSystemLocale() } returns "en"
        val sut = LocaleRepository(systemDs, appDs)

        // When
        val locale = sut.getCurrentLocale()

        // Then
        expectThat(locale).isEqualTo("en")
    }

    @Test
    fun `given a locale string when setAppLocale is called then it is written through to the app locale datasource`() = runTest {
        // Given
        every { systemDs.observeSystemLocale() } returns MutableStateFlow("en")
        every { appDs.observeAppLocale() } returns MutableStateFlow<String?>(null)
        justRun { appDs.locale = any() }
        val sut = LocaleRepository(systemDs, appDs)

        // When
        sut.setAppLocale("ru")

        // Then
        verify { appDs.locale = "ru" }
    }
}
