package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
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
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme
import kotlin.math.floor
import kotlin.random.Random

@Composable
fun SpinningNumberOverlay(
    visible: Boolean,
    targetNumber: Int,
    spinId: Int,
    modifier: Modifier = Modifier,
    onFinished: () -> Unit = {},
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut(),
        modifier = modifier,
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.6f)),
            contentAlignment = Alignment.Center,
        ) {
            SpinningCoin(
                targetNumber = targetNumber,
                spinId = spinId,
                onFinished = onFinished,
            )
        }
    }
}

@Composable
fun SpinningCoin(
    targetNumber: Int,
    spinId: Int,
    size: Dp = 140.dp,
    fullTurns: Int = 5,
    durationMillis: Int = 2200,
    onFinished: () -> Unit = {},
) {
    val angle = remember { Animatable(0f) }
    var numbers by remember { mutableStateOf(intArrayOf(targetNumber)) }

    LaunchedEffect(spinId) {
        val halfTurns = fullTurns * 2
        numbers =
            IntArray(halfTurns + 1) { i ->
                if (i == halfTurns) targetNumber else Random.nextInt(0, 10)
            }
        angle.snapTo(0f)
        angle.animateTo(
            targetValue = 360f * fullTurns,
            animationSpec =
                tween(
                    durationMillis = durationMillis,
                    easing = CubicBezierEasing(0.10f, 0.85f, 0.18f, 1f),
                ),
        )
        onFinished()
    }

    val a = angle.value
    val last = numbers.lastIndex

    fun idxFor(
        base: Float,
        faceParity: Int,
    ): Int {
        val turns = floor((base + 90f) / 360f).toInt()
        return (turns * 2 + faceParity).coerceIn(0, last)
    }

    val numberA = numbers[idxFor(a, 0)]
    val numberB = numbers[idxFor(a + 180f, 1)]

    Box(
        modifier = Modifier.size(size),
        contentAlignment = Alignment.Center,
    ) {
        DieFace(number = numberA, rotation = a, size = size)
        DieFace(number = numberB, rotation = a, size = size)
    }
}

@Composable
private fun DieFace(
    number: Int,
    rotation: Float,
    size: Dp,
) {
    val density = LocalDensity.current
    Box(
        modifier =
            Modifier
                .size(size)
                .graphicsLayer {
                    this.rotationY = rotation
                    cameraDistance = 14f * density.density
                }.background(
                    color = MaterialTheme.colorScheme.surfaceBright,
                    shape = CircleShape,
                ).border(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.onSurface,
                    width = 2.dp,
                ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = number.toString(),
            style =
                MaterialTheme
                    .typography
                    .headlineLarge
                    .copy(fontSize = 56.sp),
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Preview
@Composable
private fun SpinningNumberDemo() {
    GloomhavenMasterTheme {
        var visible by remember { mutableStateOf(false) }
        var target by remember { mutableStateOf(5) }
        var spinId by remember { mutableStateOf(0) }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Box(
                modifier =
                    Modifier
                        .size(400.dp)
                        .clipToBounds(),
                contentAlignment = Alignment.Center,
            ) {
                Button(onClick = {
                    target = Random.nextInt(0, 10)
                    spinId++
                    visible = true
                }) {
                    Text("Старт")
                }

                SpinningNumberOverlay(
                    visible = visible,
                    targetNumber = target,
                    spinId = spinId,
                    onFinished = { visible = false }, // Автоматическое скрытие
                )
            }
        }
    }
}
