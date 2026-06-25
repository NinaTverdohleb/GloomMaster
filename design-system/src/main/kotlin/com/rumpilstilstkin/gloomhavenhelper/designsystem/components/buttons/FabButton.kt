package com.rumpilstilstkin.gloomhavenhelper.designsystem.components.buttons

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ripple
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.AppIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.GloomIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme

@Composable
fun GloomFab(
    icon: GloomIcon,
    onClick: () -> Unit
) {
    GloomFabBase(
        onClick = onClick,
    ) {
        Icon(
            modifier = Modifier.size(32.dp),
            painter = icon.painter(),
            contentDescription = null
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun GloomFabWithContextMenu(
    icon: GloomIcon,
    actions: @Composable ColumnScope.() -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    val cornerRadius by animateDpAsState(
        targetValue = if (expanded) 12.dp else 16.dp,
        label = "FabShapeCornerAnimation"
    )

    val fabSize by animateDpAsState(
        targetValue = if (expanded) 40.dp else 56.dp,
        label = "FabSize"
    )

    Column(
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        AnimatedVisibility(
            visible = expanded,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                actions()
            }
        }

        GloomFabBase(
            onClick = { expanded = !expanded },
            fabSize = fabSize,
            fabRadius = cornerRadius,
        ) {
            AnimatedContent(
                targetState = expanded,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "IconTransition"
            ) { isExpanded ->
                if (isExpanded) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = AppIcon.Close.painter(),
                        contentDescription = null
                    )
                } else {
                    Icon(
                        modifier = Modifier.size(32.dp),
                        painter = icon.painter(),
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@Composable
private fun GloomFabBase(
    modifier: Modifier = Modifier,
    fabRadius: Dp = 16.dp,
    fabSize: Dp = 56.dp,
    onClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    val shape = RoundedCornerShape(fabRadius)

    Surface(
        modifier = modifier.size(fabSize),
        shape = shape,
        color = MaterialTheme.colorScheme.surfaceBright,
        contentColor = MaterialTheme.colorScheme.onSurface,
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outline
        ),
        shadowElevation = 6.dp
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = ripple(),
                    onClick = onClick
                )
        ) {
            content()
        }
    }
}

@Preview
@Composable
private fun GloomFabPreviewMenu() {
    GloomhavenMasterTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GloomFab(
                icon = AppIcon.Plus,
                onClick = {}
            )

            GloomFabWithContextMenu(
                icon = AppIcon.Plus,
            ){
                FabContextMenuItem(
                    icon = AppIcon.Buy,
                    text = "Buy",
                    isError = false,
                ){}

                FabContextMenuItem(
                    icon = AppIcon.Buy,
                    text = "Buy",
                    isError = true,
                ){}
            }
        }
    }
}