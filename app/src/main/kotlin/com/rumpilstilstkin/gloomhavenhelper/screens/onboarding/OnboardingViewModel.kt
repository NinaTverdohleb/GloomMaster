package com.rumpilstilstkin.gloomhavenhelper.screens.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloomhavenhelper.data.OnboardingRepository
import com.rumpilstilstkin.gloomhavenhelper.navigation.GlHelperScreen
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEvent.Replace
import com.rumpilstilstkin.gloomhavenhelper.screens.core.ScreenEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val onboardingRepository: OnboardingRepository,
) : ViewModel() {
    private val _screenEvents = MutableSharedFlow<ScreenEffect>()
    val screenEvents = _screenEvents.asSharedFlow()

    fun onAction(action: OnboardingAction) {
        when (action) {
            OnboardingAction.Finish -> finish()
        }
    }

    private fun finish() {
        onboardingRepository.markOnboardingShown()
        viewModelScope.launch {
            _screenEvents.emit(
                ScreenEffect.Navigation(
                    Replace(
                        screen = GlHelperScreen.Start,
                        popUpTo = GlHelperScreen.Onboarding,
                    ),
                ),
            )
        }
    }
}
