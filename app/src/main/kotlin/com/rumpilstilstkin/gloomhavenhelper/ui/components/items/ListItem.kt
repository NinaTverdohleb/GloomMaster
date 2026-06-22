package com.rumpilstilstkin.gloomhavenhelper.ui.components.items

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomCard
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenMasterTheme

@Composable
fun GloomListFilledItem(
    title: String,
    modifier: Modifier = Modifier,
    description: String? = null,
    onClick: (() -> Unit)? = null,
    rightComponent: (@Composable RowScope.() -> Unit)? = null,
    leftComponent: (@Composable RowScope.() -> Unit)? = null,
) {
    GloomCard(
        modifier = modifier,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 12.dp)
        ) {
            GloomListItem(
                modifier = Modifier.fillMaxWidth(),
                title = title,
                description = description,
                onClick = onClick,
                rightComponent = rightComponent,
                leftComponent = leftComponent,
            )
        }
    }
}

@Composable
fun GloomListItem(
    title: String,
    modifier: Modifier = Modifier,
    description: String? = null,
    onClick: (() -> Unit)? = null,
    rightComponent: (@Composable RowScope.() -> Unit)? = null,
    leftComponent: (@Composable RowScope.() -> Unit)? = null,
) = Row(
    modifier = modifier
        .fillMaxWidth()
        .heightIn(min = 64.dp)
        .clickable { onClick?.invoke() }
        .padding(horizontal = 4.dp),
    verticalAlignment = Alignment.CenterVertically
) {
    leftComponent?.let {
        leftComponent()
        Spacer(Modifier.width(16.dp))
    }
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        description?.let {
            Text(
                text = description,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
    Spacer(Modifier.weight(1f))
    rightComponent?.let {
        Spacer(Modifier.width(8.dp))
        rightComponent()
    }
}

@Preview
@Composable
private fun GloomListItemPreview() {
    GloomhavenMasterTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GloomListItem(
                modifier = Modifier.fillMaxWidth(),
                title = "Some title",
                rightComponent = {
                    RightItemText("Some text")
                }
            )

            GloomListItem(
                modifier = Modifier.fillMaxWidth(),
                title = "Some title",
                leftComponent = {
                    LeftItemIcon(painterResource(R.drawable.ic_plus))
                }
            )

            GloomListItem(
                modifier = Modifier.fillMaxWidth(),
                title = "Some title",
                leftComponent = {
                    LeftItemImage(painterResource(R.drawable.ic_team))
                }
            )

            GloomListFilledItem(
                modifier = Modifier.fillMaxWidth(),
                title = "Some title",
                rightComponent = {
                    RightItemChecker(true, {})
                }
            )
        }
    }
}