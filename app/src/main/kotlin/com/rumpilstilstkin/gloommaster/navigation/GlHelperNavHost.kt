package com.rumpilstilstkin.gloommaster.navigation

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
import com.rumpilstilstkin.gloommaster.screens.characters.quests.select.SearchQuestRoute
import com.rumpilstilstkin.gloommaster.screens.characters.start.CharacterDetailsRoute
import com.rumpilstilstkin.gloommaster.screens.goods.add.AddGoodsForCharacterScreenRoute
import com.rumpilstilstkin.gloommaster.screens.onboarding.OnboardingRoute
import com.rumpilstilstkin.gloommaster.screens.scenario.add.AddScenarioForTeamRoute
import com.rumpilstilstkin.gloommaster.screens.scenario.play.ScenarioRoute
import com.rumpilstilstkin.gloommaster.screens.settings.SettingsRoute
import com.rumpilstilstkin.gloommaster.screens.start.StartScreenRoute
import com.rumpilstilstkin.gloommaster.screens.teem.achievement.global.GlobalAchievementsRoute
import com.rumpilstilstkin.gloommaster.screens.teem.achievement.team.TeamAchievementsRoute
import com.rumpilstilstkin.gloommaster.screens.teem.edit.TeamEditRoute
import com.rumpilstilstkin.gloommaster.screens.teem.goods.AddGoodsForTeamScreenRoute

@Composable
fun GlHelperNavHost(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier,
    widthSizeClass: WindowWidthSizeClass,
    startOnboarding: Boolean = false,
) {
    NavHost(
        navController = navController,
        startDestination = if (startOnboarding) GlHelperScreen.Onboarding else GlHelperScreen.Start,
        enterTransition = { fadeIn(animationSpec = tween(300)) },
        exitTransition = { fadeOut(animationSpec = tween(300)) },
        popEnterTransition = { fadeIn(animationSpec = tween(300)) },
        popExitTransition = { fadeOut(animationSpec = tween(300)) },
        modifier = modifier,
    ) {
        composable<GlHelperScreen.Onboarding> {
            OnboardingRoute(
                navController = navController,
            )
        }
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
        composable<GlHelperScreen.Settings> {
            SettingsRoute(
                navController = navController,
            )
        }
    }
}
