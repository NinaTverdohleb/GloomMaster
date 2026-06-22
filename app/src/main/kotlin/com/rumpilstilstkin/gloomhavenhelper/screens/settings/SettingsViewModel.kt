package com.rumpilstilstkin.gloomhavenhelper.screens.settings

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.settings.LanguagesListUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team.GetCurrentTeamWithTeamsUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team.GetShareFileUseCase
import com.rumpilstilstkin.gloomhavenhelper.navigation.GlHelperDialog.AddTeamDialog
import com.rumpilstilstkin.gloomhavenhelper.navigation.GlHelperDialog.DeleteTeamDialog
import com.rumpilstilstkin.gloomhavenhelper.navigation.GlHelperScreen
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEvent.Back
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEvent.Dialog
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEvent.Screen
import com.rumpilstilstkin.gloomhavenhelper.screens.core.ScreenEffect
import com.rumpilstilstkin.gloomhavenhelper.screens.core.createBottomSheetSession
import com.rumpilstilstkin.gloomhavenhelper.screens.models.ShortTeamInfoUi
import com.rumpilstilstkin.gloomhavenhelper.screens.settings.language.SelectLanguageContract
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
                    val session = createBottomSheetSession(
                        contract = SelectLanguageContract,
                        input = Unit,
                        onResult = { }
                    )
                    _screenEvents.emit(ScreenEffect.OpenBottomSheet(session))
                }

                SettingsAction.ShowAllTeam -> {
                    val session = createBottomSheetSession(
                        contract = TeamListDialogContract,
                        input = Unit,
                        onResult = { team ->
                            team?.also { onAction(SettingsAction.SelectTeam(it)) }
                        }
                    )
                    _screenEvents.emit(ScreenEffect.OpenBottomSheet(session))
                }

                is SettingsAction.SelectTeam -> {
                    val session = createBottomSheetSession(
                        contract = TeamMenuDialogContract,
                        input = action.team,
                        onResult = { result ->
                            when (result) {
                                is TeamMenuResult.DeleteTeamRequest -> {
                                    viewModelScope.launch {
                                        _screenEvents.emit(
                                            ScreenEffect.Navigation(
                                                Dialog(
                                                    DeleteTeamDialog(
                                                        teamId = result.team.teamId,
                                                        teamName = result.team.teamName
                                                    )
                                                )
                                            )
                                        )
                                    }
                                }

                                else -> {}
                            }
                        }
                    )
                    _screenEvents.emit(ScreenEffect.OpenBottomSheet(session))
                }

                SettingsAction.TeamSetting -> {
                    _screenEvents.emit(ScreenEffect.Navigation(Screen(GlHelperScreen.EditCurrentTeam)))
                }

                SettingsAction.AddTeam -> {
                    _screenEvents.emit(ScreenEffect.Navigation(Dialog(AddTeamDialog())))
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
                                _screenEvents.emit(ScreenEffect.Message("Oops, something went wrong!"))
                            },
                        )
                }

                SettingsAction.DeleteCurrentTeam -> {
                    uiState.value.team?.also { team ->
                        _screenEvents.emit(
                            ScreenEffect.Navigation(
                                Dialog(
                                    DeleteTeamDialog(
                                        teamId = team.teamId,
                                        teamName = team.teamName
                                    )
                                )
                            )
                        )
                    }
                }

                SettingsAction.CloseBottomSheet -> {
                    _screenEvents.emit(ScreenEffect.CloseBottomSheet)
                }
            }
        }
    }
}
