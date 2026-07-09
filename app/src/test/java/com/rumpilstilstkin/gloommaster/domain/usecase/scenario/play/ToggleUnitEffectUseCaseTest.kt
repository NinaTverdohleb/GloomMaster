package com.rumpilstilstkin.gloommaster.domain.usecase.scenario.play

import com.rumpilstilstkin.gloommaster.domain.entity.monster.MonsterStatType
import com.rumpilstilstkin.gloommaster.domain.entity.scenario.MagicChargeState
import com.rumpilstilstkin.gloommaster.domain.entity.scenario.MonsterDeckState
import com.rumpilstilstkin.gloommaster.domain.entity.scenario.MonsterItem
import com.rumpilstilstkin.gloommaster.domain.entity.scenario.MonsterUnit
import com.rumpilstilstkin.gloommaster.domain.entity.scenario.ScenarioBattleState
import com.rumpilstilstkin.gloommaster.domain.usecase.scenario.play.ToggleUnitEffectUseCase
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isFalse
import strikt.assertions.isNull
import strikt.assertions.isTrue

class ToggleUnitEffectUseCaseTest {
    private val sut = ToggleUnitEffectUseCase()
    private val monsterSlug = "oozy"
    private val unitNumber = 1

    @Test
    fun `given effect not present when invoked then it is added`() {
        // Given
        val state = stateWithUnit()

        // When
        val result = sut(state, monsterSlug, unitNumber, MonsterStatType.POISON)

        // Then
        expectThat(
            result
                .activeMonsters[monsterSlug]
                ?.units[unitNumber]
                ?.effects[MonsterStatType.POISON]
        ).isTrue()
    }

    @Test
    fun `given effect already present when invoked then it is removed`() {
        // Given
        val state = stateWithUnit(effects = setOf(MonsterStatType.POISON))

        // When
        val result = sut(state, monsterSlug, unitNumber, MonsterStatType.POISON)

        // Then
        expectThat(
            result
                .activeMonsters[monsterSlug]
                ?.units[unitNumber]
                ?.effects[MonsterStatType.POISON]
        ).isFalse()
    }

    @Test
    fun `given unit not found when invoked then state unchanged`() {
        // Given
        val state = stateWithUnit()

        // When
        val result = sut(state, "missing", unitNumber, MonsterStatType.POISON)

        // Then
        expectThat(
            result
                .activeMonsters["missing"]
                ?.units[unitNumber]
                ?.effects[MonsterStatType.POISON]
        ).isNull()
    }

    private fun stateWithUnit(
        effects: Set<MonsterStatType> = emptySet()
    ) =
        ScenarioBattleState(
            generalLevel = 1,
            name = "",
            monsters = emptyMap(),
            golds = 0,
            exp = 0,
            trapDamage = 0,
            gamersCount = 2,
            monsterLevel = 1,
            deck = MonsterDeckState.create(emptyList()),
            magicState = MagicChargeState.initial(),
            availableEffects = emptySet(),
            activeMonsters =
                mapOf(
                    monsterSlug to
                            MonsterItem.fixture(slug = monsterSlug).copy(
                                units = mapOf(
                                    unitNumber to MonsterUnit.fixture(
                                        number = unitNumber,
                                        effects = (MonsterStatType.mainEffectsPack + MonsterStatType.fcEffectsPack)
                                            .associateWith { effects.contains(it) }
                                    )
                                ),
                            ),
                ),
        )
}
