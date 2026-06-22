package com.rumpilstilstkin.gloomhavenhelper.screens.teem.edit

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.DifficultyLevel
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.PackType
import com.rumpilstilstkin.gloomhavenhelper.screens.dialogs.teams.DeleteTeamDialog
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomCard
import com.rumpilstilstkin.gloomhavenhelper.ui.components.toolbar.GloomToolbar
import com.rumpilstilstkin.gloomhavenhelper.ui.team.toImage
import com.rumpilstilstkin.gloomhavenhelper.ui.team.toLabel
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenMasterTheme
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun TeamEditScreen(
    uiState: TeamEditStateUi,
    onNameChange: (String) -> Unit,
    onTogglePack: (PackType) -> Unit,
    onDifficultyChange: (DifficultyLevel) -> Unit,
    back: () -> Unit,
    showDeleteDialog: () -> Unit,
    dismissDeleteDialog: () -> Unit,
    confirmDelete: () -> Unit,
) = Scaffold(
    topBar = {
        GloomToolbar(
            title = "",
            back = back,
        )
    },
) { paddingValues ->
    Column(
        modifier =
            Modifier
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
            label = { Text(stringResource(R.string.team_name_label)) },
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(8.dp))

        DifficultySelector(
            selected = uiState.difficultyLevel,
            onSelect = onDifficultyChange,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(R.string.expansions_title),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
        )

        uiState.availablePacks.forEach { packItem ->
            GloomCard {
                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = stringResource(packItem.displayNameRes),
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

        OutlinedButton(
            onClick = showDeleteDialog,
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.error),
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                color = MaterialTheme.colorScheme.error,
                text = stringResource(R.string.delete),
            )
        }
    }

    if (uiState.showDeleteConfirmDialog) {
        DeleteTeamDialog(
            teamName = uiState.teamName,
            onDismiss = dismissDeleteDialog,
            onConfirm = confirmDelete,
        )
    }
}

@Composable
fun DifficultySelector(
    selected: DifficultyLevel,
    onSelect: (DifficultyLevel) -> Unit,
    modifier: Modifier = Modifier,
) {
    val items = remember { DifficultyLevel.entries }

    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.difficulty_level_label),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Spacer(Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            items.forEach { level ->
                DifficultyItem(
                    level = level,
                    isSelected = level == selected,
                    onClick = { onSelect(level) },
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}

@Composable
private fun DifficultyItem(
    level: DifficultyLevel,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val (icon, tint) = level.toImage()
    val bgColor =
        if (isSelected) {
            tint.copy(alpha = 0.12f)
        } else {
            MaterialTheme.colorScheme.surface
        }
    val borderColor =
        if (isSelected) {
            tint
        } else {
            MaterialTheme.colorScheme.outlineVariant
        }

    Surface(
        onClick = onClick,
        modifier = modifier,
        shape = MaterialTheme.shapes.small,
        color = bgColor,
        border = BorderStroke(0.5.dp, borderColor),
    ) {
        Column(
            modifier =
                Modifier
                    .padding(vertical = 10.dp, horizontal = 6.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(3.dp),
        ) {
            Icon(icon, contentDescription = null, tint = tint, modifier = Modifier.size(18.dp))
            Text(
                text = level.toLabel(),
                style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1A1C24)
@Composable
private fun TeamEditScreenPreview() {
    GloomhavenMasterTheme {
        TeamEditScreen(
            uiState =
                TeamEditStateUi(
                    teamName = "My Team",
                    availablePacks =
                        persistentListOf(
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
            onDifficultyChange = {},
        )
    }
}
