package com.rumpilstilstkin.gloomhavenhelper.testtags.components

object CounterTestTags {
    const val TEST_TAG_PLUS = "PickerButtonPlus"
    const val TEST_TAG_MINUS = "PickerButtonMinus"
}

object RightComponentsTestTags {
    const val CHECKER = "RightComponentsTestTagsChecker"
}

object ToolbarTestTags {
    const val BACK = "GloomToolbarBackButton"
}

object NavigationBarTestTags {
    private const val TAB_BOTTOM_PREFIX = "NavTabBottom_"
    private const val TAB_TOP_PREFIX = "NavTabTop_"

    fun tabBottom(index: Int) = "$TAB_BOTTOM_PREFIX$index"
    fun tabTop(index: Int) = "$TAB_TOP_PREFIX$index"
}
