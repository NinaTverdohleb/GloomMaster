package com.rumpilstilstkin.gloomhavenhelper.screens.characters.quests.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.buttons.GloomOutlineButton
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.text.GloomHeader
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.text.GloomHeaderVariant
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.AppIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterClassType
import com.rumpilstilstkin.gloomhavenhelper.screens.models.PersonalQuestUI
import com.rumpilstilstkin.gloomhavenhelper.screens.models.QuestTaskPhaseUI
import com.rumpilstilstkin.gloomhavenhelper.ui.icons.toImage
import kotlinx.collections.immutable.ImmutableList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestDetailsDialog(
    quest: PersonalQuestUI,
    selected: Boolean,
    onAction: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        GloomHeader(
            text = quest.title,
        )

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = quest.description,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyLarge,
        )

        HorizontalDivider()

        Task(phases = quest.phases)

        Rewards(
            classType = quest.reward.classType,
            alternativeReward = quest.reward.alternativeReward,
        )

        val buttonText = if (selected) {
            stringResource(R.string.change_button)
        } else {
            stringResource(R.string.select)
        }
        val buttonIcon = if (selected) {
            AppIcon.Restore
        } else {
            AppIcon.Check
        }

        GloomOutlineButton(
            modifier = Modifier.fillMaxWidth(),
            text = buttonText,
            icon = buttonIcon,
            onClick = onAction
        )
    }
}

@Composable
private fun Task(phases: ImmutableList<QuestTaskPhaseUI>) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        phases.forEach { phase ->
            phase.tasks.forEach { task ->
                Text(
                    text = task.text,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
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
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        GloomHeaderVariant(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.reward),
        )
        if (classType != null) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(R.string.unlock_class),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Image(
                    modifier = Modifier.size(32.dp),
                    painter = classType.toImage().painter(),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                    contentDescription = null,
                )
            }
        }
        if (alternativeReward.isNotBlank()) {
            Text(
                text = alternativeReward,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview
@Composable
private fun GoodDetailsDialogSelectedPreview() {
    GloomhavenMasterTheme {
        QuestDetailsDialog(
            quest = PersonalQuestUI.fixture(),
            selected = true,
            onAction = {},
        )
    }
}

@Preview
@Composable
private fun GoodDetailsDialogPreview() {
    GloomhavenMasterTheme {
        QuestDetailsDialog(
            quest = PersonalQuestUI.fixture(),
            selected = false,
            onAction = {},
        )
    }
}