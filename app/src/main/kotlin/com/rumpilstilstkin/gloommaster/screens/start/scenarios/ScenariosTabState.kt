package com.rumpilstilstkin.gloommaster.screens.start.scenarios

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import com.rumpilstilstkin.gloommaster.R
import com.rumpilstilstkin.gloommaster.screens.models.ShortScenarioUI
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf

@Immutable
data class ScenariosTabStateUi(
    val sections: ImmutableMap<ScenarioSectionType, ScenariosSection> = persistentMapOf(),
) {
    companion object {
        fun fixture(
            sections: ImmutableMap<ScenarioSectionType, ScenariosSection> =
                persistentMapOf(
                    ScenarioSectionType.ACCESS to ScenariosSection.fixture(isExpanded = true),
                    ScenarioSectionType.FINISHED to ScenariosSection.fixture(),
                ),
        ) = ScenariosTabStateUi(
            sections = sections,
        )
    }
}

@Immutable
data class ScenariosSection(
    val scenarios: ImmutableList<ShortScenarioUI>,
    val isExpanded: Boolean,
) {
    companion object {
        fun fixture(
            scenarios: ImmutableList<ShortScenarioUI> = persistentListOf(ShortScenarioUI.fixture()),
            isExpanded: Boolean = false,
        ) = ScenariosSection(
            scenarios = scenarios,
            isExpanded = isExpanded,
        )
    }
}

enum class ScenarioSectionType(
    @get:StringRes val titleRes: Int,
    val isActive: Boolean,
) {
    ACCESS(R.string.available_scenarios_tab, true),
    BLOCKED(R.string.blocked_scenarios_tab, false),
    FINISHED(R.string.finished_scenarios_tab, false),
}

sealed interface ScenariosTabAction {
    data class ToggleSection(
        val sectionType: ScenarioSectionType,
    ) : ScenariosTabAction

    data object AddScenario : ScenariosTabAction

    data class SelectScenario(
        val scenario: ShortScenarioUI,
    ) : ScenariosTabAction
}
