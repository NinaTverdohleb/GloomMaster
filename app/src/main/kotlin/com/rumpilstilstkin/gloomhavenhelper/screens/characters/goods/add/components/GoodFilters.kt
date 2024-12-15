package com.rumpilstilstkin.gloomhavenhelper.screens.characters.goods.add.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.GoodType
import com.rumpilstilstkin.gloomhavenhelper.screens.characters.goods.add.AddGoodsScreenActions
import com.rumpilstilstkin.gloomhavenhelper.screens.models.toImage
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenHalperTheme

@Composable
fun GoodFilters(
    searchText: String,
    filterType: GoodType?,
    onAction: (AddGoodsScreenActions) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val selectedFilter: (GoodType) -> Unit = {
                onAction(AddGoodsScreenActions.SelectFilter(it))
            }
            FilterButton(
                type = GoodType.Head,
                selectedFilter = filterType,
                onSelectedChanged = selectedFilter
            )
            FilterButton(
                type = GoodType.Foot,
                selectedFilter = filterType,
                onSelectedChanged = selectedFilter
            )
            FilterButton(
                type = GoodType.Body,
                selectedFilter = filterType,
                onSelectedChanged = selectedFilter
            )
            FilterButton(
                type = GoodType.Arm,
                selectedFilter = filterType,
                onSelectedChanged = selectedFilter
            )
            FilterButton(
                type = GoodType.DoubleArm,
                selectedFilter = filterType,
                onSelectedChanged = selectedFilter
            )
            FilterButton(
                type = GoodType.SmallThing,
                selectedFilter = filterType,
                onSelectedChanged = selectedFilter
            )
        }
        Spacer(
            modifier =Modifier.height(4.dp)
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            value = searchText,
            onValueChange = { onAction(AddGoodsScreenActions.SearchTextChange(it)) },
            label = { Text("Название или номер") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                focusedLabelColor = MaterialTheme.colorScheme.primary,
                unfocusedLabelColor = MaterialTheme.colorScheme.onSecondary,
                cursorColor = MaterialTheme.colorScheme.primary,
            )
        )
    }
}

@Composable
fun FilterButton(
    type: GoodType,
    selectedFilter: GoodType?,
    modifier: Modifier = Modifier,
    onSelectedChanged: (GoodType) -> Unit
) {
    val isChecked = selectedFilter == type
    Icon(
        modifier = modifier
            .size(40.dp)
            .clickable {
                onSelectedChanged(type)
            },
        imageVector = type.toImage(),
        contentDescription = null,
        tint = if (isChecked) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary
    )

}

@Preview
@Composable
private fun GoodFiltersSample() {
    GloomhavenHalperTheme {
        GoodFilters(
            searchText = "",
            filterType = GoodType.Arm,
            onAction = {}
        )
    }
}