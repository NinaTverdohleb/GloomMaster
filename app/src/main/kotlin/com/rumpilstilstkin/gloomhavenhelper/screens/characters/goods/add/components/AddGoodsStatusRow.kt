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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.GloomCard
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloomhavenhelper.screens.characters.goods.add.AddGoodsForCharacterScreenActions

@Composable
fun AddGoodsStatusRow(
    allGold: Int,
    goodsGold: Int,
    modifier: Modifier = Modifier,
) = Column(
    modifier = modifier,
    verticalArrangement = Arrangement.spacedBy(12.dp),
) {
    val costColor =
        if (allGold >= goodsGold) {
            MaterialTheme.colorScheme.onSurface
        } else {
            MaterialTheme.colorScheme.error
        }
    Text(
        text = stringResource(R.string.available_gold_format, allGold),
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onSurface,
    )
    Text(
        text = stringResource(R.string.cost_gold_format, goodsGold),
        style = MaterialTheme.typography.titleMedium,
        color = costColor,
    )
}

@Preview
@Composable
private fun AddGoodsStatusRowSample() {
    GloomhavenMasterTheme {
        AddGoodsStatusRow(
            allGold = 10,
            goodsGold = 20,
        )
    }
}
