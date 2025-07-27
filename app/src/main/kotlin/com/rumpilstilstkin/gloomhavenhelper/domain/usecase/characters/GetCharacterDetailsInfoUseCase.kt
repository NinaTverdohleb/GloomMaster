package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters

import com.rumpilstilstkin.gloomhavenhelper.data.CharacterRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterFullInfo
import com.rumpilstilstkin.gloomhavenhelper.domain.getNextLevel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCharacterDetailsInfoUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {
    operator fun invoke(characterId: Int): Flow<CharacterFullInfo> =
        characterRepository.getCharacterByIdFlow(characterId)
            .combine(characterRepository.getCharacterPersonalQuestFlow(characterId)) { characterInfo, quest ->
                CharacterFullInfo(
                    generalInfo = characterInfo,
                    nextLevelExperience = getNextLevel(characterInfo.level),
                    isDonateAvailable = characterInfo.goldCount >= 10,
                    personalQuest = quest
                )
            }
}