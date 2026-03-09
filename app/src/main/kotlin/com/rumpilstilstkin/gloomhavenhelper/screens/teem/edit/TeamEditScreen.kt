package com.rumpilstilstkin.gloomhavenhelper.screens.teem.edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
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
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.PackType
import com.rumpilstilstkin.gloomhavenhelper.screens.dialogs.teams.DeleteTeamDialog
import com.rumpilstilstkin.gloomhavenhelper.screens.dialogs.teams.TeamsDialog
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomAlertDialog
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomCard
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomToolbarTitle
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenHalperTheme
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun TeamEditScreen(
    uiState: TeamEditStateUi,
    onNameChange: (String) -> Unit,
    onTogglePack: (PackType) -> Unit,
    back: () -> Unit,
    showDeleteDialog: () -> Unit,
    dismissDeleteDialog: () -> Unit,
    confirmDelete: () -> Unit,
    showTeamListDialog: () -> Unit,
    dismissTeamListDialog: () -> Unit,
    selectTeam: (Int) -> Unit,
) = Scaffold(
    topBar = {
        GloomToolbarTitle(
            title = "Редактирование команды",
            back = back,
            actions = {
                IconButton(onClick = showDeleteDialog) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        )
    },
) { paddingValues ->
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        var localName by remember(uiState.teamName) { mutableStateOf(uiState.teamName) }
        OutlinedTextField(
            value = localName,
            onValueChange = { newValue ->
                localName = newValue
                onNameChange(newValue)
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Название команды") },
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Дополнения",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
        )

        uiState.availablePacks.forEach { packItem ->
            GloomCard {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = packItem.displayName,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.weight(1f),
                    )
                    Checkbox(
                        checked = packItem.isEnabled,
                        onCheckedChange = { onTogglePack(packItem.pack) },
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        if (uiState.showChangeTeamButton) {
            OutlinedButton(
                onClick = showTeamListDialog,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(text = "Сменить команду")
            }
        }
    }

    if (uiState.showDeleteConfirmDialog) {
        DeleteTeamDialog(
            teamName = uiState.teamName,
            onDismiss = dismissDeleteDialog,
            onConfirm = confirmDelete,
        )
    }

    if (uiState.showTeamListDialog) {
        TeamsDialog(
            teams = uiState.teamsForSelect,
            onDismiss = dismissTeamListDialog,
            selectTeam = selectTeam,
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1A1C24)
@Composable
private fun TeamEditScreenPreview() {
    GloomhavenHalperTheme {
        TeamEditScreen(
            uiState = TeamEditStateUi(
                teamName = "Моя команда",
                availablePacks = persistentListOf(
                    PackItemUi(
                        pack = PackType.FORGOTTEN_CIRCLES,
                        isEnabled = false,
                    ),
                ),
            ),
            onNameChange = {},
            onTogglePack = {},
            back = {},
            showDeleteDialog = {},
            dismissDeleteDialog = {},
            confirmDelete = {},
            showTeamListDialog = {},
            dismissTeamListDialog = {},
            selectTeam = {},
        )
    }
}
