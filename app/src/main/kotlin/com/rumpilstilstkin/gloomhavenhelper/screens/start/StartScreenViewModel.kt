package com.rumpilstilstkin.gloomhavenhelper.screens.start

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.PackType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.TeamInfoForSave
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team.ChangeCurrentTeamUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team.GetCurrentTeamUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team.ImportTeamUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team.SaveTeamUseCase
import com.rumpilstilstkin.gloomhavenhelper.navigation.GlHelperScreens
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEvent
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEvent.Screen
import com.rumpilstilstkin.gloomhavenhelper.utils.flatMap
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject
import kotlin.onFailure

@HiltViewModel
class StartScreenViewModel @Inject constructor(
    @param:ApplicationContext private val context: Context,
    getCurrentTeamUseCase: GetCurrentTeamUseCase,
    val changeCurrentTeamUseCase: ChangeCurrentTeamUseCase,
    val saveTeamUseCase: SaveTeamUseCase,
    val importTeamUseCase: ImportTeamUseCase,
) : ViewModel() {

    private val _navigationEvents = MutableSharedFlow<GlHelperEvent>()
    val navigationEvents = _navigationEvents.asSharedFlow()

    val uiState: StateFlow<StartScreenState> = getCurrentTeamUseCase().map { team ->
        if (team == null) {
            StartScreenState.Empty
        } else {
            StartScreenState.Team(
                name = team.name,
                id = team.id
            )
        }
    }.stateIn(
        scope = viewModelScope,
        initialValue = StartScreenState.Empty,
        started = SharingStarted.WhileSubscribed(10),
    )

    fun onAction(action: StartScreenAction) {
        viewModelScope.launch {
            when (action) {
                is StartScreenAction.CreateTeam -> {
                    saveTeamUseCase(
                        TeamInfoForSave(
                            action.teamName,
                            packs = listOf(PackType.MAIN)
                        )
                    )
                }

                is StartScreenAction.SelectTeam -> {
                    changeCurrentTeamUseCase(action.teamId)
                }

                StartScreenAction.EditTeam ->
                    _navigationEvents.emit(Screen(GlHelperScreens.EditCurrentTeam))

                is StartScreenAction.ImportTeam -> {
                    runCatching {
                        context.contentResolver.openInputStream(action.uri)?.use { inputStream ->
                            inputStream.bufferedReader().use { it.readText() }
                        } ?: throw IOException("Что то не получилось")
                    }.flatMap { data ->
                        importTeamUseCase(data)
                    }.onFailure {
                        _navigationEvents.emit(GlHelperEvent.Message("Что-то не получилось :("))
                    }
                }
            }
        }
    }
}