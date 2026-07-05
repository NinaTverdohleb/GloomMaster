package com.rumpilstilstkin.gloomhavenhelper.screens.settings

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.settings.LanguagesListUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team.GetCurrentTeamWithTeamsUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team.GetShareFileUseCase
import com.rumpilstilstkin.gloomhavenhelper.navigation.GlHelperScreen
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEvent.Back
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEvent.Screen
import com.rumpilstilstkin.gloomhavenhelper.screens.core.ScreenEffect
import com.rumpilstilstkin.gloomhavenhelper.screens.core.createOverlaySession
import com.rumpilstilstkin.gloomhavenhelper.screens.models.ShortTeamInfoUi
import com.rumpilstilstkin.gloomhavenhelper.screens.settings.language.SelectLanguageContract
import com.rumpilstilstkin.gloomhavenhelper.screens.teem.create.AddTeamDialogContract
import com.rumpilstilstkin.gloomhavenhelper.screens.teem.delete.DeleteTeamDialogContract
import com.rumpilstilstkin.gloomhavenhelper.screens.teem.list.TeamListDialogContract
import com.rumpilstilstkin.gloomhavenhelper.screens.teem.menu.TeamMenuDialogContract
import com.rumpilstilstkin.gloomhavenhelper.screens.teem.menu.TeamMenuResult
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.File
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    @param:ApplicationContext private val context: Context,
    getCurrentTeamWithTeamsCountUseCase: GetCurrentTeamWithTeamsUseCase,
    private val getShareFileUseCase: GetShareFileUseCase,
    languagesListUseCase: LanguagesListUseCase,
) : ViewModel() {
    private val _screenEvents = MutableSharedFlow<ScreenEffect>()
    val screenEvents = _screenEvents.asSharedFlow()

    val uiState: StateFlow<SettingsStateUi> =
        combine(
            getCurrentTeamWithTeamsCountUseCase(),
            languagesListUseCase(),
        ) { (team, teams), (currentLocale, allLocales) ->
            if (team == null) {
                SettingsStateUi(language = currentLocale.languageName)
            } else {
                SettingsStateUi(
                    team =
                        ShortTeamInfoUi(
                            teamId = team.teamId,
                            teamName = team.name,
                            level = team.difficultyLevel,
                        ),
                    teams =
                        teams
                            .map {
                                ShortTeamInfoUi(
                                    teamId = it.teamId,
                                    teamName = it.name,
                                    level = it.difficultyLevel,
                                )
                            }.toImmutableList(),
                    language = currentLocale.languageName,
                )
            }
        }.stateIn(
            scope = viewModelScope,
            initialValue = SettingsStateUi(Locale.getDefault().displayName),
            started = SharingStarted.WhileSubscribed(5000),
        )

    fun onAction(action: SettingsAction) {
        viewModelScope.launch {
            when (action) {
                is SettingsAction.Back -> {
                    _screenEvents.emit(ScreenEffect.Navigation(Back))
                }

                SettingsAction.ChangeLanguage -> {
                    val session =
                        createOverlaySession(
                            contract = SelectLanguageContract,
                            input = Unit,
                            onResult = { },
                        )
                    _screenEvents.emit(ScreenEffect.OpenBottomSheet(session))
                }

                SettingsAction.ShowAllTeam -> {
                    val session =
                        createOverlaySession(
                            contract = TeamListDialogContract,
                            input = Unit,
                            onResult = { team ->
                                team?.also { onAction(SettingsAction.SelectTeam(it)) }
                            },
                        )
                    _screenEvents.emit(ScreenEffect.OpenBottomSheet(session))
                }

                is SettingsAction.SelectTeam -> {
                    val session =
                        createOverlaySession(
                            contract = TeamMenuDialogContract,
                            input = action.team,
                            onResult = { result ->
                                when (result) {
                                    is TeamMenuResult.DeleteTeamRequest -> {
                                        openDeleteTeamDialog(result.team)
                                    }

                                    else -> {}
                                }
                            },
                        )
                    _screenEvents.emit(ScreenEffect.OpenBottomSheet(session))
                }

                SettingsAction.TeamSetting -> {
                    _screenEvents.emit(ScreenEffect.Navigation(Screen(GlHelperScreen.EditCurrentTeam)))
                }

                SettingsAction.AddTeam -> {
                    openAddTeamDialog()
                }

                SettingsAction.ShareTeam -> {
                    getShareFileUseCase(File(context.filesDir, "exports"))
                        .fold(
                            onSuccess = { file ->
                                val contentUri =
                                    FileProvider.getUriForFile(
                                        context,
                                        "${context.packageName}.fileprovider",
                                        file,
                                    )

                                val shareIntent =
                                    Intent(Intent.ACTION_SEND).apply {
                                        type = "application/json"
                                        putExtra(Intent.EXTRA_STREAM, contentUri)
                                        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                    }

                                val chooser =
                                    Intent.createChooser(shareIntent, "Share Team Data").apply {
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                    }
                                context.startActivity(chooser)
                            },
                            onFailure = { _ ->
                                _screenEvents.emit(ScreenEffect.Message(R.string.error_toast))
                            },
                        )
                }

                SettingsAction.DeleteCurrentTeam -> {
                    uiState.value.team?.also { team ->
                        openDeleteTeamDialog(team)
                    }
                }

                SettingsAction.CloseBottomSheet -> {
                    _screenEvents.emit(ScreenEffect.CloseBottomSheet)
                }
            }
        }
    }

    private fun openDeleteTeamDialog(team: ShortTeamInfoUi) {
        viewModelScope.launch {
            val session =
                createOverlaySession(
                    contract = DeleteTeamDialogContract,
                    input = team,
                    onResult = { result ->
                        result?.let {
                            viewModelScope.launch {
                                _screenEvents.emit(ScreenEffect.Message(R.string.team_deleted_toast))
                            }
                        }
                    },
                )
            _screenEvents.emit(ScreenEffect.OpenDialog(session))
        }
    }

    private fun openAddTeamDialog() {
        viewModelScope.launch {
            val session =
                createOverlaySession(
                    contract = AddTeamDialogContract,
                    input = Unit,
                    onResult = { result ->
                        viewModelScope.launch {
                            result?.let { success ->
                                val message =
                                    if (success) {
                                        R.string.team_created_toast
                                    } else {
                                        R.string.error_toast
                                    }
                                _screenEvents.emit(ScreenEffect.Message(message))
                            }
                        }
                    },
                )
            _screenEvents.emit(ScreenEffect.OpenDialog(session))
        }
    }
}
