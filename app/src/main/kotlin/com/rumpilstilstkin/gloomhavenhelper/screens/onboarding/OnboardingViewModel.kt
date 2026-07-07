package com.rumpilstilstkin.gloomhavenhelper.screens.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloomhavenhelper.data.OnboardingRepository
import com.rumpilstilstkin.gloomhavenhelper.navigation.GlHelperScreen
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEvent.Replace
import com.rumpilstilstkin.gloomhavenhelper.screens.core.ScreenEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val onboardingRepository: OnboardingRepository,
) : ViewModel() {
    private val _screenEvents = Channel<ScreenEffect>(Channel.BUFFERED)
    val screenEvents = _screenEvents.receiveAsFlow()

    fun onAction(action: OnboardingAction) {
        when (action) {
            OnboardingAction.Finish -> finish()
        }
    }

    private fun finish() {
        onboardingRepository.markOnboardingShown()
        viewModelScope.launch {
            _screenEvents.send(
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
