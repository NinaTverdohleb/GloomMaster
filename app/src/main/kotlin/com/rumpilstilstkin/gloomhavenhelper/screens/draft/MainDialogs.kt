package com.rumpilstilstkin.gloomhavenhelper.screens.draft

import androidx.compose.runtime.Composable
import com.rumpilstilstkin.gloomhavenhelper.screens.dialogs.character.add.AddCharacterDialog
import com.rumpilstilstkin.gloomhavenhelper.ui.scenario.ScenarioLevelInfoDialog
import com.rumpilstilstkin.gloomhavenhelper.ui.team.ProsperityDialog
import com.rumpilstilstkin.gloomhavenhelper.ui.team.ReputationDialog

@Composable
fun MainDialogs(
    reputation: Int,
    prosperity: Int,
    effectState: MainScreenEffect,
    onAction: (MainScreenAction) -> Unit
) {
    if (effectState.levelInfo != null) {
        ScenarioLevelInfoDialog(
            showDialog = effectState.showLevelInfoDialog,
            levelInfo = effectState.levelInfo,
            onDismiss = {
                onAction(MainScreenAction.HideLevelInfoDialog)
            }
        )
    }
    ReputationDialog(
        reputation = reputation,
        showDialog = effectState.showReputationDialog,
        onSave = { onAction(MainScreenAction.SetNewReputation(it)) },
        hideDialog = { onAction(MainScreenAction.HideReputationDialog) }
    )

    ProsperityDialog(
        prosperity = prosperity,
        showDialog = effectState.showProsperityDialog,
        onSave = { onAction(MainScreenAction.SetNewProsperity(it)) },
        hideDialog = { onAction(MainScreenAction.HideProsperityDialog) }
    )

    AddCharacterDialog(
        showDialog = effectState.showAddCharacterDialog,
        onDismiss = { onAction(MainScreenAction.HideAddCharacterDialog) },
        onAdd = { name, level, classType ->
            onAction(MainScreenAction.AddCharacter(
                name = name,
                level = level,
                classType = classType,
            ))
        }
    )
}
