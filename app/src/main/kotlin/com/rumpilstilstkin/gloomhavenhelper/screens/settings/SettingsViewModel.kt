package com.rumpilstilstkin.gloomhavenhelper.screens.settings

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloomhavenhelper.data.LocaleRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team.ChangeCurrentTeamUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team.DeleteCurrentTeamUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team.GetCurrentTeamWithTeamsUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team.GetShareFileUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team.SwitchPackForCurrentTeamUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team.UpdateDifficultyLevelUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team.UpdateNameForCurrentTeamUseCase
import com.rumpilstilstkin.gloomhavenhelper.navigation.GlHelperDialog
import com.rumpilstilstkin.gloomhavenhelper.navigation.GlHelperScreen
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEvent
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEvent.Dialog
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEvent.Screen
import com.rumpilstilstkin.gloomhavenhelper.screens.models.ShortTeamInfoUi
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
    private val switchPackForCurrentTeamUseCase: SwitchPackForCurrentTeamUseCase,
    private val updateNameForCurrentTeamUseCase: UpdateNameForCurrentTeamUseCase,
    private val deleteCurrentTeamUseCase: DeleteCurrentTeamUseCase,
    private val changeCurrentTeamUseCase: ChangeCurrentTeamUseCase,
    private val getShareFileUseCase: GetShareFileUseCase,
    private val updateDifficultyLevelUseCase: UpdateDifficultyLevelUseCase,
    private val localeRepository: LocaleRepository,
) : ViewModel() {
    private val _navigationEvents = MutableSharedFlow<GlHelperEvent>()
    val navigationEvents = _navigationEvents.asSharedFlow()

    val uiState: StateFlow<SettingsStateUi> =
        combine(
            getCurrentTeamWithTeamsCountUseCase(),
            localeRepository.observeLocale,
        ) { (team, teams), locale ->
            val language = Locale.forLanguageTag(locale).displayName
            if (team == null) {
                SettingsStateUi(language = language)
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
                    language = language,
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
                    _navigationEvents.emit(GlHelperEvent.Back)
                }

                SettingsAction.ChangeLanguage -> {
                    TODO()
                }

                SettingsAction.ShowAllTeam -> {
                    _navigationEvents.emit(Dialog(GlHelperDialog.TeamListDialog()))
                }

                is SettingsAction.SelectTeam -> {
                    changeCurrentTeamUseCase(action.teamId)
                }

                SettingsAction.TeamSetting -> {
                    _navigationEvents.emit(Screen(GlHelperScreen.EditCurrentTeam))
                }

                SettingsAction.AddTeam -> {
                    _navigationEvents.emit(Dialog(GlHelperDialog.AddTeamDialog()))
                }

                SettingsAction.ShareTeam -> {
                    viewModelScope.launch {
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
                                    _navigationEvents.emit(GlHelperEvent.Message("Oops, something went wrong!"))
                                },
                            )
                    }
                }
            }
        }
    }
}
