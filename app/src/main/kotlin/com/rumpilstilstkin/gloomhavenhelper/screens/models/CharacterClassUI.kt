package com.rumpilstilstkin.gloomhavenhelper.screens.models

import androidx.compose.runtime.Immutable
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterClass
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterClassType

@Immutable
data class CharacterClassUI(
    val name: String,
    val classType: CharacterClassType,
)

fun CharacterClass.toUi() = CharacterClassUI(
    name = this.name,
    classType = this.type,
)