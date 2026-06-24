package com.rumpilstilstkin.gloomhavenhelper.data

import app.cash.turbine.test
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.CharacterPersonalQuestDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.PersonalQuestDao
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.CharacterPersonalQuestBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.CharacterPersonalQuestDetailsBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.PersonalQuestBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.PersonalQuestTaskTranslationsBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.PersonalQuestTranslationsBd
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterClassType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.quest.CharacterPersonalQuestShort
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.quest.CharacterTaskItem
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.hasSize
import strikt.assertions.isEqualTo
import strikt.assertions.isNotNull
import strikt.assertions.isNull

@OptIn(ExperimentalCoroutinesApi::class)
class QuestsRepositoryTest {
    private val personalQuestDao: PersonalQuestDao = mockk()
    private val characterPersonalQuestDao: CharacterPersonalQuestDao = mockk(relaxUnitFun = true)
    private val sut = QuestsRepository(personalQuestDao, characterPersonalQuestDao)

    @Test
    fun `given a quest with two tasks and matching translations when getQuests then translated quest and tasks are returned`() = runTest {
        // Given
        val baseTasks = listOf(
            CharacterTaskItem.Check(id = 1, priority = 1, text = "raw check"),
            CharacterTaskItem.Count(id = 2, priority = 2, text = "raw count", count = 5),
        )
        coEvery { personalQuestDao.getQuests() } returns listOf(
            PersonalQuestBd(questId = "Q1", characterType = "Brute", tasks = baseTasks),
        )
        coEvery {
            personalQuestDao.getQuestTranslation(questId = "Q1", targetLocale = "ru", defaultLocale = "en")
        } returns PersonalQuestTranslationsBd(
            questId = "Q1",
            locale = "ru",
            title = "Title RU",
            description = "Desc RU",
            specialText = "Reward RU",
        )
        coEvery {
            personalQuestDao.getTasksTranslation(questId = "Q1", targetLocale = "ru", defaultLocale = "en")
        } returns listOf(
            PersonalQuestTaskTranslationsBd(questId = "Q1", locale = "ru", text = "translated check", taskId = 1),
            PersonalQuestTaskTranslationsBd(questId = "Q1", locale = "ru", text = "translated count", taskId = 2),
        )

        // When
        val quests = sut.getQuests("ru")

        // Then
        expectThat(quests).hasSize(1)
        val q = quests[0]
        expectThat(q.questNumber).isEqualTo("Q1")
        expectThat(q.title).isEqualTo("Title RU")
        expectThat(q.descriptions).isEqualTo("Desc RU")
        expectThat(q.reward.classType).isEqualTo(CharacterClassType.Brute)
        expectThat(q.reward.alternativeReward).isEqualTo("Reward RU")
        expectThat(q.tasks.map { it.text }).isEqualTo(listOf("translated check", "translated count"))
    }

    @Test
    fun `given a task whose id is missing in translations when getQuests then that task keeps its original text`() = runTest {
        // Given
        coEvery { personalQuestDao.getQuests() } returns listOf(
            PersonalQuestBd(
                questId = "Q2",
                characterType = "Brute",
                tasks = listOf(CharacterTaskItem.Check(id = 1, priority = 1, text = "original")),
            ),
        )
        coEvery {
            personalQuestDao.getQuestTranslation(questId = "Q2", targetLocale = "ru", defaultLocale = "en")
        } returns PersonalQuestTranslationsBd(
            questId = "Q2", locale = "ru", title = "T", description = "D", specialText = "R",
        )
        coEvery {
            personalQuestDao.getTasksTranslation(questId = "Q2", targetLocale = "ru", defaultLocale = "en")
        } returns emptyList()

        // When
        val quests = sut.getQuests("ru")

        // Then
        expectThat(quests[0].tasks[0].text).isEqualTo("original")
    }

