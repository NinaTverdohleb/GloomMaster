package com.rumpilstilstkin.gloommaster.ui.quest

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloommaster.R
import com.rumpilstilstkin.gloommaster.designsystem.components.items.GloomListFilledItem
import com.rumpilstilstkin.gloommaster.designsystem.components.items.GloomListItem
import com.rumpilstilstkin.gloommaster.designsystem.components.items.LeftItemNumber
import com.rumpilstilstkin.gloommaster.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloommaster.screens.models.PersonalQuestUI

@Composable
fun QuestItem(
    quest: PersonalQuestUI,
    modifier: Modifier = Modifier,
    clickItem: (PersonalQuestUI) -> Unit,
) = GloomListItem(
    modifier = modifier.padding(vertical = 8.dp),
    title = quest.title,
    onClick = { clickItem(quest) },
    leftComponent = {
        LeftItemNumber(
            number = stringResource(R.string.scenario_number_format, quest.id),
        )
    },
)

@Composable
fun QuestItemFilled(
    quest: PersonalQuestUI,
    modifier: Modifier = Modifier,
    clickItem: (PersonalQuestUI) -> Unit,
) = GloomListFilledItem(
    modifier = modifier,
    title = quest.title,
    onClick = { clickItem(quest) },
    leftComponent = {
        LeftItemNumber(
            number = stringResource(R.string.scenario_number_format, quest.id),
        )
    },
)

@Preview
@Composable
private fun QuestItemPreview() {
    GloomhavenMasterTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            QuestItem(
                quest = PersonalQuestUI.fixture(),
                clickItem = {},
            )

            QuestItemFilled(
                quest = PersonalQuestUI.fixture(),
                clickItem = {},
            )
        }
    }
}
