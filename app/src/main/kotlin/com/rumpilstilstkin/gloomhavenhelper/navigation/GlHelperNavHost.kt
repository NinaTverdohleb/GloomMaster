package com.rumpilstilstkin.gloomhavenhelper.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.rumpilstilstkin.gloomhavenhelper.screens.characters.goods.add.AddGoodsForCharacterScreenRoute
import com.rumpilstilstkin.gloomhavenhelper.screens.characters.quests.select.SearchQuestRoute
import com.rumpilstilstkin.gloomhavenhelper.screens.characters.start.CharacterDetailsRoute
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.add.AddScenarioForTeamRoute
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.monsters.ScenarioConstructorRoute
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.ScenarioRoute
import com.rumpilstilstkin.gloomhavenhelper.screens.settings.SettingsRoute
import com.rumpilstilstkin.gloomhavenhelper.screens.start.StartScreenRoute
import com.rumpilstilstkin.gloomhavenhelper.screens.teem.achievement.global.GlobalAchievementsRoute
import com.rumpilstilstkin.gloomhavenhelper.screens.teem.achievement.team.TeamAchievementsRoute
import com.rumpilstilstkin.gloomhavenhelper.screens.teem.edit.TeamEditRoute
import com.rumpilstilstkin.gloomhavenhelper.screens.teem.goods.AddGoodsForTeamScreenRoute

@Composable
fun GlHelperNavHost(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier,
    widthSizeClass: WindowWidthSizeClass,
) {
    NavHost(
        navController = navController,
        startDestination = GlHelperScreen.Start,
        enterTransition = { fadeIn(animationSpec = tween(300)) },
        exitTransition = { fadeOut(animationSpec = tween(300)) },
        popEnterTransition = { fadeIn(animationSpec = tween(300)) },
        popExitTransition = { fadeOut(animationSpec = tween(300)) },
        modifier = modifier,
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
            SearchQuestRoute(
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
        composable<GlHelperScreen.Settings> {
            SettingsRoute(
                navController = navController,
            )
        }
    }
}
