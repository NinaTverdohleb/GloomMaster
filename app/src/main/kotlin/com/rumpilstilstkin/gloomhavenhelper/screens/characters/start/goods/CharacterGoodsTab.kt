package com.rumpilstilstkin.gloomhavenhelper.screens.characters.start.goods

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.buttons.GloomFab
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.items.ActionWidth
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.items.GloomItemActionIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.AppIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloomhavenhelper.screens.characters.start.goods.components.EmptyGoods
import com.rumpilstilstkin.gloomhavenhelper.screens.models.GoodUi
import com.rumpilstilstkin.gloomhavenhelper.testtags.screens.characters.start.goods.CharacterItemsTabTestTags
import com.rumpilstilstkin.gloomhavenhelper.ui.goods.GoodItem
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun CharacterItemsTabScreen(
    goods: ImmutableList<GoodUi>,
    deleteGood: (GoodUi) -> Unit,
    addGoods: () -> Unit,
    goodDetails: (GoodUi) -> Unit,
    sellGood: (GoodUi) -> Unit,
) = Scaffold(
    floatingActionButtonPosition = FabPosition.End,
    floatingActionButton = {
        GloomFab(
            icon = AppIcon.Plus,
            onClick = addGoods,
            modifier = Modifier.testTag(CharacterItemsTabTestTags.ADD_FAB),
        )
    },
) { innerPadding ->
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
    ) {
        if (goods.isEmpty()) {
            EmptyGoods(
                modifier = Modifier.weight(1f),
            )
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                items(
                    items = goods,
                    key = { good -> good.goodId },
                ) { good ->
                    GoodItem(
                        menuWidthDp = ActionWidth.TwoIconAction,
                        modifier =
                            Modifier
                                .animateItem()
                                .testTag(CharacterItemsTabTestTags.GOOD_ITEM),
                        good = good,
                        clickItem = { goodDetails(good) },
                    ) {
                        GloomItemActionIcon(
                            modifier =
                                Modifier
                                    .fillMaxHeight()
                                    .testTag(CharacterItemsTabTestTags.SELL_ACTION),
                            icon = AppIcon.Buy,
                            isError = false,
                            onClick = { sellGood(good) },
                        )

                        GloomItemActionIcon(
                            modifier =
                                Modifier
                                    .fillMaxHeight()
                                    .testTag(CharacterItemsTabTestTags.DELETE_ACTION),
                            icon = AppIcon.Delete,
                            isError = true,
                            onClick = { deleteGood(good) },
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun CharacterItemsTabPreview() {
    GloomhavenMasterTheme {
        CharacterItemsTabScreen(
            goods =
                persistentListOf(
                    GoodUi.fixture(
                        id = 1,
                    ),
                    GoodUi.fixture(
                        id = 1,
                    ),
                ),
            deleteGood = {},
            addGoods = {},
            sellGood = { _ -> },
            goodDetails = {},
        )
    }
}

@Preview
@Composable
private fun CharacterItemsTabEmptyPreview() {
    GloomhavenMasterTheme {
        CharacterItemsTabScreen(
            goods = persistentListOf(),
            deleteGood = {},
            addGoods = {},
            sellGood = { _ -> },
            goodDetails = {},
        )
    }
}
