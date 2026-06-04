package com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario

import kotlinx.collections.immutable.persistentListOf
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.containsExactly
import strikt.assertions.hasSize
import strikt.assertions.isEqualTo
import strikt.assertions.isSameInstanceAs

class MonsterExtensionsTest {
    @Test
    fun `given list with matching slug when updateMonster then matching item is transformed`() {
        // Given
        val a = MonsterItem.fixture(slug = "brute", name = "Brute")
        val b = MonsterItem.fixture(slug = "scout", name = "Scout")

        // When
        val result =
            listOf(a, b).updateMonster("brute") { it.copy(name = "Transformed") }

        // Then
        expectThat(result[0].name).isEqualTo("Transformed")
        expectThat(result[1]).isSameInstanceAs(b)
    }

    @Test
    fun `given list with no matching slug when updateMonster then list is unchanged`() {
        // Given
        val a = MonsterItem.fixture(slug = "brute")

        // When
        val result = listOf(a).updateMonster("unknown") { it.copy(name = "X") }

        // Then
        expectThat(result).containsExactly(a)
    }

    @Test
    fun `given empty list when updateMonster then result is empty`() {
        // Given / When
        val result = emptyList<MonsterItem>().updateMonster("brute") { it }

        // Then
        expectThat(result).hasSize(0)
    }

    @Test
    fun `given monster item with units when addUnits then new units are appended`() {
        // Given
        val existing = MonsterUnit.fixture(number = 1)
        val item = MonsterItem.fixture(slug = "brute").copy(units = persistentListOf(existing))
        val added = MonsterUnit.fixture(number = 2)

        // When
        val result = item.addUnits(listOf(added))

        // Then
        expectThat(result.units).containsExactly(existing, added)
    }

    @Test
    fun `given monster item when addUnits with empty list then existing units remain`() {
        // Given
        val existing = MonsterUnit.fixture(number = 1)
        val item = MonsterItem.fixture(slug = "brute").copy(units = persistentListOf(existing))

        // When
        val result = item.addUnits(emptyList())

        // Then
        expectThat(result.units).containsExactly(existing)
    }
}
