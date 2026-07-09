package com.rumpilstilstkin.gloommaster.testtags.screens.characters.dialogs.add

object AddCharacterDialogTestTags {
    private const val CLASS_CHIP_PREFIX = "AddCharacterDialogClassChip"
    const val NAME_FIELD = "AddCharacterDialogNameField"
    const val ADD_BUTTON = "AddCharacterDialogAddButton"

    fun type(index: Int) = "$CLASS_CHIP_PREFIX$index"
}
