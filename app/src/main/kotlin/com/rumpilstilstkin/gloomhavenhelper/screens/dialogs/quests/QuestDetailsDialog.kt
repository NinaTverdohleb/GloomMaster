package com.rumpilstilstkin.gloomhavenhelper.screens.dialogs.quests

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterClassType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.quest.CharacterTaskItem
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.quest.QuestReward
import com.rumpilstilstkin.gloomhavenhelper.screens.models.PersonalQuestUI
import com.rumpilstilstkin.gloomhavenhelper.screens.models.QuestTaskPhaseUI
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomAlertDialog
import com.rumpilstilstkin.gloomhavenhelper.ui.icons.toImage
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenHalperTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestDetailsDialog(
    quest: PersonalQuestUI,
    showDialog: Boolean,
    modifier: Modifier = Modifier,
    onAction: ((String) -> Unit)? = null,
    onDismiss: () -> Unit = {},
    buttonText: String = "Выбрать",
) {
    if (!showDialog) return

    GloomAlertDialog(
        modifier = modifier,
        onNeutralRequest = null,
        onConfirmRequest = {
            onAction?.invoke(quest.id)
        },
        confirmText = buttonText,
        onDismissRequest = { onDismiss() },
        content = {
            Text(
                style = MaterialTheme.typography.headlineSmall,
                text = quest.title,
            )
            Spacer(modifier = Modifier.size(16.dp))
            Text(
                text = quest.description,
            )
            Spacer(modifier = Modifier.size(8.dp))

            HorizontalDivider()

            Spacer(modifier = Modifier.size(16.dp))

            Task(
                phases = quest.phases
            )

            Spacer(modifier = Modifier.size(16.dp))

            Rewards(
                classType = quest.reward.classType,
                alternativeReward = quest.reward.alternativeReward
            )
        }
    )
}

@Composable
private fun Task(
    phases: ImmutableList<QuestTaskPhaseUI>,
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        phases.forEach { phase ->
            phase.tasks.forEach { task ->
                Text(
                    text = task.text,
                    style = MaterialTheme.typography.labelMedium
                )
                Spacer(
                    modifier = Modifier.size(4.dp)
                )
            }
        }
    }
}

@Composable
private fun Rewards(
    classType: CharacterClassType? = null,
    alternativeReward: String,
) {
    Column {
        Text(
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.titleSmall,
            text = "Награда",
        )
        Spacer(modifier = Modifier.size(4.dp))
        if (classType != null) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Откройте класс: ")
                Image(
                    modifier = Modifier.size(32.dp),
                    painter = painterResource(id = classType.toImage()),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                    contentDescription = null
                )
            }
            Spacer(modifier = Modifier.size(4.dp))
        }
        if (alternativeReward.isNotBlank()) {
            Text(text = alternativeReward)
            Spacer(modifier = Modifier.size(4.dp))
        }
    }
}

@Preview
@Composable
private fun GoodDetailsDialogPreview() {
    GloomhavenHalperTheme {
        QuestDetailsDialog(
            quest = PersonalQuestUI(
                id = "511",
                title = "Искатель Ксорна",
                description = "Еше с детства ты слышал зов Ксорна. Когда-то его почитали как бога, но его паства давно уничтожена. Но ты можешь слышать его зов. Ты отправтлся в Глумхевен по его приказу. Ты найдешь нго останки и освободишь его. Случившееся однажды повторится снова.",
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
            showDialog = true,
            onAction = {},
            onDismiss = {},
        )
    }
}