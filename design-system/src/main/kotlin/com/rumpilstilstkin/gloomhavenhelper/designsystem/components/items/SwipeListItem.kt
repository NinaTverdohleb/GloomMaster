package com.rumpilstilstkin.gloomhavenhelper.designsystem.components.items

import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.AppIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.GoodIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme
import kotlin.math.roundToInt

enum class DragAnchors {
    Close,
    Open
}

object ActionWidth {
    @Stable
    val OneIconAction: Dp = 64.dp
    val TwoIconAction: Dp = 144.dp
}

@Composable
fun GloomSwipeableListItem(
    menuWidthDp: Dp = ActionWidth.OneIconAction,
    menuContent: @Composable () -> Unit,
    item: @Composable () -> Unit
) {
    val density = LocalDensity.current
    val menuWidthPx = with(density) { menuWidthDp.toPx() }

    val state = remember(menuWidthPx) {
        AnchoredDraggableState(
            initialValue = DragAnchors.Close,
            anchors = DraggableAnchors {
                DragAnchors.Close at 0f
                DragAnchors.Open at -menuWidthPx
            }
        )
    }

    LaunchedEffect(menuWidthPx) {
        state.updateAnchors(
            DraggableAnchors {
                DragAnchors.Close at 0f
                DragAnchors.Open at -menuWidthPx
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .height(IntrinsicSize.Max)
    ) {
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .width(menuWidthDp)
                .padding(start = 12.dp)
                .align(Alignment.CenterEnd),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            menuContent()
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .offset {
                    val xOffset = state.offset.takeIf { !it.isNaN() } ?: 0f
                    IntOffset(x = xOffset.roundToInt(), y = 0)
                }
                .anchoredDraggable(state, Orientation.Horizontal)
        ) {
            item()
        }
    }
}


@Preview
@Composable
private fun GloomSwipeListListItemPreviewGloom() {
    GloomhavenMasterTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GloomSwipeableListItem(
                menuWidthDp = ActionWidth.TwoIconAction,
                menuContent = {
                    GloomItemActionIcon(
                        modifier = Modifier.fillMaxHeight(),
                        icon = AppIcon.Delete,
                        isError = true,
                        onClick = {}
                    )
                    GloomItemActionIcon(
                        modifier = Modifier.fillMaxHeight(),
                        icon = AppIcon.Buy,
                        isError = false,
                        onClick = {}
                    )
                },
                item = {
                    GloomListFilledItem(
                        modifier = Modifier.fillMaxWidth(),
                        title = "Some title",
                        description = "desctiption",
                        leftComponent = {
                            LeftItemImage(GoodIcon.SmallThing)
                        }
                    )
                }
            )
        }
    }
}