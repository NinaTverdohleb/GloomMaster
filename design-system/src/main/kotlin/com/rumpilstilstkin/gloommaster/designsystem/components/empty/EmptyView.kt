package com.rumpilstilstkin.gloommaster.designsystem.components.empty

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloommaster.designsystem.components.buttons.GloomButton
import com.rumpilstilstkin.gloommaster.designsystem.icons.EmptyIcon
import com.rumpilstilstkin.gloommaster.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloommaster.testtags.screens.scenario.play.PlayScenarioScreenTestTags

@Composable
fun EmptyView(
    icon: EmptyIcon,
    title: String,
    description: String,
    modifier: Modifier = Modifier,
) = Column(
    modifier = modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center,
) {
    EmptyIconView(
        icon = icon,
    )
    Spacer(Modifier.height(16.dp))

    Text(
        text = title,
        style = MaterialTheme.typography.headlineLarge,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.onSurface,
    )
    Spacer(modifier = Modifier.height(4.dp))
    Text(
        text = description,
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
}

@Composable
fun EmptyViewWithAction(
    icon: EmptyIcon,
    title: String,
    description: String,
    modifier: Modifier = Modifier,
    actions: @Composable ColumnScope.() -> Unit = {},
) = Column(
    modifier = modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center,
) {
    EmptyIconView(
        icon = icon,
    )
    Spacer(Modifier.height(16.dp))

    Text(
        text = title,
        style = MaterialTheme.typography.headlineLarge,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.onSurface,
    )
    Spacer(modifier = Modifier.height(4.dp))
    Text(
        text = description,
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
    Spacer(modifier = Modifier.height(16.dp))
    actions()
}

@Preview
@Composable
private fun EmptyViewPreview() {
    GloomhavenMasterTheme {
        EmptyView(
            icon = EmptyIcon.Characters,
            title = "No characters",
            description = "Oh no, where are characters?",
        )
    }
}

@Preview
@Composable
private fun EmptyViewWithActionPreview() {
    GloomhavenMasterTheme {
        EmptyViewWithAction(
            icon = EmptyIcon.Characters,
            title = "No characters",
            description = "Oh no, where are characters?",
            actions = {
                GloomButton(
                    text = "Action",
                    modifier = Modifier.defaultMinSize(minWidth = 140.dp),
                    onClick = {},
                )
            },
        )
    }
}
