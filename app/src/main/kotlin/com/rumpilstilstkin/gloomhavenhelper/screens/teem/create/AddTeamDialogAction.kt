package com.rumpilstilstkin.gloomhavenhelper.screens.teem.create

import android.net.Uri

sealed interface AddTeamDialogAction {
    data class CreateTeam(
        val teamName: String,
    ) : AddTeamDialogAction

    data class ImportTeam(
        val uri: Uri,
    ) : AddTeamDialogAction

    data object Back : AddTeamDialogAction
}
