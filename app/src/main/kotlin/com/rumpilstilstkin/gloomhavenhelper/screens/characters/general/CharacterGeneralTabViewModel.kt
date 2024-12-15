package com.rumpilstilstkin.gloomhavenhelper.screens.characters.general

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.CheckedChangeUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.DonateUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.ExperienceChangeUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.GetCharacterDetailsInfoUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.LevelUpUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.UpdateGoldUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.UpdateNotesUseCase
import com.rumpilstilstkin.gloomhavenhelper.navigation.GlHelperScreens
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEvent
import com.rumpilstilstkin.gloomhavenhelper.screens.models.toUI
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = CharacterGeneralTabViewModel.Factory::class)
class CharacterGeneralTabViewModel @AssistedInject constructor(
    @Assisted val id: Int,
    getCharacterUseCase: GetCharacterDetailsInfoUseCase,
    private val levelUpUseCase: LevelUpUseCase,
    private val donateUseCase: DonateUseCase,
    private val updateGoldUseCase: UpdateGoldUseCase,
    private val experienceChangeUseCase: ExperienceChangeUseCase,
    private val checkedChangeUseCase: CheckedChangeUseCase,
    private val updateNotesUseCase: UpdateNotesUseCase,
) : ViewModel() {

    private val _navigationEvents = MutableSharedFlow<GlHelperEvent>()
    val navigationEvents = _navigationEvents.asSharedFlow()

    val uiState: StateFlow<CharacterGeneralTabState> = getCharacterUseCase(id).map {
        CharacterGeneralTabState(
            experience = it.generalInfo.experience,
            gold = it.generalInfo.gold,
            hasTeam = it.generalInfo.team != null,
            teamName = it.generalInfo.team?.name,
            nextLevel = it.nextLevelExperience,
            notes = it.generalInfo.notes,
            checkMarks = it.generalInfo.checkMarks,
            isDonateAvailable = it.isDonateAvailable,
            personalQuest = it.personalQuest?.toUI()

        )
    }.stateIn(
        scope = viewModelScope,
        initialValue = CharacterGeneralTabState.Empty,
        started = SharingStarted.WhileSubscribed(100),
    )

    fun onAction(action: GeneralTabActions) {
        viewModelScope.launch {
            when (action) {
                is GeneralTabActions.LevelUp -> {
                    levelUpUseCase.invoke(id)
                }

                is GeneralTabActions.GoldChanged -> {
                    updateGoldUseCase.invoke(id, action.gold)
                }

                is GeneralTabActions.Donate -> {
                    donateUseCase.invoke(id)
                }

                is GeneralTabActions.ExperienceChanged -> {
                    experienceChangeUseCase.invoke(id, action.experience)
                }

                is GeneralTabActions.CheckedChange -> {
                    checkedChangeUseCase.invoke(id, action.isChecked)
                }

                is GeneralTabActions.NoticeChanged -> {
                    updateNotesUseCase.invoke(id, action.notice)
                }

                GeneralTabActions.ChoosePersonalQuest -> {
                    _navigationEvents.emit(GlHelperEvent.Screen(GlHelperScreens.SearchPersonalQuest(id)))
                }
                GeneralTabActions.Retire -> {

                }
                is GeneralTabActions.TaskCheckedChange -> {

                }
                is GeneralTabActions.TaskCountChanged -> {

                }
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(id: Int): CharacterGeneralTabViewModel
    }
}