package com.rumpilstilstkin.gloomhavenhelper.screens.characters.perks

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rumpilstilstkin.gloomhavenhelper.screens.models.PerkUI
import com.rumpilstilstkin.gloomhavenhelper.ui.perks.perkEffectsInlineContentMap
import com.rumpilstilstkin.gloomhavenhelper.ui.perks.replacePerkTextWithIcons
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenHalperTheme
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

@Composable
fun CharacterPerksTab(
    characterId: Int,
    modifier: Modifier = Modifier
) {
    val viewModel =
        hiltViewModel<CharacterPerksTabViewModel, CharacterPerksTabViewModel.Factory> { factory ->
            factory.create(characterId)
        }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    CharacterPerkScreen(
        uiState = uiState,
        onAction = viewModel::onAction,
        modifier = modifier
    )
}

@Composable
private fun CharacterPerkScreen(
    uiState: CharacterPerksScreenState,
    onAction: (CharacterPerksTabActions) -> Unit,
    modifier: Modifier = Modifier,
) {
    var showAddPerksDialog by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showAddPerksDialog = true
                },
                content = {
                    Icon(Icons.Filled.Add, "Добавить навык")
                }
            )
        },
        content = { innerPadding ->
            AddPerksDialog(
                avaliablePerks = uiState.avaliablePerks,
                showDialog = showAddPerksDialog,
                onDismiss = { showAddPerksDialog = false },
                onPerkSelected = {
                    onAction(CharacterPerksTabActions.AddPerks(it))
                    showAddPerksDialog = false
                }
            )
            Column(
                modifier.padding(innerPadding)
            ) {
                Text(
                    text = "Доступно для добавления: ${uiState.avaliablePerksCount}",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                CharacterPerksList(
                    perks = uiState.characterPerks,
                ) {
                    onAction(CharacterPerksTabActions.DeletePerk(it))
                }
            }
        }
    )
}

@Composable
private fun AddPerksDialog(
    avaliablePerks: List<PerkUI>,
    showDialog: Boolean,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onPerkSelected: (List<Int>) -> Unit,
) {
    val checkedPerks: MutableList<Int> = mutableListOf()
    if (showDialog) {
        AlertDialog(
            modifier = modifier,
            onDismissRequest = { onDismiss.invoke() },
            title = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Доступные навыки",
                    textAlign = TextAlign.Center
                )
            },
            text = {
                LazyColumn(
                    modifier = modifier.fillMaxWidth()
                ) {
                    items(avaliablePerks) { perk ->
                        AddPerkDialogItem(
                            perk = perk,
                            onSelectedChanged = {
                                if (checkedPerks.contains(it)) {
                                    checkedPerks.remove(it)
                                } else {
                                    checkedPerks.add(it)
                                }
                            }
                        )
                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 12.dp),
                            thickness = 1.dp,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onPerkSelected(checkedPerks) }
                ) {
                    Text("Добавить")
                }
            },
            dismissButton = {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onDismiss() }
                ) {
                    Text("Закрыть")
                }
            }
        )
    }

}

@Composable
private fun AddPerkDialogItem(
    perk: PerkUI,
    modifier: Modifier = Modifier,
    onSelectedChanged: (Int) -> Unit,
) {
    var isChecked by remember { mutableStateOf(false) }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                isChecked = !isChecked
                onSelectedChanged(perk.id)
            },
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides 0.dp) {
            Checkbox(
                modifier = Modifier.padding(),
                checked = isChecked,
                onCheckedChange = {
                    isChecked = !isChecked
                    onSelectedChanged(perk.id)
                },
                colors = CheckboxDefaults.colors().copy(
                    uncheckedBorderColor = MaterialTheme.colorScheme.primary,
                )
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        PerkText(text = perk.text)
    }

}

@Composable
private fun CharacterPerksList(
    perks: List<PerkUI>,
    modifier: Modifier = Modifier,
    onDeleted: (Int) -> Unit
) {

    LazyColumn(
        modifier = modifier.fillMaxWidth()
    ) {
        items(perks) { perk ->
            PerkItem(
                perk = perk,
                onDeleted = onDeleted
            )
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 12.dp),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.outline
            )
        }
    }

}

@Composable
fun PerkItem(
    perk: PerkUI,
    modifier: Modifier = Modifier,
    onDeleted: (Int) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        PerkText(modifier = Modifier.weight(1f, fill = false), text = perk.text)
        IconButton(
            onClick = { perk.characterPerId?.let { onDeleted(it) } }) {
            Icon(Icons.Filled.Delete, "Удалить навык", tint = MaterialTheme.colorScheme.error)
        }
    }

}


@Composable
fun PerkText(
    text: String,
    modifier: Modifier = Modifier
) {
    val textWithIcons = replacePerkTextWithIcons(text)

    Text(
        modifier = modifier.padding(4.dp),
        text = textWithIcons, inlineContent = perkEffectsInlineContentMap,
        color = Color.Black,
        lineHeight = 28.sp
    )
}

@Preview
@Composable
private fun Sample() {
    GloomhavenHalperTheme {
        CharacterPerkScreen(
            uiState = CharacterPerksScreenState(
                characterPerks = listOf(
                    PerkUI(1, "Уберите две карты #01"),
                    PerkUI(
                        2,
                        "Поменяйте 1 карту #01 на 1 карту #03 hgdsjfhgasjkhdgfkjahgsdfjhgsadjfhgakjshdgfjahgsdfjkh"
                    ),
                    PerkUI(3, "Добавьте две карты #03"),
                ).toImmutableList(),
                avaliablePerks = persistentListOf(),
                avaliablePerksCount = 4
            ),
            onAction = {}
        )
    }
}

@Preview
@Composable
private fun SampleAddPerksDialog() {
    GloomhavenHalperTheme {
        AddPerksDialog(
            avaliablePerks = listOf(
                PerkUI(1, "Уберите две карты #01"),
                PerkUI(2, "Поменяйте 1 карту #01 на 1 карту #03 "),
                PerkUI(3, "Добавьте две карты #03"),
            ),
            showDialog = true,
            onDismiss = {},
            onPerkSelected = {}
        )
    }
}