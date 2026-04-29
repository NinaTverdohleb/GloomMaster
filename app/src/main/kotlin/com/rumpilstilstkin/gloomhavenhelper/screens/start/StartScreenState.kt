package com.rumpilstilstkin.gloomhavenhelper.screens.start

import android.net.Uri
import androidx.compose.runtime.Immutable

sealed interface StartScreenState {
    data object Empty : StartScreenState
    @Immutable
    data class Team(
        val id: Int,
        val name: String,
    ) : StartScreenState
}

sealed interface StartScreenAction {
    data class CreateTeam(val teamName: String) : StartScreenAction
    data class SelectTeam(val teamId: Int) : StartScreenAction
    data object EditTeam : StartScreenAction
    data class ImportTeam(val uri: Uri) : StartScreenAction
}
