package com.rumpilstilstkin.gloomhavenhelper.ui.goods

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.items.GloomListFilledItem
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.items.LeftItemImage
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloomhavenhelper.screens.models.GoodUi

@Composable
fun GoodItem(
    good: GoodUi,
    modifier: Modifier = Modifier,
    active: Boolean = false,
    clickItem: (GoodUi) -> Unit,
) = GloomListFilledItem(
    modifier = modifier,
    title = "#${good.number} ${good.name}",
    description = stringResource(R.string.gold_format, good.cost),
    active = active,
    onClick = {clickItem(good)},
    leftComponent = {
        LeftItemImage(
            icon = good.icon
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
        )
    }
}
