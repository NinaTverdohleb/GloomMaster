package com.rumpilstilstkin.gloommaster.data.datasource

import android.content.SharedPreferences
import com.rumpilstilstkin.gloommaster.data.datasource.OnboardingDatasource
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isFalse
import strikt.assertions.isTrue

class OnboardingDatasourceTest {
    private val editor: SharedPreferences.Editor = mockk(relaxed = true)
    private val preference: SharedPreferences =
        mockk {
            every { edit() } returns editor
        }
    private val sut = OnboardingDatasource(preference)

    @Test
    fun `given flag stored as true when isOnboardingShown is read then it is true`() {
        // Given
        every { preference.getBoolean(ONBOARDING_SHOWN_KEY, false) } returns true

        // When / Then
        expectThat(sut.isOnboardingShown).isTrue()
    }

    @Test
    fun `given no stored flag when isOnboardingShown is read then it defaults to false`() {
        // Given
        every { preference.getBoolean(ONBOARDING_SHOWN_KEY, false) } returns false

        // When / Then
        expectThat(sut.isOnboardingShown).isFalse()
    }

    @Test
    fun `given true when isOnboardingShown is set then true is written under the onboarding key`() {
        // When
        sut.isOnboardingShown = true

        // Then
        verify { editor.putBoolean(ONBOARDING_SHOWN_KEY, true) }
    }

    @Test
    fun `given false when isOnboardingShown is set then false is written under the onboarding key`() {
        // When
        sut.isOnboardingShown = false

        // Then
        verify { editor.putBoolean(ONBOARDING_SHOWN_KEY, false) }
    }

    private companion object {
        const val ONBOARDING_SHOWN_KEY = "onboardingShownKey"
    }
}
