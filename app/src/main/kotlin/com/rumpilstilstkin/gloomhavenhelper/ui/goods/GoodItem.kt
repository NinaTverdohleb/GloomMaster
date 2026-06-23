package com.rumpilstilstkin.gloomhavenhelper.ui.goods

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.items.ActionWidth
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.items.GloomItemActionIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.items.GloomListFilledItem
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.items.GloomSwipeableListItem
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.items.LeftItemImage
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.AppIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.GoodIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloomhavenhelper.screens.models.GoodUi

@Composable
fun GoodItem(
    good: GoodUi,
    modifier: Modifier = Modifier,
    menuWidthDp: Dp = ActionWidth.OneIconAction,
    active: Boolean = false,
    clickItem: (GoodUi) -> Unit,
    menuContent: @Composable () -> Unit,
) = GloomSwipeableListItem(
    menuWidthDp = menuWidthDp,
    menuContent = menuContent,
    item = {
        GloomListFilledItem(
            modifier = modifier,
            title = "#${good.number} ${good.name}",
            description = stringResource(R.string.gold_format, good.cost),
            active = active,
            onClick = { clickItem(good) },
            leftComponent = {
                LeftItemImage(
                    icon = good.icon
                )
            }
        )
    }
)

@Preview
@Composable
private fun GoodItemPreview() {
    GloomhavenMasterTheme {
        GoodItem(
            good =
                GoodUi.fixture(
                    id = 1,
                ),
            clickItem = {},
        ) {

        }
    }
}
