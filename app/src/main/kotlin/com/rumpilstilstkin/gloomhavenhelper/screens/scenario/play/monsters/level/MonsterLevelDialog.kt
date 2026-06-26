package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.monsters.level

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.buttons.GloomOutlineButton
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.counter.GloomCounterFull
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.items.GloomListItem
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.items.LeftItemNumber
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.AppIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme

@Composable
fun MonsterLevelDialog(
    unitLevel: Int,
    unitNumber: Int,
    monsterName: String,
    changeLevel: (Int) -> Unit,
) {
    var level by remember { mutableIntStateOf(unitLevel) }
    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        GloomListItem(
            title = monsterName,
            leftComponent = {
                LeftItemNumber(
                    number = unitNumber.toString()
                )
            }
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Level",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
            GloomCounterFull(
                value = level,
                intRange = IntRange(0, 7),
                onValueChange = {level = it},
            )
        }
        GloomOutlineButton(
            modifier = Modifier.fillMaxWidth(),
            icon = AppIcon.Check,
            text = stringResource(R.string.ok),
            onClick = {changeLevel(level)},
        )
    }
}

@Preview
@Composable
private fun MonsterLevelDialogPreview() {
    GloomhavenMasterTheme {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            MonsterLevelDialog(
                unitLevel = 1,
                unitNumber = 5,
                monsterName = "Living bones",
                changeLevel = {},
            )
        }
    }
}
