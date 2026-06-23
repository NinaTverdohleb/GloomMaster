package com.rumpilstilstkin.gloomhavenhelper.screens.characters.goods.add.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.GloomCard
import com.rumpilstilstkin.gloomhavenhelper.screens.characters.goods.add.AddGoodsForCharacterScreenActions
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme

@Composable
fun AddGoodsStatusRow(
    allGold: Int,
    goodsGold: Int,
    onAction: (AddGoodsForCharacterScreenActions) -> Unit,
) = GloomCard {
    Text(
        modifier = Modifier.align(Alignment.CenterHorizontally),
        text = stringResource(R.string.available_gold_format, allGold),
    )
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(R.string.cost_gold_format, goodsGold),
        )
    }
    Spacer(modifier = Modifier.height(16.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Button(
            enabled = goodsGold <= allGold,
            modifier = Modifier.weight(1f),
            onClick = {
                onAction(AddGoodsForCharacterScreenActions.BuySelectedGoods)
            },
        ) {
            Text(stringResource(R.string.buy))
        }
        Spacer(modifier = Modifier.width(16.dp))
        Button(
            modifier = Modifier.weight(1f),
            onClick = {
                onAction(AddGoodsForCharacterScreenActions.AddSelectedGoods)
            },
        ) {
            Text(stringResource(R.string.add))
        }
    }
}

@Preview
@Composable
private fun AddGoodsStatusRowSample() {
    GloomhavenMasterTheme {
        AddGoodsStatusRow(
            allGold = 100,
            goodsGold = 20,
            onAction = {},
        )
    }
}
