package com.rumpilstilstkin.gloomhavenhelper.ui.scenario

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.LevelInfo

@Composable
fun ScenarioLevelInfoDialog(
    showDialog: Boolean,
    levelInfo: LevelInfo,
    onDismiss: () -> Unit,
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { onDismiss.invoke() },
            title = { Text("Информация о уровне") },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text("Уровень монстров: ${levelInfo.monsterLevel}")
                    Text("ОКоличество золота за монету: ${levelInfo.goldCount}")
                    Text("Урон от ловушки: ${levelInfo.trapDamage}")
                    Text("Опыт: ${levelInfo.experience}")
                }
            },
            confirmButton = {
            }
        )
    }

}