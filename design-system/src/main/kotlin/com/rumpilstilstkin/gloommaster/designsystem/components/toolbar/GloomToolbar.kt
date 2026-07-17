package com.rumpilstilstkin.gloommaster.designsystem.components.toolbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloommaster.designsystem.R
import com.rumpilstilstkin.gloommaster.designsystem.icons.AppIcon
import com.rumpilstilstkin.gloommaster.designsystem.icons.GloomIcon
import com.rumpilstilstkin.gloommaster.designsystem.icons.NavigationIcon
import com.rumpilstilstkin.gloommaster.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloommaster.testtags.components.ToolbarTestTags

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GloomToolbarAction(
    title: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    actionClick: () -> Unit = {},
    actionIcon: GloomIcon = AppIcon.Settings,
    back: (() -> Unit)? = null,
) = TopAppBar(
    modifier = modifier,
    title = {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
        )
    },
    navigationIcon = {
        Row {
            Spacer(Modifier.width(16.dp))
            if (back != null) {
                IconButton(
                    modifier = Modifier.testTag(ToolbarTestTags.BACK),
                    onClick = back,
                ) {
                    Icon(
                        painter = NavigationIcon.Close.painter(),
                        contentDescription = stringResource(R.string.design_system_back),
                    )
                }
            }
        }
    },
    actions = {
        IconButton(onClick = actionClick) {
            Icon(
                actionIcon.painter(),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface,
            )
        }
    },
    colors =
        TopAppBarDefaults.topAppBarColors(
            containerColor = backgroundColor,
            titleContentColor = MaterialTheme.colorScheme.onSurface,
            navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
        ),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GloomToolbarAction(
    title: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    actions: @Composable RowScope.() -> Unit = {},
    back: (() -> Unit)? = null,
) = TopAppBar(
    modifier = modifier,
    title = {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
        )
    },
    navigationIcon = {
        Row {
            Spacer(Modifier.width(16.dp))
            if (back != null) {
                IconButton(
                    modifier = Modifier.testTag(ToolbarTestTags.BACK),
                    onClick = back,
                ) {
                    Icon(
                        painter = NavigationIcon.Close.painter(),
                        contentDescription = stringResource(R.string.design_system_back),
                    )
                }
            }
        }
    },
    actions = actions,
    colors =
        TopAppBarDefaults.topAppBarColors(
            containerColor = backgroundColor,
            titleContentColor = MaterialTheme.colorScheme.onSurface,
            navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
        ),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GloomToolbarNoBackAction(
    title: String,
    titleIcon: GloomIcon,
    modifier: Modifier = Modifier,
    actionClick: () -> Unit = {},
    actionIcon: GloomIcon = AppIcon.Settings,
) = TopAppBar(
    modifier = modifier,
    title = {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(Modifier.width(16.dp))
            Icon(
                titleIcon.painter(),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface,
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
            )
        }
    },
    actions = {
        IconButton(onClick = actionClick) {
            Icon(
                actionIcon.painter(),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface,
            )
        }
    },
    colors =
        TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = MaterialTheme.colorScheme.onSurface,
            navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
        ),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GloomToolbar(
    title: String,
    modifier: Modifier = Modifier,
    back: (() -> Unit)? = null,
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
            )
        },
        navigationIcon = {
            Row {
                Spacer(Modifier.width(16.dp))
                if (back != null) {
                    IconButton(
                        modifier = Modifier.testTag(ToolbarTestTags.BACK),
                        onClick = back,
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.design_system_ic_close),
                            contentDescription = stringResource(R.string.design_system_back),
                        )
                    }
                }
            }
        },
        colors =
            TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                titleContentColor = MaterialTheme.colorScheme.onSurface,
                navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
            ),
    )
}

@Preview
@Composable
private fun GloomToolbarPreview() {
    GloomhavenMasterTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            GloomToolbarNoBackAction(
                title = "Status",
                titleIcon = AppIcon.Team,
            )

            GloomToolbarAction(
                title = "Status",
                back = {},
            )

            GloomToolbar(
                title = "Status",
                back = {},
            )
        }
    }
}
