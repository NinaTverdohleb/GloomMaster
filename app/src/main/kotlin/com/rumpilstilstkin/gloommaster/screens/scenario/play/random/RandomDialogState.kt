package com.rumpilstilstkin.gloommaster.screens.scenario.play.random

sealed interface RandomDialogState {
    data object Empty : RandomDialogState

    data class Coin(
        val result: Int,
    ) : RandomDialogState

    data class Weird(
        val result: Int,
    ) : RandomDialogState
}

sealed interface RandomDialogAction {
    data object Coin : RandomDialogAction

    data object Weird : RandomDialogAction
}
