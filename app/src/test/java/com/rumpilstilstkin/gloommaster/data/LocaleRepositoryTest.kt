package com.rumpilstilstkin.gloommaster.data

import app.cash.turbine.test
import com.rumpilstilstkin.gloommaster.data.datasource.LocaleDatasource
import com.rumpilstilstkin.gloommaster.data.datasource.SystemLocaleDatasource
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.containsExactly
import strikt.assertions.isEqualTo
import strikt.assertions.isFalse
import strikt.assertions.isNull
import strikt.assertions.isTrue

@OptIn(ExperimentalCoroutinesApi::class)
class LocaleRepositoryTest {
    private val systemDs: SystemLocaleDatasource = mockk()
    private val appDs: LocaleDatasource = mockk(relaxUnitFun = true)

    private fun TestScope.newSut(): LocaleRepository =
        LocaleRepository(
            systemLocaleDataSource = systemDs,
            appLocaleDataSource = appDs,
            externalScope = TestScope(UnconfinedTestDispatcher(testScheduler)),
        )

    @Test
    fun `given appLocale is null when observeLocale emits then it falls back to the system locale`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        every { systemDs.observeSystemLocale() } returns MutableStateFlow("ru")
        every { appDs.observeAppLocale() } returns MutableStateFlow<String?>(null)
        val sut = newSut()

        // When / Then
        sut.observeLocale.test {
            expectThat(awaitItem()).isEqualTo("ru")
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `given appLocale is set when observeLocale emits then appLocale overrides the system locale`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        every { systemDs.observeSystemLocale() } returns MutableStateFlow("en")
        every { appDs.observeAppLocale() } returns MutableStateFlow<String?>("ru")
        val sut = newSut()

        // When / Then
        sut.observeLocale.test {
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
        val sut = newSut()

        // When / Then
        sut.observeLocale.test {
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
        val sut = newSut()

        // When / Then
        sut.observeLocale.test {
            expectThat(awaitItem()).isEqualTo("ru")
            systemFlow.value = "ru"
            expectNoEvents()
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `given appLocale is set when observeAppLocale is collected then the selected tag is emitted`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        every { systemDs.observeSystemLocale() } returns MutableStateFlow("en")
        every { appDs.observeAppLocale() } returns MutableStateFlow<String?>("ru")
        val sut = newSut()

        // When / Then
        sut.observeAppLocale.test {
            expectThat(awaitItem()).isEqualTo("ru")
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `given appLocale is null when observeAppLocale is collected then system language selection is preserved`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        every { systemDs.observeSystemLocale() } returns MutableStateFlow("en")
        every { appDs.observeAppLocale() } returns MutableStateFlow<String?>(null)
        val sut = newSut()

        // When / Then
        sut.observeAppLocale.test {
            expectThat(awaitItem()).isNull()
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `given selected and system languages match when selection changes then observeAppLocale emits each selection`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val appFlow = MutableStateFlow<String?>(null)
        every { systemDs.observeSystemLocale() } returns MutableStateFlow("ru")
        every { appDs.observeAppLocale() } returns appFlow
        val sut = newSut()

        // When / Then
        sut.observeAppLocale.test {
            expectThat(awaitItem()).isNull()
            appFlow.value = "ru"
            expectThat(awaitItem()).isEqualTo("ru")
            appFlow.value = null
            expectThat(awaitItem()).isNull()
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `given appLocale is set when getCurrentLocale is called then appLocale is returned`() = runTest {
        // Given
        every { systemDs.observeSystemLocale() } returns MutableStateFlow("en")
        every { appDs.observeAppLocale() } returns MutableStateFlow<String?>(null)
        every { appDs.locale } returns "ru"
        val sut = newSut()

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
        val sut = newSut()

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
        val sut = newSut()

        // When
        sut.setAppLocale("ru")

        // Then
        verify { appDs.locale = "ru" }
    }

    @Test
    fun `given appLocale is null when isLanguageDefault is read then it is true`() = runTest {
        // Given
        every { systemDs.observeSystemLocale() } returns MutableStateFlow("en")
        every { appDs.observeAppLocale() } returns MutableStateFlow<String?>(null)
        every { appDs.locale } returns null
        val sut = newSut()

        // When / Then
        expectThat(sut.isLanguageDefault).isTrue()
    }

    @Test
    fun `given appLocale is set when isLanguageDefault is read then it is false`() = runTest {
        // Given
        every { systemDs.observeSystemLocale() } returns MutableStateFlow("en")
        every { appDs.observeAppLocale() } returns MutableStateFlow<String?>(null)
        every { appDs.locale } returns "ru"
        val sut = newSut()

        // When / Then
        expectThat(sut.isLanguageDefault).isFalse()
    }

    @Test
    fun `availableLanguages exposes the supported language tags`() = runTest {
        // Given
        every { systemDs.observeSystemLocale() } returns MutableStateFlow("en")
        every { appDs.observeAppLocale() } returns MutableStateFlow<String?>(null)
        val sut = newSut()

        // When / Then
        expectThat(sut.availableLanguages).containsExactly("en", "ru")
    }
}
