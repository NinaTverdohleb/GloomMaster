package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.play

import com.rumpilstilstkin.gloomhavenhelper.data.MonsterRepository
import com.rumpilstilstkin.gloomhavenhelper.data.ScenarioGameStateRepository
import com.rumpilstilstkin.gloomhavenhelper.data.ScenarioRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.AvailableCard
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterClassType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterInfo
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.DifficultyLevel
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.LevelInfo
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.LogicalCondition
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.PackType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Prosperity
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioGameState
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioGameStateMagic
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioInfoWithName
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.TeamInfo
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.Monster
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.containsKey
import strikt.assertions.isEmpty
import strikt.assertions.isEqualTo

@OptIn(ExperimentalCoroutinesApi::class)
class RestoreScenarioStateUseCaseTest {
    private val scenarioGameStateRepository: ScenarioGameStateRepository = mockk()
    private val monsterRepository: MonsterRepository = mockk()
    private val scenarioRepository: ScenarioRepository = mockk()

    @Test
    fun `given persisted state with scenario when invoked then state is restored with name and monsters`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val team = teamInfo(level = 3, packs = listOf(PackType.MAIN))
        val levelInfo = LevelInfo(level = 3, monsterLevel = 1, goldCount = 8, trapDamage = 2, experience = 4)
        val gameState =
            ScenarioGameState(
                level = 3,
                scenarioNumber = 42,
                monsterSlugs = listOf("bandit"),
                round = 5,
                availableCards = listOf(AvailableCard("banditDeck", 10)),
                activeMonsters = emptyList(),
                magicCharges = listOf(ScenarioGameStateMagic("FIRE", 1)),
            )
        coEvery { scenarioGameStateRepository.get() } returns gameState
        coEvery { scenarioRepository.getScenario(42, "en") } returns
            scenarioInfo(name = "Boss Battle")
        coEvery {
            monsterRepository.getMonstersBySlugs(listOf("bandit"), 3, "en")
        } returns listOf(monster("bandit"))
        val sut = RestoreScenarioStateUseCase(scenarioGameStateRepository, monsterRepository, scenarioRepository)

        // When
        val result = sut(team, levelInfo, "en")

        // Then
        expectThat(result.name).isEqualTo("Boss Battle")
        expectThat(result.round).isEqualTo(5)
        expectThat(result.scenarioNumber).isEqualTo(42)
        expectThat(result.monsters).containsKey("bandit")
        expectThat(result.generalLevel).isEqualTo(3)
        expectThat(result.golds).isEqualTo(8)
        expectThat(result.exp).isEqualTo(4)
        expectThat(result.trapDamage).isEqualTo(2)
        expectThat(result.monsterLevel).isEqualTo(1)
    }

    @Test
    fun `given persisted state without scenario number when invoked then name is blank`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val team = teamInfo(level = 3, packs = listOf(PackType.MAIN))
        val gameState =
            ScenarioGameState(
                level = 3,
                scenarioNumber = null,
                monsterSlugs = emptyList(),
                round = 0,
                availableCards = emptyList(),
                activeMonsters = emptyList(),
                magicCharges = emptyList(),
            )
        coEvery { scenarioGameStateRepository.get() } returns gameState
        coEvery { monsterRepository.getMonstersBySlugs(emptyList(), 3, "en") } returns emptyList()
        val sut = RestoreScenarioStateUseCase(scenarioGameStateRepository, monsterRepository, scenarioRepository)

        // When
        val result = sut(team, null, "en")

        // Then
        expectThat(result.name).isEqualTo("")
        expectThat(result.scenarioNumber).isEqualTo(null)
        expectThat(result.monsters).isEmpty()
    }

    @Test
    fun `given no persisted state when invoked then default state is returned`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val team = teamInfo(level = 3, packs = listOf(PackType.MAIN))
        coEvery { scenarioGameStateRepository.get() } returns null
        val sut = RestoreScenarioStateUseCase(scenarioGameStateRepository, monsterRepository, scenarioRepository)

        // When
        val result = sut(team, null, "en")

        // Then — only team setter ran, so gamersCount derived from aliveCharacters.size = 0
        expectThat(result.monsters).isEmpty()
        expectThat(result.activeMonsters).isEmpty()
        expectThat(result.round).isEqualTo(0)
    }

    private fun teamInfo(
        level: Int,
        packs: List<PackType>,
    ) = TeamInfo(
        id = 7,
        name = "Team",
        level = level,
        teamAchievement = emptyList(),
        globalAchievement = emptyList(),
        reputation = 0,
        activeScenario = emptyList(),
        aliveCharacters =
            listOf(
                CharacterInfo(
                    name = "Brute",
                    level = level,
                    characterType = CharacterClassType.Brute,
                    isAlive = true,
                    id = 1,
                    team = null,
                    experience = 0,
                    goldCount = 0,
                    checkMarkCount = 0,
                    notes = "",
                    additionalContOfPerks = 0,
                ),
            ),
        shopDiscount = 0,
        prosperity = Prosperity.fixture(),
        packs = packs,
        hasActiveScenario = false,
        churchValue = 0,
        difficultyLevel = DifficultyLevel.NORMAL,
    )

    private fun scenarioInfo(name: String) =
        ScenarioInfoWithName(
            scenarioNumber = 42,
            scenarioName = name,
            scenarioRequirements = LogicalCondition(""),
            newScenario = emptyList(),
            location = "Loc",
            pack = PackType.MAIN,
            monsters = emptyList(),
            isCompleted = false,
        )

    private fun monster(slug: String) =
        Monster(
            slug = slug,
            name = slug,
            life = 5,
            stats = emptyList(),
            eliteLife = 7,
            eliteStats = emptyList(),
            deckName = "${slug}Deck",
            cards = emptyList(),
            isBoss = false,
            immunity = emptySet(),
            isFly = false,
            level = 1,
            lifeMultiple = false,
        )
}
