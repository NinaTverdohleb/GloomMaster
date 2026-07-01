package com.rumpilstilstkin.gloomhavenhelper.data.datasource

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Stores whether the first-run onboarding has already been shown, so it appears
 * only once. Backed by the shared [SharedPreferences] provided in the DI module,
 * mirroring [LocaleDatasource].
 */
@Singleton
class OnboardingDatasource @Inject constructor(
    private val preference: SharedPreferences,
) {
    var isOnboardingShown: Boolean
        get() = preference.getBoolean(ONBOARDING_SHOWN_KEY, false)
        set(value) = preference.edit { putBoolean(ONBOARDING_SHOWN_KEY, value) }

    private companion object {
        const val ONBOARDING_SHOWN_KEY = "onboardingShownKey"
    }
}
