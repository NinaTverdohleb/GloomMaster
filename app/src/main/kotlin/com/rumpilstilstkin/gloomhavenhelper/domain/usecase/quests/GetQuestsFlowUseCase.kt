package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.quests

import com.rumpilstilstkin.gloomhavenhelper.data.LocaleRepository
import com.rumpilstilstkin.gloomhavenhelper.data.QuestsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

class GetQuestsFlowUseCase @Inject constructor(
    private val questsRepository: QuestsRepository,
    private val localeRepository: LocaleRepository,
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke() = localeRepository.observeLocale.mapLatest { locale -> questsRepository.getQuests(locale) }
}
