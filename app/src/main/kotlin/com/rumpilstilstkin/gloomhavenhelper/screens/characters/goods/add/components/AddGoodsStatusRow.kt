package com.rumpilstilstkin.gloomhavenhelper.screens.characters.goods.add.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.screens.characters.goods.add.AddGoodsScreenActions
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenHalperTheme

@Composable
fun AddGoodsStatusRow(
    allGold: Int,
    goodsGold: Int,
    onAction: (AddGoodsScreenActions) -> Unit,
) {
    Column{
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
            ) {
                Button(
                    enabled = goodsGold <= allGold,
                    modifier = Modifier,
                    onClick = {
                        onAction(AddGoodsScreenActions.BuySelectedGoods)
                    }
                ) {
                    Text("Купить")
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(
                    modifier = Modifier,
                    onClick = {
                        onAction(AddGoodsScreenActions.AddSelectedGoods)
                    }
                ) {
                    Text("Добавить")
                }
            }
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