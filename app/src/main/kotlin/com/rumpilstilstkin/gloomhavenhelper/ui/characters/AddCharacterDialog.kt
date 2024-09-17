package com.rumpilstilstkin.gloomhavenhelper.ui.characters

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MonotonicFrameClock
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.screens.models.CharacterClassUI
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenHalperTheme
import com.rumpilstilstkin.gloomhavenhelper.ui.view.NumberPicker

@Composable
fun AddCharacterDialog(
    showDialog: Boolean,
    classes: List<CharacterClassUI>,
    onDismiss: () -> Unit,
    onAdd: (String, Int, Int) -> Unit
) {
    if (showDialog) {
        var newCharacterName by rememberSaveable { mutableStateOf("") }
        var selectedIndex by remember { mutableIntStateOf(0) }
        var level by remember { mutableIntStateOf(0) }
        AlertDialog(
            onDismissRequest = { onDismiss.invoke() },
            title = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Добавить персонажа",
                    textAlign = TextAlign.Center
                )
            },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    DropdownWithIconAndText(
                        modifier = Modifier,
                        items = classes,
                        selectedIndex = selectedIndex
                    ) {
                        selectedIndex = classes.indexOf(it)
                    }
                    OutlinedTextField(
                        value = newCharacterName,
                        onValueChange = { newCharacterName = it },
                        label = { Text("Имя") }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
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
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        onAdd.invoke(
                            newCharacterName,
                            level,
                            classes[selectedIndex].id
                        )
                    }
                ) {
                    Text("Добавить")
                }
            }
        )
    }
}

@Composable
fun DropdownWithIconAndText(
    items: List<CharacterClassUI>,
    selectedIndex: Int,
    modifier: Modifier = Modifier,
    onItemSelected: (CharacterClassUI) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        // Кнопка, которая открывает выпадающее меню
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                painter = painterResource(id = items[selectedIndex].imageRes),
                contentDescription = null,
                modifier = Modifier.size(48.dp)
            )
        }

        // Выпадающее меню
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEachIndexed { index, item ->
                DropdownMenuItem(
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(id = items[index].imageRes),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = item.name)
                        }
                    },
                    onClick = {
                        expanded = false
                        onItemSelected(item)
                    })
            }
        }
    }
}

@Preview
@Composable
private fun Sample() {
    GloomhavenHalperTheme {
        AddCharacterDialog(
            showDialog = true,
            classes = listOf(
                CharacterClassUI(
                    id = 1,
                    name = "Воин",
                    imageRes = R.drawable.br
                )
            ),
            onDismiss = {},
            onAdd = { _, _, _ -> }
        )
    }

}