package com.rumpilstilstkin.gloomhavenhelper.navigation.events

import com.rumpilstilstkin.gloomhavenhelper.navigation.GlHelperDialog
import com.rumpilstilstkin.gloomhavenhelper.navigation.GlHelperScreen

sealed interface GlHelperEvent {
    data class Screen(
        val screen: GlHelperScreen,
    ) : GlHelperEvent

    data class Dialog(
        val dialog: GlHelperDialog,
    ) : GlHelperEvent

    data object Back : GlHelperEvent

    data class Message(
        val text: String,
    ) : GlHelperEvent
}

