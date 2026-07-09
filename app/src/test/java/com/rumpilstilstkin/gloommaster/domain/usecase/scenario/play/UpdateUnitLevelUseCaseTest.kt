package com.rumpilstilstkin.gloommaster.domain.usecase.scenario.play

import com.rumpilstilstkin.gloommaster.domain.entity.monster.MonsterStats
import com.rumpilstilstkin.gloommaster.domain.entity.scenario.MagicChargeState
import com.rumpilstilstkin.gloommaster.domain.entity.scenario.MonsterDeckState
import com.rumpilstilstkin.gloommaster.domain.entity.scenario.MonsterItem
import com.rumpilstilstkin.gloommaster.domain.entity.scenario.MonsterUnit
import com.rumpilstilstkin.gloommaster.domain.entity.scenario.ScenarioBattleState
import com.rumpilstilstkin.gloommaster.domain.usecase.scenario.GetMonsterStatsForLevelUseCase
import com.rumpilstilstkin.gloommaster.domain.usecase.scenario.play.UpdateUnitLevelUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

@OptIn(ExperimentalCoroutinesApi::class)
class UpdateUnitLevelUseCaseTest {
    private val getMonsterStatsForLevel: GetMonsterStatsForLevelUseCase = mockk()
    private val sut = UpdateUnitLevelUseCase(getMonsterStatsForLevel)
    private val monsterSlug = "oozy"
    private val unitNumber = 1

    @Test
    fun `given existing unit at full life when invoked then maxLife and currentLife become new max`() =
        runTest(UnconfinedTestDispatcher()) {
            // Given
            coEvery {
                getMonsterStatsForLevel(monsterSlug = monsterSlug, level = 3, isElite = false)
            } returns
                    MonsterStats(
                        monsterSlug = monsterSlug,
                        level = 3,
                        isElite = false,
                        life = 12,
                        stats = emptyList(),
                    )
            val state =
                stateWithUnit(maxLife = 10, currentLife = 10)

            // When
            val result =
                sut(state, slug = monsterSlug, number = unitNumber, level = 3, isElite = false)

            // Then
            val unit = result.activeMonsters[monsterSlug]?.units[unitNumber]
            expectThat(unit?.maxLife).isEqualTo(12)
            expectThat(unit?.currentLife).isEqualTo(12)
            expectThat(unit?.level).isEqualTo(3)
        }

    @Test
    fun `given existing unit with damage when invoked then damage carries over`() =
        runTest(UnconfinedTestDispatcher()) {
            // Given — current 6 of max 10 = 4 damage; new max 12 → new current 8
            coEvery {
                getMonsterStatsForLevel(monsterSlug = monsterSlug, level = 3, isElite = false)
            } returns
                    MonsterStats(
                        monsterSlug = monsterSlug,
                        level = 3,
                        isElite = false,
                        life = 12,
                        stats = emptyList(),
                    )
            val state =
                stateWithUnit(maxLife = 10, currentLife = 6)

            // When
            val result =
                sut(state, slug = monsterSlug, number = unitNumber, level = 3, isElite = false)

            // Then
            val unit = result.activeMonsters[monsterSlug]?.units[unitNumber]
            expectThat(unit?.maxLife).isEqualTo(12)
            expectThat(unit?.currentLife).isEqualTo(8)
        }

    @Test
    fun `given lifeMultiple unit when invoked then new maxLife is multiplied by gamersCount`() =
        runTest(UnconfinedTestDispatcher()) {
            // Given — gamersCount = 3, stats.life = 5 → new max 15
            coEvery {
                getMonsterStatsForLevel(monsterSlug = monsterSlug, level = 4, isElite = false)
            } returns
                    MonsterStats(
                        monsterSlug = monsterSlug,
                        level = 4,
                        isElite = false,
                        life = 5,
                        stats = emptyList(),
                    )
            val state =
                stateWithUnit(maxLife = 6, currentLife = 6, lifeMultiple = true, gamersCount = 3)

            // When
            val result =
                sut(state, slug = monsterSlug, number = unitNumber, level = 4, isElite = false)

            // Then
            expectThat(result.activeMonsters[monsterSlug]?.units[unitNumber]?.maxLife).isEqualTo(15)
            expectThat(result.activeMonsters[monsterSlug]?.units[unitNumber]?.currentLife).isEqualTo(
                15
            )
        }

    @Test
    fun `given currentLife above old maxLife when invoked then damage is floored at zero`() =
        runTest(UnconfinedTestDispatcher()) {
            // Given — currentLife (15) > maxLife (10): damage = max(10-15, 0) = 0
            //         new current = new max - 0 = new max
            coEvery {
                getMonsterStatsForLevel(monsterSlug = monsterSlug, level = 5, isElite = false)
            } returns
                    MonsterStats(
                        monsterSlug = monsterSlug,
                        level = 5,
                        isElite = false,
                        life = 20,
                        stats = emptyList(),
                    )
            val state =
                stateWithUnit(maxLife = 10, currentLife = 15, lifeMultiple = false)

            // When
            val result =
                sut(state, slug = monsterSlug, number = unitNumber, level = 5, isElite = false)

            // Then
            expectThat(
                result
                    .activeMonsters[monsterSlug]
                    ?.units[unitNumber]
                    ?.currentLife
            )
                .isEqualTo(20)
        }

    private fun stateWithUnit(
        maxLife: Int,
        currentLife: Int = 5,
        lifeMultiple: Boolean = false,
        gamersCount: Int = 2,
    ) =
        ScenarioBattleState(
            generalLevel = 1,
            name = "",
            monsters = emptyMap(),
            golds = 0,
            exp = 0,
            trapDamage = 0,
            gamersCount = gamersCount,
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
                                        currentLife = currentLife,
                                        number = unitNumber,
                                        maxLife = maxLife,
                                        isElite = false,
                                        lifeMultiple = lifeMultiple,
                                    )
                                ),
                            ),
                ),
        )
}
