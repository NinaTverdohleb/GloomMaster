package com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario

import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.hasSize
import strikt.assertions.isEqualTo
import strikt.assertions.isSameInstanceAs

class MonsterExtensionsTest {
    @Test
    fun `given map with matching slug when updateMonster then matching item is transformed`() {
        // Given
        val a = MonsterItem.fixture(slug = "brute", name = "Brute")
        val b = MonsterItem.fixture(slug = "scout", name = "Scout")
        val monsters = mapOf("brute" to a, "scout" to b)

        // When
        val result = monsters.updateMonster("brute") { copy(name = "Transformed") }

        // Then
        expectThat(result.getValue("brute").name).isEqualTo("Transformed")
        expectThat(result.getValue("scout")).isSameInstanceAs(b)
    }

    @Test
    fun `given map with no matching slug when updateMonster then map is unchanged`() {
        // Given
        val a = MonsterItem.fixture(slug = "brute")
        val monsters = mapOf("brute" to a)

        // When
        val result = monsters.updateMonster("unknown") { copy(name = "X") }

        // Then
        expectThat(result).isSameInstanceAs(monsters)
    }

    @Test
    fun `given empty map when updateMonster then result is empty`() {
        // Given / When
        val result = emptyMap<String, MonsterItem>().updateMonster("brute") { this }

        // Then
        expectThat(result).hasSize(0)
    }

    @Test
    fun `given monster item with units when addUnits then new units are added`() {
        // Given
        val existing = MonsterUnit.fixture(number = 1)
        val item = MonsterItem.fixture(slug = "brute").copy(units = mapOf(1 to existing))
        val added = MonsterUnit.fixture(number = 2)

        // When
        val result = item.addUnits(mapOf(2 to added))

        // Then
        expectThat(result.units).isEqualTo(mapOf(1 to existing, 2 to added))
    }

    @Test
    fun `given monster item when addUnits with empty map then existing units remain`() {
        // Given
        val existing = MonsterUnit.fixture(number = 1)
        val item = MonsterItem.fixture(slug = "brute").copy(units = mapOf(1 to existing))

        // When
        val result = item.addUnits(emptyMap())

        // Then
        expectThat(result.units).isEqualTo(mapOf(1 to existing))
    }
}
