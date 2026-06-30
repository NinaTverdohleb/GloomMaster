package com.rumpilstilstkin.gloomhavenhelper.designsystem.components.items

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.counter.GloomCounterSmall
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.AppIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.GloomIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloomhavenhelper.testtags.components.RightComponentsTestTags

@Composable
fun RightItemText(text: String) =
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )

@Composable
fun RightItemLabel(text: String) =
    Text(
        text = text,
        style = MaterialTheme.typography.headlineMedium,
        color = MaterialTheme.colorScheme.surfaceTint,
    )

@Composable
fun RightItemIcon(icon: GloomIcon) =
    Icon(
        painter = icon.painter(),
        contentDescription = null,
        modifier = Modifier.size(32.dp),
        tint = MaterialTheme.colorScheme.onSurface,
    )

@Composable
fun RightItemChecker(
    checked: Boolean,
    modifier: Modifier = Modifier,
    onCheckedChange: ((Boolean) -> Unit)?,
) = Checkbox(
    modifier = modifier.testTag(RightComponentsTestTags.CHECKER),
    checked = checked,
    colors =
        CheckboxDefaults.colors(
            uncheckedColor = MaterialTheme.colorScheme.primary,
        ),
    onCheckedChange = onCheckedChange,
)

@Composable
fun RightItemNumber(number: String) =
    Box(
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

@Composable
fun CounterRightItem(
    value: Int,
    intRange: IntRange,
    onValueChange: (Int) -> Unit,
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surfaceContainerHigh,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface),
    ) {
        GloomCounterSmall(
            modifier = Modifier.padding(8.dp),
            value = value,
            intRange = intRange,
            onValueChange = onValueChange,
        )
    }
}

@Preview
@Composable
private fun GloomItemRightItemComponentsPreview() {
    GloomhavenMasterTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            RightItemText(
                text = "Some text",
            )

            RightItemIcon(
                icon = AppIcon.Check,
            )

            RightItemChecker(
                checked = true,
                onCheckedChange = null,
            )

            RightItemChecker(
                checked = false,
                onCheckedChange = null,
            )

            RightItemNumber(
                number = "5",
            )

            CounterRightItem(
                value = 5,
                intRange = IntRange(0, 15),
                onValueChange = {},
            )

            RightItemLabel(
                text = "150",
            )
        }
    }
}
