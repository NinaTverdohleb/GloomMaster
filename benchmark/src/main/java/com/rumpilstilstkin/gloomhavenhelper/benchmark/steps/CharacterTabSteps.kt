package com.rumpilstilstkin.gloomhavenhelper.benchmark.steps

import com.rumpilstilstkin.gloomhavenhelper.testtags.screens.characters.dialogs.add.AddCharacterDialogTestTags
import com.rumpilstilstkin.gloomhavenhelper.testtags.screens.characters.dialogs.menu.MenuCharacterDialogTestTags
import com.rumpilstilstkin.gloomhavenhelper.testtags.screens.start.characters.CharactersTabTestTags

import androidx.benchmark.macro.MacrobenchmarkScope
import com.rumpilstilstkin.gloomhavenhelper.benchmark.TestConsts
import com.rumpilstilstkin.gloomhavenhelper.benchmark.clickTag
import com.rumpilstilstkin.gloomhavenhelper.benchmark.existTag
import com.rumpilstilstkin.gloomhavenhelper.benchmark.pressBackAndIdle
import com.rumpilstilstkin.gloomhavenhelper.benchmark.waitForTag

fun MacrobenchmarkScope.exerciseCharactersTab(repeat: Int) {
    // Toggle a couple of available class chips back and forth.
    repeat(repeat) { clickTag(CharactersTabTestTags.type(1)) }

    // Flip the "only active" switch a few times.
    repeat(repeat) { clickTag(CharactersTabTestTags.ALIVE_SWITCH) }

    repeat(2) {
        addCharacter()
    }
}

fun MacrobenchmarkScope.openCharacterDetails() {
    if (!existTag(CharactersTabTestTags.character(0))) {
        addCharacter(true)
    }
    clickTag(CharactersTabTestTags.character(0))
    clickTag(MenuCharacterDialogTestTags.DETAILS_BUTTON)
}

private fun MacrobenchmarkScope.addCharacter(needCreate: Boolean = false) {
    clickTag(CharactersTabTestTags.ADD_FAB)
    val name = waitForTag(AddCharacterDialogTestTags.NAME_FIELD)
    name.click()
    name.text = TestConsts.TEAM_NAME
    device.pressBack()
    if (needCreate) {
        clickTag(AddCharacterDialogTestTags.ADD_BUTTON)
    } else {
        pressBackAndIdle(AddCharacterDialogTestTags.ADD_BUTTON)
    }
}