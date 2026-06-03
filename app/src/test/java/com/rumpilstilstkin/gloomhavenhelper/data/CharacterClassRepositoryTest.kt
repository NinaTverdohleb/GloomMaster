package com.rumpilstilstkin.gloomhavenhelper.data

import app.cash.turbine.test
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.TeamCharacterClassDao
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.TeamCharacterClassBd
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterClassType
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
import strikt.assertions.containsExactly
import strikt.assertions.isEqualTo

@OptIn(ExperimentalCoroutinesApi::class)
class CharacterClassRepositoryTest {
    private val dao: TeamCharacterClassDao = mockk(relaxUnitFun = true)
    private val sut = CharacterClassRepository(dao)

    @Test
    fun `given DAO emits class type names when getAvailableClassesForTeam is collected then names are decoded into CharacterClassType`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        every { dao.getClassTypesForTeam(5) } returns flowOf(listOf("Brute", "Spellweaver"))

        // When / Then
        sut.getAvailableClassesForTeam(teamId = 5).test {
            expectThat(awaitItem()).containsExactly(
                CharacterClassType.Brute,
                CharacterClassType.Spellweaver,
            )
            awaitComplete()
        }
    }

    @Test
    fun `given DAO returns TeamCharacterClassBd rows when getAvailableClassesForTeamSync then characterType strings are returned`() = runTest {
        // Given
        coEvery { dao.getClassesForTeamSync(5) } returns listOf(
            TeamCharacterClassBd(teamId = 5, characterType = "Brute"),
            TeamCharacterClassBd(teamId = 5, characterType = "Mindthief"),
        )

        // When
        val classes = sut.getAvailableClassesForTeamSync(5)

        // Then
        expectThat(classes).isEqualTo(listOf("Brute", "Mindthief"))
    }

    @Test
    fun `given a class type when addAvailableClass is called then DAO insert receives a matching entity`() = runTest {
        // When
        sut.addAvailableClass(teamId = 5, type = CharacterClassType.Brute)

        // Then
        coVerify(exactly = 1) {
            dao.insert(TeamCharacterClassBd(teamId = 5, characterType = "Brute"))
        }
    }

    @Test
    fun `given a class type when removeAvailableClass is called then DAO delete receives the same teamId and name`() = runTest {
        // When
        sut.removeAvailableClass(teamId = 5, type = CharacterClassType.Brute)

        // Then
        coVerify(exactly = 1) { dao.delete(5, "Brute") }
    }

    @Test
    fun `given several class types when addAvailableClasses is called then DAO insertAll receives them all as varargs`() = runTest {
        // When
        sut.addAvailableClasses(
            teamId = 5,
            types = listOf(CharacterClassType.Brute, CharacterClassType.Spellweaver),
        )

        // Then
        coVerify(exactly = 1) {
            dao.insertAll(
                TeamCharacterClassBd(teamId = 5, characterType = "Brute"),
                TeamCharacterClassBd(teamId = 5, characterType = "Spellweaver"),
            )
        }
    }

    @Test
    fun `given an empty list when addAvailableClasses is called then DAO insertAll is still invoked once with empty varargs`() = runTest {
        // When
        sut.addAvailableClasses(teamId = 5, types = emptyList())

        // Then
        coVerify(exactly = 1) { dao.insertAll() }
    }
}
