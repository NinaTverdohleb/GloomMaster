package com.rumpilstilstkin.gloomhavenhelper.screens.teem.create

import android.net.Uri

sealed interface AddTeamDialogState {
    data class CreateTeam(
        val teamName: String,
    ) : AddTeamDialogState

    data class ImportTeam(
        val uri: Uri,
    ) : AddTeamDialogState
}

data class AddTeamDialogComplete(
    val success: Boolean
)
