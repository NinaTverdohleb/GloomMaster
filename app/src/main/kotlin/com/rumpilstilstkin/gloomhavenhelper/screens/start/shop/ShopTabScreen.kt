package com.rumpilstilstkin.gloomhavenhelper.screens.start.shop

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.GoodType
import com.rumpilstilstkin.gloomhavenhelper.screens.dialogs.goods.GoodDetailsDialog
import com.rumpilstilstkin.gloomhavenhelper.screens.models.GoodUi
import com.rumpilstilstkin.gloomhavenhelper.ui.goods.GoodFilters
import com.rumpilstilstkin.gloomhavenhelper.ui.goods.GoodItem
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenMasterTheme
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun ShopTabScreen(
    state: ShopTabStateUi,
    addItems: () -> Unit,
    deleteItem: (goodId: Int) -> Unit,
    selectFilter: (type: GoodType) -> Unit,
    enterSearchText: (text: String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        GoodFilters(
            searchText = state.searchText,
            filterType = state.selectedFilter,
            selectFilter = selectFilter,
            changeSearchText = enterSearchText
        )
        var selectedGood by remember { mutableStateOf<GoodUi?>(null) }
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            items(state.avaliableGoods) { good ->
                GoodItem(
                    modifier = Modifier.animateItem(),
                    good = good,
                    clickItem = { selectedGood = it }
                )
            }
        }
        selectedGood?.let { good ->
            GoodDetailsDialog(
                dismiss = { selectedGood = null },
                confirm = {
                    deleteItem(good.id)
                    selectedGood = null
                },
                isActionPositive = false,
                buttonText = "Удалить",
                imagePath = good.imagePath
            )
        }
        if (state.canAdd) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                onClick = addItems,
                contentPadding = PaddingValues(16.dp)
            ) {
                Text(
                    text = "Добавить предмет",
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1A1C24)
@Composable
private fun ShopTabScreenPreview() {
    GloomhavenMasterTheme {
        ShopTabScreen(
            state = ShopTabStateUi(
                avaliableGoods = persistentListOf(
                    GoodUi.fixture(
                        id = 1,
                        number = 1,
                    ),
                    GoodUi.fixture(
                        id = 2,
                        number = 1,
                    )
                )
            ),
            deleteItem = {},
            selectFilter = {},
            enterSearchText = {},
            addItems = {}
        )
    }
}
