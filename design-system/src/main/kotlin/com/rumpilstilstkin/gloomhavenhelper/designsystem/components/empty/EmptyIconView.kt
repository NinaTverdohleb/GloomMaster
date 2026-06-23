package com.rumpilstilstkin.gloomhavenhelper.designsystem.components.empty

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.EmptyIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme

@Composable
fun EmptyIconView(
    icon: EmptyIcon,
    modifier: Modifier = Modifier
) = Box(
    modifier =
        modifier
            .size(120.dp)
            .background(
                color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f),
                shape = CircleShape,
            )
            .border(
                shape = CircleShape,
                color = MaterialTheme.colorScheme.secondary,
                width = 2.dp,
            ),
    contentAlignment = Alignment.Center,
) {
    Icon(
        painter = icon.painter(),
        contentDescription = null,
        tint = MaterialTheme.colorScheme.secondary,
    )
}

@Preview
@Composable
private fun EmptyIconPreview() {
    GloomhavenMasterTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EmptyIconView(
                icon = EmptyIcon.Characters
            )

        }
    }
}