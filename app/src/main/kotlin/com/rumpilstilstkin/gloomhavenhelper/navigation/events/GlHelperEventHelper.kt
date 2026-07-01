package com.rumpilstilstkin.gloomhavenhelper.navigation.events

import androidx.navigation.NavHostController

object GlHelperEventHelper {
    fun event(
        navController: NavHostController,
        event: GlHelperEvent,
    ) {
        when (event) {
            is GlHelperEvent.Screen -> {
                navController.navigate(event.screen)
            }

            is GlHelperEvent.Replace -> {
                navController.navigate(event.screen) {
                    popUpTo(event.popUpTo) { inclusive = true }
                }
            }

            is GlHelperEvent.Back -> {
                navController.popBackStack()
            }
        }
    }
}
