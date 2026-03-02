package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.screens.models.MonsterItem
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomAlertDialog
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenHalperTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonsterListDialog(
    showDialog: Boolean,
    monsters: List<MonsterItem>,
    selectMonster: (List<Int>) -> Unit,
    onDismiss: () -> Unit,
) {
    var selectedIds by remember { mutableStateOf(emptyList<Int>()) }

    if (showDialog) {
        GloomAlertDialog(
            onDismissRequest = onDismiss,
            onConfirmRequest = {
                selectMonster(selectedIds)
            },
            confirmEnabled = selectedIds.isNotEmpty(),
            title = "Выберете врагов"
        ) {
            Column {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 420.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(monsters.size) { index ->
                        MonsterClassItem(
                            name = monsters[index].name,
                            isSelected = selectedIds.contains(monsters[index].id),
                            onClick = {
                                selectedIds = if (selectedIds.contains(monsters[index].id)) {
                                    selectedIds - monsters[index].id
                                } else {
                                    selectedIds + monsters[index].id
                                }
                            },
                        )
                    }
                }

            }
        }
    }
}

@Composable
private fun MonsterClassItem(
    name: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    val borderColor = if(isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
    val borderWidth = if(isSelected) 1.5.dp else 1.dp
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer).border(
                borderWidth,
                borderColor,
                RoundedCornerShape(12.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 18.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
        )
        if (isSelected) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp),
            )
        }
    }
}

@Preview
@Composable
private fun MonsterListDialogPreview() {
    GloomhavenHalperTheme {
        MonsterListDialog(
            showDialog = true,
            monsters = listOf(
                MonsterItem.fixture(1, "Скелет"),
                MonsterItem.fixture(2, "Зомби"),
            ),
            onDismiss = {},
            selectMonster = {},
        )
    }
}