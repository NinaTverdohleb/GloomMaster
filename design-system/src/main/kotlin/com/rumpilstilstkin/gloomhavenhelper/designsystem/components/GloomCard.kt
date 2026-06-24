package com.rumpilstilstkin.gloomhavenhelper.designsystem.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme

@Composable
fun GloomCard(
    modifier: Modifier = Modifier,
    active: Boolean = false,
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceContainer,
    content: @Composable ColumnScope.() -> Unit,
) = Card(
    modifier = modifier,
    shape = RoundedCornerShape(16.dp),
    colors =
        cardColors(
            containerColor = backgroundColor,
        ),
    border =
        BorderStroke(
            1.dp,
            if(active) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
        ),
    content = content
)

@Preview
@Composable
private fun GloomCardPreview() {
    GloomhavenMasterTheme {
        GloomCard(
            modifier = Modifier.fillMaxWidth(),
            active = false
        ) {
            Box(
                modifier = Modifier.height(40.dp),
            )
        }
    }
}
