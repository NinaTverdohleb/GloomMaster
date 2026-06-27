package com.rumpilstilstkin.gloomhavenhelper.data

import com.rumpilstilstkin.gloomhavenhelper.bd.dao.MonsterDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.ScenarioDao
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.MonsterAbilityCardBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.MonsterAbilityCardTranslationBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.MonsterBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.MonsterStatsBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.MonsterTextStatsBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.MonsterWithNameBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.ScenarioBd
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterAction
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterCardAction
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterName
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterStatType
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.contains
import strikt.assertions.hasSize
import strikt.assertions.isEmpty
import strikt.assertions.isEqualTo

@OptIn(ExperimentalCoroutinesApi::class)
class MonsterRepositoryTest {
    private val monsterDao: MonsterDao = mockk()
    private val scenarioDao: ScenarioDao = mockk()
    private val sut = MonsterRepository(monsterDao, scenarioDao)

    @Test
    fun `given a scenario with monsters when getMonsterSlugsForScenario then the slug list is passed through`() = runTest {
        // Given
        coEvery { scenarioDao.getScenario(5) } returns ScenarioBd(
            scenarioNumber = 5,
            monsters = listOf("bandit-guard", "city-archer"),
            pack = "MAIN",
        )

        // When
        val slugs = sut.getMonsterSlugsForScenario(5)

        // Then
        expectThat(slugs).isEqualTo(listOf("bandit-guard", "city-archer"))
    }

    @Test
    fun `given numeric and text stats are both present when getMonsterStats then their stats lists are concatenated`() = runTest {
        // Given
        val numericAction = MonsterAction.Action(statType = MonsterStatType.MOVE, modifier = "2")
        val textAction = MonsterAction.Text(content = "extra text")
        coEvery {
            monsterDao.getStats(monsterSlug = "bandit-guard", level = 1, isElite = false)
        } returns MonsterStatsBd(
            monsterSlug = "bandit-guard",
            scenarioLevel = 1,
            isElite = false,
            life = 7,
            stats = listOf(numericAction),
        )
        coEvery {
            monsterDao.getTextStats(
                monster = "bandit-guard",
                level = 1,
                isElite = false,
                targetLocale = "ru",
                defaultLocale = "en",
            )
        } returns MonsterTextStatsBd(
            locale = "ru",
            monsterSlug = "bandit-guard",
            scenarioLevel = 1,
            isElite = false,
            stats = listOf(textAction),
        )

        // When
        val stats = sut.getMonsterStats(
            monsterSlug = "bandit-guard",
            level = 1,
            isElite = false,
            locale = "ru",
        )

        // Then
        expectThat(stats.life).isEqualTo(7)
        expectThat(stats.stats).isEqualTo(listOf(numericAction, textAction))
    }

    @Test
    fun `given text stats are null when getMonsterStats then only the numeric stats are returned`() = runTest {
        // Given
        val numericAction = MonsterAction.Action(statType = MonsterStatType.MOVE, modifier = "2")
        coEvery {
            monsterDao.getStats(monsterSlug = "bandit-guard", level = 1, isElite = false)
        } returns MonsterStatsBd(
            monsterSlug = "bandit-guard",
            scenarioLevel = 1,
            isElite = false,
            life = 7,
            stats = listOf(numericAction),
        )
        coEvery {
            monsterDao.getTextStats(
                monster = "bandit-guard",
                level = 1,
                isElite = false,
                targetLocale = "ru",
                defaultLocale = "en",
            )
        } returns null

        // When
        val stats = sut.getMonsterStats(
            monsterSlug = "bandit-guard",
            level = 1,
            isElite = false,
            locale = "ru",
        )

        // Then
        expectThat(stats.stats).isEqualTo(listOf(numericAction))
    }

    @Test
    fun `given DAO returns monsters by packs when getMonstersForPacks then result is a slug to name map`() = runTest {
        // Given
        coEvery {
            monsterDao.getMonstersByPacks(packs = listOf("MAIN"), targetLocale = "ru", defaultLocale = "en")
        } returns listOf(
            monsterWithName(slug = "bandit-guard", name = "Bandit Guard"),
            monsterWithName(slug = "city-archer", name = "City Archer"),
        )

        // When
        val result = sut.getMonstersForPacks(packs = listOf("MAIN"), locale = "ru")

        val expected = listOf(
            MonsterName(
                slug = "bandit-guard",
                name = "Bandit Guard"
            ),
            MonsterName(
                slug = "city-archer",
                name = "City Archer"
            )
        )
        // Then
        expectThat(result).isEqualTo(expected)
    }

