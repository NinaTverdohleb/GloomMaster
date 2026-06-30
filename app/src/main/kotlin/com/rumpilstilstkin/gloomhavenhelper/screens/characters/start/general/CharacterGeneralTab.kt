package com.rumpilstilstkin.gloomhavenhelper.screens.characters.start.general

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.quest.CharacterTaskItem
import com.rumpilstilstkin.gloomhavenhelper.screens.characters.start.general.components.CheckMarksBlock
import com.rumpilstilstkin.gloomhavenhelper.screens.characters.start.general.components.ExperienceRow
import com.rumpilstilstkin.gloomhavenhelper.screens.characters.start.general.components.GoldRow
import com.rumpilstilstkin.gloomhavenhelper.screens.characters.start.general.components.NotesRow
import com.rumpilstilstkin.gloomhavenhelper.screens.characters.start.general.components.PersonalQuestView
import com.rumpilstilstkin.gloomhavenhelper.screens.models.PersonalQuestUI
import com.rumpilstilstkin.gloomhavenhelper.testtags.screens.characters.start.CharacterGeneralTabTestTags

@Composable
fun CharacterGeneralTab(
    state: CharacterGeneralTabState,
    modifier: Modifier = Modifier,
    openNotes: () -> Unit,
    onLevelUp: () -> Unit,
    onGoldChanged: (Int) -> Unit,
    onExperienceChanged: (Int) -> Unit,
    onCheckedChange: (Boolean) -> Unit,
    choosePersonalQuest: () -> Unit,
    onTaskCheckedChange: (CharacterTaskItem.Check) -> Unit,
    showQuestDetails: (PersonalQuestUI) -> Unit,
    onTaskCountChanged: (CharacterTaskItem.Count, Int) -> Unit,
) {
    Column(
        modifier =
            modifier
                .testTag(CharacterGeneralTabTestTags.SCROLL_COLUMN)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        ExperienceRow(
            experience = state.experience,
            nextLevelExperience = state.nextLevel,
            onLevelUp = onLevelUp,
            onExperienceChanged = onExperienceChanged,
        )
        GoldRow(
            goldCount = state.goldCount,
            onGoldChanged = onGoldChanged,
        )
        CheckMarksBlock(
            checkMarkCount = state.checkMarkCount,
            onCheckedChange = onCheckedChange,
        )
        PersonalQuestView(
            quest = state.personalQuest,
            onTaskCheckedChange = onTaskCheckedChange,
            onTaskCountChanged = onTaskCountChanged,
            showQuestDetails = showQuestDetails,
            choosePersonalQuest = choosePersonalQuest,
        )
        NotesRow(
            notes = state.notes,
            openNotes = openNotes,
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
            openNotes = {},
            onLevelUp = {},
            onGoldChanged = {},
            onExperienceChanged = {},
            onCheckedChange = {},
            choosePersonalQuest = {},
            onTaskCheckedChange = {},
            showQuestDetails = {},
            onTaskCountChanged = { _, _ -> },
        )
    }
}
