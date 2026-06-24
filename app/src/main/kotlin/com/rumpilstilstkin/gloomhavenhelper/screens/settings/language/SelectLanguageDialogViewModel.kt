package com.rumpilstilstkin.gloomhavenhelper.screens.settings.language

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.settings.LanguagesListUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.settings.SetLanguageUseCase
import com.rumpilstilstkin.gloomhavenhelper.screens.teem.list.TeamListDialogComplete
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectLanguageDialogViewModel @Inject constructor(
    languagesListUseCase: LanguagesListUseCase,
    private val setLanguageUseCase: SetLanguageUseCase,
) : ViewModel() {
    private val _complete = Channel<SelectLanguageDialogComplete>(Channel.BUFFERED)
    val complete = _complete.receiveAsFlow()

    val uiState: StateFlow<SelectLanguageDialogState> =
        languagesListUseCase()
            .map { (_, languages) ->
                SelectLanguageDialogState(
                    languages = languages.toImmutableList(),
                )
            }.stateIn(
                scope = viewModelScope,
                initialValue = SelectLanguageDialogState(persistentListOf()),
                started = SharingStarted.WhileSubscribed(5000),
            )

    fun onAction(action: SelectLanguageDialogAction) {
        viewModelScope.launch {
            when (action) {
                is SelectLanguageDialogAction.SelectLanguage -> {
                    setLanguageUseCase(action.languageTag)
                    _complete.send(SelectLanguageDialogComplete)
                }
            }
        }
    }
}
