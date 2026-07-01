package com.rumpilstilstkin.gloomhavenhelper.data

import com.rumpilstilstkin.gloomhavenhelper.data.datasource.OnboardingDatasource
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isFalse
import strikt.assertions.isTrue

class OnboardingRepositoryTest {
    private val datasource: OnboardingDatasource = mockk(relaxUnitFun = true)

    private fun newSut(onboardingEnabled: Boolean) =
        OnboardingRepository(
            onboardingDatasource = datasource,
            onboardingEnabled = onboardingEnabled,
        )

    @Test
    fun `given onboarding enabled and not yet shown when shouldShowOnboarding then it is true`() {
        // Given
        every { datasource.isOnboardingShown } returns false
        val sut = newSut(onboardingEnabled = true)

        // When / Then
        expectThat(sut.shouldShowOnboarding()).isTrue()
    }

    @Test
    fun `given onboarding enabled but already shown when shouldShowOnboarding then it is false`() {
        // Given
        every { datasource.isOnboardingShown } returns true
        val sut = newSut(onboardingEnabled = true)

        // When / Then
        expectThat(sut.shouldShowOnboarding()).isFalse()
    }

    @Test
    fun `given onboarding disabled by build type when shouldShowOnboarding then it is false even if not shown`() {
        // Given
        every { datasource.isOnboardingShown } returns false
        val sut = newSut(onboardingEnabled = false)

        // When / Then
        expectThat(sut.shouldShowOnboarding()).isFalse()
    }

    @Test
    fun `given onboarding disabled and already shown when shouldShowOnboarding then it is false`() {
        // Given
        every { datasource.isOnboardingShown } returns true
        val sut = newSut(onboardingEnabled = false)

        // When / Then
        expectThat(sut.shouldShowOnboarding()).isFalse()
    }

    @Test
    fun `given onboarding disabled when shouldShowOnboarding then the shown flag is not read`() {
        // Given — short-circuit: a disabled gate must not touch the datasource
        val sut = newSut(onboardingEnabled = false)

        // When
        sut.shouldShowOnboarding()

        // Then
        verify(exactly = 0) { datasource.isOnboardingShown }
    }

    @Test
    fun `when markOnboardingShown then the datasource flag is set to true`() {
        // Given
        val sut = newSut(onboardingEnabled = true)

        // When
        sut.markOnboardingShown()

        // Then
        verify { datasource.isOnboardingShown = true }
    }
}
