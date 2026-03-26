package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomAlertDialog
import com.rumpilstilstkin.gloomhavenhelper.ui.components.NumberPicker
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenMasterTheme

@Composable
fun MonsterLevelDialog(
    unitLevel: Int,
    monsterName: String,
    dismiss: () -> Unit,
    changeLevel: (Int) -> Unit,
) {
    var level by remember { mutableIntStateOf(unitLevel) }
    GloomAlertDialog(
        onDismissRequest = dismiss,
        onConfirmRequest = { changeLevel(level) },
        onNeutralRequest = null,
        confirmText = "Сохранить",
        content = {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        modifier = Modifier,
                        text = monsterName,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    NumberPicker(
                        modifier = Modifier.fillMaxWidth(),
                        value = level,
                        intRange = IntRange(0, 7)
                    ) {
                        level = it
                    }
                }
            }
        }
    )
}

@Preview
@Composable
private fun MonsterLevelDialogPreview() {
    GloomhavenMasterTheme {
        MonsterLevelDialog(
            unitLevel = 1,
            monsterName = "Персонаж",
            dismiss = {},
            changeLevel = {}
        )
    }
}