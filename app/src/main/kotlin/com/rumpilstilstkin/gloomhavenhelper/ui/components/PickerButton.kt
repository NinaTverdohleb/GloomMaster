package com.rumpilstilstkin.gloomhavenhelper.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.ui.icons.Minus
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenHalperTheme

@Composable
fun PickerButton(
    value: Int,
    type: PickerButtonType,
    modifier: Modifier = Modifier,
    onValueChange: (Int) -> Unit = {},
    size: GloomSize = GloomSize.M,
) {
    val sizeDp = when (size) {
        GloomSize.S -> 32.dp
        GloomSize.M -> 48.dp
    }
    IconButton(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.secondaryContainer,
                shape = RoundedCornerShape(16.dp),
            )
            .border(
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.outline,
                width = 1.dp
            )
            .size(sizeDp),
        onClick = {
            onValueChange(
                type.action(value)
            )
        },
    ) {
        Icon(
            modifier = modifier
                .size(sizeDp),
            imageVector = type.image,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
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