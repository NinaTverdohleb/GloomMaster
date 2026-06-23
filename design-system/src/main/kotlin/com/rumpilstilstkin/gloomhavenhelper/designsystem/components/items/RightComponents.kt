package com.rumpilstilstkin.gloomhavenhelper.designsystem.components.items

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.AppIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.GloomIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme

@Composable
fun RightItemText(
    text: String
) = Text(
    text = text,
    style = MaterialTheme.typography.bodyMedium,
    color = MaterialTheme.colorScheme.onSurfaceVariant
)

@Composable
fun RightItemIcon(
    icon: GloomIcon
) = Icon(
    painter = icon.painter(),
    contentDescription = null,
    modifier = Modifier.size(32.dp),
    tint = MaterialTheme.colorScheme.onSurface,
)

@Composable
fun RightItemChecker(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
) = Checkbox(
    checked = checked,
    colors = CheckboxDefaults.colors(
        uncheckedColor = MaterialTheme.colorScheme.primary,

        ),
    onCheckedChange = onCheckedChange,
)

@Composable
fun RightItemNumber(
    number: String
) = Box(
    modifier =
        Modifier
            .size(40.dp)
            .background(
                color = MaterialTheme.colorScheme.secondary,
                shape = CircleShape,
            ),
    contentAlignment = Alignment.Center,
) {
    Text(
        modifier = Modifier,
        style = MaterialTheme.typography.headlineSmall,
        text = number,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.onSecondary,
    )
}

@Preview
@Composable
private fun GloomItemRightItemComponentsPreview() {
    GloomhavenMasterTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            RightItemText(
                text = "Some text"
            )

            RightItemIcon(
                icon = AppIcon.Check
            )

            RightItemChecker(
                checked = true,
                onCheckedChange = null
            )

            RightItemChecker(
                checked = false,
                onCheckedChange = null
            )

            RightItemNumber(
                number = "5"
            )
        }
    }
}