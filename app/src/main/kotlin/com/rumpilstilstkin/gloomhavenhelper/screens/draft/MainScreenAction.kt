package com.rumpilstilstkin.gloomhavenhelper.screens.draft

import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterClassType

sealed interface MainScreenAction {
    data object ShowLevelInfoDialog : MainScreenAction
    data object HideLevelInfoDialog : MainScreenAction
    data object ShowProsperityDialog : MainScreenAction
    data object HideProsperityDialog : MainScreenAction
    data object ShowReputationDialog : MainScreenAction
    data object HideReputationDialog : MainScreenAction
    data class SetNewReputation(val reputation: Int) : MainScreenAction
    data class SetNewProsperity(val prosperity: Int) : MainScreenAction
    data object ShowAddCharacterDialog : MainScreenAction
    data object HideAddCharacterDialog : MainScreenAction
    data class AddCharacter(val name: String, val level: Int, val classType: CharacterClassType) : MainScreenAction
    data class EditCharacter(val id: Int, val level: Int) : MainScreenAction
    data class DeleteCharacter(val id: Int) : MainScreenAction
    data class LeaveCharacter(val id: Int) : MainScreenAction
    data class CompleteScenario(val scenarioNumber: Int) : MainScreenAction


}