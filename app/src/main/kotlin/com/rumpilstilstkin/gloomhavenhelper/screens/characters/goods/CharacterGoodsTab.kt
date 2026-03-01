package com.rumpilstilstkin.gloomhavenhelper.screens.characters.goods

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.rumpilstilstkin.gloomhavenhelper.navigation.GlHelperScreens
import com.rumpilstilstkin.gloomhavenhelper.screens.dialogs.goods.GoodDetailsDialog
import com.rumpilstilstkin.gloomhavenhelper.screens.models.GoodUi
import com.rumpilstilstkin.gloomhavenhelper.ui.icons.GloomhavenIcons
import com.rumpilstilstkin.gloomhavenhelper.ui.icons.goods.Arm
import com.rumpilstilstkin.gloomhavenhelper.ui.icons.goods.Foot
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenHalperTheme

@Composable
fun CharacterItemsTab(
    characterId: Int,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val viewModel =
        hiltViewModel<CharacterItemsTabViewModel, CharacterItemsTabViewModel.Factory> { factory ->
            factory.create(characterId)
        }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CharacterGoods(
        goods = uiState,
        modifier = modifier,
        onGoodDelete = {
            viewModel.onAction(CharacterItemsTabActions.DeleteGood(it))
        },
        addGoods = {
            navController.navigate(GlHelperScreens.AddGoodsForCharacter(characterId))
        },
        onSell = {
            viewModel.onAction(CharacterItemsTabActions.SellGood(it))
        }
    )
}

@Composable
fun CharacterGoods(
    goods: List<GoodUi>,
    modifier: Modifier = Modifier,
    onGoodDelete: (Int) -> Unit,
    addGoods: () -> Unit,
    onSell: (Int) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { addGoods() },
                content = {
                    Icon(Icons.Filled.Add, "Добавить предмет")
                }
            )
        },
        content = { innerPadding ->
            LazyColumn(
                modifier = modifier.padding(innerPadding)
            ) {
                items(goods) { good ->
                    GoodItem(
                        good = good,
                        onDelete = onGoodDelete,
                        onSell = onSell
                    )
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 12.dp),
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            }
        }
    )
}

@Composable
private fun GoodItem(
    good: GoodUi,
    modifier: Modifier = Modifier,
    onDelete: (Int) -> Unit,
    onSell: (Int) -> Unit
) {
    var showDetailsDialog by remember { mutableStateOf(false) }
    GoodDetailsDialog(
        goodNumber = good.number,
        showDialog = showDetailsDialog,
        isAction = true,
        buttonText = "Продать",
        imagePath = good.imagePath,
        onDismiss = { showDetailsDialog = false },
        onAction = { good.characterGoodId?.let { onSell(it) } }
    )
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .clickable { showDetailsDialog = true },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(34.dp),
                imageVector = good.typeImage,
                contentDescription = null,
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(text = "#" + good.number)
            Spacer(modifier = Modifier.width(8.dp))

            Text(
                modifier = Modifier.width(200.dp),
                text = good.name
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "" + good.cost + "G")
            IconButton(
                onClick = {
                    good.characterGoodId?.let { onDelete(it) }
                }
            ) {
                Icon(Icons.Filled.Delete, "Удалить предмет", tint = MaterialTheme.colorScheme.error)
            }
        }

    }
}

@Preview
@Composable
private fun CharacterItemsTabExample() {
    GloomhavenHalperTheme {
        CharacterGoods(
            goods = listOf(
                GoodUi(
                    id = 1,
                    number = 1,
                    name = "Сапоги большого шага поешь этих сладких французких булок",
                    typeImage = GloomhavenIcons.GoodTypes.Foot,
                    cost = 20,
                    image = ""
                ),
                GoodUi(
                    id = 1,
                    number = 2,
                    name = "Перчатка большого шага",
                    typeImage = GloomhavenIcons.GoodTypes.Arm,
                    cost = 30,
                    image = ""
                )
            ),
            onGoodDelete = {},
            addGoods = {},
            onSell = {},
        )
    }
}