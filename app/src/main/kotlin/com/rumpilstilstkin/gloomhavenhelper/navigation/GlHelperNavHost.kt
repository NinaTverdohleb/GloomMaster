package com.rumpilstilstkin.gloomhavenhelper.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.rumpilstilstkin.gloomhavenhelper.screens.characters.CharacterDetailsScreen
import com.rumpilstilstkin.gloomhavenhelper.screens.characters.goods.add.AddGoodsScreen
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.ScenarioScreen
import com.rumpilstilstkin.gloomhavenhelper.screens.start.StartScreen
import com.rumpilstilstkin.gloomhavenhelper.screens.teem.create.TeamCreateScreen
import com.rumpilstilstkin.gloomhavenhelper.screens.teem.edit.TeamDetailsScreen

@Composable
fun GlHelperNavHost(
    navController: NavHostController = rememberNavController(),
    innerPadding: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = GlHelperScreens.Start,
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        composable<GlHelperScreens.Start> {
            StartScreen(
                navController = navController
            )
        }
        composable<GlHelperScreens.TeamCreate> {
            TeamCreateScreen(
                navController = navController
            )
        }
        composable<GlHelperScreens.TeamDetails> {
            TeamDetailsScreen()
        }
        composable<GlHelperScreens.Scenario> {
            ScenarioScreen()
        }
        composable<GlHelperScreens.CharacterDetails> {
            val args = it.toRoute<GlHelperScreens.CharacterDetails>()
            CharacterDetailsScreen(
                navController = navController,
                characterId = args.characterId
            )
        }
        composable<GlHelperScreens.AddGoodsForCharacter> {
            val args = it.toRoute<GlHelperScreens.AddGoodsForCharacter>()
            AddGoodsScreen(
                characterId = args.characterId,
                navController = navController
            )
        }
    }
}
