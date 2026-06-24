package com.rumpilstilstkin.gloomhavenhelper.screens.characters.start.general

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloomhavenhelper.screens.characters.start.general.components.CheckMarksBlock
import com.rumpilstilstkin.gloomhavenhelper.screens.characters.start.general.components.ExperienceRow
import com.rumpilstilstkin.gloomhavenhelper.screens.characters.start.general.components.GoldRow
import com.rumpilstilstkin.gloomhavenhelper.screens.characters.start.general.components.NotesRow
import com.rumpilstilstkin.gloomhavenhelper.screens.characters.start.general.components.PersonalQuestView
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomAlertDialog

@Composable
fun CharacterGeneralTab(
    state: CharacterGeneralTabState,
    modifier: Modifier = Modifier,
    onAction: (GeneralTabActions) -> Unit,
) {
    Column(
        modifier =
            modifier
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        ExperienceRow(
            experience = state.experience,
            nextLevelExperience = state.nextLevel,
            onLevelUp = { onAction(GeneralTabActions.LevelUp) },
            onExperienceChanged = { onAction(GeneralTabActions.ExperienceChanged(it)) },
        )
        GoldRow(
            goldCount = state.goldCount,
            onGoldChanged = { onAction(GeneralTabActions.GoldChanged(it)) },
        )
        CheckMarksBlock(
            checkMarkCount = state.checkMarkCount,
            onCheckedChange = { onAction(GeneralTabActions.CheckedChange(it)) },
        )
        PersonalQuestView(
            quest = state.personalQuest,
            onTaskCheckedChange =  { onAction(GeneralTabActions.TaskCheckedChange(it)) },
            onTaskCountChanged = { i, k -> onAction(GeneralTabActions.TaskCountChanged(i, k)) },
            showQuestDetails = {},
            choosePersonalQuest = { onAction(GeneralTabActions.ChoosePersonalQuest) },
        )
        NotesRow(
            notes = state.notes,
            openNotes = { },
        )
    }
}

@Preview
@Composable
private fun Sample() {
    GloomhavenMasterTheme {
        CharacterGeneralTab(
            state =
                CharacterGeneralTabState(
                    experience = 150,
                    goldCount = 10,
                    checkMarkCount = 15,
                    hasTeam = false,
                    teamName = null,
                    nextLevel = 175,
                    notes = "Some notes about my journey.",
                ),
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
            onAction = {},
        )
    }
}
