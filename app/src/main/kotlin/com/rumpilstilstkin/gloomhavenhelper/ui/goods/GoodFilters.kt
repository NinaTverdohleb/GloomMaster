package com.rumpilstilstkin.gloomhavenhelper.ui.goods

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
    selectFilter: (GoodType) -> Unit,
    changeSearchText: (String) -> Unit,
    modifier: Modifier = Modifier
) = Column(
    modifier = modifier
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        GoodType.entries.forEach {
            FilterButton(
                type = it,
                selectedFilter = filterType,
                onSelectedChanged = selectFilter
            )
        }
    }
    Spacer(
        modifier = Modifier.height(4.dp)
    )

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        value = searchText,
        onValueChange = changeSearchText,
        label = { Text("Название или номер") },
    )
}

@Composable
private fun FilterButton(
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
        tint = if (isChecked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant
    )

}

@Preview
@Composable
private fun GoodFiltersPreview() {
    GloomhavenHalperTheme {
        GoodFilters(
            searchText = "",
            filterType = GoodType.Arm,
            selectFilter = {},
            changeSearchText = {},
        )
    }
}