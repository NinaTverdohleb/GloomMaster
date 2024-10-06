package com.rumpilstilstkin.gloomhavenhelper.screens.start.characters

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterClassType
import com.rumpilstilstkin.gloomhavenhelper.navigation.GlHelperScreens
import com.rumpilstilstkin.gloomhavenhelper.screens.models.CharacterClassUI
import com.rumpilstilstkin.gloomhavenhelper.screens.models.CharacterUI
import com.rumpilstilstkin.gloomhavenhelper.screens.dialogs.character.add.AddCharacterDialog
import com.rumpilstilstkin.gloomhavenhelper.ui.characters.CharacterList
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenHalperTheme

@Composable
fun CharactersTab(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: CharactersTabViewModel = hiltViewModel()
) {

    // add characterDialog
    var showAddCharacterDialog by remember { mutableStateOf(false) }
    val addCharacter: (String, Int, CharacterClassType) -> Unit = { name, level, classType ->
        viewModel.onAction(
            CharactersTabAction.AddCharacter(
                name = name,
                level = level,
                characterType = classType
            )
        )
        showAddCharacterDialog = false
    }
    AddCharacterDialog(
        showDialog = showAddCharacterDialog,
        onDismiss = { showAddCharacterDialog = false },
        onAdd = addCharacter
    )

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (uiState) {
        is CharactersTabState.Empty -> {
            EmptyCharactersTab(modifier) {
                showAddCharacterDialog = true
            }

        }

        is CharactersTabState.Data -> {
            val data = (uiState as CharactersTabState.Data)
            CharactersContent(
                filters = data.filters,
                characters = data.characters,
                modifier = modifier,
                switchAlive = {viewModel.onAction(CharactersTabAction.SwitchAlive)},
                addCharacter = {showAddCharacterDialog = true},
                openCharacterDetails = {navController.navigate(GlHelperScreens.CharacterDetails(it))}
            )
        }
    }
}


@Composable
fun EmptyCharactersTab(
    modifier: Modifier = Modifier,
    addCharacter: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(painter = painterResource(id = R.drawable.logo), contentDescription = "logo")
        Spacer(modifier = Modifier.height(48.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                addCharacter.invoke()
            }) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = "Добавить персонажа",
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun CharactersContent(
    filters: Filters,
    characters: List<CharacterUI>,
    modifier: Modifier = Modifier,
    switchAlive: () -> Unit,
    openCharacterDetails: (Int) -> Unit,
    addCharacter: () -> Unit
) {
    Column(
        modifier = modifier
            .padding(24.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Button(
                onClick = {
                    addCharacter.invoke()
                },
                modifier = Modifier
            ) {
                Text("Добавить персонажа")
            }

            Icon(
                modifier = Modifier.clickable {
                    switchAlive.invoke()
                },
                imageVector = if (filters.filterAlive) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
                contentDescription = "isAlive"
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .padding(8.dp)
                .verticalScroll(rememberScrollState())
                .weight(weight = 1f, fill = false)
        ) {
            CharacterList(characters = characters) {
                openCharacterDetails.invoke(it)
            }
        }
    }
}


@Preview
@Composable
private fun EmptyExample() {
    GloomhavenHalperTheme {
        EmptyCharactersTab {
            // nope
        }
    }
}

@Preview
@Composable
private fun DataExample() {
    GloomhavenHalperTheme {
        CharactersContent(
            filters = Filters(),
            characters = listOf(
                CharacterUI(
                    name = "Name",
                    level = 6,
                    characterClass = CharacterClassUI(
                        imageRes = R.drawable.br,
                        name = "Name",
                        classType = CharacterClassType.Brute
                    ),teamName = null

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
            switchAlive = {},
            addCharacter = {},
            openCharacterDetails = {}
        )
    }
}