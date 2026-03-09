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
import com.rumpilstilstkin.gloomhavenhelper.screens.characters.start.CharacterDetailsRoute
import com.rumpilstilstkin.gloomhavenhelper.screens.characters.goods.add.AddGoodsForCharacterScreenRoute
import com.rumpilstilstkin.gloomhavenhelper.screens.characters.quests.freeselect.SearchQuestScreen
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.ScenarioRoute
import com.rumpilstilstkin.gloomhavenhelper.screens.start.StartScreenRoute
import com.rumpilstilstkin.gloomhavenhelper.screens.teem.achievement.global.GlobalAchievementsRoute
import com.rumpilstilstkin.gloomhavenhelper.screens.teem.edit.TeamEditRoute
import com.rumpilstilstkin.gloomhavenhelper.screens.teem.achievement.team.TeamAchievementsRoute
import com.rumpilstilstkin.gloomhavenhelper.screens.teem.goods.AddGoodsForTeamScreenRoute
import com.rumpilstilstkin.gloomhavenhelper.screens.teem.scenarios.AddScenarioForTeamRoute

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
            StartScreenRoute(
                navController = navController
            )
        }
        composable<GlHelperScreens.EditCurrentTeam> {
            TeamEditRoute(
                navController = navController
            )
        }
        composable<GlHelperScreens.Scenario> {
            val args = it.toRoute<GlHelperScreens.Scenario>()
            ScenarioRoute(
                navController = navController,
                scenarioId = args.scenarioId
            )
        }
        composable<GlHelperScreens.CharacterDetails> {
            val args = it.toRoute<GlHelperScreens.CharacterDetails>()
            CharacterDetailsRoute(
                navController = navController,
                characterId = args.characterId
            )
        }
        composable<GlHelperScreens.AddGoodsForCharacter> {
            val args = it.toRoute<GlHelperScreens.AddGoodsForCharacter>()
            AddGoodsForCharacterScreenRoute(
                characterId = args.characterId,
                navController = navController
            )
        }
        composable<GlHelperScreens.SearchPersonalQuest> {
            val args = it.toRoute<GlHelperScreens.SearchPersonalQuest>()
            SearchQuestScreen(
                characterId = args.characterId,
                navController = navController
            )
        }
        composable<GlHelperScreens.AddGoodsForTeam> {
            AddGoodsForTeamScreenRoute(
                navController = navController
            )
        }
        composable<GlHelperScreens.AddScenarioForTeam> {
            AddScenarioForTeamRoute(
                navController = navController
            )
        }
        composable<GlHelperScreens.TeamAchievements> {
            TeamAchievementsRoute(
                navController = navController
            )
        }
        composable<GlHelperScreens.GlobalAchievements> {
            GlobalAchievementsRoute(
                navController = navController
            )
        }
    }
}
