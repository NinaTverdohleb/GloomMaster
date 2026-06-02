package com.rumpilstilstkin.gloomhavenhelper.screens.dialogs.character

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.screens.models.CharacterClassTypeUI
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomAlertDialog
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomVariantCard
import com.rumpilstilstkin.gloomhavenhelper.ui.components.NumberPicker
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenMasterTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun AddCharacterDialog(
    avaliableClasses: ImmutableList<CharacterClassTypeUI>,
    onDismiss: () -> Unit,
    addCharacter: (name: String, level: Int, characterClass: CharacterClassTypeUI) -> Unit,
) {
    var newCharacterName by rememberSaveable { mutableStateOf("") }
    var selectedClass by remember { mutableStateOf(avaliableClasses.firstOrNull()) }
    var level by remember { mutableIntStateOf(1) }

    GloomAlertDialog(
        confirmEnabled = selectedClass != null,
        confirmText = stringResource(R.string.add),
        onDismissRequest = onDismiss,
        onConfirmRequest = {
            selectedClass?.let {
                addCharacter(
                    newCharacterName,
                    level,
                    it,
                )
            }
        },
    ) {
        GloomVariantCard {
            LazyVerticalGrid(
                modifier = Modifier.padding(4.dp),
                columns = GridCells.Adaptive(48.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(avaliableClasses) { classType ->
                    val isSelected = classType == selectedClass
                    Icon(
                        painter = painterResource(id = classType.image),
                        contentDescription = stringResource(classType.titleRes),
                        modifier =
                            Modifier
                                .size(32.dp)
                                .clickable {
                                    selectedClass =
                                        if (isSelected) {
                                            null
                                        } else {
                                            classType
                                        }
                                },
                        tint =
                            if (isSelected) {
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
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(it.titleRes),
                textAlign = TextAlign.Center,
            )
        }

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = newCharacterName,
            onValueChange = { newCharacterName = it },
            label = { Text(stringResource(R.string.name_label)) },
        )
        Text(stringResource(R.string.character_level_label))
        NumberPicker(
            modifier = Modifier.fillMaxWidth(),
            value = level,
            intRange = IntRange(1, 9),
        ) {
            level = it
        }
    }
}

@Preview
@Composable
private fun AddCharacterDialogPreview() {
    GloomhavenMasterTheme {
        AddCharacterDialog(
            avaliableClasses =
                persistentListOf(
                    CharacterClassTypeUI.Brute,
                    CharacterClassTypeUI.Spellweaver,
                ),
            onDismiss = {},
            addCharacter = { _, _, _ -> },
        )
    }
}
