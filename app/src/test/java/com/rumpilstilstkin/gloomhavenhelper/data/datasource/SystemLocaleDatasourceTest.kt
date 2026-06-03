package com.rumpilstilstkin.gloomhavenhelper.data.datasource

import android.content.ComponentCallbacks2
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import androidx.core.os.ConfigurationCompat
import androidx.core.os.LocaleListCompat
import app.cash.turbine.test
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.slot
import io.mockk.unmockkStatic
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import java.util.Locale

@OptIn(ExperimentalCoroutinesApi::class)
class SystemLocaleDatasourceTest {
    private val context: Context = mockk(relaxed = true)
    private val resources: Resources = mockk()
    private val configuration: Configuration = mockk()
    private val sut = SystemLocaleDatasource(context)

    @Before
    fun setUp() {
        mockkStatic(ConfigurationCompat::class)
        every { context.resources } returns resources
        every { resources.configuration } returns configuration
    }

    @After
    fun tearDown() {
        unmockkStatic(ConfigurationCompat::class)
    }

    @Test
    fun `given system locale is ru when getSystemLocale then ru is returned`() {
        // Given
        every { ConfigurationCompat.getLocales(configuration) } returns LocaleListCompat.create(Locale.forLanguageTag("ru"))

        // When
        val value = sut.getSystemLocale()

        // Then
        expectThat(value).isEqualTo("ru")
    }

    @Test
    fun `given an empty LocaleList when getSystemLocale then platform default language is returned`() {
        // Given
        every { ConfigurationCompat.getLocales(configuration) } returns LocaleListCompat.getEmptyLocaleList()

        // When
        val value = sut.getSystemLocale()

        // Then
        expectThat(value).isEqualTo(Locale.getDefault().language)
    }

    @Test
    fun `given observeSystemLocale subscribed when collecting then the initial current locale is emitted`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        every { ConfigurationCompat.getLocales(configuration) } returns LocaleListCompat.create(Locale.forLanguageTag("ru"))
        every { context.registerComponentCallbacks(any()) } just Runs
        every { context.unregisterComponentCallbacks(any()) } just Runs

        // When / Then
        sut.observeSystemLocale().test {
            expectThat(awaitItem()).isEqualTo("ru")
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `given a config change when callbacks fire then a new locale emission is produced`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val callbacksSlot = slot<ComponentCallbacks2>()
        every { context.registerComponentCallbacks(capture(callbacksSlot)) } just Runs
        every { context.unregisterComponentCallbacks(any()) } just Runs
        every { ConfigurationCompat.getLocales(configuration) } returnsMany listOf(
            LocaleListCompat.create(Locale.forLanguageTag("ru")),
            LocaleListCompat.create(Locale.forLanguageTag("en")),
        )

        // When / Then
        sut.observeSystemLocale().test {
            expectThat(awaitItem()).isEqualTo("ru")
            callbacksSlot.captured.onConfigurationChanged(configuration)
            expectThat(awaitItem()).isEqualTo("en")
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `given two consecutive identical locales when callbacks fire then only one emission goes through`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val callbacksSlot = slot<ComponentCallbacks2>()
        every { context.registerComponentCallbacks(capture(callbacksSlot)) } just Runs
        every { context.unregisterComponentCallbacks(any()) } just Runs
        every { ConfigurationCompat.getLocales(configuration) } returns LocaleListCompat.create(Locale.forLanguageTag("ru"))

        // When / Then
        sut.observeSystemLocale().test {
            expectThat(awaitItem()).isEqualTo("ru")
            callbacksSlot.captured.onConfigurationChanged(configuration)
            callbacksSlot.captured.onConfigurationChanged(configuration)
            expectNoEvents()
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `given observeSystemLocale collection is cancelled when the flow closes then component callbacks are unregistered`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        every { ConfigurationCompat.getLocales(configuration) } returns LocaleListCompat.create(Locale.forLanguageTag("ru"))
        every { context.registerComponentCallbacks(any()) } just Runs
        every { context.unregisterComponentCallbacks(any()) } just Runs

        // When
        sut.observeSystemLocale().test {
            awaitItem()
            cancelAndIgnoreRemainingEvents()
        }

        // Then
        verify { context.unregisterComponentCallbacks(any()) }
    }
}
