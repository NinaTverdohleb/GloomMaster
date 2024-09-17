package com.rumpilstilstkin.gloomhavenhelper.ui.characters.add

import androidx.lifecycle.ViewModel
import com.rumpilstilstkin.gloomhavenhelper.data.ClassRepository
import com.rumpilstilstkin.gloomhavenhelper.screens.models.CharacterClassUI
import com.rumpilstilstkin.gloomhavenhelper.screens.models.toUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class AddCharactersDialogViewModel @Inject constructor(
    private val classRepository: ClassRepository,
) : ViewModel() {
   val classes: List<CharacterClassUI> = runBlocking { classRepository.getAllClasses().map { it.toUI() } }
}