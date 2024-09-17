package com.rumpilstilstkin.gloomhavenhelper.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rumpilstilstkin.gloomhavenhelper.screens.main.MainScreen
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
    }
}
