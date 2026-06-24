package com.rumpilstilstkin.gloomhavenhelper.ui.goods

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.items.GloomItemActionIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.text.GloomHeader
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.AppIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.GoodType
import com.rumpilstilstkin.gloomhavenhelper.screens.models.GoodUi

@Composable
fun AddGoodsView(
    state: AddGoodsViewState,
    modifier: Modifier = Modifier,
    selectFilter: (GoodType) -> Unit,
    changeSearchText: (String) -> Unit,
    unselectGood: (GoodUi) -> Unit,
    selectGood: (GoodUi) -> Unit,
    clickGood: (GoodUi) -> Unit,
    bottomContent: @Composable () -> Unit = {},
) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .padding(16.dp),
    ) {
        GoodFilters(
            searchText = state.searchText,
            filterType = state.selectedFilter,
            selectFilter = selectFilter,
            changeSearchText = changeSearchText,
        )
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(vertical = 24.dp),
        ) {
            if (state.selectedGoods.isNotEmpty()) {
                item(key = "SelectedHeader") {
                    GloomHeader(text = stringResource(R.string.selected_goods))
                    Spacer(modifier = Modifier.height(4.dp))
                }
                items(
                    items = state.selectedGoods,
                    key = { item -> item.goodId }
                ) { good ->
                    GoodItem(
                        modifier = Modifier.animateItem(),
                        good = good,
                        active = true,
                        clickItem = { clickGood(good) },
                    ) {
                        GloomItemActionIcon(
                            modifier = Modifier.fillMaxHeight(),
                            icon = AppIcon.Delete,
                            isError = true,
                            onClick = { unselectGood(good) }
                        )
                    }
                }
            }
            item(
                key = "UnselectHeader"
            ) {
                GloomHeader(text = stringResource(R.string.available_goods))
                Spacer(modifier = Modifier.height(4.dp))
            }

            items(
                items = state.availableGoods,
                key = { item -> item.goodId }
            ) { good ->
                GoodItem(
                    modifier = Modifier.animateItem(),
                    good = good,
                    clickItem = { clickGood(good) },
                ) {
                    GloomItemActionIcon(
                        modifier = Modifier.fillMaxHeight(),
                        icon = AppIcon.Plus,
                        isError = false,
                        onClick = { selectGood(good) }
                    )
                }
            }
        }
        bottomContent()
    }
}

@Preview
@Composable
private fun GoodFiltersPreview() {
    GloomhavenMasterTheme {
        AddGoodsView(
            modifier = Modifier.fillMaxSize(),
            state = AddGoodsViewState.fixture(),
            selectFilter = {},
            changeSearchText = {},
            selectGood = {},
            unselectGood = {},
            clickGood = {}
        )
    }
}
