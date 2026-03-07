package com.rumpilstilstkin.gloomhavenhelper.screens.start.scenarios

import androidx.compose.runtime.Immutable

@Immutable
data class ScenariosTabStateUi(
    val placeholder: Boolean = false,
)

sealed interface ScenariosTabAction
