package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters

import app.cash.turbine.test
import com.rumpilstilstkin.gloomhavenhelper.data.CharacterRepository
import com.rumpilstilstkin.gloomhavenhelper.data.LocaleRepository
import com.rumpilstilstkin.gloomhavenhelper.data.QuestsRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterClassType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterInfo
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.DifficultyLevel
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.PackType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Team
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.quest.CharacterPersonalQuest
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.quest.QuestReward
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNull
import strikt.assertions.isTrue

@OptIn(ExperimentalCoroutinesApi::class)
class GetCharacterDetailsInfoUseCaseTest {
    private val characterRepository: CharacterRepository = mockk()
    private val questsRepository: QuestsRepository = mockk()
    private val localeRepository: LocaleRepository = mockk()

    @Test
    fun `given existing character when invoked then emits CharacterFullInfo with computed nextLevel and donate flag`() = runTest(UnconfinedTestDispatcher()) {
        // Given — level 2 → getNextLevel = 95; goldCount 12 → donate available
        val info = character(level = 2, goldCount = 12)
        val quest = quest()
        every { localeRepository.observeLocaleUnic } returns flowOf("en")
        every { characterRepository.getCharacterByIdFlow(5) } returns flowOf(info)
        every { questsRepository.getCharacterPersonalQuestFlow(5, "en") } returns flowOf(quest)
        val sut = GetCharacterDetailsInfoUseCase(characterRepository, questsRepository, localeRepository)

        // When / Then
        sut(5).test {
            val item = awaitItem()!!
            expectThat(item.generalInfo).isEqualTo(info)
            expectThat(item.nextLevelExperience).isEqualTo(95)
            expectThat(item.isDonateAvailable).isTrue()
            expectThat(item.personalQuest).isEqualTo(quest)
            awaitComplete()
        }
    }

    @Test
    fun `given missing character when invoked then emits null`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        every { localeRepository.observeLocaleUnic } returns flowOf("en")
        every { characterRepository.getCharacterByIdFlow(5) } returns flowOf(null)
        every { questsRepository.getCharacterPersonalQuestFlow(5, "en") } returns flowOf(null)
        val sut = GetCharacterDetailsInfoUseCase(characterRepository, questsRepository, localeRepository)

        // When / Then
        sut(5).test {
            expectThat(awaitItem()).isNull()
            awaitComplete()
        }
    }

    private fun character(
        level: Int,
        goldCount: Int,
    ) = CharacterInfo(
        name = "Brute",
        level = level,
        characterType = CharacterClassType.Brute,
        isAlive = true,
        id = 5,
        team = Team(teamId = 1, name = "T", packs = listOf(PackType.MAIN), difficultyLevel = DifficultyLevel.NORMAL),
        experience = 50,
        goldCount = goldCount,
        checkMarkCount = 0,
        notes = "",
        additionalContOfPerks = 0,
    )

    private fun quest() =
        CharacterPersonalQuest(
            questId = "q1",
            title = "T",
            descriptions = "D",
            reward = QuestReward(classType = null, alternativeReward = ""),
            tasks = emptyList(),
        )
}