    @Test
    fun `given DAO returns ability cards for slugs when getMonsterCards then each card is mapped into AvailableCard`() = runTest {
        // Given
        coEvery { monsterDao.getCardsByMonsterSlugs(listOf("bandit-guard")) } returns listOf(
            MonsterAbilityCardBd(deckName = "guard", cardId = 1, initiative = 10),
            MonsterAbilityCardBd(deckName = "guard", cardId = 2, initiative = 20),
        )

        // When
        val cards = sut.getMonsterCards(listOf("bandit-guard"))

        // Then
        expectThat(cards.map { it.deck to it.cardId }).isEqualTo(
            listOf("guard" to 1, "guard" to 2),
        )
    }

    @Test
    fun `given a full fixture when getMonstersBySlugs then monsters are returned with regular and elite stats plus mapped cards`() = runTest {
        // Given
        coEvery {
            monsterDao.getMonstersBySlugs(
                slugs = listOf("bandit-guard"),
                targetLocale = "ru",
                defaultLocale = "en",
            )
        } returns listOf(monsterWithName(slug = "bandit-guard", deckName = "guard", name = "Bandit Guard"))
        coEvery {
            monsterDao.getStatsForMonsters(listOf("bandit-guard"), level = 1)
        } returns listOf(
            MonsterStatsBd(
                monsterSlug = "bandit-guard",
                scenarioLevel = 1,
                isElite = false,
                life = 7,
                stats = listOf(MonsterAction.Action(MonsterStatType.MOVE, "2")),
            ),
            MonsterStatsBd(
                monsterSlug = "bandit-guard",
                scenarioLevel = 1,
                isElite = true,
                life = 11,
                stats = listOf(MonsterAction.Action(MonsterStatType.MOVE, "3")),
            ),
        )
        coEvery {
            monsterDao.getTextStatsForMonsters(listOf("bandit-guard"), 1, "ru", "en")
        } returns emptyList()
        coEvery { monsterDao.getCardsByDeckNames(listOf("guard")) } returns listOf(
            MonsterAbilityCardBd(deckName = "guard", cardId = 1, initiative = 10),
        )
        coEvery { monsterDao.getActionCardsByDecks(listOf("guard"), "ru", "en") } returns listOf(
            MonsterAbilityCardTranslationBd(
                deckName = "guard",
                locale = "ru",
                cardId = 1,
                actions = listOf(MonsterCardAction(text = "Move 2")),
            ),
        )

        // When
        val monsters = sut.getMonstersBySlugs(slugs = listOf("bandit-guard"), level = 1, locale = "ru")

        // Then
        expectThat(monsters).hasSize(1)
        val m = monsters[0]
        expectThat(m.slug).isEqualTo("bandit-guard")
        expectThat(m.life).isEqualTo(7)
        expectThat(m.eliteLife).isEqualTo(11)
        expectThat(m.cards).hasSize(1)
        expectThat(m.cards[0].actions.map { it.text }).contains("Move 2")
    }

    @Test
    fun `given an empty slug list when getMonstersBySlugs then no DAO is hit and the result is empty`() = runTest {
        // When
        val monsters = sut.getMonstersBySlugs(slugs = emptyList(), level = 1, locale = "ru")

        // Then
        expectThat(monsters).isEmpty()
        coVerify(exactly = 0) { monsterDao.getMonstersBySlugs(any(), any(), any()) }
        coVerify(exactly = 0) { monsterDao.getStatsForMonsters(any(), any()) }
        coVerify(exactly = 0) { monsterDao.getTextStatsForMonsters(any(), any(), any(), any()) }
    }

    @Test
    fun `given duplicate slugs in input when getMonstersBySlugs then DAO is called with distinct slugs`() = runTest {
        // Given
        coEvery {
            monsterDao.getMonstersBySlugs(
                slugs = listOf("bandit-guard"),
                targetLocale = "ru",
                defaultLocale = "en",
            )
        } returns listOf(monsterWithName(slug = "bandit-guard", deckName = "guard", name = "Bandit Guard"))
        coEvery { monsterDao.getStatsForMonsters(listOf("bandit-guard"), 1) } returns emptyList()
        coEvery { monsterDao.getTextStatsForMonsters(listOf("bandit-guard"), 1, "ru", "en") } returns emptyList()
        coEvery { monsterDao.getCardsByDeckNames(listOf("guard")) } returns emptyList()
        coEvery { monsterDao.getActionCardsByDecks(listOf("guard"), "ru", "en") } returns emptyList()

        // When
        sut.getMonstersBySlugs(
            slugs = listOf("bandit-guard", "bandit-guard", "bandit-guard"),
            level = 1,
            locale = "ru",
        )

        // Then
        coVerify(exactly = 1) {
            monsterDao.getMonstersBySlugs(
                slugs = listOf("bandit-guard"),
                targetLocale = "ru",
                defaultLocale = "en",
            )
        }
    }

