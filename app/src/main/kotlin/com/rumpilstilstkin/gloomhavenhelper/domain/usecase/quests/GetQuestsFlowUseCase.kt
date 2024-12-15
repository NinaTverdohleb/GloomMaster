package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.quests

import com.rumpilstilstkin.gloomhavenhelper.data.QuestsRepository
import javax.inject.Inject

class GetQuestsFlowUseCase @Inject constructor(
    private val questsRepository: QuestsRepository,
) {

    operator fun invoke() = questsRepository.getQuestsFlow()
}