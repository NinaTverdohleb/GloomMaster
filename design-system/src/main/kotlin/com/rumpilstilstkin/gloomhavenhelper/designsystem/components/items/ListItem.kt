package com.rumpilstilstkin.gloomhavenhelper.designsystem.components.items

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.GloomCard
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.text.image.TextWithImagesByCode
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.AppIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.GoodIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme

@Composable
fun TextImageFilledItem(
    text: String,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    rightComponent: (@Composable RowScope.() -> Unit)? = null,
    leftComponent: (@Composable RowScope.() -> Unit)? = null,
) = GloomCard(
    modifier = modifier,
) {
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 12.dp),
    ) {
        TextImageItem(
            modifier = Modifier.fillMaxWidth(),
            text = text,
            leftComponent = leftComponent,
            rightComponent = rightComponent,
            onClick = onClick,
        )
    }
}

@Composable
fun TextImageOutlineItem(
    text: String,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    rightComponent: (@Composable RowScope.() -> Unit)? = null,
    leftComponent: (@Composable RowScope.() -> Unit)? = null,
) = Box(
    modifier =
        modifier
            .border(
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.outline,
                width = 1.dp,
            ).fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 12.dp),
) {
    TextImageItem(
        modifier = Modifier.fillMaxWidth(),
        text = text,
        leftComponent = leftComponent,
        rightComponent = rightComponent,
        onClick = onClick,
    )
}

@Composable
fun TextImageItem(
    text: String,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    rightComponent: (@Composable RowScope.() -> Unit)? = null,
    leftComponent: (@Composable RowScope.() -> Unit)? = null,
) = GloomBaseListItem(
    modifier = modifier,
    middleComponent = {
        TextWithImagesByCode(
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.titleMedium,
            text = text,
        )
    },
    leftComponent = leftComponent,
    rightComponent = rightComponent,
    onClick = onClick,
)

@Composable
fun GloomListFilledItem(
    title: String,
    modifier: Modifier = Modifier,
    description: String? = null,
    onClick: (() -> Unit)? = null,
    active: Boolean = false,
    rightComponent: (@Composable RowScope.() -> Unit)? = null,
    leftComponent: (@Composable RowScope.() -> Unit)? = null,
) {
    GloomCard(
        modifier = modifier,
        active = active,
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 12.dp),
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
fun GloomListOutlineItem(
    title: String,
    modifier: Modifier = Modifier,
    description: String? = null,
    onClick: (() -> Unit)? = null,
    rightComponent: (@Composable RowScope.() -> Unit)? = null,
    leftComponent: (@Composable RowScope.() -> Unit)? = null,
) {
    Box(
        modifier =
            modifier
                .border(
                    shape = RoundedCornerShape(16.dp),
                    color = MaterialTheme.colorScheme.outline,
                    width = 1.dp,
                ).fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 12.dp),
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

@Composable
fun GloomListItem(
    title: String,
    modifier: Modifier = Modifier,
    description: String? = null,
    onClick: (() -> Unit)? = null,
    rightComponent: (@Composable RowScope.() -> Unit)? = null,
    leftComponent: (@Composable RowScope.() -> Unit)? = null,
) = GloomBaseListItem(
    modifier = modifier,
    middleComponent = {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
            description?.let {
                Spacer(Modifier.height(4.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    },
    leftComponent = leftComponent,
    rightComponent = rightComponent,
    onClick = onClick,
)

@Composable
fun GloomBaseListItem(
    middleComponent: (@Composable BoxScope.() -> Unit),
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    rightComponent: (@Composable RowScope.() -> Unit)? = null,
    leftComponent: (@Composable RowScope.() -> Unit)? = null,
) = Row(
    modifier =
        modifier
            .fillMaxWidth()
            .heightIn(min = 64.dp)
            .then(
                if (onClick != null) {
                    Modifier.clickable(onClick = onClick)
                } else {
                    Modifier
                },
            ).padding(horizontal = 4.dp),
    verticalAlignment = Alignment.CenterVertically,
) {
    leftComponent?.let {
        leftComponent()
        Spacer(Modifier.width(16.dp))
    }
    Box(modifier = Modifier.weight(1f)) {
        middleComponent()
    }
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
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            GloomListItem(
                modifier = Modifier.fillMaxWidth(),
                title = "Some title",
                rightComponent = {
                    RightItemText("Some text")
                },
            )

            GloomListItem(
                modifier = Modifier.fillMaxWidth(),
                title = "Some title",
                leftComponent = {
                    LeftItemIcon(AppIcon.Plus)
                },
            )

            GloomListItem(
                modifier = Modifier.fillMaxWidth(),
                title = "Some title",
                leftComponent = {
                    LeftItemImage(AppIcon.Team)
                },
            )

            GloomListFilledItem(
                modifier = Modifier.fillMaxWidth(),
                title = "Some title",
                rightComponent = {
                    RightItemChecker(checked = true, onCheckedChange = {})
                },
            )

            GloomListFilledItem(
                modifier = Modifier.fillMaxWidth(),
                title = "Some title",
                description = "desctiption",
                leftComponent = {
                    LeftItemImage(GoodIcon.SmallThing)
                },
            )

            GloomListFilledItem(
                modifier = Modifier.fillMaxWidth(),
                title = "Achivment",
                rightComponent = {
                    CounterRightItem(
                        value = 5,
                        intRange = IntRange(0, 15),
                        onValueChange = {},
                    )
                },
            )

            GloomListOutlineItem(
                modifier = Modifier.fillMaxWidth(),
                title = "Achivment",
            )

            GloomListOutlineItem(
                modifier = Modifier.fillMaxWidth(),
                title = "Achivmentdsfsadfasdfasdfasdfasdfsdasdfasdfasdfsadfsa",
                rightComponent = {
                    RightItemChecker(checked = true, onCheckedChange ={})
                },
            )

            TextImageFilledItem(
                text = "Replace one card #01 with one card #03",
            )
        }
    }
}