    @Test
    fun `given elite stats are missing when getMonstersBySlugs then eliteLife is zero and eliteStats are empty`() = runTest {
        // Given
        coEvery {
            monsterDao.getMonstersBySlugs(
                slugs = listOf("bandit-guard"),
                targetLocale = "ru",
                defaultLocale = "en",
            )
        } returns listOf(monsterWithName(slug = "bandit-guard", deckName = "guard"))
        coEvery { monsterDao.getStatsForMonsters(listOf("bandit-guard"), 1) } returns listOf(
            MonsterStatsBd(
                monsterSlug = "bandit-guard",
                scenarioLevel = 1,
                isElite = false,
                life = 7,
                stats = emptyList(),
            ),
        )
        coEvery { monsterDao.getTextStatsForMonsters(listOf("bandit-guard"), 1, "ru", "en") } returns emptyList()
        coEvery { monsterDao.getCardsByDeckNames(listOf("guard")) } returns emptyList()
        coEvery { monsterDao.getActionCardsByDecks(listOf("guard"), "ru", "en") } returns emptyList()

        // When
        val monsters = sut.getMonstersBySlugs(slugs = listOf("bandit-guard"), level = 1, locale = "ru")

        // Then
        expectThat(monsters[0].eliteLife).isEqualTo(0)
        expectThat(monsters[0].eliteStats).isEmpty()
    }

    @Test
    fun `given no cards in the deck when getMonstersBySlugs then cards is an empty list`() = runTest {
        // Given
        coEvery {
            monsterDao.getMonstersBySlugs(
                slugs = listOf("bandit-guard"),
                targetLocale = "ru",
                defaultLocale = "en",
            )
        } returns listOf(monsterWithName(slug = "bandit-guard", deckName = "guard"))
        coEvery { monsterDao.getStatsForMonsters(listOf("bandit-guard"), 1) } returns emptyList()
        coEvery { monsterDao.getTextStatsForMonsters(listOf("bandit-guard"), 1, "ru", "en") } returns emptyList()
        coEvery { monsterDao.getCardsByDeckNames(listOf("guard")) } returns emptyList()
        coEvery { monsterDao.getActionCardsByDecks(listOf("guard"), "ru", "en") } returns emptyList()

        // When
        val monsters = sut.getMonstersBySlugs(slugs = listOf("bandit-guard"), level = 1, locale = "ru")

        // Then
        expectThat(monsters[0].cards).isEmpty()
    }

    @Test
    fun `given a card has no matching action translation when getMonstersBySlugs then that card has empty actions`() = runTest {
        // Given
        coEvery {
            monsterDao.getMonstersBySlugs(
                slugs = listOf("bandit-guard"),
                targetLocale = "ru",
                defaultLocale = "en",
            )
        } returns listOf(monsterWithName(slug = "bandit-guard", deckName = "guard"))
        coEvery { monsterDao.getStatsForMonsters(listOf("bandit-guard"), 1) } returns emptyList()
        coEvery { monsterDao.getTextStatsForMonsters(listOf("bandit-guard"), 1, "ru", "en") } returns emptyList()
        coEvery { monsterDao.getCardsByDeckNames(listOf("guard")) } returns listOf(
            MonsterAbilityCardBd(deckName = "guard", cardId = 99, initiative = 50),
        )
        coEvery { monsterDao.getActionCardsByDecks(listOf("guard"), "ru", "en") } returns emptyList()

        // When
        val monsters = sut.getMonstersBySlugs(slugs = listOf("bandit-guard"), level = 1, locale = "ru")

        // Then
        expectThat(monsters[0].cards).hasSize(1)
        expectThat(monsters[0].cards[0].actions).isEmpty()
    }

    companion object {
        fun monsterWithName(
            slug: String,
            deckName: String = "deck",
            name: String = "Monster",
            isBoss: Boolean = false,
            pack: String = "MAIN",
        ): MonsterWithNameBd = MonsterWithNameBd(
            monster = MonsterBd(
                slug = slug,
                deckName = deckName,
                isBoss = isBoss,
                pack = pack,
            ),
            name = name,
        )
    }
}
