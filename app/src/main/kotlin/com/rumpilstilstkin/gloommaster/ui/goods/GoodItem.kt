package com.rumpilstilstkin.gloommaster.ui.goods

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.rumpilstilstkin.gloommaster.R
import com.rumpilstilstkin.gloommaster.designsystem.components.items.ActionWidth
import com.rumpilstilstkin.gloommaster.designsystem.components.items.GloomListFilledItem
import com.rumpilstilstkin.gloommaster.designsystem.components.items.GloomSwipeableListItem
import com.rumpilstilstkin.gloommaster.designsystem.components.items.LeftItemImage
import com.rumpilstilstkin.gloommaster.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloommaster.screens.models.GoodUi

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
            title = stringResource(R.string.good_title_format, good.number, good.name),
            description = stringResource(R.string.gold_format, good.cost),
            active = active,
            onClick = { clickItem(good) },
            leftComponent = {
                LeftItemImage(
                    icon = good.icon,
                )
            },
        )
    },
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
