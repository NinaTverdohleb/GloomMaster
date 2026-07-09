package com.rumpilstilstkin.gloommaster.ui.goods

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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloommaster.R
import com.rumpilstilstkin.gloommaster.designsystem.components.items.GloomItemActionIcon
import com.rumpilstilstkin.gloommaster.designsystem.components.text.GloomHeader
import com.rumpilstilstkin.gloommaster.designsystem.icons.AppIcon
import com.rumpilstilstkin.gloommaster.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloommaster.domain.entity.GoodType
import com.rumpilstilstkin.gloommaster.screens.models.GoodUi
import com.rumpilstilstkin.gloommaster.testtags.ui.goods.AddGoodsViewTestTags

@Composable
fun AddGoodsView(
    state: AddGoodsViewState,
    modifier: Modifier = Modifier,
    selectFilter: (GoodType) -> Unit,
    changeSearchText: (String) -> Unit,
    unselectGood: (GoodUi) -> Unit,
    selectGood: (GoodUi) -> Unit,
    clickGood: (GoodUi) -> Unit,
    additionalContent: (@Composable () -> Unit)? = null,
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
            additionalContent = additionalContent,
        )
        LazyColumn(
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
                    key = { item -> item.goodId },
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
                            onClick = { unselectGood(good) },
                        )
                    }
                }
            }
            item(
                key = "UnselectHeader",
            ) {
                GloomHeader(text = stringResource(R.string.available_goods))
                Spacer(modifier = Modifier.height(4.dp))
            }

            itemsIndexed(
                items = state.availableGoods,
                key = { _, item -> item.goodId },
            ) { index, good ->
                GoodItem(
                    modifier =
                        Modifier
                            .animateItem()
                            .testTag(AddGoodsViewTestTags.availableGood(index)),
                    good = good,
                    clickItem = { clickGood(good) },
                ) {
                    GloomItemActionIcon(
                        modifier =
                            Modifier
                                .fillMaxHeight()
                                .testTag(AddGoodsViewTestTags.ADD_GOOD_ACTION),
                        icon = AppIcon.Plus,
                        isError = false,
                        onClick = { selectGood(good) },
                    )
                }
            }
        }
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
            clickGood = {},
        )
    }
}
