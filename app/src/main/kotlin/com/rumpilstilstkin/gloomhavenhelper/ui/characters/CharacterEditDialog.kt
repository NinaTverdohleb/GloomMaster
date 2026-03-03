package com.rumpilstilstkin.gloomhavenhelper.ui.characters

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenHalperTheme
import com.rumpilstilstkin.gloomhavenhelper.ui.components.NumberPicker

@Composable
fun CharacterEditDialog(
    characterName: String,
    @DrawableRes
    classImage: Int,
    showDialog: Boolean,
    startLevel: Int,
    onDismiss: () -> Unit,
    onSave: (Int) -> Unit,
    onDelete: () -> Unit,
    onLeave: () -> Unit
) {
    if (showDialog) {
        var level by remember { mutableIntStateOf(startLevel) }
        AlertDialog(
            onDismissRequest = { onDismiss.invoke() },
            title = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = classImage),
                            contentDescription = null,
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            modifier = Modifier,
                            text = characterName,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Уровень персонажа")
                    NumberPicker(
                        modifier = Modifier.fillMaxWidth(),
                        value = level,
                        intRange = IntRange(0, 9)
                    ) {
                        level = it
                    }
                }
            },
            confirmButton = {
                Column {
                    ElevatedButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            onLeave.invoke()
                        }
                    ) {
                        Text("На покой")
                    }
                    FilledTonalButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            onDelete.invoke()
                        }
                    ) {
                        Text("Удалить")
                    }

                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            onSave.invoke(level)
                        }
                    ) {
                        Text("Сохранить")
                    }
                }
            },

            )
    }
}

@Preview
@Composable
private fun Sample() {
    GloomhavenHalperTheme {
        CharacterEditDialog(
            showDialog = true,
            characterName = "Brute",
            classImage = R.drawable.br,
            startLevel = 1,
            onDismiss = {},
            onSave = { _ -> },
            onDelete = {},
            onLeave = {}
        )
    }
}