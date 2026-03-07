package com.rumpilstilstkin.gloomhavenhelper.screens.start.scenarios

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenHalperTheme

@Composable
internal fun ScenariosTabScreen(
    state: ScenariosTabStateUi,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "Сценарии",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1A1C24)
@Composable
private fun ScenariosTabScreenPreview() {
    GloomhavenHalperTheme {
        ScenariosTabScreen(
            state = ScenariosTabStateUi(),
        )
    }
}
