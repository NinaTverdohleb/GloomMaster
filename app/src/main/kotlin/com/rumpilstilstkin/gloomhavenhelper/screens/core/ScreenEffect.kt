package com.rumpilstilstkin.gloomhavenhelper.screens.core

import android.widget.Toast
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.dialogs.GloomBasicDialog
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEvent
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEventHelper
import kotlinx.coroutines.launch

sealed interface ScreenEffect {
    data class OpenBottomSheet(
        val session: OverlaySession,
    ) : ScreenEffect

    data class OpenDialog(
        val session: OverlaySession,
    ) : ScreenEffect

    data object CloseBottomSheet : ScreenEffect

    data object CloseDialog : ScreenEffect

    data class Navigation(
        val event: GlHelperEvent,
    ) : ScreenEffect

    data class Message(
        val message: String,
    ) : ScreenEffect
}

interface OverlayContract<Input, Output> {
    @Composable
    fun Content(
        input: Input,
        onDismissWithResult: (Output?) -> Unit,
    )
}

interface OverlaySession {
    @Composable
    fun Render(onDismiss: () -> Unit)
}

fun <Input, Output> createOverlaySession(
    contract: OverlayContract<Input, Output>,
    input: Input,
    onResult: (Output?) -> Unit,
): OverlaySession =
    object : OverlaySession {
        @Composable
        override fun Render(onDismiss: () -> Unit) {
            contract.Content(
                input = input,
                onDismissWithResult = { result ->
                    onResult(result)
                    onDismiss()
                },
            )
        }
    }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LaunchedScreenEffect(
    navController: NavHostController,
    effect: ScreenEffect?,
) {
    var currentBottomSheetSession by remember { mutableStateOf<OverlaySession?>(null) }
    var currentDialogSession by remember { mutableStateOf<OverlaySession?>(null) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    LaunchedEffect(effect) {
        effect?.let {
            when (it) {
                is ScreenEffect.OpenBottomSheet -> {
                    currentBottomSheetSession = it.session
                }

                ScreenEffect.CloseBottomSheet -> {
                    launch { sheetState.hide() }.invokeOnCompletion {
                        currentBottomSheetSession = null
                    }
                }

                is ScreenEffect.Navigation -> {
                    GlHelperEventHelper.event(
                        event = it.event,
                        navController = navController,
                    )
                }

                is ScreenEffect.Message -> {
                    Toast.makeText(navController.context, it.message, Toast.LENGTH_LONG).show()
                }

                ScreenEffect.CloseDialog -> {
                    currentDialogSession = null
                }

                is ScreenEffect.OpenDialog -> {
                    currentDialogSession = it.session
                }
            }
        }
    }

    currentBottomSheetSession?.let { session ->
        ModalBottomSheet(
            onDismissRequest = {
                currentBottomSheetSession = null
            },
            sheetState = sheetState,
        ) {
            session.Render(
                onDismiss = { currentBottomSheetSession = null },
            )
        }
    }

    currentDialogSession?.let { session ->
        GloomBasicDialog(
            onDismissRequest = { currentDialogSession = null },
        ) {
            session.Render(
                onDismiss = { currentDialogSession = null },
            )
        }
    }
}
