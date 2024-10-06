package com.rumpilstilstkin.gloomhavenhelper.screens.dialogs.character.add

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterClassType
import com.rumpilstilstkin.gloomhavenhelper.screens.models.CharacterClassUI
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenHalperTheme
import com.rumpilstilstkin.gloomhavenhelper.ui.view.NumberPicker

@Composable
fun AddCharacterDialog(
    showDialog: Boolean,
    viewModel: AddCharactersDialogViewModel = hiltViewModel(),
    onDismiss: () -> Unit,
    onAdd: (String, Int, CharacterClassType) -> Unit
) {
    val classes = viewModel.classes
    if (showDialog) {
        var newCharacterName by rememberSaveable { mutableStateOf("") }
        var selectedIndex by remember { mutableIntStateOf(0) }
        var level by remember { mutableIntStateOf(1) }

        if (classes.isEmpty()) return

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
                Surface {

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
                        Text("Уровень персонажа")
                        NumberPicker(
                            modifier = Modifier.fillMaxWidth(),
                            value = level,
                            intRange = IntRange(1, 9)
                        ) {
                            level = it
                        }
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
                            classes[selectedIndex].classType
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
        modifier = modifier
            .wrapContentSize(Alignment.Center),
        contentAlignment = Alignment.Center
    ) {
        // Кнопка, которая открывает выпадающее меню
        IconButton(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = RectangleShape
                )
                .padding(6.dp),
            onClick = { expanded = !expanded }
        ) {
            Icon(
                modifier = Modifier
                    .size(72.dp),
                painter = painterResource(id = items[selectedIndex].imageRes),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary,

                )
        }

        // Выпадающее меню
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
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
        DropdownWithIconAndText(
            items = listOf(
                CharacterClassUI(
                    name = "Allan Ball",
                    classType = CharacterClassType.Plagueherald,
                    imageRes = R.drawable.ph
                ),
                CharacterClassUI(
                    name = "Allan Ball2",
                    classType = CharacterClassType.Brute,
                    imageRes = R.drawable.br
                )
            ),
            selectedIndex = 0,
            onItemSelected = {}
        )
    }

}