package com.rumpilstilstkin.gloomhavenhelper.designsystem.components.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.AppIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.GloomIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme


@Composable
fun GloomOutlineButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: GloomIcon? = null,
    isError: Boolean = false,
    enabled: Boolean = true
) {
    GloomOutlineButtonBasic(
        text = text,
        onClick = onClick,
        modifier = modifier,
        icon = icon,
        isFilled = false,
        isError = isError,
        enabled = enabled,
    )
}

@Composable
fun GloomOutlineButtonIcon(
    icon: GloomIcon,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    enabled: Boolean = true
) {
    GloomOutlineButtonBasic(
        onClick = onClick,
        modifier = modifier,
        icon = icon,
        isFilled = false,
        isError = isError,
        enabled = enabled,
    )
}

@Composable
fun GloomOutlineFilledButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: GloomIcon? = null,
    isError: Boolean = false,
    enabled: Boolean = true
) {
    GloomOutlineButtonBasic(
        text = text,
        onClick = onClick,
        modifier = modifier,
        icon = icon,
        isFilled = true,
        isError = isError,
        enabled = enabled,
    )
}

@Composable
fun GloomOutlineFilledButtonIcon(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: GloomIcon,
    isError: Boolean = false,
    enabled: Boolean = true
) {
    GloomOutlineButtonBasic(
        onClick = onClick,
        modifier = modifier,
        icon = icon,
        isFilled = true,
        isError = isError,
        enabled = enabled,
    )
}

@Composable
private fun GloomOutlineButtonBasic(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String? = null,
    icon: GloomIcon? = null,
    isFilled: Boolean = false,
    isError: Boolean = false,
    enabled: Boolean = true
) {
    val contentColor = if (isError) {
        MaterialTheme.colorScheme.error
    } else {
        MaterialTheme.colorScheme.onSurface
    }

    val containerColor = if (isFilled) {
        MaterialTheme.colorScheme.surfaceContainer
    } else {
        Color.Transparent
    }

    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(width = 1.dp, color = contentColor),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = containerColor.copy(alpha = 0.5f),
            disabledContentColor = contentColor.copy(alpha = 0.38f)
        ),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 10.dp),
    ) {
        icon?.let {
            Icon(
                painter = icon.painter(),
                contentDescription = null,
                modifier = Modifier.size(28.dp)
            )
        }
        if (text != null && icon != null) {
            Spacer(modifier = Modifier.width(4.dp))
        }
        text?.let {
            Text(
                text = text,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@Preview
@Composable
private fun GloomOutlineButtonPreview() {
    GloomhavenMasterTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GloomOutlineButton(
                text = "Action",
                icon = AppIcon.Delete,
                isError = true,
                onClick = {}
            )

            GloomOutlineButton(
                text = "Action",
                icon = AppIcon.Check,
                isError = false,
                onClick = {}
            )

            GloomOutlineButtonIcon(
                icon = AppIcon.Check,
                isError = false,
                onClick = {}
            )

            GloomOutlineFilledButton(
                text = "Action",
                icon = AppIcon.Delete,
                isError = true,
                onClick = {}
            )

            GloomOutlineFilledButton(
                text = "Action",
                icon = AppIcon.Check,
                isError = false,
                onClick = {}
            )

            GloomOutlineFilledButtonIcon(
                icon = AppIcon.Check,
                isError = false,
                onClick = {}
            )
        }
    }
}