package com.rumpilstilstkin.gloommaster.screens.goods.add

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.rumpilstilstkin.gloommaster.R
import com.rumpilstilstkin.gloommaster.designsystem.components.buttons.FabContextMenuItem
import com.rumpilstilstkin.gloommaster.designsystem.components.buttons.GloomFabWithContextMenu
import com.rumpilstilstkin.gloommaster.designsystem.components.toolbar.GloomToolbar
import com.rumpilstilstkin.gloommaster.designsystem.icons.AppIcon
import com.rumpilstilstkin.gloommaster.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloommaster.domain.entity.GoodType
import com.rumpilstilstkin.gloommaster.screens.goods.add.components.AddGoodsStatusRow
import com.rumpilstilstkin.gloommaster.screens.models.GoodUi
import com.rumpilstilstkin.gloommaster.testtags.screens.goods.character.AddGoodsForCharacterScreenTestTags
import com.rumpilstilstkin.gloommaster.ui.goods.AddGoodsView
import com.rumpilstilstkin.gloommaster.ui.goods.AddGoodsViewState

@Composable
internal fun AddGoodsScreen(
    uiState: AddGoodsForCharacterScreenUiState,
    selectFilter: (GoodType) -> Unit,
    changeSearchText: (String) -> Unit,
    selectGood: (GoodUi) -> Unit,
    unselectGood: (GoodUi) -> Unit,
    openGood: (GoodUi) -> Unit,
    addGoods: () -> Unit,
    buyGoods: () -> Unit,
    back: () -> Unit,
) = Scaffold(
    topBar = {
        GloomToolbar(
            title = stringResource(R.string.add_goods_to_shop_title),
            back = back,
        )
    },
    floatingActionButtonPosition = FabPosition.End,
    floatingActionButton = {
        var expanded by remember { mutableStateOf(false) }
        if (uiState.goodsState.selectedGoods.isNotEmpty()) {
            GloomFabWithContextMenu(
                expanded = expanded,
                icon = AppIcon.Check,
                onClick = { expanded = !expanded },
                modifier = Modifier.testTag(AddGoodsForCharacterScreenTestTags.CONFIRM_FAB),
            ) {
                if (uiState.allGold >= uiState.goodsGold) {
                    FabContextMenuItem(
                        icon = AppIcon.Buy,
                        text = stringResource(R.string.buy),
                        isError = false,
                        modifier = Modifier.testTag(AddGoodsForCharacterScreenTestTags.BUY_ACTION),
                    ) { buyGoods() }
                }

                FabContextMenuItem(
                    icon = AppIcon.Get,
                    text = stringResource(R.string.get),
                    isError = false,
                    modifier = Modifier.testTag(AddGoodsForCharacterScreenTestTags.GET_ACTION),
                ) { addGoods() }
            }
        }
    },
) { paddingValues ->
    AddGoodsView(
        state = uiState.goodsState,
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(paddingValues),
        selectFilter = selectFilter,
        changeSearchText = changeSearchText,
        selectGood = selectGood,
        unselectGood = unselectGood,
        clickGood = openGood,
    ) {
        AddGoodsStatusRow(
            allGold = uiState.allGold,
            goodsGold = uiState.goodsGold,
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
            selectFilter = {},
            changeSearchText = {},
            selectGood = {},
            unselectGood = {},
            addGoods = {},
            back = {},
            openGood = {},
            buyGoods = {},
        )
    }
}
