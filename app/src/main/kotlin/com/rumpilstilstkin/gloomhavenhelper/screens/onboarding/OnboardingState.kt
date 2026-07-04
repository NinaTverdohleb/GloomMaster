package com.rumpilstilstkin.gloomhavenhelper.screens.onboarding

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.rumpilstilstkin.gloomhavenhelper.R
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

/** User actions on the onboarding screen. */
sealed interface OnboardingAction {
    data object Finish : OnboardingAction
}

/** One onboarding slide: an illustration with a title and body. */
data class OnboardingPage(
    @param:DrawableRes val imageRes: Int,
    @param:StringRes val titleRes: Int,
    @param:StringRes val bodyRes: Int,
)

/**
 * Default onboarding content — six placeholder slides. Images and copy are meant
 * to be replaced later; this keeps the flow buildable and runnable in the meantime.
 */
val defaultOnboardingPages: ImmutableList<OnboardingPage> =
    persistentListOf(
        OnboardingPage(
            R.drawable.ic_onboarding_1,
            R.string.onboarding_title_1,
            R.string.onboarding_body_1,
        ),
        OnboardingPage(
            R.drawable.ic_onboarding_2,
            R.string.onboarding_title_2,
            R.string.onboarding_body_2,
        ),
        OnboardingPage(
            R.drawable.ic_onboarding_3,
            R.string.onboarding_title_3,
            R.string.onboarding_body_3,
        ),
        OnboardingPage(
            R.drawable.ic_onboarding_4,
            R.string.onboarding_title_4,
            R.string.onboarding_body_4,
        ),
        OnboardingPage(
            R.drawable.ic_onboarding_5,
            R.string.onboarding_title_5,
            R.string.onboarding_body_5,
        ),
    )
