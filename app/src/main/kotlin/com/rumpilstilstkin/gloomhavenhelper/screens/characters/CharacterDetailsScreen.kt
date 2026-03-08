package com.rumpilstilstkin.gloomhavenhelper.screens.characters

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.screens.characters.general.CharacterGeneralTab
import com.rumpilstilstkin.gloomhavenhelper.screens.characters.goods.CharacterItemsTab
import com.rumpilstilstkin.gloomhavenhelper.screens.characters.perks.CharacterPerksTab
import com.rumpilstilstkin.gloomhavenhelper.screens.models.toImage
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenHalperTheme
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomBadge
import com.rumpilstilstkin.gloomhavenhelper.ui.icons.toImage

@Composable
fun CharacterDetailsScreen(
    characterId: Int,
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val viewModel =
        hiltViewModel<CharacterDetailsViewModel, CharacterDetailsViewModel.Factory> { factory ->
            factory.create(characterId)
        }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showTeamsDialog by remember { mutableStateOf(false) }

    CharacterDetailsMain(
        classImg = uiState.characterClass.image,
        name = uiState.name,
        teamName = uiState.teamName ?: "Укажите команду...",
        level = uiState.level,
        modifier = modifier,
        showFirstTab = { CharacterGeneralTab(characterId, navController) },
        showSecondTab = { CharacterItemsTab(characterId, navController) },
        showThirdTab = { CharacterPerksTab(characterId) },
        onNameClick = { /*viewModel.onAction(CharacterDetailsActions.EditName)*/ },
        onTeamClick = { showTeamsDialog = true }
    )

}

@Composable
fun CharacterDetailsMain(
    classImg: Int,
    name: String,
    teamName: String?,
    level: Int,
    modifier: Modifier = Modifier,
    showFirstTab: @Composable () -> Unit,
    showSecondTab: @Composable () -> Unit,
    showThirdTab: @Composable () -> Unit,
    onNameClick: () -> Unit,
    onTeamClick: () -> Unit
) {
    Column(
        modifier = modifier
    ) {
        CharacterHeader(
            classImg = classImg,
            name = name,
            level = level,
            teamName = teamName ?: "Укажите команду...",
            onNameClick = onNameClick,
            onTeamClick = onTeamClick
        )

        CharactersTabs(
            showFirstTab = showFirstTab,
            showSecondTab = showSecondTab,
            showThirdTab = showThirdTab
        )
    }

}

@Composable
fun CharacterHeader(
    classImg: Int,
    name: String,
    teamName: String,
    level: Int,
    modifier: Modifier = Modifier,
    onNameClick: () -> Unit,
    onTeamClick: () -> Unit
) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.primary)
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = painterResource(id = classImg),
                contentDescription = null,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.background),
                modifier = Modifier.size(48.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, fill = false)
                    .padding(horizontal = 16.dp)
                    .clickable { onNameClick() },
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }

            GloomBadge(
                text = level.toString(),
                modifier = Modifier.size(42.dp)
            )

        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.clickable { onTeamClick() }.padding(4.dp)
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.background
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = teamName, color = MaterialTheme.colorScheme.onPrimary)
        }
    }
}

@Composable
fun CharactersTabs(
    modifier: Modifier = Modifier,
    showFirstTab: @Composable () -> Unit,
    showSecondTab: @Composable () -> Unit,
    showThirdTab: @Composable () -> Unit
) {
    var tabIndex by rememberSaveable { mutableIntStateOf(0) }

    val tabs = listOf("Общее", "Предметы", "Навыки")

    Surface(
        modifier = modifier.fillMaxSize()
    ) {
        Column {
            PrimaryTabRow(
                selectedTabIndex = tabIndex,
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(text = { Text(text = title) },
                        selected = tabIndex == index,
                        onClick = { tabIndex = index }
                    )
                }
            }
            when (tabIndex) {
                0 -> showFirstTab()
                1 -> showSecondTab()
                2 -> showThirdTab()
            }
        }
    }
}

@Preview
@Composable
private fun CharacterDetailsExample() {
    GloomhavenHalperTheme {
        CharacterDetailsMain(
            classImg = R.drawable.ic_be,
            name = "Warrior",
            teamName = null,
            level = 10,
            showFirstTab = { },
            showSecondTab = { },
            showThirdTab = { },
            onNameClick = { },
            onTeamClick = { }
        )
    }
}