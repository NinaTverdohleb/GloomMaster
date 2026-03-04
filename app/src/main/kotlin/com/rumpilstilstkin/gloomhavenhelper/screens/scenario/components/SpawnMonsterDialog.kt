package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomAlertDialog
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenHalperTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpawnMonsterDialog(
    monsterName: String,
    availableIds: List<Int>,
    onDismiss: () -> Unit,
    onSpawn: (numbers: List<Int>, isElite: Boolean) -> Unit,
) {
    var selectedTier by remember { mutableStateOf(UnitTier.Normal) }
    var selectedIds by remember { mutableStateOf<List<Int>>(emptyList()) }
    GloomAlertDialog(
        title = monsterName,
        titleIcon = Icons.Default.Person,
        onDismissRequest = onDismiss,
        onConfirmRequest = {
            onSpawn(selectedIds, selectedTier == UnitTier.Elite)
        },
        confirmEnabled = selectedIds.isNotEmpty()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            TierSelector(
                selected = selectedTier,
                onSelect = { selectedTier = it },
            )

            // ── Assign Unit ID ─────────────────────────────────────────────
            UnitIdGrid(
                ids = availableIds,
                selectedIds = selectedIds,
                onSelect = { number ->
                    selectedIds = if (selectedIds.contains(number)) {
                        selectedIds - number
                    } else {
                        selectedIds + number
                    }
                },
            )
        }
    }
}

// ─── Tier selector ────────────────────────────────────────────────────────────

enum class UnitTier(val text: String) { Normal("Обычные"), Elite("Элитные") }

@Composable
private fun TierSelector(
    selected: UnitTier,
    onSelect: (UnitTier) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        UnitTier.entries.forEach { tier ->
            val isSelected = tier == selected
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(6.dp))
                    .background(
                        if (isSelected) MaterialTheme.colorScheme.secondary else Color.Transparent
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
                        if (isSelected) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant
                    if (tier == UnitTier.Elite) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = contentColor,
                            modifier = Modifier.size(12.dp),
                        )
                    }
                    Text(
                        text = tier.text,
                        style = MaterialTheme.typography.bodyMedium,
                        color = contentColor,
                    )
                }
            }
        }
    }
}

// ─── Unit ID grid ─────────────────────────────────────────────────────────────

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
        modifier = modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(8.dp))
            .background(
                if (isSelected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.secondaryContainer
                }
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = id.toString(),
            fontSize = 14.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = if (isSelected) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Preview
@Composable
private fun SpawnMonsterDialogPreview() {
    GloomhavenHalperTheme {
        SpawnMonsterDialog(
            monsterName = "Хвостожабка",
            availableIds = (1..14).toList(),
            onDismiss = { },
            onSpawn = { _, _ -> },
        )
    }
}