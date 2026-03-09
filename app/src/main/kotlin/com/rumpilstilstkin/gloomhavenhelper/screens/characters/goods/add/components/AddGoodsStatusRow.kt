package com.rumpilstilstkin.gloomhavenhelper.screens.characters.goods.add.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.screens.characters.goods.add.AddGoodsForCharacterScreenActions
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomVariantCard
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenHalperTheme

@Composable
fun AddGoodsStatusRow(
    allGold: Int,
    goodsGold: Int,
    onAction: (AddGoodsForCharacterScreenActions) -> Unit,
) = GloomVariantCard {
    Text(
        modifier = Modifier.align(Alignment.CenterHorizontally),
        text = "Доступно : $allGold G",
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Стоимость : $goodsGold G",
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
            }
        ) {
            Text("Купить")
        }
        Spacer(modifier = Modifier.width(16.dp))
        Button(
            modifier = Modifier.weight(1f),
            onClick = {
                onAction(AddGoodsForCharacterScreenActions.AddSelectedGoods)
            }
        ) {
            Text("Добавить")
        }
    }
}

@Preview
@Composable
private fun AddGoodsStatusRowSample() {
    GloomhavenHalperTheme {
        AddGoodsStatusRow(
            allGold = 100,
            goodsGold = 20,
            onAction = {},
        )
    }
}