package com.rumpilstilstkin.gloomhavenhelper.ui.components.items

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.shape.RoundedCornerShape
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
fun LeftItemIcon(
    icon: Painter
) = Icon(
    painter = icon,
    contentDescription = null,
    modifier = Modifier.size(32.dp),
    tint = MaterialTheme.colorScheme.onSurface,
)

@Composable
fun LeftItemImage(
    icon: Painter
) = Box(
    modifier = Modifier
        .background(
            color = MaterialTheme.colorScheme.surfaceContainerLow,
            shape = RoundedCornerShape(8.dp)
        )
        .border(
            width = 1.dp,
            shape = RoundedCornerShape(8.dp),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        .padding(8.dp)
){
    Icon(
        painter = icon,
        contentDescription = null,
        modifier = Modifier.size(28.dp),
        tint = MaterialTheme.colorScheme.onSurfaceVariant,
    )
}

@Preview
@Composable
private fun GloomItemLeftItemComponentsPreview() {
    GloomhavenMasterTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LeftItemIcon(
                icon = painterResource(R.drawable.ic_plus)
            )
            LeftItemImage(
                icon = painterResource(R.drawable.ic_team)
            )
        }
    }
}