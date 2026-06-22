package com.rumpilstilstkin.gloomhavenhelper.ui.components.toolbar

import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenMasterTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GloomToolbarAction(
    title: String,
    modifier: Modifier = Modifier,
    actionClick: () -> Unit = {},
    actionIcon: Painter = painterResource(R.drawable.ic_settings),
    back: (() -> Unit)? = null,
) = TopAppBar(
    modifier = modifier,
    title = {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge
        )
    },
    navigationIcon = {
        Row {
            Spacer(Modifier.width(16.dp))
            if (back != null) {
                IconButton(onClick = back) {
                    Icon(
                        painter = painterResource(R.drawable.ic_close),
                        contentDescription = stringResource(R.string.back)
                    )
                }
            }
        }
    },
    actions = {
        IconButton(onClick = actionClick) {
            Icon(
                actionIcon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface,
            )
        }
    },
    colors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.surface,
        titleContentColor = MaterialTheme.colorScheme.onSurface,
        navigationIconContentColor = MaterialTheme.colorScheme.onSurface
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
                style = MaterialTheme.typography.titleLarge
            )
        },
        navigationIcon = {
            Row {
                Spacer(Modifier.width(16.dp))
                if (back != null) {
                    IconButton(onClick = back) {
                        Icon(
                            painter = painterResource(R.drawable.ic_close),
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = MaterialTheme.colorScheme.onSurface,
            navigationIconContentColor = MaterialTheme.colorScheme.onSurface
        )
    )
}

@Preview
@Composable
private fun GloomToolbarActionPreview() {
    GloomhavenMasterTheme {
        GloomToolbarAction(
            title = "Status",
            back = {},
        )
    }
}

@Preview
@Composable
private fun GloomToolbarPreview() {
    GloomhavenMasterTheme {
        GloomToolbar(
            title = "Status",
            back = {},
        )
    }
}
