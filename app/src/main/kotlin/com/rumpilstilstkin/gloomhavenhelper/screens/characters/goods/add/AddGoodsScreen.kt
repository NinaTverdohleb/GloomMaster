package com.rumpilstilstkin.gloomhavenhelper.screens.characters.goods.add

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.rumpilstilstkin.gloomhavenhelper.screens.characters.goods.add.components.AddGoodsStatusRow
import com.rumpilstilstkin.gloomhavenhelper.ui.goods.AddGoodsView
import com.rumpilstilstkin.gloomhavenhelper.ui.goods.AddGoodsViewState
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenHalperTheme

@Composable
fun AddGoodsForCharacterScreenRoute(
    characterId: Int,
    navController: NavHostController,
) {
    val viewModel =
        hiltViewModel<AddGoodsScreenViewModel, AddGoodsScreenViewModel.Factory> { factory ->
            factory.create(characterId)
        }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    if (uiState.isClose) {
        navController.popBackStack()
    }

    AddGoodsScreen(
        uiState = uiState,
        onAction = { viewModel.onAction(it) }
    )
}

@Composable
internal fun AddGoodsScreen(
    uiState: AddGoodsScreenUiState,
    onAction: (AddGoodsScreenActions) -> Unit,
) {
    AddGoodsView(
        state = uiState.goodsState,
        modifier = Modifier.fillMaxWidth(),
        selectFilter = { onAction(AddGoodsScreenActions.SelectFilter(it)) },
        changeSearchText = { onAction(AddGoodsScreenActions.SearchTextChange(it)) },
        selectGood = { onAction(AddGoodsScreenActions.SelectGood(it.id)) },
        unselectGood = { onAction(AddGoodsScreenActions.UnselectGood(it.id)) }
    ) {
        AddGoodsStatusRow(
            allGold = uiState.allGold,
            goodsGold = uiState.goodsGold,
            onAction = { onAction(it) }
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1A1C24)
@Composable
private fun AddGoodsScreenPreview() {
    GloomhavenHalperTheme {
        AddGoodsScreen(
            uiState = AddGoodsScreenUiState(
                goodsState = AddGoodsViewState.fixture()
            ),
            onAction = {}
        )
    }
}