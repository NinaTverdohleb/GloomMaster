package com.rumpilstilstkin.gloommaster.data.mappers

import com.rumpilstilstkin.gloommaster.bd.entity.ScenarioBd
import com.rumpilstilstkin.gloommaster.bd.entity.ScenarioWithNameBd
import com.rumpilstilstkin.gloommaster.domain.entity.PackType
import com.rumpilstilstkin.gloommaster.data.mappers.toDomain
import com.rumpilstilstkin.gloommaster.data.mappers.toInfoDomain
import com.rumpilstilstkin.gloommaster.data.mappers.toShortDomain
import org.junit.Test
import org.junit.Assert.assertThrows
import strikt.api.expectThat
import strikt.assertions.contains
import strikt.assertions.isEmpty
import strikt.assertions.isEqualTo

class ScenarioMapperTest {
    @Test
    fun `given ScenarioWithNameBd happy when toDomain then fields copy and newScenarios split into ints`() {
        // Given
        val bd = withNameFixture(newScenarios = "10, 11, 12")

        // When
        val info = bd.toDomain(isCompleted = false, dictionary = emptyMap())

        // Then
        expectThat(info.scenarioNumber).isEqualTo(bd.scenarioNumber)
        expectThat(info.scenarioName).isEqualTo(bd.name)
        expectThat(info.pack).isEqualTo(PackType.MAIN)
        expectThat(info.location).isEqualTo(bd.locationName)
        expectThat(info.monsters).isEqualTo(bd.monsters)
        expectThat(info.newScenario).isEqualTo(listOf(10, 11, 12))
        expectThat(info.isCompleted).isEqualTo(false)
    }

    @Test
    fun `given ScenarioWithNameBd with empty newScenarios when toDomain then newScenario is empty`() {
        // Given
        val bd = withNameFixture(newScenarios = "")

        // When
        val info = bd.toDomain(dictionary = emptyMap())

        // Then
        expectThat(info.newScenario).isEmpty()
    }

    @Test
    fun `given ScenarioWithNameBd with blank segments in newScenarios when toDomain then blanks are filtered out`() {
        // Given
        val bd = withNameFixture(newScenarios = "10,, 12")

        // When
        val info = bd.toDomain(dictionary = emptyMap())

        // Then
        expectThat(info.newScenario).isEqualTo(listOf(10, 12))
    }

    @Test
    fun `given dictionary with a requirement token when toDomain then the substitution appears in the condition string`() {
        // Given
        val bd = withNameFixture(requirements = "ach_1")
        val dictionary = mapOf("ach_1" to "Resolved Achievement")

        // When
        val info = bd.toDomain(dictionary = dictionary)

        // Then
        expectThat(info.scenarioRequirements.condition).contains("Resolved Achievement")
    }

    @Test
    fun `given isCompleted true when toDomain then isCompleted is propagated`() {
        // Given
        val bd = withNameFixture()

        // When
        val info = bd.toDomain(isCompleted = true, dictionary = emptyMap())

        // Then
        expectThat(info.isCompleted).isEqualTo(true)
    }

    @Test
    fun `given ScenarioBd when toShortDomain then requirements wrap into LogicalCondition as-is`() {
        // Given
        val bd = scenarioBdFixture(requirements = "ach_1 && ach_2")

        // When
        val short = bd.toShortDomain(isCompleted = true)

        // Then
        expectThat(short.scenarioNumber).isEqualTo(bd.scenarioNumber)
        expectThat(short.pack).isEqualTo(PackType.MAIN)
        expectThat(short.scenarioRequirements.condition).isEqualTo("ach_1 && ach_2")
        expectThat(short.isCompleted).isEqualTo(true)
        expectThat(short.monsters).isEqualTo(bd.monsters)
    }

    @Test
    fun `given ScenarioBd with whitespace-padded newScenarios when toInfoDomain then numbers are trimmed and parsed`() {
        // Given
        val bd = scenarioBdFixture(newScenarios = "  5  ")

        // When
        val info = bd.toInfoDomain()

        // Then
        expectThat(info.newScenario).isEqualTo(listOf(5))
        expectThat(info.scenarioNumber).isEqualTo(bd.scenarioNumber)
        expectThat(info.pack).isEqualTo(PackType.MAIN)
    }

    @Test
    fun `given ScenarioBd with invalid pack when toShortDomain then IllegalArgumentException is thrown`() {
        // Given
        val bd = scenarioBdFixture(pack = "NOT_A_PACK")

        // When / Then
        assertThrows(IllegalArgumentException::class.java) { bd.toShortDomain(isCompleted = false) }
    }

    @Test
    fun `given ScenarioBd with invalid pack when toInfoDomain then IllegalArgumentException is thrown`() {
        // Given
        val bd = scenarioBdFixture(pack = "NOT_A_PACK")

        // When / Then
        assertThrows(IllegalArgumentException::class.java) { bd.toInfoDomain() }
    }

    companion object {
        fun withNameFixture(
            scenarioNumber: Int = 1,
            newScenarios: String = "",
            requirements: String = "",
            monsters: List<String> = listOf("slug-a"),
            locationName: String = "Outpost",
            pack: String = "MAIN",
            name: String = "Black Barrow",
        ): ScenarioWithNameBd = ScenarioWithNameBd(
            scenarioNumber = scenarioNumber,
            newScenarios = newScenarios,
            requirements = requirements,
            monsters = monsters,
            locationName = locationName,
            pack = pack,
            name = name,
        )

        fun scenarioBdFixture(
            scenarioNumber: Int = 1,
            newScenarios: String = "",
            requirements: String = "",
            monsters: List<String> = listOf("slug-a"),
            location: String = "",
            pack: String = "MAIN",
        ): ScenarioBd = ScenarioBd(
            scenarioNumber = scenarioNumber,
            newScenarios = newScenarios,
            requirements = requirements,
            monsters = monsters,
            location = location,
            pack = pack,
        )
    }
}
