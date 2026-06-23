package com.rumpilstilstkin.gloomhavenhelper.ui.goods

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.text.GloomHeader
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.GoodType
import com.rumpilstilstkin.gloomhavenhelper.screens.dialogs.goods.GoodDetailsDialog
import com.rumpilstilstkin.gloomhavenhelper.screens.models.GoodUi

@Composable
fun AddGoodsView(
    state: AddGoodsViewState,
    modifier: Modifier = Modifier,
    selectFilter: (GoodType) -> Unit,
    changeSearchText: (String) -> Unit,
    unselectGood: (GoodUi) -> Unit,
    selectGood: (GoodUi) -> Unit,
    bottomContent: @Composable () -> Unit = {},
) {
    var selectedGood by remember { mutableStateOf<GoodUi?>(null) }

    Column(
        modifier =
            modifier
                .fillMaxSize()
                .padding(8.dp),
    ) {
        GoodFilters(
            searchText = state.searchText,
            filterType = state.selectedFilter,
            selectFilter = selectFilter,
            changeSearchText = changeSearchText,
        )
        Spacer(
            modifier = Modifier.height(24.dp),
        )
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            if (state.selectedGoods.isNotEmpty()) {
                item {
                    GloomHeader(text = stringResource(R.string.selected_goods),)
                    Spacer(modifier = Modifier.height(4.dp),)
                }
                items(state.selectedGoods) { good ->
                    GoodItem(
                        modifier = Modifier.animateItem(),
                        good = good,
                        active = true,
                        clickItem = { selectedGood = it },
                    )
                }
            }
            item {
                GloomHeader(text = stringResource(R.string.available_goods),)
                Spacer(modifier = Modifier.height(4.dp),)
            }

            items(state.availableGoods) { good ->
                GoodItem(
                    modifier = Modifier.animateItem(),
                    good = good,
                    clickItem = { selectedGood = it },
                )
            }
        }
        bottomContent()
    }

    selectedGood?.let { good ->
        if (state.selectedGoods.contains(good)) {
            GoodDetailsDialog(
                dismiss = { selectedGood = null },
                confirm = {
                    unselectGood(good)
                    selectedGood = null
                },
                buttonText = stringResource(R.string.remove),
                isActionPositive = false,
                imagePath = good.imagePath,
            )
        } else {
            GoodDetailsDialog(
                dismiss = { selectedGood = null },
                confirm = {
                    selectGood(good)
                    selectedGood = null
                },
                buttonText = stringResource(R.string.add),
                imagePath = good.imagePath,
            )
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
        )
    }
}
