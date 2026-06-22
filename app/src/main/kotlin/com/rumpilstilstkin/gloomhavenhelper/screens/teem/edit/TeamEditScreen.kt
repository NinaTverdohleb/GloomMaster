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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomCard
import com.rumpilstilstkin.gloomhavenhelper.ui.components.items.GloomListFilledItem
import com.rumpilstilstkin.gloomhavenhelper.ui.components.items.GloomListItem
import com.rumpilstilstkin.gloomhavenhelper.ui.components.items.RightItemChecker
import com.rumpilstilstkin.gloomhavenhelper.ui.components.text.GloomHeader
import com.rumpilstilstkin.gloomhavenhelper.ui.components.text.GloomOutlinedTextField
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
) = Scaffold(
    topBar = {
        GloomToolbar(
            title = stringResource(R.string.team_settings),
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
    ) {
        var localName by remember(uiState.teamName) { mutableStateOf(uiState.teamName) }
        Spacer(modifier = Modifier.height(16.dp))
        GloomOutlinedTextField(
            value = localName,
            onValueChange = { newValue ->
                localName = newValue
                onNameChange(newValue)
            },
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(R.string.team_name_label),
        )

        Spacer(modifier = Modifier.height(32.dp))

        GloomHeader(
            text = stringResource(R.string.difficulty_level_label),
        )
        Spacer(Modifier.height(16.dp))

        DifficultySelector(
            selected = uiState.difficultyLevel,
            onSelect = onDifficultyChange,
        )

        Spacer(modifier = Modifier.height(32.dp))

        GloomHeader(text = stringResource(R.string.expansions_title))

        Spacer(modifier = Modifier.height(16.dp))
        uiState.availablePacks.forEach { packItem ->
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                GloomListFilledItem(
                    title = stringResource(packItem.displayNameRes),
                    rightComponent = {
                        RightItemChecker(
                            checked = packItem.isEnabled,
                            onCheckedChange = { onTogglePack(packItem.pack) },
                        )
                    },
                )
            }
        }
    }
}

@Composable
fun DifficultySelector(
    selected: DifficultyLevel,
    onSelect: (DifficultyLevel) -> Unit,
) {
    val items = remember { DifficultyLevel.entries }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        items.forEach { level ->
            DifficultyItem(
                level = level,
                isSelected = level == selected,
                onClick = { onSelect(level) },
            )
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
    val icon = level.toImage()
    val contentColor =
        if (isSelected) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.onSurfaceVariant
        }

    Surface(
        onClick = onClick,
        modifier = modifier.width(84.dp),
        shape = MaterialTheme.shapes.large,
        color = MaterialTheme.colorScheme.surfaceContainer,
        border = BorderStroke(1.dp, contentColor),
    ) {
        Column(
            modifier =
                Modifier
                    .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = contentColor,
                modifier = Modifier.size(32.dp)
            )
            Text(
                text = level.toLabel(),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = contentColor
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
            onDifficultyChange = {},
        )
    }
}
