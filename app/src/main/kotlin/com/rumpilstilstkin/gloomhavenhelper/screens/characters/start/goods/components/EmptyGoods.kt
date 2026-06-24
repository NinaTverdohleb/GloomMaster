package com.rumpilstilstkin.gloomhavenhelper.screens.characters.start.goods.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.empty.EmptyView
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.EmptyIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme

@Composable
fun EmptyGoods(modifier: Modifier = Modifier) =
    EmptyView(
        modifier = modifier,
        icon = EmptyIcon.Goods,
        title = stringResource(R.string.empty_character_goods),
        description = stringResource(R.string.empty_character_goods_description),
    )

@Preview
@Composable
private fun EmptyGoodsPreview() {
    GloomhavenMasterTheme {
        EmptyGoods()
    }
}
