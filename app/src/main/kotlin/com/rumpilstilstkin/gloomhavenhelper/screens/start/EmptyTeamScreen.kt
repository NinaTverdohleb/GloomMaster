package com.rumpilstilstkin.gloomhavenhelper.screens.start

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.buttons.GloomButton
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.empty.EmptyIconView
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.EmptyIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloomhavenhelper.testtags.screens.start.EmptyTeamScreenTestTags

@Composable
internal fun EmptyTeamScreen(
    modifier: Modifier = Modifier,
    addTeam: () -> Unit,
) = Column(
    modifier =
        modifier
            .fillMaxSize()
            .padding(32.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center,
) {
    EmptyIconView(EmptyIcon.Logo)
    Spacer(modifier = Modifier.height(48.dp))
    GloomButton(
        modifier =
            Modifier
                .fillMaxWidth()
                .testTag(EmptyTeamScreenTestTags.START_BUTTON),
        text = stringResource(R.string.start_adventure),
        onClick = addTeam,
    )
}

@Preview
@Composable
private fun EmptyTeamScreenPreview() {
    GloomhavenMasterTheme {
        EmptyTeamScreen(addTeam = {})
    }
}
