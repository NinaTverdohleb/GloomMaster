package com.rumpilstilstkin.gloomhavenhelper.data

import app.cash.turbine.test
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.CharacterGoodsDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.GoodsDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.TeamGoodDao
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.CharacterGoodBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.GoodBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.GoodWithTranslation
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.TeamGoodBd
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.PackType
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
import strikt.assertions.containsExactlyInAnyOrder
import strikt.assertions.hasSize
import strikt.assertions.isEqualTo

@OptIn(ExperimentalCoroutinesApi::class)
class GoodsRepositoryTest {
    private val goodsDao: GoodsDao = mockk()
    private val teamGoodDao: TeamGoodDao = mockk(relaxUnitFun = true)
    private val characterGoodsDao: CharacterGoodsDao = mockk(relaxUnitFun = true)
    private val sut = GoodsRepository(goodsDao, teamGoodDao, characterGoodsDao)

    @Test
    fun `given DAO returns goods from several packs when getGoods then only goods in the requested packs are kept`() = runTest {
        // Given
        coEvery { goodsDao.getAll(targetLocale = "ru", defaultLocale = "en") } returns listOf(
            withTranslation(goodBd(goodId = 1, pack = "MAIN"), name = "MainItem"),
            withTranslation(goodBd(goodId = 2, pack = "FORGOTTEN_CIRCLES"), name = "FcItem"),
            withTranslation(goodBd(goodId = 3, pack = "MAIN"), name = "MainItem2"),
        )

        // When
        val goods = sut.getGoods(packs = setOf(PackType.MAIN), locale = "ru")

        // Then
        expectThat(goods.map { it.id }).containsExactlyInAnyOrder(1, 3)
        goods.forEach { expectThat(it.pack).isEqualTo(PackType.MAIN) }
    }

    @Test
    fun `given an empty pack set when getGoods then the result is empty`() = runTest {
        // Given
        coEvery { goodsDao.getAll(targetLocale = "ru", defaultLocale = "en") } returns listOf(
            withTranslation(goodBd(goodId = 1, pack = "MAIN"), name = "MainItem"),
        )

        // When
        val goods = sut.getGoods(packs = emptySet(), locale = "ru")

        // Then
        expectThat(goods).hasSize(0)
    }

    @Test
    fun `given DAO emits goods when getGoodsForTeam is collected then mapped goods reach the consumer`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        every { teamGoodDao.getGoodsForTeam(teamId = 5, targetLocale = "ru", defaultLocale = "en") } returns flowOf(
            listOf(withTranslation(goodBd(goodId = 11, pack = "MAIN"), name = "Helm")),
        )

