package com.rumpilstilstkin.gloomhavenhelper.screens.characters.quests.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
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
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.quest.QuestReward
import com.rumpilstilstkin.gloomhavenhelper.screens.dialogs.quests.QuestDetailsDialog
import com.rumpilstilstkin.gloomhavenhelper.screens.models.PersonalQuestUI
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomCard
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenHalperTheme
import kotlinx.collections.immutable.persistentListOf

@Composable
fun PersonalQuestItem(
    quest: PersonalQuestUI,
    chooseQuest: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var showDetailsDialog by remember { mutableStateOf(false) }
    QuestDetailsDialog(
        quest = quest,
        showDialog = showDetailsDialog,
        onAction = { chooseQuest(it) },
        onDismiss = { showDetailsDialog = false }
    )
    GloomCard(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showDetailsDialog = true },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
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
            }
        }
    }
}

@Preview
@Composable
private fun PersonalQuestItemPreview() {
    GloomhavenHalperTheme {
        PersonalQuestItem(
            quest = PersonalQuestUI(
                id = "512",
                title = "Some title",
                description = "",
                phases = persistentListOf(),
                completed = false,
                reward = QuestReward(
                    classType = null,
                    alternativeReward = ""
                )
            ),
            chooseQuest = {}
        )
    }

}