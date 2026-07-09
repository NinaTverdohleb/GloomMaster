package com.rumpilstilstkin.gloommaster.domain.usecase.characters.quests

import com.rumpilstilstkin.gloommaster.data.QuestsRepository
import com.rumpilstilstkin.gloommaster.domain.entity.quest.CharacterPersonalQuestShort
import com.rumpilstilstkin.gloommaster.domain.entity.quest.CharacterTaskItem
import com.rumpilstilstkin.gloommaster.domain.usecase.characters.quests.QuestTaskUpdateUseCase
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.containsExactly
import strikt.assertions.isEqualTo

@OptIn(ExperimentalCoroutinesApi::class)
class QuestTaskUpdateUseCaseTest {
    private val questsRepository: QuestsRepository = mockk()

    @Test
    fun `given Check task replacement when invoked then matching task is swapped`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val before = CharacterTaskItem.Check(id = 1, priority = 0, text = "Task", isChecked = false)
        val other = CharacterTaskItem.Count(id = 2, priority = 1, text = "Counter", count = 5, currentCount = 1)
        val existing = CharacterPersonalQuestShort(questId = "q1", tasks = listOf(before, other))
        val updated = before.copy(isChecked = true)
        val captured = slot<CharacterPersonalQuestShort>()
        coEvery { questsRepository.getCharacterQuestById(5) } returns existing
        coJustRun { questsRepository.updateCharacterQuest(capture(captured), 5) }
        val sut = QuestTaskUpdateUseCase(questsRepository)

        // When
        sut(characterId = 5, task = updated)

        // Then
        expectThat(captured.captured.questId).isEqualTo("q1")
        expectThat(captured.captured.tasks).containsExactly(updated, other)
    }

    @Test
    fun `given Count task increment when invoked then matching task is swapped`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val before = CharacterTaskItem.Count(id = 7, priority = 0, text = "Counter", count = 5, currentCount = 1)
        val existing = CharacterPersonalQuestShort(questId = "q1", tasks = listOf(before))
        val updated = before.copy(currentCount = 2)
        val captured = slot<CharacterPersonalQuestShort>()
        coEvery { questsRepository.getCharacterQuestById(5) } returns existing
        coJustRun { questsRepository.updateCharacterQuest(capture(captured), 5) }
        val sut = QuestTaskUpdateUseCase(questsRepository)

        // When
        sut(characterId = 5, task = updated)

        // Then
        expectThat(captured.captured.tasks).containsExactly(updated)
    }

    @Test
    fun `given unknown task id when invoked then tasks are unchanged but update is still called`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val existing = CharacterPersonalQuestShort(
            questId = "q1",
            tasks = listOf(CharacterTaskItem.Check(id = 1, priority = 0)),
        )
        val unknown = CharacterTaskItem.Check(id = 999, priority = 0)
        val captured = slot<CharacterPersonalQuestShort>()
        coEvery { questsRepository.getCharacterQuestById(5) } returns existing
        coJustRun { questsRepository.updateCharacterQuest(capture(captured), 5) }
        val sut = QuestTaskUpdateUseCase(questsRepository)

        // When
        sut(characterId = 5, task = unknown)

        // Then
        expectThat(captured.captured.tasks).containsExactly(CharacterTaskItem.Check(id = 1, priority = 0))
    }

    @Test
    fun `given no character quest when invoked then update is not called`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        coEvery { questsRepository.getCharacterQuestById(5) } returns null
        val sut = QuestTaskUpdateUseCase(questsRepository)

        // When
        sut(characterId = 5, task = CharacterTaskItem.Check(id = 1, priority = 0))

        // Then
        coVerify(exactly = 0) { questsRepository.updateCharacterQuest(any(), any()) }
    }
}
