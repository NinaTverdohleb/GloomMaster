package com.rumpilstilstkin.gloomhavenhelper.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.rumpilstilstkin.gloomhavenhelper.ui.icons.Minus
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenHalperTheme

@Composable
fun PickerButton(
    value: Int,
    type: PickerButtonType,
    modifier: Modifier = Modifier,
    onValueChange: (Int) -> Unit = {},
) {
    IconButton(
        modifier = modifier.background(
            color = MaterialTheme.colorScheme.primary,
            shape = CircleShape
        ),
        onClick = {
            onValueChange(
                type.action(value)
            )
        },
    ) {
        Icon(
            imageVector = type.image,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onPrimary
        )
    }
}

enum class PickerButtonType(
    val image: ImageVector,
    val action: (Int) -> Int
) {
    PLUS(Icons.Rounded.Add, { it + 1 }),
    MINUS(Minus, { it - 1 })
}

@Preview
@Composable
private fun SamplePlus() {
    GloomhavenHalperTheme {

        PickerButton(
            0,
            PickerButtonType.PLUS
        )
    }
}

@Preview
@Composable
private fun SampleMinus() {
    GloomhavenHalperTheme {
        PickerButton(
            0,
            PickerButtonType.MINUS
        )
    }
}