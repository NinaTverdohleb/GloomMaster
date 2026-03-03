package com.rumpilstilstkin.gloomhavenhelper.screens.characters.general.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterClassType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.quest.CharacterTaskItem
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.quest.QuestReward
import com.rumpilstilstkin.gloomhavenhelper.screens.dialogs.quests.QuestDetailsDialog
import com.rumpilstilstkin.gloomhavenhelper.screens.models.PersonalQuestUI
import com.rumpilstilstkin.gloomhavenhelper.screens.models.QuestTaskPhaseUI
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenHalperTheme
import com.rumpilstilstkin.gloomhavenhelper.ui.components.NumberPicker
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomSize
import kotlinx.collections.immutable.persistentListOf

@Composable
fun PersonalQuestView(
    quest: PersonalQuestUI,
    onRetire: () -> Unit,
    onTaskCheckedChange: (CharacterTaskItem.Check) -> Unit,
    selectNewQuest: () -> Unit,
    onTaskCountChanged: (CharacterTaskItem.Count, Int) -> Unit,
    modifier: Modifier = Modifier
) {

    var showDetailsDialog by remember { mutableStateOf(false) }
    QuestDetailsDialog(
        quest = quest,
        showDialog = showDetailsDialog,
        onAction = { selectNewQuest() },
        onDismiss = { showDetailsDialog = false },
        buttonText = "Сменить"
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable { showDetailsDialog = true },
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "# ${quest.id}",
                style = MaterialTheme.typography.headlineSmall,
            )
            Spacer(
                modifier = Modifier.width(16.dp)
            )
            Text(
                modifier = Modifier.weight(1f),
                text = quest.title,
                style = MaterialTheme.typography.headlineSmall,
            )
            IconButton(
                onClick = onRetire,
                enabled = quest.completed
            ) {
                Icon(
                    modifier = Modifier.size(32.dp),
                    imageVector = Icons.Filled.CheckCircle,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondary,
                )
            }
        }

        quest.phases.forEach { phase ->
            if (phase.visible) {
                phase.tasks.forEach { task ->
                    when (task) {
                        is CharacterTaskItem.Check -> CheckTask(
                            questTask = task,
                            onTaskCheckedChange = onTaskCheckedChange
                        )

                        is CharacterTaskItem.Count -> CountTask(
                            questTask = task,
                            onTaskCountChanged = onTaskCountChanged
                        )
                    }

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun CheckTask(
    questTask: CharacterTaskItem.Check,
    onTaskCheckedChange: (CharacterTaskItem.Check) -> Unit,
) {
    Card(
        modifier = Modifier
            .padding(4.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 48.dp)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = questTask.text
            )
            Spacer(
                modifier = Modifier.width(16.dp)
            )
            Checkbox(
                checked = questTask.isChecked,
                onCheckedChange = { onTaskCheckedChange(questTask) },
                colors = CheckboxDefaults.colors().copy(
                    uncheckedBorderColor = MaterialTheme.colorScheme.primary,
                )
            )
        }
    }
}

@Composable
private fun CountTask(
    questTask: CharacterTaskItem.Count,
    onTaskCountChanged: (CharacterTaskItem.Count, Int) -> Unit,
) {
    Card(
        modifier = Modifier
            .padding(4.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 48.dp)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = questTask.text
            )
            Spacer(
                modifier = Modifier.width(16.dp)
            )

            NumberPicker(
                size = GloomSize.S,
                value = questTask.currentCount,
                intRange = IntRange(0, questTask.count),
                onValueChange = {value ->
                    onTaskCountChanged(questTask, value)
                }
            )
        }
    }
}

@Preview
@Composable
private fun PersonalQuestViewPreview() {
    GloomhavenHalperTheme {
        PersonalQuestView(
            quest = PersonalQuestUI(
                id = "511",
                title = "title",
                description = "description",
                completed = false,
                reward = QuestReward(
                    classType = CharacterClassType.Plagueherald,
                    alternativeReward = "Откройте конверт B"
                ),
                phases = persistentListOf(
                    QuestTaskPhaseUI(
                        priority = 0,
                        tasks = persistentListOf(
                            CharacterTaskItem.Count(
                                priority = 0,
                                text = "Пройдите три сценария с названием Склеп",
                                count = 3,
                                currentCount = 0,
                                id = 1
                            )
                        )
                    ),
                    QuestTaskPhaseUI(
                        priority = 1,
                        tasks = persistentListOf(
                            CharacterTaskItem.Check(
                                priority = 1,
                                text = "Откройте и пройдите полностью сенарий \"Жуткий погреб\"",
                                id = 2
                            )
                        )
                    )
                )
            ),
            onRetire = {},
            selectNewQuest = {},
            onTaskCheckedChange = {},
            onTaskCountChanged = {i, k -> }
        )
    }
}