package com.rumpilstilstkin.gloommaster.domain.usecase.characters

import com.rumpilstilstkin.gloommaster.data.CharacterRepository
import com.rumpilstilstkin.gloommaster.data.LocaleRepository
import com.rumpilstilstkin.gloommaster.data.QuestsRepository
import com.rumpilstilstkin.gloommaster.domain.entity.CharacterFullInfo
import com.rumpilstilstkin.gloommaster.domain.getNextLevel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

class GetCharacterDetailsInfoUseCase @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val questsRepository: QuestsRepository,
    private val localeRepository: LocaleRepository,
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(characterId: Int): Flow<CharacterFullInfo?> =
        localeRepository.observeLocale.flatMapLatest { locale ->
            characterRepository
                .getCharacterByIdFlow(characterId)
                .combine(
                    questsRepository.getCharacterPersonalQuestFlow(
                        characterId,
                        locale,
                    ),
                ) { characterInfo, quest ->
                    characterInfo?.let {
                        CharacterFullInfo(
                            generalInfo = characterInfo,
                            nextLevelExperience = getNextLevel(characterInfo.level),
                            isDonateAvailable = characterInfo.goldCount >= 10,
                            personalQuest = quest,
                        )
                    }
                }
        }
}
