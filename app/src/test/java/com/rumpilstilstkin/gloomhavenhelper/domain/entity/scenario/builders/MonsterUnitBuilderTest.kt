package com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.builders

import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioGameStateMonsterUnit
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.Monster
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterAction
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterCard
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterStatType
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.containsExactly
import strikt.assertions.isEqualTo
import strikt.assertions.isFalse
import strikt.assertions.isTrue

@OptIn(ExperimentalCoroutinesApi::class)
class MonsterUnitBuilderTest {
    @Test
    fun `given same lastLevel and stateUnit level when build then uses baseMonster and does not call getMonster`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val base = monster(slug = "brute", life = 10, level = 3)
        val stateUnit = stateUnit(number = 1, currentLife = 10, maxLife = 10, level = 3, isElite = false)
        val getMonster = mockk<suspend (Int, String) -> Monster?>()

        // When
        val result =
            MonsterUnitBuilder(stateUnit, base)
                .levels(levels = 3 to 5)
                .gamersCount(2)
                .build(getMonster)

        // Then
        expectThat(result.maxLife).isEqualTo(10)
        expectThat(result.level).isEqualTo(3)
        coVerify(exactly = 0) { getMonster.invoke(any(), any()) }
    }

    @Test
    fun `given different levels when build then calls getMonster with computed level and uses returned stats`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val base = monster(slug = "brute", life = 10, level = 1)
        val levelled = monster(
            slug = "brute",
            life = 30,
            level = 4,
            stats = listOf(MonsterAction.Action(statType = MonsterStatType.MOVE, modifier = "3")),
        )
        val stateUnit = stateUnit(number = 1, currentLife = 30, maxLife = 30, level = 2, isElite = false)
        val getMonster = mockk<suspend (Int, String) -> Monster?>()
        coEvery { getMonster.invoke(any(), any()) } returns levelled

        // When
        val result =
            MonsterUnitBuilder(stateUnit, base)
                .levels(levels = 1 to 3)
                .gamersCount(2)
                .build(getMonster)

        // Then — computed level = newLevel + (stateUnit.level - lastLevel) = 3 + (2 - 1) = 4
        coVerify { getMonster.invoke(4, "brute") }
        expectThat(result.maxLife).isEqualTo(30)
        expectThat(result.level).isEqualTo(4)
        expectThat(result.stats.toList()).isEqualTo(levelled.stats)
    }

    @Test
    fun `given computed level would be negative when build then it is clamped to zero`() = runTest(UnconfinedTestDispatcher()) {
        // Given — last=5, new=0, unit.level=2 → computed = 0 + (2-5) = -3 → clamped to 0
        val base = monster(slug = "brute", level = 5)
        val zeroLevelMonster = monster(slug = "brute", level = 0, life = 5)
        val stateUnit = stateUnit(number = 1, currentLife = 5, maxLife = 5, level = 2, isElite = false)
        val getMonster = mockk<suspend (Int, String) -> Monster?>()
        coEvery { getMonster.invoke(0, "brute") } returns zeroLevelMonster

        // When
        val result =
            MonsterUnitBuilder(stateUnit, base)
                .levels(levels = 5 to 0)
                .gamersCount(1)
                .build(getMonster)

        // Then
        coVerify { getMonster.invoke(0, "brute") }
        expectThat(result.level).isEqualTo(0)
    }

    @Test
    fun `given different levels and getMonster returns null when build then falls back to baseMonster`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val base = monster(slug = "brute", life = 12, level = 1)
        val stateUnit = stateUnit(number = 1, currentLife = 12, maxLife = 12, level = 1, isElite = false)
        val getMonster = mockk<suspend (Int, String) -> Monster?>()
        coEvery { getMonster.invoke(any(), any()) } returns null

        // When
        val result =
            MonsterUnitBuilder(stateUnit, base)
                .levels(levels = 0 to 2)
                .gamersCount(1)
                .build(getMonster)

        // Then
        expectThat(result.maxLife).isEqualTo(12)
        expectThat(result.level).isEqualTo(1)
    }

    @Test
    fun `given elite unit when build then uses eliteLife and eliteStats and isSpecial is true`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val eliteStats = listOf(MonsterAction.Action(statType = MonsterStatType.ATTACK, modifier = "5"))
        val base = monster(
            slug = "brute",
            life = 10,
            eliteLife = 22,
            stats = listOf(MonsterAction.Action(statType = MonsterStatType.MOVE, modifier = "2")),
            eliteStats = eliteStats,
        )
        val stateUnit = stateUnit(number = 1, currentLife = 22, maxLife = 22, level = 1, isElite = true)

        // When
        val result =
            MonsterUnitBuilder(stateUnit, base)
                .levels(levels = 1 to 1)
                .gamersCount(4)
                .build { _, _ -> null }

        // Then
        expectThat(result.maxLife).isEqualTo(22)
        expectThat(result.stats.toList()).isEqualTo(eliteStats)
        expectThat(result.isSpecial).isTrue()
    }

    @Test
    fun `given non-elite lifeMultiple true when build then maxLife is multiplied by gamersCount`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val base = monster(slug = "boss", life = 8, lifeMultiple = true)
        val stateUnit = stateUnit(number = 1, currentLife = 32, maxLife = 32, level = 1, isElite = false)

        // When
        val result =
            MonsterUnitBuilder(stateUnit, base)
                .levels(levels = 1 to 1)
                .gamersCount(4)
                .build { _, _ -> null }

        // Then
        expectThat(result.maxLife).isEqualTo(32)
        expectThat(result.isSpecial).isFalse()
    }

    @Test
    fun `given non-elite lifeMultiple false when build then maxLife equals base life`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val base = monster(slug = "brute", life = 10, lifeMultiple = false)
        val stateUnit = stateUnit(number = 1, currentLife = 10, maxLife = 10, level = 1, isElite = false)

        // When
        val result =
            MonsterUnitBuilder(stateUnit, base)
                .levels(levels = 1 to 1)
                .gamersCount(4)
                .build { _, _ -> null }

        // Then
        expectThat(result.maxLife).isEqualTo(10)
    }

    @Test
    fun `given persisted damage when build then currentLife reflects maxLife minus persisted damage`() = runTest(UnconfinedTestDispatcher()) {
        // Given — persisted: maxLife=10, currentLife=4 → damage=6 → newCurrentLife = 10 - 6 = 4
        val base = monster(slug = "brute", life = 10)
        val stateUnit = stateUnit(number = 1, currentLife = 4, maxLife = 10, level = 1, isElite = false)

        // When
        val result =
            MonsterUnitBuilder(stateUnit, base)
                .levels(levels = 1 to 1)
                .gamersCount(1)
                .build { _, _ -> null }

        // Then
        expectThat(result.maxLife).isEqualTo(10)
        expectThat(result.currentLife).isEqualTo(4)
    }

    @Test
    fun `given persisted currentLife exceeds maxLife when build then negative damage is clamped to zero`() = runTest(UnconfinedTestDispatcher()) {
        // Given — persisted: maxLife=5, currentLife=8 → damage = max(5-8, 0) = 0 → newCurrentLife = 10
        val base = monster(slug = "brute", life = 10)
        val stateUnit = stateUnit(number = 1, currentLife = 8, maxLife = 5, level = 1, isElite = false)

        // When
        val result =
            MonsterUnitBuilder(stateUnit, base)
                .levels(levels = 1 to 1)
                .gamersCount(1)
                .build { _, _ -> null }

        // Then
        expectThat(result.currentLife).isEqualTo(10)
    }

    @Test
    fun `given stateUnit when build then passthrough fields are carried over`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val effects = setOf(MonsterStatType.POISON, MonsterStatType.WOUND)
        val immunity = setOf(MonsterStatType.STUN)
        val base = monster(slug = "brute", level = 2, immunity = immunity)
        val stateUnit = stateUnit(
            number = 7,
            currentLife = 10,
            maxLife = 10,
            level = 2,
            isElite = false,
            effects = effects,
            isNew = false,
        )

        // When
        val result =
            MonsterUnitBuilder(stateUnit, base)
                .levels(levels = 2 to 2)
                .gamersCount(1)
                .build { _, _ -> null }

        // Then
        expectThat(result.number).isEqualTo(7)
        expectThat(result.effects.toList()).containsExactly(MonsterStatType.POISON, MonsterStatType.WOUND)
        expectThat(result.isNew).isFalse()
        expectThat(result.level).isEqualTo(2)
        expectThat(result.immunity.toList()).containsExactly(MonsterStatType.STUN)
    }

    private fun monster(
        slug: String,
        life: Int = 10,
        eliteLife: Int = 20,
        stats: List<MonsterAction> = emptyList(),
        eliteStats: List<MonsterAction> = emptyList(),
        level: Int = 1,
        lifeMultiple: Boolean = false,
        immunity: Set<MonsterStatType> = emptySet(),
        deckName: String = "deck",
        cards: List<MonsterCard> = emptyList(),
        isBoss: Boolean = false,
        isFly: Boolean = false,
    ) = Monster(
        slug = slug,
        name = slug,
        life = life,
        stats = stats,
        eliteLife = eliteLife,
        eliteStats = eliteStats,
        deckName = deckName,
        cards = cards,
        isBoss = isBoss,
        immunity = immunity,
        isFly = isFly,
        level = level,
        lifeMultiple = lifeMultiple,
    )

    private fun stateUnit(
        number: Int,
        currentLife: Int,
        maxLife: Int,
        level: Int,
        isElite: Boolean,
        effects: Set<MonsterStatType> = emptySet(),
        isNew: Boolean = true,
    ) = ScenarioGameStateMonsterUnit(
        number = number,
        currentLife = currentLife,
        level = level,
        isElite = isElite,
        effects = effects,
        isNew = isNew,
        maxLife = maxLife,
    )
}
