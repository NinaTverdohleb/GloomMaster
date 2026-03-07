package com.rumpilstilstkin.gloomhavenhelper.screens.teem.goods

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.GoodType
import com.rumpilstilstkin.gloomhavenhelper.screens.models.GoodUi
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomToolbarTitle
import com.rumpilstilstkin.gloomhavenhelper.ui.goods.AddGoodsView
import com.rumpilstilstkin.gloomhavenhelper.ui.goods.AddGoodsViewState
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenHalperTheme

@Composable
internal fun AddGoodsForTeamScreen(
    uiState: AddGoodsForTeamUiState,
    selectFilter: (GoodType) -> Unit,
    changeSearchText: (String) -> Unit,
    selectGood: (GoodUi) -> Unit,
    unselectGood: (GoodUi) -> Unit,
    addGoods: () -> Unit,
    back: () -> Unit,
) = Scaffold(
    topBar = {
        GloomToolbarTitle(
            title = "",
            back = back,
        )
    },
) { paddingValues ->
    AddGoodsView(
        state = uiState.goodsState,
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValues),
        selectFilter = selectFilter,
        changeSearchText = changeSearchText,
        selectGood = selectGood,
        unselectGood = unselectGood,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Button(
                modifier = Modifier.weight(1f),
                enabled = uiState.goodsState.selectedGoods.isNotEmpty(),
                onClick = addGoods,
            ) {
                Text(text = "Добавить")
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1A1C24)
@Composable
private fun AddGoodsForTeamScreenPreview() {
    GloomhavenHalperTheme {
        AddGoodsForTeamScreen(
            uiState = AddGoodsForTeamUiState(
                goodsState = AddGoodsViewState.fixture(),
            ),
            selectFilter = {},
            changeSearchText = {},
            selectGood = {},
            unselectGood = {},
            addGoods = {},
            back = {}
        )
    }
}
