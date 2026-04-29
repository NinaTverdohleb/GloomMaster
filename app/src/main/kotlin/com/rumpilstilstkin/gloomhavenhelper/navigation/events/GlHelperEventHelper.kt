package com.rumpilstilstkin.gloomhavenhelper.navigation.events

import android.widget.Toast
import androidx.navigation.NavHostController

object GlHelperEventHelper {
    fun event(
        navController: NavHostController,
        event: GlHelperEvent
    ) {
        when (event) {
            is GlHelperEvent.Screen -> {
                navController.navigate(event.screen)
            }

            is GlHelperEvent.Back -> {
                navController.popBackStack()
            }

            is GlHelperEvent.Message -> {
                Toast.makeText(
                    navController.context,
                    event.text,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}