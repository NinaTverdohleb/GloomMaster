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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEvent
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEventHelper
import kotlinx.coroutines.launch

sealed interface ScreenEffect {
    data class OpenBottomSheet(val session: BottomSheetSession) : ScreenEffect
    data object CloseBottomSheet : ScreenEffect
    data class Navigation(val event: GlHelperEvent) : ScreenEffect
    data class Message(val message: String) : ScreenEffect
}

interface BottomSheetContract<Input, Output> {
    @Composable
    fun Content(input: Input, onDismissWithResult: (Output?) -> Unit)
}

interface BottomSheetSession {
    @Composable
    fun Render(onDismiss: () -> Unit)
}

fun <Input, Output> createBottomSheetSession(
    contract: BottomSheetContract<Input, Output>,
    input: Input,
    onResult: (Output?) -> Unit
): BottomSheetSession = object : BottomSheetSession {

    @Composable
    override fun Render(onDismiss: () -> Unit) {
        contract.Content(
            input = input,
            onDismissWithResult = { result ->
                onResult(result)
                onDismiss()
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LaunchedScreenEffect(navController: NavHostController, effect: ScreenEffect?) {
    var currentSession by remember { mutableStateOf<BottomSheetSession?>(null) }
    val sheetState = rememberModalBottomSheetState()

    LaunchedEffect(effect) {
        effect?.let {
            when (it) {
                is ScreenEffect.OpenBottomSheet -> currentSession = it.session
                ScreenEffect.CloseBottomSheet -> currentSession = null
                is ScreenEffect.Navigation -> GlHelperEventHelper.event(
                    event = it.event,
                    navController = navController
                )

                is ScreenEffect.Message -> {
                    Toast.makeText(navController.context, it.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    currentSession?.let { session ->
        ModalBottomSheet(
            onDismissRequest = {
                currentSession = null
            },
            sheetState = sheetState
        ) {
            session.Render(
                onDismiss = { currentSession = null }
            )
        }
    }
}