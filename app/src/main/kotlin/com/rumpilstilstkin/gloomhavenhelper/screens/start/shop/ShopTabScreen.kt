package com.rumpilstilstkin.gloomhavenhelper.screens.start.shop

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.buttons.GloomFab
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.AppIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.GoodType
import com.rumpilstilstkin.gloomhavenhelper.screens.dialogs.goods.GoodDetailsDialog
import com.rumpilstilstkin.gloomhavenhelper.screens.models.GoodUi
import com.rumpilstilstkin.gloomhavenhelper.ui.goods.GoodFilters
import com.rumpilstilstkin.gloomhavenhelper.ui.goods.GoodItem
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun ShopTabScreen(
    state: ShopTabStateUi,
    addItems: () -> Unit,
    deleteItem: (goodId: Int) -> Unit,
    selectFilter: (type: GoodType) -> Unit,
    enterSearchText: (text: String) -> Unit,
) = Scaffold(
    floatingActionButtonPosition = FabPosition.End,
    floatingActionButton = {
        if (state.canAdd) {
            GloomFab(
                icon = AppIcon.Plus,
                onClick = addItems,
            )
        }
    }
) { innerPadding ->
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
    ) {
        GoodFilters(
            searchText = state.searchText,
            filterType = state.selectedFilter,
            selectFilter = selectFilter,
            changeSearchText = enterSearchText,
        )
        var selectedGood by remember { mutableStateOf<GoodUi?>(null) }
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(vertical = 24.dp),
        ) {
            items(
                items = state.avaliableGoods,
                key = { it.goodId },
            ) { good ->
                GoodItem(
                    modifier = Modifier.animateItem(),
                    good = good,
                    clickItem = { selectedGood = it },
                )
            }
        }
        selectedGood?.let { good ->
            GoodDetailsDialog(
                dismiss = { selectedGood = null },
                confirm = {
                    deleteItem(good.number)
                    selectedGood = null
                },
                isActionPositive = false,
                buttonText = stringResource(R.string.delete),
                imagePath = good.imagePath,
            )
        }
    }
}


@Preview(showBackground = true, backgroundColor = 0xFF1A1C24)
@Composable
private fun ShopTabScreenPreview() {
    GloomhavenMasterTheme {
        ShopTabScreen(
            state =
                ShopTabStateUi(
                    avaliableGoods =
                        persistentListOf(
                            GoodUi.fixture(
                                id = 1,
                            ),
                            GoodUi.fixture(
                                id = 2,
                            ),
                        ),
                ),
            deleteItem = {},
            selectFilter = {},
            enterSearchText = {},
            addItems = {},
        )
    }
}
