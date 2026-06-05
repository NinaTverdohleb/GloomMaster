package com.rumpilstilstkin.gloomhavenhelper.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.rumpilstilstkin.gloomhavenhelper.screens.characters.goods.add.AddGoodsForCharacterScreenRoute
import com.rumpilstilstkin.gloomhavenhelper.screens.characters.quests.freeselect.SearchQuestScreen
import com.rumpilstilstkin.gloomhavenhelper.screens.characters.start.CharacterDetailsRoute
import com.rumpilstilstkin.gloomhavenhelper.screens.dialogs.teams.change.TeamListDialogRoute
import com.rumpilstilstkin.gloomhavenhelper.screens.dialogs.teams.create.AddTeamDialogRoute
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.monsters.ScenarioConstructorRoute
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.ScenarioRoute
import com.rumpilstilstkin.gloomhavenhelper.screens.settings.SettingsRoute
import com.rumpilstilstkin.gloomhavenhelper.screens.start.StartScreenRoute
import com.rumpilstilstkin.gloomhavenhelper.screens.teem.achievement.global.GlobalAchievementsRoute
import com.rumpilstilstkin.gloomhavenhelper.screens.teem.achievement.team.TeamAchievementsRoute
import com.rumpilstilstkin.gloomhavenhelper.screens.teem.edit.TeamEditRoute
import com.rumpilstilstkin.gloomhavenhelper.screens.teem.goods.AddGoodsForTeamScreenRoute
import com.rumpilstilstkin.gloomhavenhelper.screens.teem.scenarios.AddScenarioForTeamRoute

@Composable
fun GlHelperNavHost(
    navController: NavHostController = rememberNavController(),
    innerPadding: PaddingValues,
    widthSizeClass: WindowWidthSizeClass,
) {
    NavHost(
        navController = navController,
        startDestination = GlHelperScreen.Start,
        enterTransition = { fadeIn(animationSpec = tween(300)) },
        exitTransition = { fadeOut(animationSpec = tween(300)) },
        popEnterTransition = { fadeIn(animationSpec = tween(300)) },
        popExitTransition = { fadeOut(animationSpec = tween(300)) },
        modifier =
            Modifier
                .fillMaxSize()
                .padding(innerPadding),
    ) {
        composable<GlHelperScreen.Start> {
            StartScreenRoute(
                navController = navController,
            )
        }
        composable<GlHelperScreen.EditCurrentTeam> {
            TeamEditRoute(
                navController = navController,
            )
        }
        composable<GlHelperScreen.Scenario> {
            ScenarioRoute(
                navController = navController,
                widthSizeClass = widthSizeClass,
            )
        }
        composable<GlHelperScreen.CharacterDetails> {
            val args = it.toRoute<GlHelperScreen.CharacterDetails>()
            CharacterDetailsRoute(
                navController = navController,
                characterId = args.characterId,
            )
        }
        composable<GlHelperScreen.AddGoodsForCharacter> {
            val args = it.toRoute<GlHelperScreen.AddGoodsForCharacter>()
            AddGoodsForCharacterScreenRoute(
                characterId = args.characterId,
                navController = navController,
            )
        }
        composable<GlHelperScreen.SearchPersonalQuest> {
            val args = it.toRoute<GlHelperScreen.SearchPersonalQuest>()
            SearchQuestScreen(
                characterId = args.characterId,
                navController = navController,
            )
        }
        composable<GlHelperScreen.AddGoodsForTeam> {
            AddGoodsForTeamScreenRoute(
                navController = navController,
            )
        }
        composable<GlHelperScreen.AddScenarioForTeam> {
            AddScenarioForTeamRoute(
                navController = navController,
            )
        }
        composable<GlHelperScreen.TeamAchievements> {
            TeamAchievementsRoute(
                navController = navController,
            )
        }
        composable<GlHelperScreen.GlobalAchievements> {
            GlobalAchievementsRoute(
                navController = navController,
            )
        }
        composable<GlHelperScreen.ScenarioConstructor> {
            ScenarioConstructorRoute(
                navController = navController,
            )
        }
        dialog<GlHelperDialog.AddTeamDialog> {
            AddTeamDialogRoute(
                navController = navController,
            )
        }

        dialog<GlHelperDialog.TeamListDialog> {
            TeamListDialogRoute(
                navController = navController,
            )
        }

        composable<GlHelperScreen.Settings>(
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(400),
                ) + fadeIn(animationSpec = tween(200))
            },
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(400),
                ) + fadeOut(animationSpec = tween(200))
            },
        ) {
            SettingsRoute(
                navController = navController,
            )
        }
    }
}
