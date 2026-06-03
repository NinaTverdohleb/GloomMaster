package com.rumpilstilstkin.gloomhavenhelper.data

import app.cash.turbine.test
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.CharacterDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.CharacterPerksDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.TeamDao
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.CharacterBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.CharacterPerkBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.CharacterPerkWithNameBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.TeamBd
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterClassType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterForSave
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNotNull
import strikt.assertions.isNull

@OptIn(ExperimentalCoroutinesApi::class)
class CharacterRepositoryTest {
    private val characterDao: CharacterDao = mockk(relaxUnitFun = true)
    private val teamDao: TeamDao = mockk()
    private val perksDao: CharacterPerksDao = mockk(relaxUnitFun = true)
    private val sut = CharacterRepository(characterDao, teamDao, perksDao)

    @Test
    fun `given a characterId and perkId when addCharacterPerk then DAO insert is invoked with that pair`() = runTest {
        // When
        sut.addCharacterPerk(characterId = 7, perkId = 42)

        // Then
        coVerify(exactly = 1) {
            perksDao.insert(CharacterPerkBd(characterId = 7, perkId = 42))
        }
    }

    @Test
    fun `given a characterId and perkId when deleteCharacterPerk then DAO deleteById is invoked with that pair`() = runTest {
        // When
        sut.deleteCharacterPerk(characterId = 7, perkId = 42)

        // Then
        coVerify(exactly = 1) {
            perksDao.deleteById(characterId = 7, perkId = 42)
        }
    }

    @Test
    fun `given DAO emits a perks list when getCharacterPerksFlow is collected then mapped Perks reach the consumer and locales are passed through`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        every {
            perksDao.getCharacterPerksFlow(
                characterId = 7,
                targetLocale = "ru",
                defaultLocale = "en",
            )
        } returns flowOf(
            listOf(
                CharacterPerkWithNameBd(perk = CharacterPerkBd(id = 100, characterId = 7, perkId = 1), text = "perk A"),
                CharacterPerkWithNameBd(perk = CharacterPerkBd(id = 101, characterId = 7, perkId = 2), text = "perk B"),
            ),
        )