    @Test
    fun `given the quest has a null characterType when getQuests then QuestReward classType is null`() = runTest {
        // Given
        coEvery { personalQuestDao.getQuests() } returns listOf(
            PersonalQuestBd(
                questId = "Q3",
                characterType = null,
                tasks = listOf(CharacterTaskItem.Check(id = 1, priority = 1, text = "x")),
            ),
        )
        coEvery {
            personalQuestDao.getQuestTranslation(questId = "Q3", targetLocale = "ru", defaultLocale = "en")
        } returns PersonalQuestTranslationsBd(
            questId = "Q3", locale = "ru", title = "T", description = "D", specialText = "R",
        )
        coEvery {
            personalQuestDao.getTasksTranslation(questId = "Q3", targetLocale = "ru", defaultLocale = "en")
        } returns emptyList()

        // When
        val quests = sut.getQuests("ru")

        // Then
        expectThat(quests[0].reward.classType).isNull()
    }

    @Test
    fun `given DAO emits a personal quest with character tasks when getCharacterPersonalQuestFlow then the character's tasks are used`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val baseTasks = listOf(CharacterTaskItem.Check(id = 1, priority = 1, text = "base text"))
        val personalTasks = listOf(CharacterTaskItem.Check(id = 1, priority = 1, text = "personal text", isChecked = true))
        every { characterPersonalQuestDao.getCharacterPersonalQuestFlow(7) } returns flowOf(
            CharacterPersonalQuestDetailsBd(
                characterPersonalQuest = CharacterPersonalQuestBd(
                    id = 1, characterId = 7, questId = "Q1", tasks = personalTasks,
                ),
                personalTask = PersonalQuestBd(questId = "Q1", characterType = "Brute", tasks = baseTasks),
            ),
        )
        coEvery {
            personalQuestDao.getQuestTranslation(questId = "Q1", targetLocale = "ru", defaultLocale = "en")
        } returns PersonalQuestTranslationsBd(
            questId = "Q1", locale = "ru", title = "T", description = "D", specialText = "R",
        )
        coEvery {
            personalQuestDao.getTasksTranslation(questId = "Q1", targetLocale = "ru", defaultLocale = "en")
        } returns emptyList()

