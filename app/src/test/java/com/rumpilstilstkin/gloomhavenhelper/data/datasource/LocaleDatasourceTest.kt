package com.rumpilstilstkin.gloomhavenhelper.data.datasource

import android.content.SharedPreferences
import app.cash.turbine.test
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNull

@OptIn(ExperimentalCoroutinesApi::class)
class LocaleDatasourceTest {
    private val prefs: SharedPreferences = mockk(relaxUnitFun = true)
    private val editor: SharedPreferences.Editor = mockk(relaxed = true)
    private val sut = LocaleDatasource(prefs)

    @Test
    fun `given prefs has a stored locale when reading locale then the stored string is returned`() {
        // Given
        every { prefs.getString(APP_LOCALE_KEY, null) } returns "ru"

        // When
        val value = sut.locale

        // Then
        expectThat(value).isEqualTo("ru")
    }

    @Test
    fun `given prefs has no stored locale when reading locale then null is returned`() {
        // Given
        every { prefs.getString(APP_LOCALE_KEY, null) } returns null

        // When
        val value = sut.locale

        // Then
        expectThat(value).isNull()
    }

    @Test
    fun `given a non-null locale when writing it then prefs editor receives putString with that value`() {
        // Given
        every { prefs.edit() } returns editor

        // When
        sut.locale = "ru"

        // Then
        verify { editor.putString(APP_LOCALE_KEY, "ru") }
    }

    @Test
    fun `given a null locale when writing it then prefs editor receives remove for APP_LOCALE_KEY`() {
        // Given
        every { prefs.edit() } returns editor

        // When
        sut.locale = null

        // Then
        verify { editor.remove(APP_LOCALE_KEY) }
    }

    @Test
    fun `given observeAppLocale subscribed when collecting then the initial current locale is emitted`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        every { prefs.getString(APP_LOCALE_KEY, null) } returns "ru"
        every { prefs.registerOnSharedPreferenceChangeListener(any()) } just Runs
        every { prefs.unregisterOnSharedPreferenceChangeListener(any()) } just Runs

        // When / Then
        sut.observeAppLocale().test {
            expectThat(awaitItem()).isEqualTo("ru")
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `given APP_LOCALE_KEY changes when listener fires then a new emission is produced`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val listenerSlot = slot<SharedPreferences.OnSharedPreferenceChangeListener>()
        every { prefs.registerOnSharedPreferenceChangeListener(capture(listenerSlot)) } just Runs
        every { prefs.unregisterOnSharedPreferenceChangeListener(any()) } just Runs
        every { prefs.getString(APP_LOCALE_KEY, null) } returnsMany listOf("ru", "en")

        // When / Then
        sut.observeAppLocale().test {
            expectThat(awaitItem()).isEqualTo("ru")
            listenerSlot.captured.onSharedPreferenceChanged(prefs, APP_LOCALE_KEY)
            expectThat(awaitItem()).isEqualTo("en")
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `given a different prefs key changes when listener fires then no new emission is produced`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val listenerSlot = slot<SharedPreferences.OnSharedPreferenceChangeListener>()
        every { prefs.registerOnSharedPreferenceChangeListener(capture(listenerSlot)) } just Runs
        every { prefs.unregisterOnSharedPreferenceChangeListener(any()) } just Runs
        every { prefs.getString(APP_LOCALE_KEY, null) } returns "ru"

        // When / Then
        sut.observeAppLocale().test {
            expectThat(awaitItem()).isEqualTo("ru")
            listenerSlot.captured.onSharedPreferenceChanged(prefs, "someOtherKey")
            expectNoEvents()
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `given two consecutive identical locale values when listener fires twice then only one emission goes through`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val listenerSlot = slot<SharedPreferences.OnSharedPreferenceChangeListener>()
        every { prefs.registerOnSharedPreferenceChangeListener(capture(listenerSlot)) } just Runs
        every { prefs.unregisterOnSharedPreferenceChangeListener(any()) } just Runs
        every { prefs.getString(APP_LOCALE_KEY, null) } returns "ru"

        // When / Then
        sut.observeAppLocale().test {
            expectThat(awaitItem()).isEqualTo("ru")
            listenerSlot.captured.onSharedPreferenceChanged(prefs, APP_LOCALE_KEY)
            listenerSlot.captured.onSharedPreferenceChanged(prefs, APP_LOCALE_KEY)
            expectNoEvents()
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `given observeAppLocale collection is cancelled when the flow closes then the listener is unregistered`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        every { prefs.getString(APP_LOCALE_KEY, null) } returns "ru"
        every { prefs.registerOnSharedPreferenceChangeListener(any()) } just Runs
        every { prefs.unregisterOnSharedPreferenceChangeListener(any()) } just Runs

        // When
        sut.observeAppLocale().test {
            awaitItem()
            cancelAndIgnoreRemainingEvents()
        }

        // Then
        verify { prefs.unregisterOnSharedPreferenceChangeListener(any()) }
    }

    companion object {
        private const val APP_LOCALE_KEY = "appLocaleKey"
    }
}
