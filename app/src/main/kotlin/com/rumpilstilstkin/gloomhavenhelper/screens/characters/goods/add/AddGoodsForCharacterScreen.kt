package com.rumpilstilstkin.gloomhavenhelper.screens.characters.goods.add

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.rumpilstilstkin.gloomhavenhelper.screens.characters.goods.add.components.AddGoodsStatusRow
import com.rumpilstilstkin.gloomhavenhelper.ui.components.toolbar.GloomToolbar
import com.rumpilstilstkin.gloomhavenhelper.ui.goods.AddGoodsView
import com.rumpilstilstkin.gloomhavenhelper.ui.goods.AddGoodsViewState
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenMasterTheme

@Composable
internal fun AddGoodsScreen(
    uiState: AddGoodsForCharacterScreenUiState,
    onAction: (AddGoodsForCharacterScreenActions) -> Unit,
) = Scaffold(
    topBar = {
        GloomToolbar(
            title = "",
            back = { onAction(AddGoodsForCharacterScreenActions.Close) },
        )
    },
) { paddingValues ->
    AddGoodsView(
        state = uiState.goodsState,
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(paddingValues),
        selectFilter = { onAction(AddGoodsForCharacterScreenActions.SelectFilter(it)) },
        changeSearchText = { onAction(AddGoodsForCharacterScreenActions.SearchTextChange(it)) },
        selectGood = { onAction(AddGoodsForCharacterScreenActions.SelectGood(it)) },
        unselectGood = { onAction(AddGoodsForCharacterScreenActions.UnselectGood(it)) },
    ) {
        AddGoodsStatusRow(
            allGold = uiState.allGold,
            goodsGold = uiState.goodsGold,
            onAction = { onAction(it) },
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1A1C24)
@Composable
private fun AddGoodsScreenPreview() {
    GloomhavenMasterTheme {
        AddGoodsScreen(
            uiState =
                AddGoodsForCharacterScreenUiState(
                    goodsState = AddGoodsViewState.fixture(),
                ),
            onAction = {},
        )
    }
}
