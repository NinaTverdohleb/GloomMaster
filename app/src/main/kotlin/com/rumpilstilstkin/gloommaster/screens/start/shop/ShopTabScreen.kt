package com.rumpilstilstkin.gloommaster.screens.start.shop

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloommaster.designsystem.components.buttons.GloomFab
import com.rumpilstilstkin.gloommaster.designsystem.components.items.GloomItemActionIcon
import com.rumpilstilstkin.gloommaster.designsystem.icons.AppIcon
import com.rumpilstilstkin.gloommaster.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloommaster.domain.entity.GoodType
import com.rumpilstilstkin.gloommaster.screens.models.GoodUi
import com.rumpilstilstkin.gloommaster.testtags.screens.start.shop.ShopTabTestTags
import com.rumpilstilstkin.gloommaster.ui.goods.GoodFilters
import com.rumpilstilstkin.gloommaster.ui.goods.GoodItem
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun ShopTabScreen(
    state: ShopTabStateUi,
    addItems: () -> Unit,
    deleteItem: (goodId: Int) -> Unit,
    selectFilter: (type: GoodType) -> Unit,
    enterSearchText: (text: String) -> Unit,
    selectItem: (GoodUi) -> Unit,
) = Scaffold(
    floatingActionButtonPosition = FabPosition.End,
    floatingActionButton = {
        if (state.canAdd) {
            GloomFab(
                icon = AppIcon.Plus,
                onClick = addItems,
                modifier = Modifier.testTag(ShopTabTestTags.ADD_FAB),
            )
        }
    },
) { innerPadding ->
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(innerPadding),
    ) {
        GoodFilters(
            searchText = state.searchText,
            filterType = state.selectedFilter,
            selectFilter = selectFilter,
            changeSearchText = enterSearchText,
        )
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
                    clickItem = { selectItem(good) },
                ) {
                    GloomItemActionIcon(
                        modifier = Modifier.fillMaxHeight(),
                        icon = AppIcon.Delete,
                        isError = true,
                        onClick = { deleteItem(good.goodId) },
                    )
                }
            }
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
            selectItem = {},
        )
    }
}
