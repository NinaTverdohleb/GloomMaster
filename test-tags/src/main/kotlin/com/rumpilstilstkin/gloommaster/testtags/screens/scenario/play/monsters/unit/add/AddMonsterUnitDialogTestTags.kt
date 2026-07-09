package com.rumpilstilstkin.gloommaster.testtags.screens.scenario.play.monsters.unit.add

object AddMonsterUnitDialogTestTags {
    private const val TIER_PREFIX = "AddMonsterUnitDialogTier_"
    private const val UNIT_PREFIX = "AddMonsterUnitDialogUnit_"
    const val CONFIRM_BUTTON = "AddMonsterUnitDialogOkButton"

    fun tier(tier: String) = "$TIER_PREFIX$tier"

    fun unit(id: Int) = "$UNIT_PREFIX$id"
}
