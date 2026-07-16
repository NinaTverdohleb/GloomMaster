package com.rumpilstilstkin.gloommaster.screens.core

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavHostController
import com.rumpilstilstkin.gloommaster.designsystem.components.dialogs.GloomBasicDialog
import com.rumpilstilstkin.gloommaster.designsystem.components.dialogs.GloomBasicModalBottomSheet
import com.rumpilstilstkin.gloommaster.navigation.events.GlHelperEvent
import com.rumpilstilstkin.gloommaster.navigation.events.GlHelperEventHelper
import kotlinx.coroutines.flow.Flow
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
        @param:StringRes val resId: Int,
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
    effects: Flow<ScreenEffect>,
) {
    var currentBottomSheetSession by remember { mutableStateOf<OverlaySession?>(null) }
    var currentDialogSession by remember { mutableStateOf<OverlaySession?>(null) }
    val sheetState =
        rememberModalBottomSheetState(
            skipPartiallyExpanded = true,
        )
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(effects, lifecycleOwner) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            effects.collect { effect ->
                when (effect) {
                    is ScreenEffect.OpenBottomSheet -> {
                        currentBottomSheetSession = effect.session
                    }

                    ScreenEffect.CloseBottomSheet -> {
                        launch { sheetState.hide() }.invokeOnCompletion {
                            currentBottomSheetSession = null
                        }
                    }

                    is ScreenEffect.Navigation -> {
                        GlHelperEventHelper.event(
                            event = effect.event,
                            navController = navController,
                        )
                    }

                    is ScreenEffect.Message -> {
                        Toast.makeText(navController.context, effect.resId, Toast.LENGTH_SHORT).show()
                    }

                    ScreenEffect.CloseDialog -> {
                        currentDialogSession = null
                    }

                    is ScreenEffect.OpenDialog -> {
                        currentDialogSession = effect.session
                    }
                }
            }
        }
    }

    currentBottomSheetSession?.let { session ->
        GloomBasicModalBottomSheet(
            onDismissRequest = {
                currentBottomSheetSession = null
            },
            sheetState = sheetState,
        ) {
            OverlayViewModelStoreOwner {
                session.Render(
                    onDismiss = { currentBottomSheetSession = null },
                )
            }
        }
    }

    currentDialogSession?.let { session ->
        GloomBasicDialog(
            onDismissRequest = { currentDialogSession = null },
        ) {
            OverlayViewModelStoreOwner {
                session.Render(
                    onDismiss = { currentDialogSession = null },
                )
            }
        }
    }
}

@Composable
private fun OverlayViewModelStoreOwner(content: @Composable () -> Unit) {
    val parentOwner = checkNotNull(LocalViewModelStoreOwner.current)
    val viewModelStoreOwner =
        remember(parentOwner) {
            OverlayStoreOwner(parentOwner)
        }
    DisposableEffect(viewModelStoreOwner) {
        onDispose { viewModelStoreOwner.viewModelStore.clear() }
    }
    CompositionLocalProvider(
        LocalViewModelStoreOwner provides viewModelStoreOwner,
        content = content,
    )
}

private class OverlayStoreOwner(
    private val parent: ViewModelStoreOwner,
) : ViewModelStoreOwner,
    HasDefaultViewModelProviderFactory {
    override val viewModelStore = ViewModelStore()

    override val defaultViewModelProviderFactory: ViewModelProvider.Factory
        get() =
            (parent as? HasDefaultViewModelProviderFactory)?.defaultViewModelProviderFactory
                ?: ViewModelProvider.NewInstanceFactory()

    override val defaultViewModelCreationExtras: CreationExtras
        get() =
            (parent as? HasDefaultViewModelProviderFactory)?.defaultViewModelCreationExtras
                ?: CreationExtras.Empty
}
