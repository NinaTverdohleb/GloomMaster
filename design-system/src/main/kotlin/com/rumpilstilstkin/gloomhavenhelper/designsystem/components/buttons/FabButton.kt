package com.rumpilstilstkin.gloomhavenhelper.designsystem.components.buttons

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
fun GloomFab(
    icon: GloomIcon,
    onClick: () -> Unit
) {
    val shape = RoundedCornerShape(16.dp)
    FloatingActionButton(
        modifier = Modifier.border(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outline,
            shape = shape
        ),
        onClick = onClick,
        shape = shape,
        containerColor = MaterialTheme.colorScheme.surfaceBright,
        contentColor = MaterialTheme.colorScheme.onSurface,
    ) {
        Icon(
            modifier = Modifier.size(32.dp),
            painter = icon.painter(),
            contentDescription = null
        )
    }
}

@Preview
@Composable
private fun GloomFabPreview() {
    GloomhavenMasterTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GloomFab(
                icon = AppIcon.Plus,
                onClick = {}
            )
        }
    }
}