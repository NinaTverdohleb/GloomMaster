package com.rumpilstilstkin.gloomhavenhelper.benchmark

import com.rumpilstilstkin.gloomhavenhelper.benchmark.AppTags.ScenarioBlock.SCENARIO_CARD_PREFIX

/**
 * Mirror of the app's testTag literals, grouped by screen / component.
 *
 * The `:benchmark` module shares no code with `:app`, so these strings are
 * matched by value against the `testTag`s declared in the app's per-screen
 * `*TestTags` objects. Keep both sides in sync.
 *
 * Tags are exposed to UiAutomator as resource-id via `testTagsAsResourceId = true`
 * (set on the root Surface in MainActivity, and re-enabled inside `GloomBasicDialog`
 * because dialogs render in their own window) and located with `By.res(tag)`.
 */
object AppTags {
    const val BACK = "GloomToolbarBackButton"

    object TeamTabScreen{
        const val ROOT_COLUMN = "TeamTabScreenTestTagsRoot"
    }

    /** EmptyTeamScreen — first screen when no team exists. */
    object EmptyTeam {
        const val START_BUTTON = "EmptyTeamScreenStartButton"
    }

    /** AddTeamDialog — create-team dialog. */
    object AddTeamDialog {
        const val NAME_FIELD = "AddTeamDialogTeamNameField"
        const val ADD_BUTTON = "AddTeamDialogAddTeamButton"
    }

    /** ScenarioBlock — available scenarios list on the team tab. */
    object ScenarioBlock {
        private const val SCENARIO_CARD_PREFIX = "ScenarioBlockScenarioCard"

        fun scenarioCard(index: Int) = "$SCENARIO_CARD_PREFIX$index"
    }

    /** MenuScenarioDialog — opened by tapping a scenario card. */
    object MenuScenarioDialog {
        const val PLAY_SCENARIO_BUTTON = "MenuScenarioDialogPlayScenarioButton"
    }

    /** ScenarioScreen — active scenario play screen. */
    object ScenarioScreen {
        const val ADD_MONSTER_FAB = "ScenarioScreenAddMonsterFab"
        const val ROUND_BUTTON = "ScenarioScreenRoundButton"
        private const val MONSTER_CARD_PREFIX = "ScenarioScreenMonsterCard"
        fun card(index: Int) = "$MONSTER_CARD_PREFIX$index"
    }

    /** ScenarioHeader — magic charges row at the top of the play screen. */
    object ScenarioHeader {
        private const val MAGIC_PREFIX = "ScenarioHeaderMagic_"

        /** [magic] is the name of the domain `Magic` enum entry, e.g. "FIRE". */
        fun magic(magic: String) = "$MAGIC_PREFIX$magic"
    }

    /** AddScenarioMonstersDialog — pick monsters to add to the scenario. */
    object AddScenarioMonstersDialog {
        private const val MONSTER_PREFIX = "AddScenarioMonstersDialogMonsterItem"
        const val ADD_BUTTON = "AddScenarioMonstersDialogAddButton"

        fun monster(index: Int) = "$MONSTER_PREFIX$index"
    }

    /** AddMonsterUnitDialog — pick tier + unit number for a monster. */
    object AddMonsterUnitDialog {
        private const val UNIT_PREFIX = "AddMonsterUnitDialogUnit_"
        const val OK_BUTTON = "AddMonsterUnitDialogOkButton"

        fun unit(id: Int) = "$UNIT_PREFIX$id"
    }

    /** RegularMonsterUnit — a monster unit card on the play screen. */
    object MonsterUnit {
        private const val EFFECT_PREFIX = "RegularMonsterUnitEffect_"

        /** [effect] is the name of the `MonsterStatType` enum entry, e.g. "POISON". */
        fun effect(effect: String) = "$EFFECT_PREFIX$effect"
    }

    object MonsterCard{
        const val ADD_UNITS_BUTTON = "MonsterCardTestTagsAddUnitsButton"
        const val DELETE_BUTTON = "MonsterCardTestTagsDeleteButton"
    }

    object GloomCounter{
        const val TEST_TAG_PLUS = "PickerButtonPlus"
        const val TEST_TAG_MINUS = "PickerButtonMinus"
    }

    object RightComponentsTestTags{
        const val CHECKER = "RightComponentsTestTagsChecker"
    }
}
