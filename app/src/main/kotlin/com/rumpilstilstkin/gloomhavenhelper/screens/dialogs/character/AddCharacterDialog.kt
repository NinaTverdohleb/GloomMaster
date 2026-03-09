package com.rumpilstilstkin.gloomhavenhelper.screens.dialogs.character

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterClassType
import com.rumpilstilstkin.gloomhavenhelper.screens.models.CharacterClassTypeUI
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomAlertDialog
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomVariantCard
import com.rumpilstilstkin.gloomhavenhelper.ui.components.NumberPicker
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenHalperTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun AddCharacterDialog(
    avaliableClasses: ImmutableList<CharacterClassTypeUI>,
    onDismiss: () -> Unit,
    addCharacter: (name: String, level: Int, characterClass: CharacterClassTypeUI) -> Unit
) {
    var newCharacterName by rememberSaveable { mutableStateOf("") }
    var selectedClass by remember { mutableStateOf<CharacterClassTypeUI?>(null) }
    var level by remember { mutableIntStateOf(1) }

    GloomAlertDialog(
        confirmEnabled = selectedClass != null,
        confirmText = "Добавтить",
        onDismissRequest = onDismiss,
        onConfirmRequest = {
            selectedClass?.let {
                addCharacter(
                    newCharacterName,
                    level,
                    it
                )
            }
        }
    ) {
        GloomVariantCard {
            LazyVerticalGrid(
                modifier = Modifier.Companion.padding(4.dp),
                columns = GridCells.Adaptive(48.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(avaliableClasses) { classType ->
                    val isSelected = classType == selectedClass
                    Icon(
                        painter = painterResource(id = classType.image),
                        contentDescription = classType.title,
                        modifier = Modifier.Companion
                            .size(32.dp)
                            .clickable {
                                selectedClass = if (isSelected) {
                                    null
                                } else {
                                    classType
                                }
                            },
                        tint = if (isSelected) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.outlineVariant
                        },
                    )
                }
            }
        }

        selectedClass?.let {
            Text(
                modifier = Modifier.Companion.fillMaxWidth(),
                text = it.title,
                textAlign = TextAlign.Companion.Center
            )
        }

        OutlinedTextField(
            value = newCharacterName,
            onValueChange = { newCharacterName = it },
            label = { Text("Имя") }
        )
        Text("Уровень персонажа")
        NumberPicker(
            modifier = Modifier.Companion.fillMaxWidth(),
            value = level,
            intRange = IntRange(1, 9)
        ) {
            level = it
        }
    }
}

@Composable
private fun CharacterDialog(
    selectedIndex: Int,
    characterName: String,
    level: Int,
    classes: List<CharacterClassTypeUI>,
    onClassSelect: (Int) -> Unit,
    onCharacterNameChanged: (String) -> Unit,
    onDismiss: () -> Unit,
    onLevelChanged: (Int) -> Unit,
    onAdd: (String, Int, CharacterClassType) -> Unit,
) {
    AlertDialog(
        onDismissRequest = { onDismiss.invoke() },
        title = {
            Text(
                modifier = Modifier.Companion.fillMaxWidth(),
                text = "Добавить персонажа",
                textAlign = TextAlign.Companion.Center
            )
        },
        text = {
            Surface {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.Companion.CenterHorizontally
                ) {
                    DropdownWithIconAndText(
                        modifier = Modifier.Companion,
                        items = classes,
                        selectedIndex = selectedIndex
                    ) {
                        onClassSelect.invoke(classes.indexOf(it))
                        //selectedIndex = classes.indexOf(it)
                    }
                    OutlinedTextField(
                        value = characterName,
                        onValueChange = {
                            onCharacterNameChanged.invoke(it)
                        },
                        label = { Text("Имя") }
                    )
                    Text("Уровень персонажа")
                    NumberPicker(
                        modifier = Modifier.Companion.fillMaxWidth(),
                        value = level,
                        intRange = IntRange(1, 9)
                    ) {
                        onLevelChanged(it)
                    }
                }
            }
        },
        confirmButton = {
            Button(
                modifier = Modifier.Companion.fillMaxWidth(),
                onClick = {
                    onAdd.invoke(
                        characterName,
                        level,
                        classes[selectedIndex].type
                    )
                }
            ) {
                Text("Добавить")
            }
        }
    )
}

@Composable
fun DropdownWithIconAndText(
    items: List<CharacterClassTypeUI>,
    selectedIndex: Int,
    modifier: Modifier = Modifier.Companion,
    onItemSelected: (CharacterClassTypeUI) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .wrapContentSize(Alignment.Companion.Center),
        contentAlignment = Alignment.Companion.Center
    ) {
        // Кнопка, которая открывает выпадающее меню
        IconButton(
            modifier = Modifier.Companion
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = RectangleShape
                )
                .padding(6.dp),
            onClick = { expanded = !expanded }
        ) {
            Icon(
                modifier = Modifier.Companion
                    .size(72.dp),
                painter = painterResource(id = items[selectedIndex].image),
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
                        Row(verticalAlignment = Alignment.Companion.CenterVertically) {
                            Icon(
                                painter = painterResource(id = items[index].image),
                                contentDescription = null,
                                modifier = Modifier.Companion.size(24.dp)
                            )
                            Spacer(modifier = Modifier.Companion.width(8.dp))
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
private fun AddCharacterDialogPreview() {
    GloomhavenHalperTheme {
        AddCharacterDialog(
            avaliableClasses = persistentListOf(
                CharacterClassTypeUI.Brute,
                CharacterClassTypeUI.Spellweaver
            ),
            onDismiss = {},
            addCharacter = { _, _, _ -> }
        )
    }
}