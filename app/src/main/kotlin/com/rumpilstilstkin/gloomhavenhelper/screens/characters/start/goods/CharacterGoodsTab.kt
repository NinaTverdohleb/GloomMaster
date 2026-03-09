package com.rumpilstilstkin.gloomhavenhelper.screens.characters.start.goods

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.screens.dialogs.goods.GoodDetailsDialogCustomActions
import com.rumpilstilstkin.gloomhavenhelper.screens.models.GoodUi
import com.rumpilstilstkin.gloomhavenhelper.ui.goods.GoodItem
import com.rumpilstilstkin.gloomhavenhelper.ui.icons.GloomhavenIcons
import com.rumpilstilstkin.gloomhavenhelper.ui.icons.goods.Arm
import com.rumpilstilstkin.gloomhavenhelper.ui.icons.goods.Foot
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenHalperTheme

@Composable
fun CharacterItemsTabScreen(
    goods: List<GoodUi>,
    deleteGood: (Int) -> Unit,
    addGoods: () -> Unit,
    sellGood: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        var selectedGood by remember { mutableStateOf<GoodUi?>(null) }

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            items(goods) { good ->
                GoodItem(
                    modifier = Modifier.animateItem(),
                    good = good,
                    active = true,
                    clickItem = { selectedGood = it }
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = addGoods,
            ) {
                Text(text = "Добавить")
            }
        }

        selectedGood?.let { good ->
            GoodDetailsDialogCustomActions(
                dismiss = { selectedGood = null },
                imagePath = good.imagePath
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.error),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color.Transparent,
                            contentColor = MaterialTheme.colorScheme.error,
                        ),
                        onClick = {
                            deleteGood(good.id)
                            selectedGood = null
                        }
                    ) {
                        Text(text = "Удалить")
                    }
                    OutlinedButton(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color.Transparent,
                            contentColor = MaterialTheme.colorScheme.onSurface,
                        ),
                        onClick = {
                            sellGood(good.id)
                            selectedGood = null
                        }
                    ) {
                        Text(text = "Продать")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun CharacterItemsTabPreview() {
    GloomhavenHalperTheme {
        CharacterItemsTabScreen(
            goods = listOf(
                GoodUi(
                    id = 1,
                    number = 1,
                    name = "Сапоги большого шага поешь этих сладких французких булок",
                    typeImage = GloomhavenIcons.GoodTypes.Foot,
                    cost = 20,
                    image = ""
                ),
                GoodUi(
                    id = 1,
                    number = 2,
                    name = "Перчатка большого шага",
                    typeImage = GloomhavenIcons.GoodTypes.Arm,
                    cost = 30,
                    image = ""
                )
            ),
            deleteGood = {},
            addGoods = {},
            sellGood = {},
        )
    }
}