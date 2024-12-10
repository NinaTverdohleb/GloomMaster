package com.rumpilstilstkin.gloomhavenhelper.screens.characters.goods.add

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.GoodType
import com.rumpilstilstkin.gloomhavenhelper.screens.characters.goods.add.components.AddGoodsStatusRow
import com.rumpilstilstkin.gloomhavenhelper.screens.characters.goods.add.components.GoodFilters
import com.rumpilstilstkin.gloomhavenhelper.screens.characters.goods.add.components.GoodItem
import com.rumpilstilstkin.gloomhavenhelper.screens.characters.goods.add.components.SelectedGoodsView
import com.rumpilstilstkin.gloomhavenhelper.screens.models.GoodUi
import com.rumpilstilstkin.gloomhavenhelper.ui.icons.GloomhavenIcons
import com.rumpilstilstkin.gloomhavenhelper.ui.icons.goods.Foot
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenHalperTheme
import kotlinx.collections.immutable.persistentListOf

@Composable
fun AddGoodsScreen(
    characterId: Int,
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val viewModel =
        hiltViewModel<AddGoodsScreenViewModel, AddGoodsScreenViewModel.Factory> { factory ->
            factory.create(characterId)
        }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    if (uiState.isClose) {
        navController.popBackStack()
    }

    AddGoodsView(
        state = uiState,
        modifier = modifier,
        onAction = viewModel::onAction,
    )
}

@Composable
private fun AddGoodsView(
    state: AddGoodsScreenUiState,
    modifier: Modifier = Modifier,
    onAction: (AddGoodsScreenActions) -> Unit,
) {
    val scrollState = rememberLazyListState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        GoodFilters(
            searchText = state.searchText,
            filterType = state.selectedFilter,
            onAction = onAction
        )
        Spacer(
            modifier = Modifier.height(16.dp)
        )
        LazyColumn(
            modifier = Modifier.weight(1f),
            state = scrollState
        ) {
            if (state.selectedGoods.isNotEmpty()) {
                item {
                    SelectedGoodsView(
                        modifier = Modifier.animateItem(),
                        selectedGoods = state.selectedGoods,
                        onAction = onAction
                    )
                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )
                }
            }
            item {
                Text(
                    text = "Доступные товары",
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(
                    modifier = Modifier.height(16.dp)
                )
            }
            itemsIndexed(state.availableGoods) { index, good ->
                GoodItem(
                    modifier = Modifier
                        .padding(8.dp)
                        .animateItem(),
                    good = good,
                    onActionClick = { onAction(AddGoodsScreenActions.SelectGood(good.id)) }
                )
                if (index != state.availableGoods.lastIndex) {
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 16.dp),
                    )
                }
            }
        }
        AddGoodsStatusRow(
            allGold = state.allGold,
            goodsGold = state.goodsGold,
            onAction = onAction
        )
    }
}

@Preview
@Composable
private fun SampleItem() {
    GloomhavenHalperTheme {
        Box(
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        ) {
            AddGoodsView(
                state = AddGoodsScreenUiState(
                    searchText = "",
                    availableGoods = persistentListOf(
                        GoodUi(
                            id = 1,
                            number = 1,
                            name = "Сапоги большого шага поешь этих сладких французких булок",
                            typeImage = GloomhavenIcons.GoodTypes.Foot,
                            cost = 20,
                        ),
                        GoodUi(
                            id = 2,
                            number = 1,
                            name = "Сапоги большого шага поешь этих сладких французких булок",
                            typeImage = GloomhavenIcons.GoodTypes.Foot,
                            cost = 20,
                        )
                    ),
                    selectedGoods = persistentListOf(
                        GoodUi(
                            id = 1,
                            number = 1,
                            name = "Сапоги большого шага поешь этих сладких французких булок",
                            typeImage = GloomhavenIcons.GoodTypes.Foot,
                            cost = 20,
                        ),
                        GoodUi(
                            id = 2,
                            number = 1,
                            name = "Сапоги большого шага поешь этих сладких французких булок",
                            typeImage = GloomhavenIcons.GoodTypes.Foot,
                            cost = 20,
                        )
                    ),
                    selectedFilter = GoodType.Arm,
                    allGold = 10,
                    goodsGold = 11
                ),
                onAction = {},
            )
        }
    }
}