package com.rumpilstilstkin.gloomhavenhelper.designsystem.components.counter

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.AppIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.GloomIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun GloomCountButtonSmall(
    value: Int,
    modifier: Modifier = Modifier,
    type: PickerButtonType = PickerButtonType.PLUS,
    onValueChange: (Int) -> Unit = {},
) {
    IconButton(
        modifier =
            modifier
                .background(color = Color.Transparent)
                .size(24.dp),
        onClick = { onValueChange(type.action(value)) },
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .padding(4.dp)
        ) {
            Icon(
                painter = type.image.painter(),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}


@Composable
fun GloomCountButton(
    value: Int,
    modifier: Modifier = Modifier,
    repeat: Boolean = false,
    type: PickerButtonType = PickerButtonType.PLUS,
    onValueChange: (Int) -> Unit = {},
) {
    val scope = rememberCoroutineScope()
    val initialDelayMillis = 500.milliseconds
    val repeatIntervalMillis = 100.milliseconds
    IconButton(
        modifier =
            modifier
                .background(
                    color = MaterialTheme.colorScheme.surfaceBright,
                    shape = RoundedCornerShape(10.dp),
                )
                .border(
                    shape = RoundedCornerShape(10.dp),
                    color = MaterialTheme.colorScheme.outline,
                    width = 1.dp,
                )
                .size(36.dp)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            onValueChange(type.action(value))
                        },
                        onPress = {
                            if (repeat) {
                                val job = scope.launch {
                                    delay(initialDelayMillis)
                                    while (true) {
                                        onValueChange(type.action(value))
                                        delay(repeatIntervalMillis)
                                    }
                                }
                                try {
                                    awaitRelease()
                                } finally {
                                    job.cancel()
                                }
                            }
                        }
                    )
                },
        onClick = { },
    ) {
        Box(
            modifier = Modifier.size(36.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = modifier.size(24.dp),
                painter = type.image.painter(),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface,
            )
        }
    }

}

enum class PickerButtonType(
    val image: GloomIcon,
    val action: (Int) -> Int,
) {
    PLUS(AppIcon.Plus, { it + 1 }),
    MINUS(AppIcon.Minus, { it - 1 }),
}


@Preview
@Composable
private fun GloomCountButtonPreview() {
    GloomhavenMasterTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            GloomCountButton(
                value = 0,
                type = PickerButtonType.PLUS
            )

            GloomCountButton(
                value = 0,
                type = PickerButtonType.MINUS
            )

            GloomCountButtonSmall(
                value = 0,
                type = PickerButtonType.PLUS
            )

            GloomCountButtonSmall(
                value = 0,
                type = PickerButtonType.MINUS
            )
        }
    }
}