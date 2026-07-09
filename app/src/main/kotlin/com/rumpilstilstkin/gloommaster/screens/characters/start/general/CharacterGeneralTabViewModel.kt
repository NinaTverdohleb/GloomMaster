package com.rumpilstilstkin.gloommaster.screens.characters.start.general

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloommaster.domain.usecase.characters.ExperienceChangeUseCase
import com.rumpilstilstkin.gloommaster.domain.usecase.characters.GetCharacterDetailsInfoUseCase
import com.rumpilstilstkin.gloommaster.domain.usecase.characters.LevelUpUseCase
import com.rumpilstilstkin.gloommaster.domain.usecase.characters.MarksCheckedChangeUseCase
import com.rumpilstilstkin.gloommaster.domain.usecase.characters.UpdateGoldUseCase
import com.rumpilstilstkin.gloommaster.domain.usecase.characters.quests.QuestTaskUpdateUseCase
import com.rumpilstilstkin.gloommaster.navigation.GlHelperScreen.SearchPersonalQuest
import com.rumpilstilstkin.gloommaster.navigation.events.GlHelperEvent.Screen
import com.rumpilstilstkin.gloommaster.screens.characters.quests.dialog.QuestDetailsDialogContract
import com.rumpilstilstkin.gloommaster.screens.characters.quests.dialog.QuestDetailsDialogInput
import com.rumpilstilstkin.gloommaster.screens.characters.start.general.text.EditTextDialogContract
import com.rumpilstilstkin.gloommaster.screens.core.ScreenEffect
import com.rumpilstilstkin.gloommaster.screens.core.createOverlaySession
import com.rumpilstilstkin.gloommaster.screens.models.PersonalQuestUI
import com.rumpilstilstkin.gloommaster.screens.models.toUI
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = CharacterGeneralTabViewModel.Factory::class)
class CharacterGeneralTabViewModel @AssistedInject constructor(
    @Assisted val id: Int,
    getCharacterUseCase: GetCharacterDetailsInfoUseCase,
    private val levelUpUseCase: LevelUpUseCase,
    private val updateGoldUseCase: UpdateGoldUseCase,
    private val experienceChangeUseCase: ExperienceChangeUseCase,
    private val checkedChangeUseCase: MarksCheckedChangeUseCase,
    private val questTaskUpdateUseCase: QuestTaskUpdateUseCase,
) : ViewModel() {
    private val _screenEvents = Channel<ScreenEffect>(Channel.BUFFERED)
    val screenEvents = _screenEvents.receiveAsFlow()

    val uiState: StateFlow<CharacterGeneralTabState> =
        getCharacterUseCase(id)
            .map {
                if (it == null) {
                    return@map CharacterGeneralTabState.Empty
                }
                CharacterGeneralTabState(
                    experience = it.generalInfo.experience,
                    goldCount = it.generalInfo.goldCount,
                    hasTeam = it.generalInfo.team != null,
                    teamName = it.generalInfo.team?.name,
                    nextLevel = it.nextLevelExperience,
                    notes = it.generalInfo.notes,
                    checkMarkCount = it.generalInfo.checkMarkCount,
                    isDonateAvailable = it.isDonateAvailable,
                    personalQuest = it.personalQuest?.toUI(),
                )
            }.stateIn(
                scope = viewModelScope,
                initialValue = CharacterGeneralTabState.Empty,
                started = SharingStarted.WhileSubscribed(5000),
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

                is GeneralTabActions.ExperienceChanged -> {
                    experienceChangeUseCase.invoke(id, action.experience)
                }

                is GeneralTabActions.CheckedChange -> {
                    checkedChangeUseCase.invoke(id, action.isChecked)
                }

                GeneralTabActions.ChoosePersonalQuest -> {
                    _screenEvents.send(
                        ScreenEffect.Navigation(
                            (Screen(SearchPersonalQuest(id))),
                        ),
                    )
                }

                is GeneralTabActions.TaskCheckedChange -> {
                    questTaskUpdateUseCase.invoke(
                        characterId = id,
                        task =
                            action.task.copy(
                                isChecked = !action.task.isChecked,
                            ),
                    )
                }

                is GeneralTabActions.TaskCountChanged -> {
                    questTaskUpdateUseCase.invoke(
                        characterId = id,
                        task =
                            action.task.copy(
                                currentCount = action.count,
                            ),
                    )
                }

                GeneralTabActions.OpenNotes -> {
                    openNotesDialog(characterId = id)
                }

                GeneralTabActions.OpenQuest -> {
                    val quest = uiState.value.personalQuest ?: return@launch
                    openQuestDialog(
                        quest = quest,
                        characterId = id,
                    )
                }
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(id: Int): CharacterGeneralTabViewModel
    }

    private fun openNotesDialog(characterId: Int) {
        viewModelScope.launch {
            val session =
                createOverlaySession(
                    contract = EditTextDialogContract,
                    input = characterId,
                    onResult = { },
                )
            _screenEvents.send(ScreenEffect.OpenBottomSheet(session))
        }
    }

    private fun openQuestDialog(
        quest: PersonalQuestUI,
        characterId: Int,
    ) {
        viewModelScope.launch {
            val session =
                createOverlaySession(
                    contract = QuestDetailsDialogContract,
                    input =
                        QuestDetailsDialogInput(
                            quest = quest,
                            characterId = characterId,
                            selected = true,
                        ),
                    onResult = {
                        onAction(GeneralTabActions.ChoosePersonalQuest)
                    },
                )
            _screenEvents.send(ScreenEffect.OpenDialog(session))
        }
    }
}
