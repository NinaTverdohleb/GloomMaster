package com.rumpilstilstkin.gloomhavenhelper.screens.teem.create

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterClassType
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEventHelper
import com.rumpilstilstkin.gloomhavenhelper.screens.dialogs.character.add.AddCharacterDialog
import com.rumpilstilstkin.gloomhavenhelper.screens.models.CharacterClassUI
import com.rumpilstilstkin.gloomhavenhelper.screens.models.CharacterUI
import com.rumpilstilstkin.gloomhavenhelper.ui.characters.CharacterWithDialogList
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenHalperTheme

@Composable
fun TeamCreateScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: TeamCreateViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val navigationEvents by viewModel.navigationEvents.collectAsStateWithLifecycle(initialValue = null)

    LaunchedEffect(navigationEvents) {
        navigationEvents?.let { event ->
            GlHelperEventHelper.event(
                event = event,
                navController = navController
            )
        }
    }

    AddCharacterDialog(
        showDialog = uiState.showCharacterDialog,
        onDismiss = { viewModel.action(TeamCreateAction.HideCharacterDialog) }
    ) { name, level, classType ->
        viewModel.action(
            TeamCreateAction.AddCharacter(
                name = name,
                level = level,
                characterType = classType,
            )
        )
    }

    Content(
        teamName = uiState.name,
        characters = uiState.characters,
        canAdd = uiState.canAdd,
        modifier = modifier,
        action = { viewModel.action(it) }
    )
}

@Composable
fun Content(
    teamName: String,
    characters: List<CharacterUI>,
    canAdd: Boolean,
    modifier: Modifier = Modifier,
    action: (TeamCreateAction) -> Unit,
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { action.invoke(TeamCreateAction.Save) },
                content = {
                    Icon(Icons.Outlined.Done, "Сохранить команду")
                },
                shape = RoundedCornerShape(32.dp),

            )
        },
        content = { innerPadding ->
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(innerPadding)
                    .padding(32.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                TeamPart(name = teamName) {
                    action.invoke(TeamCreateAction.EditName(it))
                }

                CharacterPart(
                    characters = characters,
                    canAdd = canAdd,
                    action = action
                )
            }
        }
    )
}

@Composable
fun TeamPart(
    name: String,
    modifier: Modifier = Modifier,
    onNameChanged: (String) -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Создание комманды",
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
        )
        Spacer(
            modifier = Modifier.height(16.dp)
        )

        OutlinedTextField(
            modifier = Modifier.align(CenterHorizontally),
            value = name,
            onValueChange = { onNameChanged.invoke(it) },
            label = { Text("Название комманды") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                focusedLabelColor = MaterialTheme.colorScheme.primary,
                unfocusedLabelColor = MaterialTheme.colorScheme.onSecondary,
                cursorColor = MaterialTheme.colorScheme.primary,
            )
        )
    }
}

@Composable
fun CharacterPart(
    characters: List<CharacterUI>,
    canAdd: Boolean,
    modifier: Modifier = Modifier,
    action: (TeamCreateAction) -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Игроки",
            fontSize = 18.sp,
        )

        CharacterWithDialogList(
            characters = characters,
            onSave = { characterId, newLevel ->
                action.invoke(
                    TeamCreateAction.UpdateCharacter(
                        characterId,
                        newLevel
                    )
                )
            },
            onDelete = { action.invoke(TeamCreateAction.DeleteCharacter(it)) },
            onLeave = { action.invoke(TeamCreateAction.LeaveCharacter(it)) }
        )

        if (canAdd) {
            Button(
                onClick = { action.invoke(TeamCreateAction.ShowCharacterDialog) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Добавить")
            }
        }
    }
}

@Preview
@Composable
private fun Sample() {
    GloomhavenHalperTheme {
            Content(
                teamName = "Name",
                characters = listOf(
                    CharacterUI(
                        name = "Name",
                        level = 6,
                        characterClass = CharacterClassUI(
                            imageRes = R.drawable.br,
                            name = "Name",
                            classType = CharacterClassType.Brute
                        ), teamName = null
                    ),
                    CharacterUI(
                        name = "Name2",
                        level = 6,
                        characterClass = CharacterClassUI(
                            imageRes = R.drawable.br,
                            name = "Name",
                            classType = CharacterClassType.Brute
                        ), teamName = null
                    )
                ),
                canAdd = true
            ) { }
        }
}


