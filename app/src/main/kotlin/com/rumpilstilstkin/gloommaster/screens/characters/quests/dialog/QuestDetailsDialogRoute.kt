package com.rumpilstilstkin.gloommaster.screens.characters.quests.dialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rumpilstilstkin.gloommaster.screens.core.OverlayContract

object QuestDetailsDialogContract : OverlayContract<QuestDetailsDialogInput, Unit> {
    @Composable
    override fun Content(
        input: QuestDetailsDialogInput,
        onDismissWithResult: (Unit?) -> Unit,
    ) {
        QuestDetailsDialogRoute(
            input = input,
            close = onDismissWithResult,
        )
    }
}

@Composable
fun QuestDetailsDialogRoute(
    input: QuestDetailsDialogInput,
    close: (Unit?) -> Unit,
    viewModel: QuestDetailsDialogViewModel = hiltViewModel(),
) {
    val complete by viewModel.complete.collectAsStateWithLifecycle(initialValue = null)

    LaunchedEffect(complete) {
        complete?.let {
            close(Unit)
        }
    }

    QuestDetailsDialog(
        quest = input.quest,
        selected = input.selected,
        onAction = {
            if (input.selected) {
                close(Unit)
            } else {
                viewModel.onAction(
                    QuestDetailsDialogAction.SelectQuest(
                        questId = input.quest.id,
                        characterId = input.characterId,
                    ),
                )
            }
        },
    )
}
