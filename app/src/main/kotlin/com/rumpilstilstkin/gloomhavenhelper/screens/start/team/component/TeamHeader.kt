package com.rumpilstilstkin.gloomhavenhelper.screens.start.team.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.ui.components.RoundButton
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenMasterTheme

@Composable
internal fun TeamHeader(
    teamName: String,
    teamLevel: Int,
    modifier: Modifier = Modifier,
    onLevelClick: () -> Unit = {},
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RoundButton(
            text = teamLevel.toString(),
        ) {
            onLevelClick.invoke()
        }
        Spacer(modifier = Modifier.size(24.dp))
        Text(
            modifier =
                Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically),
            text = teamName,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.headlineMedium,
        )
    }
}

@Preview
@Composable
private fun Sample() {
    GloomhavenMasterTheme {
        TeamHeader(
            teamName = "Super mega long team name that is incredibly huge",
            teamLevel = 6,
            onLevelClick = {},
        )
    }
}
