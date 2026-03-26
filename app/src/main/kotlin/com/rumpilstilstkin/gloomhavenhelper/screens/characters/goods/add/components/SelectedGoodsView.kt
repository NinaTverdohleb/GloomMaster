package com.rumpilstilstkin.gloomhavenhelper.screens.characters.goods.add.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.screens.characters.goods.add.AddGoodsForCharacterScreenActions
import com.rumpilstilstkin.gloomhavenhelper.screens.dialogs.goods.GoodDetailsDialog
import com.rumpilstilstkin.gloomhavenhelper.screens.models.GoodUi
import com.rumpilstilstkin.gloomhavenhelper.ui.goods.GoodItem
import com.rumpilstilstkin.gloomhavenhelper.ui.icons.GloomhavenIcons
import com.rumpilstilstkin.gloomhavenhelper.ui.icons.goods.Foot
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenMasterTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun SelectedGoodsView(
    selectedGoods: ImmutableList<GoodUi>,
    onAction: (AddGoodsForCharacterScreenActions) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedGood by remember { mutableStateOf<GoodUi?>(null) }
    Column(modifier = modifier) {
        Text(
            text = "Выбранные товары",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(
            modifier = Modifier.height(16.dp)
        )
        Card {
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                selectedGoods.forEachIndexed { index, good ->
                    GoodItem(
                        good = good,
                        clickItem = { selectedGood = it }
                    )
                    if (index != selectedGoods.lastIndex) {
                        HorizontalDivider()
                    }
                }
            }
        }
    }
    selectedGood?.let { good ->
        GoodDetailsDialog(
            dismiss = { selectedGood = null },
            confirm = {
                onAction(AddGoodsForCharacterScreenActions.UnselectGood(good))
                selectedGood = null
            },
            buttonText = "Убрать",
            imagePath = good.imagePath
        )
    }
}

@Preview
@Composable
private fun SelectedGoodsViewPreview() {
    GloomhavenMasterTheme {
        SelectedGoodsView(
            selectedGoods = persistentListOf(
                GoodUi(
                    id = 1,
                    number = 1,
                    name = "Сапоги большого шага поешь этих сладких французких булок",
                    typeImage = GloomhavenIcons.GoodTypes.Foot,
                    cost = 20,
                    image = ""
                ),
                GoodUi(
                    id = 2,
                    number = 1,
                    name = "Сапоги большого шага поешь этих сладких французких булок",
                    typeImage = GloomhavenIcons.GoodTypes.Foot,
                    cost = 20,
                    image = ""
                )
            ),
            onAction = {}
        )
    }

}