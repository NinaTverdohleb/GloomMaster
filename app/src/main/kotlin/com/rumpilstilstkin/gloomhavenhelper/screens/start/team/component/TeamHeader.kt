package com.rumpilstilstkin.gloomhavenhelper.screens.start.team.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.toNumberIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme

@Composable
internal fun TeamHeader(
    teamName: String,
    teamLevel: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier.size(77.dp),
            painter = teamLevel.toNumberIcon().painter(),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.secondary,
        )
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            modifier =
                Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically),
            text = teamName,
            color = MaterialTheme.colorScheme.primaryContainer,
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
        )
    }
}
