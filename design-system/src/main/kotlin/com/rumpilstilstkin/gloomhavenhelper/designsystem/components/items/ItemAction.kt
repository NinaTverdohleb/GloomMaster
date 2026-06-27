package com.rumpilstilstkin.gloomhavenhelper.designsystem.components.items

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.AppIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.GloomIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme

@Composable
fun GloomItemActionIcon(
    onClick: () -> Unit,
    icon: GloomIcon,
    isError: Boolean,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    val contentColor =
        if (isError) {
            MaterialTheme.colorScheme.error
        } else {
            MaterialTheme.colorScheme.onSurface
        }

    val borderColor =
        if (isError) {
            MaterialTheme.colorScheme.error
        } else {
            MaterialTheme.colorScheme.outline
        }

    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(width = 1.dp, color = borderColor),
        colors =
            ButtonDefaults.outlinedButtonColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                contentColor = contentColor,
                disabledContainerColor = MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 0.5f),
                disabledContentColor = contentColor.copy(alpha = 0.38f),
            ),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 10.dp),
    ) {
        Icon(
            painter = icon.painter(),
            contentDescription = null,
            modifier = Modifier.size(28.dp),
        )
    }
}

@Preview
@Composable
private fun GloomItemActionPreview() {
    GloomhavenMasterTheme {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            GloomItemActionIcon(
                icon = AppIcon.Delete,
                isError = false,
                onClick = {},
            )
        }
    }
}
