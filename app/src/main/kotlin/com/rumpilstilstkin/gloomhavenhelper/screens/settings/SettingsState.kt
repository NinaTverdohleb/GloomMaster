package com.rumpilstilstkin.gloomhavenhelper.screens.settings

import androidx.compose.runtime.Immutable
import com.rumpilstilstkin.gloomhavenhelper.screens.models.ShortTeamInfoUi
import com.rumpilstilstkin.gloomhavenhelper.screens.teem.edit.TeamEditAction
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class SettingsStateUi(
    val language: String,
    val team: ShortTeamInfoUi? = null,
    val teams: ImmutableList<ShortTeamInfoUi> = persistentListOf(),
)

sealed interface SettingsAction {
    data object Back : SettingsAction

    data object ChangeLanguage : SettingsAction

    data object ShowAllTeam : SettingsAction

    data object TeamSetting : SettingsAction

    data object AddTeam : SettingsAction

    data object ShareTeam : SettingsAction

    data class SelectTeam(
        val teamId: Int,
    ) : SettingsAction
}
