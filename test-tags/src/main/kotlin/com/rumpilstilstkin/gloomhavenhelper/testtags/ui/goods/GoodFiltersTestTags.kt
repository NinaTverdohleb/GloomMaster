package com.rumpilstilstkin.gloomhavenhelper.testtags.ui.goods

object GoodFiltersTestTags {
    private const val FILTER_PREFIX = "GoodFilter_"
    const val SEARCH_FIELD = "GoodFiltersSearchField"

    fun filter(index: Int) = "$FILTER_PREFIX$index"
}
