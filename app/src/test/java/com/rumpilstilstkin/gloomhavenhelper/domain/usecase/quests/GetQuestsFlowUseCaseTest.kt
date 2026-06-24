package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.quests

import app.cash.turbine.test
import com.rumpilstilstkin.gloomhavenhelper.data.LocaleRepository
import com.rumpilstilstkin.gloomhavenhelper.data.QuestsRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterClassType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.quest.CharacterPersonalQuest
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.quest.QuestReward
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

@OptIn(ExperimentalCoroutinesApi::class)
class GetQuestsFlowUseCaseTest {
    private val questsRepository: QuestsRepository = mockk()
    private val localeRepository: LocaleRepository = mockk()

    @Test
    fun `given locale emits ru when invoked then mapped quests from repo are emitted`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val quests = listOf(quest("alpha"), quest("beta"))
        every { localeRepository.observeLocale } returns flowOf("ru")
        coEvery { questsRepository.getQuests("ru") } returns quests
        val sut = GetQuestsFlowUseCase(questsRepository, localeRepository)

        // When / Then
        sut().test {
            expectThat(awaitItem()).containsExactly(quest("alpha"), quest("beta"))
            awaitComplete()
        }
        coVerify(exactly = 1) { questsRepository.getQuests("ru") }
    }

    private fun quest(id: String) =
        CharacterPersonalQuest(
            questNumber = id,
            title = "title-$id",
            descriptions = "desc-$id",
            reward = QuestReward(classType = CharacterClassType.Brute, alternativeReward = ""),
            tasks = emptyList(),
        )
}
