package com.rumpilstilstkin.gloomhavenhelper.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenHalperTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GloomToolbarStatus(
    status: String,
    statusClick: () -> Unit = {},
    back: (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
) = CenterAlignedTopAppBar(
    windowInsets = WindowInsets(0, 0, 0, 0),
    title = {
        Box(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(16.dp)
                )
                .heightIn(min = 24.dp)
                .clickable {
                    statusClick()
                }
                .padding(horizontal = 10.dp, vertical = 2.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier.padding(4.dp),
                text = status,
                style = LocalTextStyle.current.copy(
                    fontSize = 14.sp,
                    lineHeight = 12.sp,
                    platformStyle = PlatformTextStyle(includeFontPadding = false),
                    lineHeightStyle = LineHeightStyle(
                        alignment = LineHeightStyle.Alignment.Center,
                        trim = LineHeightStyle.Trim.Both
                    )
                ),
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )
        }
    },
    navigationIcon = {
        if (back != null) {
            IconButton(onClick = back) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    },
    actions = actions
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GloomToolbarTitle(
    title: String,
    back: (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
) = TopAppBar(
    windowInsets = WindowInsets(0, 0, 0, 0),
    title = {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )
    },
    navigationIcon = {
        if (back != null) {
            IconButton(onClick = back) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    },
    actions = actions
)

@Preview
@Composable
private fun GloomToolbarStatusPreview() {
    GloomhavenHalperTheme {
        GloomToolbarStatus(
            status = "Status",
            back = {},
            actions = {
                IconButton(onClick = {}) {
                    Icon(
                        Icons.Default.Check,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        )
    }
}

@Preview
@Composable
private fun GloomToolbarTitlePreview() {
    GloomhavenHalperTheme {
        GloomToolbarTitle(
            title = "Status",
            back = {},
            actions = {
                IconButton(onClick = {}) {
                    Icon(
                        Icons.Default.Check,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        )
    }
}