        // When / Then
        sut.getCharacterPerksFlow(characterId = 7, locale = "ru").test {
            val emitted = awaitItem()
            expectThat(emitted.map { it.id }).isEqualTo(listOf(1, 2))
            expectThat(emitted.map { it.text }).isEqualTo(listOf("perk A", "perk B"))
            awaitComplete()
        }
    }

    @Test
    fun `given DAO returns CharacterPerkBd rows when getCharacterPerks then perkIds are returned`() = runTest {
        // Given
        coEvery { perksDao.getCharacterPerks(7) } returns listOf(
            CharacterPerkBd(characterId = 7, perkId = 1),
            CharacterPerkBd(characterId = 7, perkId = 2),
            CharacterPerkBd(characterId = 7, perkId = 5),
        )

        // When
        val perkIds = sut.getCharacterPerks(7)

        // Then
        expectThat(perkIds).isEqualTo(listOf(1, 2, 5))
    }

    @Test
    fun `given team is present when getCharacterByTeamId is collected then each character is enriched with the team`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        every { characterDao.findByTeamIdFlow(10) } returns flowOf(
            listOf(
                bdFixture(characterId = 1, teamId = 10),
                bdFixture(characterId = 2, teamId = 10),
            ),
        )
        coEvery { teamDao.findById(10) } returns teamBdFixture(teamId = 10)

        // When / Then
        sut.getCharacterByTeamId(teamId = 10).test {
            val emitted = awaitItem()
            expectThat(emitted.map { it.id }).isEqualTo(listOf(1, 2))
            emitted.forEach { info ->
                expectThat(info.team).isNotNull()
                expectThat(info.team?.teamId).isEqualTo(10)
            }
            awaitComplete()
        }
    }

    @Test
    fun `given team lookup returns null when getCharacterByTeamId is collected then characters are emitted with team null`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        every { characterDao.findByTeamIdFlow(10) } returns flowOf(
            listOf(bdFixture(characterId = 1, teamId = 10)),
        )
        coEvery { teamDao.findById(10) } returns null

        // When / Then
        sut.getCharacterByTeamId(teamId = 10).test {
            val emitted = awaitItem()
            expectThat(emitted[0].team).isNull()
            awaitComplete()
        }
    }

    @Test
    fun `given DAO returns characters when getCharacterByTeamIdSync then characters are returned with team set to null`() = runTest {
        // Given
        coEvery { characterDao.findByTeamId(10) } returns listOf(
            bdFixture(characterId = 1, teamId = 10),
        )

        // When
        val characters = sut.getCharacterByTeamIdSync(10)

        // Then
        expectThat(characters[0].team).isNull()
        expectThat(characters[0].id).isEqualTo(1)
    }

    @Test
    fun `given DAO emits the character with a team when getCharacterByIdFlow then it is enriched with the team`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        every { characterDao.getCharacterByIdFlow(1) } returns flowOf(
            bdFixture(characterId = 1, teamId = 10),
        )
        coEvery { teamDao.findById(10) } returns teamBdFixture(teamId = 10)

        // When / Then
        sut.getCharacterByIdFlow(1).test {
            val emitted = awaitItem()
            expectThat(emitted).isNotNull()
            expectThat(emitted?.team?.teamId).isEqualTo(10)
            awaitComplete()
        }
    }

    @Test
    fun `given DAO emits null when getCharacterByIdFlow is collected then null reaches the consumer and teamDao is not hit`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        every { characterDao.getCharacterByIdFlow(1) } returns flowOf(null)

        // When / Then
        sut.getCharacterByIdFlow(1).test {
            expectThat(awaitItem()).isNull()
            awaitComplete()
        }
        coVerify(exactly = 0) { teamDao.findById(any()) }
    }

    @Test
    fun `given DAO returns the character when getCharacterById then it is mapped to a short domain entity`() = runTest {
        // Given
        coEvery { characterDao.getCharacterById(1) } returns bdFixture(characterId = 1, teamId = 10)

        // When
        val short = sut.getCharacterById(1)

        // Then
        expectThat(short).isNotNull()
        expectThat(short?.id).isEqualTo(1)
        expectThat(short?.teamId).isEqualTo(10)
    }

    @Test
    fun `given DAO returns null when getCharacterById then null is returned`() = runTest {
        // Given
        coEvery { characterDao.getCharacterById(1) } returns null

        // When
        val short = sut.getCharacterById(1)

        // Then
        expectThat(short).isNull()
    }

    @Test
    fun `given DAO insert returns a Long when addCharacter then the result is converted to Int`() = runTest {
        // Given
        coEvery { characterDao.insert(any()) } returns 42L

        // When
        val id = sut.addCharacter(
            CharacterForSave(
                name = "Eve",
                level = 1,
                characterType = CharacterClassType.Brute,
                experience = 0,
            ),
        )

        // Then
        expectThat(id).isEqualTo(42)
    }

    @Test
    fun `given DAO has the character when updateLevel then DAO update is called with the new level only`() = runTest {
        // Given
        val original = bdFixture(characterId = 1, level = 2, notes = "keep")
        coEvery { characterDao.getCharacterById(1) } returns original

        // When
        sut.updateLevel(id = 1, level = 5)

        // Then
        coVerify(exactly = 1) {
            characterDao.update(match { it.level == 5 && it.notes == "keep" })
        }
    }

    @Test
    fun `given DAO has the character when setLevel then DAO update receives both level and experience`() = runTest {
        // Given
        coEvery { characterDao.getCharacterById(1) } returns bdFixture(characterId = 1, level = 1, experience = 0)

        // When
        sut.setLevel(id = 1, level = 4, experience = 200)

        // Then
        coVerify(exactly = 1) {
            characterDao.update(match { it.level == 4 && it.experience == 200 })
        }
    }

    @Test
    fun `given DAO has the character when updateNotes then DAO update is called with the new notes`() = runTest {
        // Given
        coEvery { characterDao.getCharacterById(1) } returns bdFixture(characterId = 1, notes = "old")

        // When
        sut.updateNotes(id = 1, notes = "new notes")

        // Then
        coVerify(exactly = 1) { characterDao.update(match { it.notes == "new notes" }) }
    }

    @Test
    fun `given DAO has the character when updateCheckMarks then DAO update is called with the new count`() = runTest {
        // Given
        coEvery { characterDao.getCharacterById(1) } returns bdFixture(characterId = 1, checkMarkCount = 0)

        // When
        sut.updateCheckMarks(id = 1, checkMarkCount = 3)

        // Then
        coVerify(exactly = 1) { characterDao.update(match { it.checkMarkCount == 3 }) }
    }

    @Test
    fun `given DAO has the character when updateExperience then DAO update is called with the new experience`() = runTest {
        // Given
        coEvery { characterDao.getCharacterById(1) } returns bdFixture(characterId = 1, experience = 0)

        // When
        sut.updateExperience(id = 1, experience = 99)

        // Then
        coVerify(exactly = 1) { characterDao.update(match { it.experience == 99 }) }
    }

    @Test
    fun `given DAO has the character when updateGold then DAO update is called with the new gold count`() = runTest {
        // Given
        coEvery { characterDao.getCharacterById(1) } returns bdFixture(characterId = 1, goldCount = 30)

        // When
        sut.updateGold(id = 1, goldCount = 80)

        // Then
        coVerify(exactly = 1) { characterDao.update(match { it.goldCount == 80 }) }
    }

    @Test
    fun `given DAO has the character when leaveCharacter then DAO update is called with isAlive false`() = runTest {
        // Given
        coEvery { characterDao.getCharacterById(1) } returns bdFixture(characterId = 1, isAlive = true)

        // When
        sut.leaveCharacter(id = 1)

        // Then
        coVerify(exactly = 1) { characterDao.update(match { !it.isAlive }) }
    }

    @Test
    fun `given DAO has the character when setTeam then DAO update is called with the new teamId`() = runTest {
        // Given
        coEvery { characterDao.getCharacterById(1) } returns bdFixture(characterId = 1, teamId = 5)

        // When
        sut.setTeam(characterId = 1, teamId = 9)

        // Then
        coVerify(exactly = 1) { characterDao.update(match { it.teamId == 9 }) }
    }

    @Test
    fun `given DAO has the character when updateName then DAO update is called with the new name`() = runTest {
        // Given
        coEvery { characterDao.getCharacterById(1) } returns bdFixture(characterId = 1, name = "Old")

        // When
        sut.updateName(id = 1, name = "New")

        // Then
        coVerify(exactly = 1) { characterDao.update(match { it.name == "New" }) }
    }

    @Test
    fun `given DAO returns null when any read-modify-write method is called then DAO update is never invoked`() = runTest {
        // Given
        coEvery { characterDao.getCharacterById(1) } returns null

        // When
        sut.updateLevel(id = 1, level = 5)
        sut.setLevel(id = 1, level = 5, experience = 100)
        sut.updateNotes(id = 1, notes = "x")
        sut.updateCheckMarks(id = 1, checkMarkCount = 1)
        sut.updateExperience(id = 1, experience = 1)
        sut.updateGold(id = 1, goldCount = 1)
        sut.leaveCharacter(id = 1)
        sut.setTeam(characterId = 1, teamId = 1)
        sut.updateName(id = 1, name = "x")

        // Then
        coVerify(exactly = 0) { characterDao.update(any()) }
    }

    @Test
    fun `given an id when deleteCharacter then DAO deleteById is invoked with that id`() = runTest {
        // When
        sut.deleteCharacter(1)

        // Then
        coVerify(exactly = 1) { characterDao.deleteById(1) }
    }

    companion object {
        fun bdFixture(
            characterId: Int = 1,
            name: String = "Brute",
            level: Int = 1,
            experience: Int = 0,
            goldCount: Int = 30,
            characterType: String = "Brute",
            teamId: Int? = 10,
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

        fun teamBdFixture(
            teamId: Int = 10,
            packs: List<String> = listOf("MAIN"),
            difficultyLevel: Int = 0,
        ): TeamBd = TeamBd(
            teamId = teamId,
            name = "Squad",
            packs = packs,
            difficultyLevel = difficultyLevel,
        )
    }
}
