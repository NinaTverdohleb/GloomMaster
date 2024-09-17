package com.rumpilstilstkin.gloomhavenhelper.screens.characters

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.rumpilstilstkin.gloomhavenhelper.screens.characters.general.CharacterGeneralTab
import com.rumpilstilstkin.gloomhavenhelper.screens.characters.items.CharacterItemsTab
import com.rumpilstilstkin.gloomhavenhelper.screens.characters.perks.CharacterPerksTab
import com.rumpilstilstkin.gloomhavenhelper.screens.start.characters.CharactersTab
import com.rumpilstilstkin.gloomhavenhelper.screens.start.plot.CompanyTab
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenHalperTheme

@Composable
fun CharacterDetailsScreen(
    navController: NavHostController? =null,
    characterId: Int,
    modifier: Modifier = Modifier
) {
    var tabIndex by remember { mutableIntStateOf(0) }

    val tabs = listOf("Общее", "Предметы", "Преки")
    Scaffold(
        topBar = {
            TabRow(
                modifier = Modifier.padding(top = 32.dp),
                selectedTabIndex = tabIndex,
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(text = { Text(text = title) },
                        selected = tabIndex == index,
                        onClick = { tabIndex = index }
                    )
                }
            }
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier.padding(innerPadding).fillMaxSize()
            ) {
                when (tabIndex) {
                    0 -> CharacterGeneralTab(characterId)
                    1 -> CharacterItemsTab()
                    2 -> CharacterPerksTab()
                }
            }
        }
    )
}

@Preview
@Composable
private fun CharacterDetailsExample() {
    GloomhavenHalperTheme {
        CharacterDetailsScreen(
            characterId = 1
        )
    }
}