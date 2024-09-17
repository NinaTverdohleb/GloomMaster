package com.rumpilstilstkin.gloomhavenhelper.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenHalperTheme
import com.rumpilstilstkin.gloomhavenhelper.ui.view.RoundButton


@Composable
fun TitleTeamRow(
    teamName: String,
    teamLevel: Int,
    modifier: Modifier = Modifier,
    onLevelClick: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically),
            text = teamName,
            style = MaterialTheme.typography.titleLarge,
        )
        Spacer(modifier = Modifier.size(16.dp))
        RoundButton(
            text = teamLevel.toString(),
            color = MaterialTheme.colorScheme.secondary,
            size = 64.dp
        ) {
            onLevelClick.invoke()
        }
    }
}

@Preview
@Composable
private fun Sample() {
    GloomhavenHalperTheme {
        TitleTeamRow(
            teamName = "Супер мега длинное имя пипец какое невыносимо огромное",
            teamLevel = 6
        ) {}
    }

}
