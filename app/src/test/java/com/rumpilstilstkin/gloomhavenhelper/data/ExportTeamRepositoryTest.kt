package com.rumpilstilstkin.gloomhavenhelper.data

import android.content.res.Resources
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterClassType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterInfo
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.DifficultyLevel
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.PackType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ShortTeamInfo
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Team
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.export.TeamExportDto
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.quest.CharacterPersonalQuestShort
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.quest.CharacterTaskItem
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.hasSize
import strikt.assertions.isEqualTo
import strikt.assertions.isNull
import strikt.assertions.isTrue

@OptIn(ExperimentalCoroutinesApi::class)
class ExportTeamRepositoryTest {
    private val characterRepository: CharacterRepository = mockk()
    private val teamRepository: TeamRepository = mockk()
    private val scenarioRepository: ScenarioRepository = mockk()
    private val goodsRepository: GoodsRepository = mockk()
    private val characterClassRepository: CharacterClassRepository = mockk()
    private val questsRepository: QuestsRepository = mockk()
    private val sut = ExportTeamRepository(
        characterRepository = characterRepository,
        teamRepository = teamRepository,
        scenarioRepository = scenarioRepository,
        goodsRepository = goodsRepository,
        characterClassRepository = characterClassRepository,
        questsRepository = questsRepository,
    )

    @Test
    fun `given two characters with quests, goods, and perks when getExportTeamData then the JSON round-trips into a matching DTO`() = runTest {
        // Given
        val team = shortTeamInfoFixture(teamId = 5)
        val c1 = characterInfoFixture(id = 1, name = "Brute", team = team.toTeam())
        val c2 = characterInfoFixture(id = 2, name = "Sun", team = team.toTeam(), type = CharacterClassType.Sunkeeper)
        coEvery { teamRepository.getTeamInfo(5) } returns team
        coEvery { goodsRepository.getTeamGoodsNumbers(5) } returns listOf(10, 11)
        coEvery { characterClassRepository.getAvailableClassesForTeamSync(5) } returns listOf("Brute", "Sunkeeper")
        coEvery { scenarioRepository.getAllTeamScenarios(5) } returns listOf(
            com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioShortInfo(
                scenarioNumber = 1,
                scenarioRequirements = com.rumpilstilstkin.gloomhavenhelper.domain.entity.LogicalCondition(""),
                isCompleted = true,
                pack = PackType.MAIN,
                monsters = emptyList(),
            ),
            com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioShortInfo(
                scenarioNumber = 2,
                scenarioRequirements = com.rumpilstilstkin.gloomhavenhelper.domain.entity.LogicalCondition(""),
                isCompleted = false,
                pack = PackType.MAIN,
                monsters = emptyList(),
            ),
        )
        coEvery { characterRepository.getCharacterByTeamIdSync(5) } returns listOf(c1, c2)

        coEvery { questsRepository.getCharacterQuestById(1) } returns CharacterPersonalQuestShort(
            questId = "Q1",
            tasks = listOf(CharacterTaskItem.Check(id = 1, priority = 1, text = "task A")),
        )
        coEvery { questsRepository.getCharacterQuestById(2) } returns null

        coEvery { goodsRepository.getCharacterGoodNumbers(1) } returns listOf(50, 51)
        coEvery { goodsRepository.getCharacterGoodNumbers(2) } returns emptyList()

        coEvery { characterRepository.getCharacterPerks(1) } returns listOf(100, 101)
        coEvery { characterRepository.getCharacterPerks(2) } returns listOf(200)

        // When
        val result = sut.getExportTeamData(5)

        // Then
        expectThat(result.isSuccess).isTrue()
        val dto = Json.decodeFromString<TeamExportDto>(result.getOrNull()!!)
        expectThat(dto.team.teamId).isEqualTo(5)
        expectThat(dto.teamGoods).isEqualTo(listOf(10, 11))
        expectThat(dto.unlockedClasses).isEqualTo(listOf("Brute", "Sunkeeper"))
        expectThat(dto.teamScenarios.map { it.scenarioNumber to it.completed }).isEqualTo(
            listOf(1 to true, 2 to false),
        )
        expectThat(dto.characters).hasSize(2)
        expectThat(dto.characters[0].generalInfo.id).isEqualTo(1)
        expectThat(dto.characters[0].personalQuest?.questId).isEqualTo("Q1")
        expectThat(dto.characters[0].goodDisplayNumbers).isEqualTo(listOf(50, 51))
        expectThat(dto.characters[0].perks).isEqualTo(listOf(100, 101))
        expectThat(dto.characters[1].personalQuest).isNull()
        expectThat(dto.characters[1].goodDisplayNumbers).isEqualTo(emptyList())
        expectThat(dto.characters[1].perks).isEqualTo(listOf(200))
    }

    @Test
    fun `given the team has no characters when getExportTeamData then the DTO carries an empty character list and per-character DAOs are not hit`() = runTest {
        // Given
        val team = shortTeamInfoFixture(teamId = 5)
        coEvery { teamRepository.getTeamInfo(5) } returns team
        coEvery { goodsRepository.getTeamGoodsNumbers(5) } returns emptyList()
        coEvery { characterClassRepository.getAvailableClassesForTeamSync(5) } returns emptyList()
        coEvery { scenarioRepository.getAllTeamScenarios(5) } returns emptyList()
        coEvery { characterRepository.getCharacterByTeamIdSync(5) } returns emptyList()

        // When
        val result = sut.getExportTeamData(5)

        // Then
        expectThat(result.isSuccess).isTrue()
        val dto = Json.decodeFromString<TeamExportDto>(result.getOrNull()!!)
        expectThat(dto.characters).hasSize(0)
        coVerify(exactly = 0) { characterRepository.getCharacterPerks(any()) }
        coVerify(exactly = 0) { questsRepository.getCharacterQuestById(any()) }
        coVerify(exactly = 0) { goodsRepository.getCharacterGoodNumbers(any()) }
    }

    @Test
    fun `given teamRepository returns null when getExportTeamData then Result is failure with Resources NotFoundException`() = runTest {
        // Given
        coEvery { teamRepository.getTeamInfo(5) } returns null

        // When
        val result = sut.getExportTeamData(5)

        // Then
        expectThat(result.isFailure).isTrue()
        expectThat(result.exceptionOrNull() is Resources.NotFoundException).isTrue()
    }

    companion object {
        fun shortTeamInfoFixture(teamId: Int = 1): ShortTeamInfo = ShortTeamInfo(
            teamId = teamId,
            name = "Squad",
            achievements = emptyList(),
            aliveCharacterIds = emptyList(),
            reputation = 0,
            prosperity = 0,
            packs = listOf(PackType.MAIN),
            churchValue = 100,
            difficultyLevel = DifficultyLevel.NORMAL,
            countRetiredCharacters = 0,
        )

        fun ShortTeamInfo.toTeam(): Team = Team(
            teamId = teamId,
            name = name,
            packs = packs,
            difficultyLevel = difficultyLevel,
        )

        fun characterInfoFixture(
            id: Int = 1,
            name: String = "Brute",
            team: Team? = null,
            type: CharacterClassType = CharacterClassType.Brute,
        ): CharacterInfo = CharacterInfo(
            name = name,
            level = 1,
            characterType = type,
            isAlive = true,
            id = id,
            team = team,
            experience = 0,
            goldCount = 30,
            checkMarkCount = 0,
            notes = "",
            additionalContOfPerks = 0,
        )
    }
}
