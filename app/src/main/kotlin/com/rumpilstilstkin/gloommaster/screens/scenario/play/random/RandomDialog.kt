package com.rumpilstilstkin.gloommaster.screens.scenario.play.random

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloommaster.R
import com.rumpilstilstkin.gloommaster.designsystem.components.buttons.GloomOutlineButton
import com.rumpilstilstkin.gloommaster.designsystem.components.text.GloomHeader
import com.rumpilstilstkin.gloommaster.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloommaster.screens.scenario.play.components.SpinningNumberOverlay
import com.rumpilstilstkin.gloommaster.testtags.screens.characters.dialogs.add.AddCharacterDialogTestTags
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

@Composable
fun RandomDialog(
    uiState: RandomDialogState,
    onCoin: () -> Unit,
    onWeird: () -> Unit,
) = Column(
    horizontalAlignment = Alignment.CenterHorizontally,
) {
    GloomHeader(
        text = stringResource(R.string.random_title),
        textAlign = TextAlign.Center,
    )
    Spacer(
        modifier = Modifier.height(24.dp),
    )
    GloomOutlineButton(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(R.string.random_action_coin),
        onClick = onCoin,
    )
    Spacer(
        modifier = Modifier.height(8.dp),
    )
    GloomOutlineButton(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(R.string.random_action_weird),
        onClick = onWeird,
    )

    Spacer(
        modifier = Modifier.height(16.dp),
    )
    Box(
        modifier =
            Modifier
                .height(180.dp)
                .fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_border_top_start),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier =
                Modifier
                    .size(48.dp)
                    .align(Alignment.TopStart),
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_border_top_end),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier =
                Modifier
                    .size(48.dp)
                    .align(Alignment.TopEnd),
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_border_bottom_start),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier =
                Modifier
                    .size(48.dp)
                    .align(Alignment.BottomStart),
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_border_bottom_end),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier =
                Modifier
                    .size(48.dp)
                    .align(Alignment.BottomEnd),
        )
        AnimatedContent(
            targetState = uiState,
            transitionSpec = {
                fadeIn(animationSpec = tween(200)) togetherWith
                    fadeOut(animationSpec = tween(200)) using
                    SizeTransform(
                        clip = false,
                        sizeAnimationSpec = { _, _ -> tween(200) },
                    )
            },
            label = "SmoothTextTransition",
        ) { state ->
            when (state) {
                RandomDialogState.Empty -> {
                    Text(
                        text = stringResource(R.string.empty_weird),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center,
                    )
                }

                is RandomDialogState.Coin -> {
                    SpinningNumberOverlay(
                        visible = true,
                        background = Color.Unspecified,
                        targetNumber = state.result,
                        spinId = state.result,
                        maxValue = 16,
                        size = 100.dp,
                        onFinished = { },
                    )
                }

                is RandomDialogState.Weird -> {
                    WeirdText(
                        text = Weird(state.result),
                    )
                }
            }
        }
    }
}

@Composable
private fun WeirdText(text: String) {
    var displayedText by remember { mutableStateOf("") }

    val blurRadius = remember { Animatable(0f) }
    val alphaValue = remember { Animatable(1f) }

    LaunchedEffect(text) {
        if (displayedText.isEmpty()) {
            displayedText = text
            blurRadius.snapTo(15f)
            alphaValue.snapTo(0f)
            coroutineScope {
                val focusAnim = async { blurRadius.animateTo(0f, animationSpec = tween(400)) }
                val revealAnim = async { alphaValue.animateTo(1f, animationSpec = tween(300)) }
                focusAnim.await()
                revealAnim.await()
            }
            return@LaunchedEffect
        }

        if (displayedText == text) return@LaunchedEffect

        coroutineScope {
            val blurAnim = async { blurRadius.animateTo(30f, animationSpec = tween(250)) }
            val alphaAnim = async { alphaValue.animateTo(0f, animationSpec = tween(200)) }
            blurAnim.await()
            alphaAnim.await()
        }

        displayedText = text

        coroutineScope {
            val focusAnim = async { blurRadius.animateTo(0f, animationSpec = tween(350)) }
            val revealAnim = async { alphaValue.animateTo(1f, animationSpec = tween(250)) }
            focusAnim.await()
            revealAnim.await()
        }
    }

    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = displayedText,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            modifier =
                Modifier
                    .graphicsLayer {
                        alpha = alphaValue.value

                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
                            renderEffect =
                                if (blurRadius.value > 0.5f) {
                                    android.graphics.RenderEffect
                                        .createBlurEffect(
                                            blurRadius.value,
                                            blurRadius.value,
                                            android.graphics.Shader.TileMode.DECAL,
                                        ).asComposeRenderEffect()
                                } else {
                                    null
                                }
                        }
                    },
        )
    }
}

@Preview
@Composable
private fun RandomDialogEmptyPreview() {
    GloomhavenMasterTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            RandomDialog(
                uiState = RandomDialogState.Empty,
                onCoin = {},
                onWeird = {},
            )
        }
    }
}

@Preview
@Composable
private fun RandomDialogCoinPreview() {
    GloomhavenMasterTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            RandomDialog(
                uiState = RandomDialogState.Coin(12),
                onCoin = {},
                onWeird = {},
            )
        }
    }
}

@Preview
@Composable
private fun RandomDialogWeirdPreview() {
    GloomhavenMasterTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            RandomDialog(
                uiState = RandomDialogState.Weird(4),
                onCoin = {},
                onWeird = {},
            )
        }
    }
}
