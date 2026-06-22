package com.rumpilstilstkin.gloomhavenhelper.ui.components.items

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenMasterTheme

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
    icon: Painter
) = Icon(
    painter = icon,
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
                icon = painterResource(R.drawable.ic_check)
            )

            RightItemChecker(
                checked = true,
                onCheckedChange = null
            )

            RightItemChecker(
                checked = false,
                onCheckedChange = null
            )
        }
    }
}