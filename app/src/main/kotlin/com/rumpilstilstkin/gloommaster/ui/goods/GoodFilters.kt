package com.rumpilstilstkin.gloommaster.ui.goods

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloommaster.R
import com.rumpilstilstkin.gloommaster.designsystem.components.text.GloomOutlinedTextSearchField
import com.rumpilstilstkin.gloommaster.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloommaster.domain.entity.GoodType
import com.rumpilstilstkin.gloommaster.screens.models.toImage
import com.rumpilstilstkin.gloommaster.testtags.ui.goods.GoodFiltersTestTags

@Composable
fun GoodFilters(
    searchText: String,
    filterType: GoodType?,
    selectFilter: (GoodType) -> Unit,
    changeSearchText: (String) -> Unit,
    modifier: Modifier = Modifier,
    additionalContent: (@Composable () -> Unit)? = null,
) = Column(
    modifier = modifier,
    verticalArrangement = Arrangement.spacedBy(24.dp),
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        GoodType.entries.forEachIndexed { index, type ->
            FilterButton(
                modifier = Modifier.testTag(GoodFiltersTestTags.filter(index)),
                type = type,
                selectedFilter = filterType,
                onSelectedChanged = selectFilter,
            )
        }
    }
    additionalContent?.invoke()

    GloomOutlinedTextSearchField(
        modifier =
            Modifier
                .fillMaxWidth()
                .testTag(GoodFiltersTestTags.SEARCH_FIELD),
        value = searchText,
        onValueChange = changeSearchText,
        placeholder = stringResource(R.string.search_hint),
    )
}

@Composable
private fun FilterButton(
    type: GoodType,
    selectedFilter: GoodType?,
    modifier: Modifier = Modifier,
    onSelectedChanged: (GoodType) -> Unit,
) {
    val isChecked = selectedFilter == type
    Icon(
        modifier =
            modifier
                .size(40.dp)
                .clickable {
                    onSelectedChanged(type)
                },
        painter = type.toImage().painter(),
        contentDescription = null,
        tint = if (isChecked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant,
    )
}

@Preview
@Composable
private fun GoodFiltersPreview() {
    GloomhavenMasterTheme {
        GoodFilters(
            searchText = "",
            filterType = GoodType.Arm,
            selectFilter = {},
            changeSearchText = {},
        )
    }
}
