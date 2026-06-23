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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.screens.dialogs.goods.GoodDetailsDialogCustomActions
import com.rumpilstilstkin.gloomhavenhelper.screens.models.GoodUi
import com.rumpilstilstkin.gloomhavenhelper.ui.goods.GoodItem
import com.rumpilstilstkin.gloomhavenhelper.ui.icons.GloomhavenIcons
import com.rumpilstilstkin.gloomhavenhelper.ui.icons.goods.Arm
import com.rumpilstilstkin.gloomhavenhelper.ui.icons.goods.Foot
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme

@Composable
fun CharacterItemsTabScreen(
    goods: List<GoodUi>,
    deleteGood: (Int) -> Unit,
    addGoods: () -> Unit,
    sellGood: (id: Int, cost: Int) -> Unit,
) {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(8.dp),
    ) {
        var selectedGood by remember { mutableStateOf<GoodUi?>(null) }

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 16.dp),
        ) {
            items(goods) { good ->
                GoodItem(
                    modifier = Modifier.animateItem(),
                    good = good,
                    active = true,
                    clickItem = { selectedGood = it },
                )
            }
        }

        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = addGoods,
            ) {
                Text(text = stringResource(R.string.add))
            }
        }

        selectedGood?.let { good ->
            GoodDetailsDialogCustomActions(
                dismiss = { selectedGood = null },
                imagePath = good.imagePath,
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    OutlinedButton(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.error),
                        colors =
                            ButtonDefaults.outlinedButtonColors(
                                containerColor = Color.Transparent,
                                contentColor = MaterialTheme.colorScheme.error,
                            ),
                        onClick = {
                            deleteGood(good.goodId)
                            selectedGood = null
                        },
                    ) {
                        Text(text = stringResource(R.string.delete))
                    }
                    OutlinedButton(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
                        colors =
                            ButtonDefaults.outlinedButtonColors(
                                containerColor = Color.Transparent,
                                contentColor = MaterialTheme.colorScheme.onSurface,
                            ),
                        onClick = {
                            sellGood(good.goodId, good.cost)
                            selectedGood = null
                        },
                    ) {
                        Text(text = stringResource(R.string.sell_button))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun CharacterItemsTabPreview() {
    GloomhavenMasterTheme {
        CharacterItemsTabScreen(
            goods =
                listOf(
                    GoodUi.fixture(
                        id = 1,
                    ),
                    GoodUi.fixture(
                        id = 1,
                    ),
                ),
            deleteGood = {},
            addGoods = {},
            sellGood = { _, _ -> },
        )
    }
}
