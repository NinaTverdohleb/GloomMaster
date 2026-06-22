package com.rumpilstilstkin.gloomhavenhelper.screens.teem.create

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.PackType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.TeamInfoForSave
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team.ImportTeamUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team.SaveTeamUseCase
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEvent
import com.rumpilstilstkin.gloomhavenhelper.utils.flatMap
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class AddTeamDialogViewModel @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val saveTeamUseCase: SaveTeamUseCase,
    private val importTeamUseCase: ImportTeamUseCase,
) : ViewModel() {
    private val _navigationEvents = MutableSharedFlow<GlHelperEvent>()
    val navigationEvents = _navigationEvents.asSharedFlow()

    fun onAction(action: AddTeamDialogAction) {
        viewModelScope.launch {
            when (action) {
                is AddTeamDialogAction.CreateTeam -> {
                    saveTeamUseCase(
                        TeamInfoForSave(
                            action.teamName,
                            packs = listOf(PackType.MAIN),
                        ),
                    )
                    _navigationEvents.emit(GlHelperEvent.Back)
                }

                AddTeamDialogAction.Back -> {
                    _navigationEvents.emit(GlHelperEvent.Back)
                }

                is AddTeamDialogAction.ImportTeam -> {
                    runCatching {
                        context.contentResolver.openInputStream(action.uri)?.use { inputStream ->
                            inputStream.bufferedReader().use { it.readText() }
                        } ?: throw IOException("Oops, something went wrong!")
                    }.flatMap { data ->
                        importTeamUseCase(data)
                    }.fold(
                        onSuccess = { _navigationEvents.emit(GlHelperEvent.Back) },
                        onFailure = { _navigationEvents.emit(GlHelperEvent.Message("Oops, something went wrong!")) },
                    )
                }
            }
        }
    }
}
