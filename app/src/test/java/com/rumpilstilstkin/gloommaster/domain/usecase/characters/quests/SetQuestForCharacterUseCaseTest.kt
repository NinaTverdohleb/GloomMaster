package com.rumpilstilstkin.gloommaster.domain.usecase.characters.quests

import com.rumpilstilstkin.gloommaster.data.QuestsRepository
import com.rumpilstilstkin.gloommaster.domain.entity.quest.CharacterPersonalQuestShort
import com.rumpilstilstkin.gloommaster.domain.usecase.characters.quests.SetQuestForCharacterUseCase
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerifyOrder
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SetQuestForCharacterUseCaseTest {
    private val questsRepository: QuestsRepository = mockk()

    @Test
    fun `given questId and characterId when invoked then quest is fetched and assigned to character`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val quest = CharacterPersonalQuestShort(questId = "q1", tasks = emptyList())
        coEvery { questsRepository.getQuestById("q1") } returns quest
        coJustRun { questsRepository.updateCharacterQuest(quest, 5) }
        val sut = SetQuestForCharacterUseCase(questsRepository)

        // When
        sut(questId = "q1", characterId = 5)

        // Then
        coVerifyOrder {
            questsRepository.getQuestById("q1")
            questsRepository.updateCharacterQuest(quest, 5)
        }
    }
}
