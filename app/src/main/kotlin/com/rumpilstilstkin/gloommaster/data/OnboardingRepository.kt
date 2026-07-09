package com.rumpilstilstkin.gloommaster.data

import com.rumpilstilstkin.gloommaster.data.datasource.OnboardingDatasource
import com.rumpilstilstkin.gloommaster.di.OnboardingEnabled
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Single source of truth for the first-run onboarding: whether it should be shown
 * and marking it as done. Combines the build-type gate ([onboardingEnabled], off
 * for benchmark builds) with the persisted "already shown" flag so callers never
 * re-derive that logic.
 */
@Singleton
class OnboardingRepository @Inject constructor(
    private val onboardingDatasource: OnboardingDatasource,
    @param:OnboardingEnabled private val onboardingEnabled: Boolean,
) {
    fun shouldShowOnboarding(): Boolean = onboardingEnabled && !onboardingDatasource.isOnboardingShown

    fun markOnboardingShown() {
        onboardingDatasource.isOnboardingShown = true
    }
}
