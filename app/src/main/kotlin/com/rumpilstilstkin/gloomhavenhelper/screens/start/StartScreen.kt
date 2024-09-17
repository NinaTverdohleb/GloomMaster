package com.rumpilstilstkin.gloomhavenhelper.screens.start

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.compose.material3.Text
import com.rumpilstilstkin.gloomhavenhelper.screens.start.characters.CharactersTab
import com.rumpilstilstkin.gloomhavenhelper.screens.start.plot.CompanyTab

@Composable
fun StartScreen(
    navController: NavHostController,
) {
    var tabIndex by remember { mutableIntStateOf(0) }

    val tabs = listOf("Компания", "Персонажи")

    Column(modifier = Modifier.fillMaxWidth()) {
        TabRow(selectedTabIndex = tabIndex) {
            tabs.forEachIndexed { index, title ->
                Tab(text = { Text(text = title) },
                    selected = tabIndex == index,
                    onClick = { tabIndex = index }
                )
            }
        }
        when (tabIndex) {
            0 -> CompanyTab(navController)
            1 -> CharactersTab(navController)
        }
    }
}