        // When / Then
        sut.getGoodsForTeam(teamId = 5, locale = "ru").test {
            val emitted = awaitItem()
            expectThat(emitted.map { it.id }).isEqualTo(listOf(11))
            awaitComplete()
        }
    }

    @Test
    fun `given DAO returns team good numbers when getTeamGoodsNumbers then the list is passed through`() = runTest {
        // Given
        coEvery { teamGoodDao.getTeamGoodNumbers(5) } returns listOf(1, 2, 3)

        // When
        val numbers = sut.getTeamGoodsNumbers(5)

        // Then
        expectThat(numbers).isEqualTo(listOf(1, 2, 3))
    }

    @Test
    fun `given DAO Flow emits a list when getCharacterGoodNumbers then the first emission is returned`() = runTest {
        // Given
        every { characterGoodsDao.getCharactersGoodNumbers(7) } returns flowOf(listOf(10, 11))

        // When
        val numbers = sut.getCharacterGoodNumbers(7)

        // Then
        expectThat(numbers).isEqualTo(listOf(10, 11))
    }

    @Test
    fun `given DAO Flow when getCharacterGoodIds then the underlying Flow is passed through`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        every { characterGoodsDao.getCharactersGoodIds(listOf(7, 8)) } returns flowOf(listOf(100, 101))

        // When / Then
        sut.getCharacterGoodIds(listOf(7, 8)).test {
            expectThat(awaitItem()).isEqualTo(listOf(100, 101))
            awaitComplete()
        }
    }

    @Test
    fun `given DAO returns goods with duplicate displayNumbers when getGoodIdsByNumbers then ids are grouped by displayNumber`() = runTest {
        // Given
        coEvery { goodsDao.getGoodsByNumbers(listOf(1, 2)) } returns listOf(
            goodBd(goodId = 100, displayNumber = 1),
            goodBd(goodId = 101, displayNumber = 1),
            goodBd(goodId = 200, displayNumber = 2),
        )

        // When
        val result = sut.getGoodIdsByNumbers(listOf(1, 2))

        // Then
        expectThat(result[1]).isEqualTo(listOf(100, 101))
        expectThat(result[2]).isEqualTo(listOf(200))
    }

    @Test
    fun `given an empty input list when getGoodIdsByNumbers then the resulting map is empty`() = runTest {
        // Given
        coEvery { goodsDao.getGoodsByNumbers(emptyList()) } returns emptyList()

        // When
        val result = sut.getGoodIdsByNumbers(emptyList())

        // Then
        expectThat(result.isEmpty()).isEqualTo(true)
    }

    @Test
    fun `given a teamId and goodId when delete is called then the team good DAO receives the same pair`() = runTest {
        // When
        sut.delete(teamId = 5, goodId = 100)

        // Then
        coVerify(exactly = 1) { teamGoodDao.delete(5, 100) }
    }

    @Test
    fun `given goodIds when addGoodsToTeam is called then the DAO receives TeamGoodBd entities for the team`() = runTest {
        // When
        sut.addGoodsToTeam(teamId = 5, goodIds = listOf(1, 2))

        // Then
        coVerify(exactly = 1) {
            teamGoodDao.insertAll(
                listOf(
                    TeamGoodBd(teamId = 5, goodId = 1),
                    TeamGoodBd(teamId = 5, goodId = 2),
                ),
            )
        }
    }

    @Test
    fun `given a teamId and goodId when removeGoodFromTeam is called then the team good DAO receives that pair`() = runTest {
        // When
        sut.removeGoodFromTeam(teamId = 5, goodId = 50)

        // Then
        coVerify(exactly = 1) { teamGoodDao.delete(5, 50) }
    }

    @Test
    fun `given goodIds when addCharacterGoods is called then the DAO receives CharacterGoodBd entities for the character`() = runTest {
        // When
        sut.addCharacterGoods(characterId = 7, goodIds = listOf(1, 2))

        // Then
        coVerify(exactly = 1) {
            characterGoodsDao.insertAll(
                listOf(
                    CharacterGoodBd(characterId = 7, goodId = 1),
                    CharacterGoodBd(characterId = 7, goodId = 2),
                ),
            )
        }
    }

    @Test
    fun `given a goodId and characterId when deleteCharacterGood is called then the DAO receives the same pair`() = runTest {
        // When
        sut.deleteCharacterGood(goodId = 10, characterId = 7)

        // Then
        coVerify(exactly = 1) { characterGoodsDao.delete(characterId = 7, goodId = 10) }
    }

    @Test
    fun `given DAO emits character goods when getCharacterGoods is collected then mapped goods reach the consumer`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        every { characterGoodsDao.getCharacterGoodsFlow(characterId = 7, targetLocale = "ru", defaultLocale = "en") } returns flowOf(
            listOf(withTranslation(goodBd(goodId = 50, pack = "MAIN"), name = "Boots")),
        )

        // When / Then
        sut.getCharacterGoods(characterId = 7, locale = "ru").test {
            val emitted = awaitItem()
            expectThat(emitted.map { it.id }).isEqualTo(listOf(50))
            awaitComplete()
        }
    }

    companion object {
        fun goodBd(
            goodId: Int = 1,
            displayNumber: Int = 1,
            type: String = "Body",
            cost: Int = 10,
            image: String = "img.png",
            pack: String = "MAIN",
        ): GoodBd = GoodBd(
            goodId = goodId,
            displayNumber = displayNumber,
            type = type,
            cost = cost,
            image = image,
            pack = pack,
        )

        fun withTranslation(good: GoodBd, name: String = "Translated"): GoodWithTranslation =
            GoodWithTranslation(good = good, name = name)
    }
}
