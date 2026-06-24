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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterClassType
import com.rumpilstilstkin.gloomhavenhelper.screens.models.PersonalQuestUI
import com.rumpilstilstkin.gloomhavenhelper.screens.models.QuestTaskPhaseUI
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomAlertDialog
import com.rumpilstilstkin.gloomhavenhelper.ui.icons.toImage
import kotlinx.collections.immutable.ImmutableList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestDetailsDialogOld(
    quest: PersonalQuestUI,
    showDialog: Boolean,
    modifier: Modifier = Modifier,
    onAction: ((String) -> Unit)? = null,
    onDismiss: () -> Unit = {},
    buttonText: String = stringResource(R.string.select),
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
                phases = quest.phases,
            )

            Spacer(modifier = Modifier.size(16.dp))

            Rewards(
                classType = quest.reward.classType,
                alternativeReward = quest.reward.alternativeReward,
            )
        },
    )
}

@Composable
private fun Task(phases: ImmutableList<QuestTaskPhaseUI>) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        phases.forEach { phase ->
            phase.tasks.forEach { task ->
                Text(
                    text = task.text,
                    style = MaterialTheme.typography.labelMedium,
                )
                Spacer(
                    modifier = Modifier.size(4.dp),
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
            text = stringResource(R.string.reward),
        )
        Spacer(modifier = Modifier.size(4.dp))
        if (classType != null) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = stringResource(R.string.unlock_class))
                Image(
                    modifier = Modifier.size(32.dp),
                    painter = classType.toImage().painter(),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                    contentDescription = null,
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
private fun GoodDetailsDialogOldPreview() {
    GloomhavenMasterTheme {
        QuestDetailsDialogOld(
            quest = PersonalQuestUI.fixture(),
            showDialog = true,
            onAction = {},
            onDismiss = {},
        )
    }
}
