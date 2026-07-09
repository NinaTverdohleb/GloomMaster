package com.rumpilstilstkin.gloommaster.screens.teem.create

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloommaster.domain.entity.PackType
import com.rumpilstilstkin.gloommaster.domain.entity.TeamInfoForSave
import com.rumpilstilstkin.gloommaster.domain.usecase.team.ImportTeamUseCase
import com.rumpilstilstkin.gloommaster.domain.usecase.team.SaveTeamUseCase
import com.rumpilstilstkin.gloommaster.utils.flatMap
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class AddTeamDialogViewModel @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val saveTeamUseCase: SaveTeamUseCase,
    private val importTeamUseCase: ImportTeamUseCase,
) : ViewModel() {
    private val _complete = Channel<AddTeamDialogComplete>(Channel.BUFFERED)
    val complete = _complete.receiveAsFlow()

    fun onAction(action: AddTeamDialogState) {
        viewModelScope.launch {
            when (action) {
                is AddTeamDialogState.CreateTeam -> {
                    saveTeamUseCase(
                        TeamInfoForSave(
                            action.teamName,
                            packs = listOf(PackType.MAIN),
                        ),
                    )
                    _complete.send(AddTeamDialogComplete(true))
                }

                is AddTeamDialogState.ImportTeam -> {
                    runCatching {
                        context.contentResolver.openInputStream(action.uri)?.use { inputStream ->
                            inputStream.bufferedReader().use { it.readText() }
                        } ?: throw IOException("Oops, something went wrong!")
                    }.flatMap { data ->
                        importTeamUseCase(data)
                    }.fold(
                        onSuccess = { _complete.send(AddTeamDialogComplete(true)) },
                        onFailure = { _complete.send(AddTeamDialogComplete(false)) },
                    )
                }
            }
        }
    }
}
