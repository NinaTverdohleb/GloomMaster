package com.rumpilstilstkin.gloommaster.data

import app.cash.turbine.test
import com.rumpilstilstkin.gloommaster.bd.dao.AchievementDao
import com.rumpilstilstkin.gloommaster.bd.entity.AchievementBd
import com.rumpilstilstkin.gloommaster.bd.entity.AchievementTranslateBd
import com.rumpilstilstkin.gloommaster.domain.entity.PackType
import com.rumpilstilstkin.gloommaster.data.AchievementRepository
import com.rumpilstilstkin.gloommaster.data.LocaleRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.hasSize
import strikt.assertions.isEmpty
import strikt.assertions.isEqualTo

@OptIn(ExperimentalCoroutinesApi::class)
class AchievementRepositoryTest {
    private val dao: AchievementDao = mockk()
    private val localeRepository: LocaleRepository = mockk()

    private fun newSut(externalScope: CoroutineScope): AchievementRepository =
        AchievementRepository(
            achievementDao = dao,
            localeRepository = localeRepository,
            externalScope = externalScope,
        )

    @Test
    fun `given DAO returns translations when dictionary is collected then the first emission is the loaded snapshot`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        every { localeRepository.observeLocale } returns flowOf("ru")
        coEvery {
            dao.getAllTranslations(targetLocale = "ru", defaultLocale = "en")
        } returns listOf(
            AchievementTranslateBd(slug = "ach_1", locale = "ru", name = "Достижение 1"),
            AchievementTranslateBd(slug = "ach_2", locale = "ru", name = "Достижение 2"),
        )
        val sut = newSut(TestScope(UnconfinedTestDispatcher(testScheduler)))

        // When / Then
        sut.dictionary.test {
            val emitted = awaitItem()
            expectThat(emitted["ach_1"]).isEqualTo("Достижение 1")
            expectThat(emitted["ach_2"]).isEqualTo("Достижение 2")
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `given DAO returns translations when currentDictionary is awaited then it returns the loaded map`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        every { localeRepository.observeLocale } returns flowOf("ru")
        coEvery {
            dao.getAllTranslations(targetLocale = "ru", defaultLocale = "en")
        } returns listOf(
            AchievementTranslateBd(slug = "ach_1", locale = "ru", name = "Достижение 1"),
        )
        val sut = newSut(TestScope(UnconfinedTestDispatcher(testScheduler)))

        // When
        val dictionary = sut.currentDictionary()

        // Then
        expectThat(dictionary["ach_1"]).isEqualTo("Достижение 1")
    }

    @Test
    fun `given DAO returns an empty list when currentDictionary is awaited then it returns an empty map without hanging`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        every { localeRepository.observeLocale } returns flowOf("ru")
        coEvery {
            dao.getAllTranslations(targetLocale = "ru", defaultLocale = "en")
        } returns emptyList()
        val sut = newSut(TestScope(UnconfinedTestDispatcher(testScheduler)))

        // When
        val dictionary = sut.currentDictionary()

        // Then
        expectThat(dictionary).isEmpty()
    }

    @Test
    fun `given the locale flow emits ru then en when dictionary is collected then DAO is invoked twice`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val localeFlow = MutableSharedFlow<String>(replay = 1)
        every { localeRepository.observeLocale } returns localeFlow
        coEvery {
            dao.getAllTranslations(targetLocale = "ru", defaultLocale = "en")
        } returns listOf(AchievementTranslateBd(slug = "ach", locale = "ru", name = "Достижение"))
        coEvery {
            dao.getAllTranslations(targetLocale = "en", defaultLocale = "en")
        } returns listOf(AchievementTranslateBd(slug = "ach", locale = "en", name = "Achievement"))
        val sut = newSut(TestScope(UnconfinedTestDispatcher(testScheduler)))

        // When / Then
        sut.dictionary.test {
            localeFlow.emit("ru")
            expectThat(awaitItem()["ach"]).isEqualTo("Достижение")
            localeFlow.emit("en")
            expectThat(awaitItem()["ach"]).isEqualTo("Achievement")
            cancelAndIgnoreRemainingEvents()
        }
        coVerify(exactly = 1) { dao.getAllTranslations(targetLocale = "ru", defaultLocale = "en") }
        coVerify(exactly = 1) { dao.getAllTranslations(targetLocale = "en", defaultLocale = "en") }
    }

    @Test
    fun `given DAO returns global achievements when getGlobalAchievementsByPacks then maxRang is mapped to maxValue and value is one`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        every { localeRepository.observeLocale } returns flowOf("ru")
        coEvery { dao.getGlobalAchievementsByPacks(listOf(PackType.MAIN.name)) } returns listOf(
            AchievementBd(slug = "g1", pack = PackType.MAIN.name, maxRang = 3, isGlobal = true),
            AchievementBd(slug = "g2", pack = PackType.MAIN.name, maxRang = 1, isGlobal = true),
        )
        val sut = newSut(TestScope(UnconfinedTestDispatcher(testScheduler)))

        // When
        val achievements = sut.getGlobalAchievementsByPacks(listOf(PackType.MAIN.name))

        // Then
        expectThat(achievements).hasSize(2)
        expectThat(achievements[0].slug).isEqualTo("g1")
        expectThat(achievements[0].maxValue).isEqualTo(3)
        expectThat(achievements[0].value).isEqualTo(1)
        expectThat(achievements[0].isGlobal).isEqualTo(true)
    }

    @Test
    fun `given DAO returns team achievements when getTeamAchievementsByPacks then maxRang is mapped to maxValue and isGlobal is false`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        every { localeRepository.observeLocale } returns flowOf("ru")
        coEvery { dao.getTeamAchievementsByPacks(listOf(PackType.MAIN.name)) } returns listOf(
            AchievementBd(slug = "t1", pack = PackType.MAIN.name, maxRang = 2, isGlobal = false),
        )
        val sut = newSut(TestScope(UnconfinedTestDispatcher(testScheduler)))

        // When
        val achievements = sut.getTeamAchievementsByPacks(listOf(PackType.MAIN.name))

        // Then
        expectThat(achievements).hasSize(1)
        expectThat(achievements[0].slug).isEqualTo("t1")
        expectThat(achievements[0].maxValue).isEqualTo(2)
        expectThat(achievements[0].isGlobal).isEqualTo(false)
    }

    @Test
    fun `given DAO returns translations by slugs when getAchievementsNameBySlugs then result is a slug to name map`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        every { localeRepository.observeLocale } returns flowOf("ru")
        coEvery {
            dao.getTeamAchievementsBySlugs(
                slugs = listOf("ach_1", "ach_2"),
                targetLocale = "ru",
                defaultLocale = "en",
            )
        } returns listOf(
            AchievementTranslateBd(slug = "ach_1", locale = "ru", name = "Достижение 1"),
            AchievementTranslateBd(slug = "ach_2", locale = "ru", name = "Достижение 2"),
        )
        val sut = newSut(TestScope(UnconfinedTestDispatcher(testScheduler)))

        // When
        val map = sut.getAchievementsNameBySlugs(slugs = listOf("ach_1", "ach_2"), locale = "ru")

        // Then
        expectThat(map["ach_1"]).isEqualTo("Достижение 1")
        expectThat(map["ach_2"]).isEqualTo("Достижение 2")
    }
}
