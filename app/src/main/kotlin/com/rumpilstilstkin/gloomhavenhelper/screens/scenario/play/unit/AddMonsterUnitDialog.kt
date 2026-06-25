package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.unit

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.buttons.GloomOutlineButton
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.text.GloomHeader
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.AppIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMonsterUnitDialog(
    monsterName: String,
    availableIds: List<Int>,
    selectedTier: UnitTier,
    selectedIds: List<Int>,
    selectTier: (UnitTier) -> Unit,
    toggleId: (Int) -> Unit,
    spawn: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        GloomHeader(
            monsterName
        )
        TierSelector(
            selected = selectedTier,
            onSelect = selectTier,
        )

        UnitIdGrid(
            ids = availableIds,
            selectedIds = selectedIds,
            onSelect = toggleId,
        )
        GloomOutlineButton(
            modifier = Modifier.fillMaxWidth(),
            icon = AppIcon.Check,
            text = stringResource(R.string.ok),
            onClick = spawn,
        )
    }
}


enum class UnitTier(
    @param:StringRes val textRes: Int,
) {
    Normal(R.string.tier_normal),
    Elite(R.string.tier_elite),
}

@Composable
private fun TierSelector(
    selected: UnitTier,
    onSelect: (UnitTier) -> Unit,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.surfaceContainerHighest)
                .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        UnitTier.entries.forEach { tier ->
            val isSelected = tier == selected
            Box(
                modifier =
                    Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(6.dp))
                        .background(
                            if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
                        )
                        .clickable { onSelect(tier) }
                        .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    val contentColor =
                        if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                    Text(
                        text = stringResource(tier.textRes),
                        style = MaterialTheme.typography.bodyMedium,
                        color = contentColor,
                    )
                }
            }
        }
    }
}


@Composable
private fun UnitIdGrid(
    ids: List<Int>,
    selectedIds: List<Int>,
    onSelect: (Int) -> Unit,
) {
    val columns = 5
    val rows = ids.chunked(columns)

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        rows.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                row.forEach { id ->
                    UnitIdCell(
                        id = id,
                        isSelected = selectedIds.contains(id),
                        onClick = { onSelect(id) },
                        modifier = Modifier.weight(1f),
                    )
                }
                // fill empty slots in last row
                repeat(columns - row.size) {
                    Spacer(Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
private fun UnitIdCell(
    id: Int,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            modifier
                .aspectRatio(1f)
                .clip(RoundedCornerShape(8.dp))
                .background(
                    if (isSelected) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        Color.Unspecified
                    },
                )
                .border(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.onSurface,
                    width = 1.dp,
                )
                .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = id.toString(),
            style = MaterialTheme.typography.labelLarge,
            color = if (isSelected) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Preview
@Composable
private fun AddMonsterUnitDialogPreview() {
    GloomhavenMasterTheme {
        Box(
            Modifier
                .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                .padding(16.dp)
        ) {
            AddMonsterUnitDialog(
                monsterName = "Living Bones",
                availableIds = (1..15).toList(),
                selectedTier = UnitTier.Normal,
                selectedIds = listOf(1, 5),
                selectTier = {},
                toggleId = {},
                spawn = {},
            )
        }
    }
}
