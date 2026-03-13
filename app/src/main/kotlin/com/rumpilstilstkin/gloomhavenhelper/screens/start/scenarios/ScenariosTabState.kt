package com.rumpilstilstkin.gloomhavenhelper.screens.start.scenarios

import androidx.compose.runtime.Immutable
import com.rumpilstilstkin.gloomhavenhelper.screens.models.ShortScenarioUI
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
            sections: ImmutableMap<ScenarioSectionType, ScenariosSection> = persistentMapOf(
                ScenarioSectionType.ACCESS to ScenariosSection.fixture(isExpanded = true),
                ScenarioSectionType.FINISHED to ScenariosSection.fixture()
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

enum class ScenarioSectionType(val title: String, val isActive: Boolean) {
    ACCESS("Доступные сценарии", true),
    BLOCKED("Недоступные сценарии", false),
    FINISHED("Пройденные сценарии", false)
}

sealed interface ScenariosTabAction {
    data class StartScenario(val scenarioId: Int) : ScenariosTabAction
    data class CompleteScenario(val scenarioId: Int) : ScenariosTabAction
    data class ToggleSection(val sectionType: ScenarioSectionType) : ScenariosTabAction
    data object AddScenario : ScenariosTabAction
    data class DeleteScenario(val scenarioNumber: Int) : ScenariosTabAction
    data class RestoreScenario(val scenarioNumber: Int) : ScenariosTabAction
}
