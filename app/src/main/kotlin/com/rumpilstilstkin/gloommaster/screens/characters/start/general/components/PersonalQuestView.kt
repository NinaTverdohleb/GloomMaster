package com.rumpilstilstkin.gloommaster.screens.characters.start.general.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloommaster.R
import com.rumpilstilstkin.gloommaster.designsystem.components.GloomCard
import com.rumpilstilstkin.gloommaster.designsystem.components.buttons.GloomOutlineButton
import com.rumpilstilstkin.gloommaster.designsystem.components.items.CounterRightItem
import com.rumpilstilstkin.gloommaster.designsystem.components.items.GloomListOutlineItem
import com.rumpilstilstkin.gloommaster.designsystem.components.items.RightItemChecker
import com.rumpilstilstkin.gloommaster.designsystem.components.text.GloomHeader
import com.rumpilstilstkin.gloommaster.designsystem.icons.AppIcon
import com.rumpilstilstkin.gloommaster.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloommaster.domain.entity.CharacterClassType
import com.rumpilstilstkin.gloommaster.domain.entity.quest.CharacterTaskItem
import com.rumpilstilstkin.gloommaster.domain.entity.quest.QuestReward
import com.rumpilstilstkin.gloommaster.screens.models.PersonalQuestUI
import com.rumpilstilstkin.gloommaster.screens.models.QuestTaskPhaseUI
import com.rumpilstilstkin.gloommaster.testtags.screens.characters.start.CharacterGeneralTabTestTags
import com.rumpilstilstkin.gloommaster.ui.quest.QuestItem
import kotlinx.collections.immutable.persistentListOf

@Composable
fun PersonalQuestView(
    quest: PersonalQuestUI?,
    modifier: Modifier = Modifier,
    choosePersonalQuest: () -> Unit,
    onTaskCheckedChange: (CharacterTaskItem.Check) -> Unit,
    showQuestDetails: (PersonalQuestUI) -> Unit,
    onTaskCountChanged: (CharacterTaskItem.Count, Int) -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        GloomHeader(stringResource(R.string.personal_quest_title))
        if (quest == null) {
            GloomOutlineButton(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .testTag(CharacterGeneralTabTestTags.CHOOSE_QUEST),
                text = stringResource(R.string.add_personal_quest),
                onClick = choosePersonalQuest,
                icon = AppIcon.Plus,
            )
        } else {
            PersonalQuest(
                quest = quest,
                onTaskCheckedChange = onTaskCheckedChange,
                onTaskCountChanged = onTaskCountChanged,
                showQuestDetails = showQuestDetails,
            )
        }
    }
}

@Composable
fun PersonalQuest(
    quest: PersonalQuestUI,
    showQuestDetails: (PersonalQuestUI) -> Unit,
    onTaskCheckedChange: (CharacterTaskItem.Check) -> Unit,
    onTaskCountChanged: (CharacterTaskItem.Count, Int) -> Unit,
    modifier: Modifier = Modifier,
) = GloomCard(
    modifier = modifier,
) {
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .clickable { showQuestDetails(quest) }
                .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        QuestItem(
            quest = quest,
            clickItem = showQuestDetails,
        )
        quest.phases.forEach { phase ->
            if (phase.visible) {
                phase.tasks.forEach { task ->
                    when (task) {
                        is CharacterTaskItem.Check -> {
                            GloomListOutlineItem(
                                title = task.text,
                                rightComponent = {
                                    RightItemChecker(
                                        task.isChecked,
                                    ) {
                                        onTaskCheckedChange(task)
                                    }
                                },
                            )
                        }

                        is CharacterTaskItem.Count -> {
                            GloomListOutlineItem(
                                title = task.text,
                                rightComponent = {
                                    CounterRightItem(
                                        value = task.currentCount,
                                        intRange = IntRange(0, task.count),
                                        onValueChange = { value ->
                                            onTaskCountChanged(task, value)
                                        },
                                    )
                                },
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PersonalQuestPreview() {
    GloomhavenMasterTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            PersonalQuestView(
                quest = null,
                onTaskCheckedChange = {},
                onTaskCountChanged = { i, k -> },
                choosePersonalQuest = {},
                showQuestDetails = {},
            )
            PersonalQuestView(
                quest =
                    PersonalQuestUI(
                        id = "511",
                        title = "Seeker of Xorn",
                        description = "Ever since you were a child, you heard the call of Xorn.",
                        completed = false,
                        reward =
                            QuestReward(
                                classType = CharacterClassType.Plagueherald,
                                alternativeReward = "Open envelope B",
                            ),
                        phases =
                            persistentListOf(
                                QuestTaskPhaseUI(
                                    priority = 0,
                                    visible = true,
                                    tasks =
                                        persistentListOf(
                                            CharacterTaskItem.Count(
                                                priority = 0,
                                                text = "Complete three scenarios with the name Crypt",
                                                count = 3,
                                                currentCount = 3,
                                                id = 1,
                                            ),
                                        ),
                                ),
                                QuestTaskPhaseUI(
                                    priority = 1,
                                    visible = true,
                                    tasks =
                                        persistentListOf(
                                            CharacterTaskItem.Check(
                                                priority = 1,
                                                text = "Open and complete the scenario \"Jekserah's Plans\"",
                                                id = 2,
                                            ),
                                        ),
                                ),
                            ),
                    ),
                onTaskCheckedChange = {},
                onTaskCountChanged = { i, k -> },
                choosePersonalQuest = {},
                showQuestDetails = {},
            )
        }
    }
}
