package com.rumpilstilstkin.gloomhavenhelper.data.mappers

import com.rumpilstilstkin.gloomhavenhelper.bd.entity.CharacterBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.TeamBd
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.DifficultyLevel
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.PackType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ShortTeamInfo
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.TeamInfoForSave
import org.junit.Assert.assertThrows
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.containsExactlyInAnyOrder
import strikt.assertions.isEmpty
import strikt.assertions.isEqualTo

class TeamMapperTest {
    @Test
    fun `given TeamInfoForSave when toBd then packs are serialized by name and difficulty by value`() {
        // Given
        val source = TeamInfoForSave.fixture(
            name = "Squad",
            packs = listOf(PackType.MAIN, PackType.FORGOTTEN_CIRCLES),
            difficultyLevel = DifficultyLevel.HARD,
        )

        // When
        val bd = source.toBd()

        // Then
        expectThat(bd.name).isEqualTo("Squad")
        expectThat(bd.packs).isEqualTo(listOf("MAIN", "FORGOTTEN_CIRCLES"))
        expectThat(bd.difficultyLevel).isEqualTo(DifficultyLevel.HARD.value)
    }

    @Test
    fun `given TeamBd and mixed alive and dead characters when toDomain then alive ids and retired count are split correctly`() {
        // Given
        val team = teamBdFixture()
        val characters = listOf(
            bdFixture(characterId = 1, isAlive = true),
            bdFixture(characterId = 2, isAlive = false),
            bdFixture(characterId = 3, isAlive = true),
            bdFixture(characterId = 4, isAlive = false),
        )

        // When
        val info = team.toDomain(characters)

        // Then
        expectThat(info.aliveCharacterIds).containsExactlyInAnyOrder(1, 3)
        expectThat(info.countRetiredCharacters).isEqualTo(2)
    }

    @Test
    fun `given TeamBd and an empty characters list when toDomain then aliveCharacterIds is empty and retired count is zero`() {
        // Given
        val team = teamBdFixture()

        // When
        val info = team.toDomain(emptyList())

        // Then
        expectThat(info.aliveCharacterIds).isEmpty()
        expectThat(info.countRetiredCharacters).isEqualTo(0)
    }

    @Test
    fun `given TeamBd and only alive characters when toDomain then retired count is zero`() {
        // Given
        val team = teamBdFixture()
        val characters = listOf(
            bdFixture(characterId = 1, isAlive = true),
            bdFixture(characterId = 2, isAlive = true),
        )

        // When
        val info = team.toDomain(characters)

        // Then
        expectThat(info.aliveCharacterIds).containsExactlyInAnyOrder(1, 2)
        expectThat(info.countRetiredCharacters).isEqualTo(0)
    }

    @Test
    fun `given TeamBd and only dead characters when toDomain then aliveCharacterIds is empty`() {
        // Given
        val team = teamBdFixture()
        val characters = listOf(
            bdFixture(characterId = 1, isAlive = false),
            bdFixture(characterId = 2, isAlive = false),
        )

        // When
        val info = team.toDomain(characters)

        // Then
        expectThat(info.aliveCharacterIds).isEmpty()
        expectThat(info.countRetiredCharacters).isEqualTo(2)
    }

    @Test
    fun `given ShortTeamInfo when toBd then packs become string names and difficulty value is preserved`() {
        // Given
        val short = ShortTeamInfo(
            teamId = 5,
            name = "Squad",
            achievements = emptyList(),
            aliveCharacterIds = listOf(1, 2),
            reputation = 7,
            prosperity = 10,
            packs = listOf(PackType.FORGOTTEN_CIRCLES),
            churchValue = 100,
            difficultyLevel = DifficultyLevel.VERY_HARD,
            countRetiredCharacters = 1,
        )

        // When
        val bd = short.toBd()

        // Then
        expectThat(bd.teamId).isEqualTo(5)
        expectThat(bd.packs).isEqualTo(listOf("FORGOTTEN_CIRCLES"))
        expectThat(bd.difficultyLevel).isEqualTo(DifficultyLevel.VERY_HARD.value)
        expectThat(bd.churchValue).isEqualTo(100)
    }

    @Test
    fun `given TeamBd with invalid pack string when toDomain then IllegalArgumentException is thrown`() {
        // Given
        val team = teamBdFixture(packs = listOf("NOT_A_PACK"))

        // When / Then
        assertThrows(IllegalArgumentException::class.java) { team.toDomain(emptyList<CharacterBd>()) }
    }

    companion object {
        fun teamBdFixture(
            teamId: Int = 1,
            name: String = "Squad",
            packs: List<String> = listOf("MAIN"),
            difficultyLevel: Int = DifficultyLevel.NORMAL.value,
            reputation: Int = 0,
            prosperity: Int = 0,
            churchValue: Int = 100,
        ): TeamBd = TeamBd(
            teamId = teamId,
            name = name,
            achievements = emptyList(),
            reputation = reputation,
            prosperity = prosperity,
            churchValue = churchValue,
            packs = packs,
            difficultyLevel = difficultyLevel,
        )

        fun bdFixture(
            characterId: Int = 1,
            isAlive: Boolean = true,
        ): CharacterBd = CharacterBd(
            characterId = characterId,
            name = "Brute",
            level = 1,
            experience = 0,
            goldCount = 30,
            characterType = "Brute",
            teamId = 1,
            isAlive = isAlive,
        )
    }
}