        // When / Then
        sut.getCharacterPersonalQuestFlow(characterId = 7, locale = "ru").test {
            val emitted = awaitItem()
            expectThat(emitted).isNotNull()
            expectThat(emitted!!.tasks).hasSize(1)
            val checkTask = emitted.tasks[0] as CharacterTaskItem.Check
            expectThat(checkTask.text).isEqualTo("personal text")
            expectThat(checkTask.isChecked).isEqualTo(true)
            awaitComplete()
        }
    }

    @Test
    fun `given DAO emits null when getCharacterPersonalQuestFlow is collected then null reaches the consumer and translation DAOs are not hit`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        every { characterPersonalQuestDao.getCharacterPersonalQuestFlow(7) } returns flowOf(null)

        // When / Then
        sut.getCharacterPersonalQuestFlow(characterId = 7, locale = "ru").test {
            expectThat(awaitItem()).isNull()
            awaitComplete()
        }
        coVerify(exactly = 0) {
            personalQuestDao.getQuestTranslation(any(), any(), any())
        }
        coVerify(exactly = 0) {
            personalQuestDao.getTasksTranslation(any(), any(), any())
        }
    }

    @Test
    fun `given a questId when getQuestById then DAO is called and tasks are wrapped into CharacterPersonalQuestShort`() = runTest {
        // Given
        val tasks = listOf(CharacterTaskItem.Check(id = 1, priority = 1, text = "x"))
        coEvery { personalQuestDao.getQuest("Q1") } returns PersonalQuestBd(
            questId = "Q1", characterType = "Brute", tasks = tasks,
        )

        // When
        val short = sut.getQuestById("Q1")

        // Then
        expectThat(short.questId).isEqualTo("Q1")
        expectThat(short.tasks).isEqualTo(tasks)
    }

    @Test
    fun `given DAO has the character quest when getCharacterQuestById then short DTO is returned`() = runTest {
        // Given
        val tasks = listOf(CharacterTaskItem.Count(id = 1, priority = 1, text = "x", count = 3, currentCount = 1))
        coEvery { characterPersonalQuestDao.getCharacterQuestById(7) } returns CharacterPersonalQuestDetailsBd(
            characterPersonalQuest = CharacterPersonalQuestBd(id = 1, characterId = 7, questId = "Q1", tasks = tasks),
            personalTask = PersonalQuestBd(questId = "Q1", characterType = "Brute", tasks = emptyList()),
        )

        // When
        val short = sut.getCharacterQuestById(7)

        // Then
        expectThat(short).isNotNull()
        expectThat(short?.questId).isEqualTo("Q1")
        expectThat(short?.tasks).isEqualTo(tasks)
    }

    @Test
    fun `given DAO has no character quest when getCharacterQuestById then null is returned`() = runTest {
        // Given
        coEvery { characterPersonalQuestDao.getCharacterQuestById(7) } returns null

        // When
        val short = sut.getCharacterQuestById(7)

        // Then
        expectThat(short).isNull()
    }

    @Test
    fun `given a quest and characterId when setQuestForCharacter then DAO insert receives a matching entity`() = runTest {
        // Given
        val tasks = listOf(CharacterTaskItem.Check(id = 1, priority = 1, text = "x"))
        val quest = CharacterPersonalQuestShort(questId = "Q1", tasks = tasks)

        // When
        sut.setQuestForCharacter(quest = quest, characterId = 7)

        // Then
        coVerify(exactly = 1) {
            characterPersonalQuestDao.insert(
                match { it.characterId == 7 && it.questId == "Q1" && it.tasks == tasks },
            )
        }
    }

    @Test
    fun `given a quest and characterId when updateCharacterQuest then DAO deleteByCharacterId runs before insert`() = runTest {
        // Given
        val tasks = listOf(CharacterTaskItem.Check(id = 1, priority = 1, text = "x"))
        val quest = CharacterPersonalQuestShort(questId = "Q1", tasks = tasks)

        // When
        sut.updateCharacterQuest(quest = quest, characterId = 7)

        // Then
        coVerifyOrder {
            characterPersonalQuestDao.deleteByCharacterId(7)
            characterPersonalQuestDao.insert(any())
        }
    }

    @Test
    fun `given a characterId when deleteCharacterQuests then DAO deleteByCharacterId is invoked once`() = runTest {
        // When
        sut.deleteCharacterQuests(7)

        // Then
        coVerify(exactly = 1) { characterPersonalQuestDao.deleteByCharacterId(7) }
    }

    @Test
    fun `given a Count task with matching translation when getQuests then the translated text is copied onto the Count task`() = runTest {
        // Given
        val tasks = listOf(CharacterTaskItem.Count(id = 1, priority = 1, text = "raw", count = 5))
        coEvery { personalQuestDao.getQuests() } returns listOf(
            PersonalQuestBd(questId = "Q4", characterType = "Brute", tasks = tasks),
        )
        coEvery {
            personalQuestDao.getQuestTranslation(questId = "Q4", targetLocale = "ru", defaultLocale = "en")
        } returns PersonalQuestTranslationsBd(questId = "Q4", locale = "ru", title = "T", description = "D", specialText = "R")
        coEvery {
            personalQuestDao.getTasksTranslation(questId = "Q4", targetLocale = "ru", defaultLocale = "en")
        } returns listOf(
            PersonalQuestTaskTranslationsBd(questId = "Q4", locale = "ru", text = "translated", taskId = 1),
        )

        // When
        val quests = sut.getQuests("ru")

        // Then
        val task = quests[0].tasks[0] as CharacterTaskItem.Count
        expectThat(task.text).isEqualTo("translated")
        expectThat(task.count).isEqualTo(5)
    }
}
