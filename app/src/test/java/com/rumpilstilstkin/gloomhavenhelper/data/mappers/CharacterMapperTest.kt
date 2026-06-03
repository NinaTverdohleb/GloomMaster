package com.rumpilstilstkin.gloomhavenhelper.data.mappers

import com.rumpilstilstkin.gloomhavenhelper.bd.entity.CharacterBd
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterClassType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterForSave
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.DifficultyLevel
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.PackType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Team
import org.junit.Test
import org.junit.Assert.assertThrows
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNull

class CharacterMapperTest {
    @Test
    fun `given CharacterForSave with non-null goldCount when toBd then all fields are copied 1 to 1`() {
        // Given
        val source = CharacterForSave.fixture(
            name = "Eve",
            level = 3,
            characterType = CharacterClassType.Spellweaver,
            experience = 42,
            teamId = 7,
            goldCount = 50,
            isAlive = true,
            notes = "lvl3",
            checkMarkCount = 4,
            additionalContOfPerks = 1,
        )

        // When
        val bd = source.toBd()

        // Then
        expectThat(bd.name).isEqualTo("Eve")
        expectThat(bd.level).isEqualTo(3)
        expectThat(bd.characterType).isEqualTo("Spellweaver")
        expectThat(bd.experience).isEqualTo(42)
        expectThat(bd.teamId).isEqualTo(7)
        expectThat(bd.goldCount).isEqualTo(50)
        expectThat(bd.isAlive).isEqualTo(true)
        expectThat(bd.notes).isEqualTo("lvl3")
        expectThat(bd.checkMarkCount).isEqualTo(4)
        expectThat(bd.additionalContOfPerks).isEqualTo(1)
    }

    @Test
    fun `given CharacterForSave with null goldCount at level 0 when toBd then goldCount equals 15`() {
        // Given
        val source = CharacterForSave.fixture(level = 0, goldCount = null)

        // When
        val bd = source.toBd()

        // Then
        expectThat(bd.goldCount).isEqualTo(15)
    }

    @Test
    fun `given CharacterForSave with null goldCount at level 4 when toBd then goldCount equals 75`() {
        // Given
        val source = CharacterForSave.fixture(level = 4, goldCount = null)

        // When
        val bd = source.toBd()

        // Then
        expectThat(bd.goldCount).isEqualTo(75)
    }

    @Test
    fun `given CharacterBd and a non-null Team when toDomain then fields and team are propagated and type is decoded`() {
        // Given
        val bd = bdFixture(characterType = "Cragheart")
        val team = Team(
            teamId = 9,
            name = "Squad",
            packs = listOf(PackType.MAIN),
            difficultyLevel = DifficultyLevel.NORMAL,
        )

        // When
        val info = bd.toDomain(team)

        // Then
        expectThat(info.characterType).isEqualTo(CharacterClassType.Cragheart)
        expectThat(info.team).isEqualTo(team)
        expectThat(info.id).isEqualTo(bd.characterId)
        expectThat(info.name).isEqualTo(bd.name)
        expectThat(info.level).isEqualTo(bd.level)
        expectThat(info.goldCount).isEqualTo(bd.goldCount)
        expectThat(info.checkMarkCount).isEqualTo(bd.checkMarkCount)
        expectThat(info.additionalContOfPerks).isEqualTo(bd.additionalContOfPerks)
    }

    @Test
    fun `given CharacterBd and a null Team when toDomain then team is null and the rest is correct`() {
        // Given
        val bd = bdFixture()

        // When
        val info = bd.toDomain(team = null)

        // Then
        expectThat(info.team).isNull()
        expectThat(info.name).isEqualTo(bd.name)
        expectThat(info.id).isEqualTo(bd.characterId)
    }

    @Test
    fun `given CharacterBd when toShortDomain then team is absent and teamId is present`() {
        // Given
        val bd = bdFixture(teamId = 42)

        // When
        val short = bd.toShortDomain()

        // Then
        expectThat(short.teamId).isEqualTo(42)
        expectThat(short.name).isEqualTo(bd.name)
        expectThat(short.characterType).isEqualTo(CharacterClassType.Brute)
    }

    @Test
    fun `given CharacterBd with invalid characterType when toDomain then IllegalArgumentException is thrown`() {
        // Given
        val bd = bdFixture(characterType = "NotAClass")

        // When / Then
        assertThrows(IllegalArgumentException::class.java) { bd.toDomain(team = null) }
    }

    @Test
    fun `given CharacterBd with invalid characterType when toShortDomain then IllegalArgumentException is thrown`() {
        // Given
        val bd = bdFixture(characterType = "NotAClass")

        // When / Then
        assertThrows(IllegalArgumentException::class.java) { bd.toShortDomain() }
    }

    companion object {
        fun bdFixture(
            characterId: Int = 1,
            name: String = "Brute",
            level: Int = 1,
            experience: Int = 0,
            goldCount: Int = 30,
            characterType: String = "Brute",
            teamId: Int? = 1,
            isAlive: Boolean = true,
            notes: String = "",
            checkMarkCount: Int = 0,
            additionalContOfPerks: Int = 0,
        ): CharacterBd = CharacterBd(
            characterId = characterId,
            name = name,
            level = level,
            experience = experience,
            goldCount = goldCount,
            characterType = characterType,
            teamId = teamId,
            isAlive = isAlive,
            notes = notes,
            checkMarkCount = checkMarkCount,
            additionalContOfPerks = additionalContOfPerks,
        )
    }
}
