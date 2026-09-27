package com.rumpilstilstkin.gloommaster.screens

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloommaster.bd.filler.DatabaseFiller
import com.rumpilstilstkin.gloommaster.data.LocaleRepository
import com.rumpilstilstkin.gloommaster.data.OnboardingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    databaseFiller: DatabaseFiller,
    private val onboardingRepository: OnboardingRepository,
    localeRepository: LocaleRepository,
) : ViewModel() {
    val uiState: StateFlow<MainActivityUiState> =
        flowOf(1)
            .map {
                databaseFiller.fillDatabase()
                val startOnboarding = onboardingRepository.shouldShowOnboarding()
                MainActivityUiState.Success(
                    startOnboarding = startOnboarding,
                )
            }.stateIn(
                scope = viewModelScope,
                initialValue = MainActivityUiState.Loading,
                started = SharingStarted.WhileSubscribed(5_000),
            )

    init {
        viewModelScope.launch {
            localeRepository.observeAppLocale.collect { locale ->
                val appLocales =
                    if (locale == null) {
                        LocaleListCompat.getEmptyLocaleList()
                    } else {
                        LocaleListCompat.forLanguageTags(locale)
                    }
                if (AppCompatDelegate.getApplicationLocales() != appLocales) {
                    AppCompatDelegate.setApplicationLocales(appLocales)
                }
            }
        }
    }
}

sealed interface MainActivityUiState {
    data object Loading : MainActivityUiState

    data class Success(
        val startOnboarding: Boolean,
    ) : MainActivityUiState
}
