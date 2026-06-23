package com.rumpilstilstkin.gloomhavenhelper.designsystem.components.counter

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.AppIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.GloomIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme

@Composable
fun GloomCountButton(
    value: Int,
    modifier: Modifier = Modifier,
    type: PickerButtonType = PickerButtonType.PLUS,
    onValueChange: (Int) -> Unit = {},
) {
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
                .size(36.dp),
        onClick = { onValueChange(type.action(value)) },
    ) {
        Icon(
            modifier = modifier.size(24.dp),
            painter = type.image.painter(),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface,
        )
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
        }
    }
}