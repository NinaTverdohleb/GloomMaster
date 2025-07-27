package com.rumpilstilstkin.gloomhavenhelper.screens.characters.general

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.quest.CharacterTaskItem
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.MarksCheckedChangeUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.DonateUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.ExperienceChangeUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.GetCharacterDetailsInfoUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.LevelUpUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.UpdateGoldUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.UpdateNotesUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.quests.QuestTaskUpdateUseCase
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
    private val checkedChangeUseCase: MarksCheckedChangeUseCase,
    private val updateNotesUseCase: UpdateNotesUseCase,
    private val questTaskUpdateUseCase: QuestTaskUpdateUseCase,
) : ViewModel() {

    private val _navigationEvents = MutableSharedFlow<GlHelperEvent>()
    val navigationEvents = _navigationEvents.asSharedFlow()

    val uiState: StateFlow<CharacterGeneralTabState> = getCharacterUseCase(id).map {
        CharacterGeneralTabState(
            experience = it.generalInfo.experience,
            goldCount = it.generalInfo.goldCount,
            hasTeam = it.generalInfo.team != null,
            teamName = it.generalInfo.team?.name,
            nextLevel = it.nextLevelExperience,
            notes = it.generalInfo.notes,
            checkMarkCount = it.generalInfo.checkMarkCount,
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
                    updateGoldUseCase.invoke(id, action.goldCount)
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

                is GeneralTabActions.NotesChanged -> {
                    updateNotesUseCase.invoke(id, action.notes)
                }

                GeneralTabActions.ChoosePersonalQuest -> {
                    _navigationEvents.emit(
                        GlHelperEvent.Screen(
                            GlHelperScreens.SearchPersonalQuest(
                                id
                            )
                        )
                    )
                }

                GeneralTabActions.Retire -> {

                }

                is GeneralTabActions.TaskCheckedChange -> {
                    questTaskUpdateUseCase.invoke(
                        characterId = id,
                        task = action.task.copy(
                            isChecked = !action.task.isChecked
                        )
                    )
                }

                is GeneralTabActions.TaskCountChanged -> {
                    questTaskUpdateUseCase.invoke(
                        characterId = id,
                        task = action.task.copy(
                            currentCount = action.count
                        )
                    )
                }
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(id: Int): CharacterGeneralTabViewModel
    }
}
