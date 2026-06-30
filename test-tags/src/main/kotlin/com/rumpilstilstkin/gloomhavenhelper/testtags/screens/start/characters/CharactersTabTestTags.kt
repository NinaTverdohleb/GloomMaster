package com.rumpilstilstkin.gloomhavenhelper.testtags.screens.start.characters

object CharactersTabTestTags {
    private const val CLASS_CHIP_PREFIX = "CharacterAvailableClassChip"
    private const val CHARACTER_ITEM_PREFIX = "CharactersTabCharacterItem"
    const val ADD_FAB = "CharactersTabAddFab"
    const val ALIVE_SWITCH = "CharactersTabAliveSwitch"

    fun type(index: Int) = "$CLASS_CHIP_PREFIX$index"

    fun character(index: Int) = "$CHARACTER_ITEM_PREFIX$index"
}
