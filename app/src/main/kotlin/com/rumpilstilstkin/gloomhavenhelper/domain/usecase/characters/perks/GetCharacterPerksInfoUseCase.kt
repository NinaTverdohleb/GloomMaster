package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.perks

import com.rumpilstilstkin.gloomhavenhelper.data.CharacterRepository
import com.rumpilstilstkin.gloomhavenhelper.data.PerksRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterPerksInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.math.max

class GetCharacterPerksInfoUseCase @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val perksRepository: PerksRepository,
) {

    operator fun invoke(characterId: Int, ): Flow<CharacterPerksInfo> {
        return characterRepository.getCharacterPerksFlow(characterId).map { perks ->
            val character = characterRepository.getCharacterById(characterId)
            val avaliablePerks = perksRepository.getPerksForCharacterClass(character.characterType)
                .filter { perk -> perk.id !in perks.map { it.id } }
            val allCount = character.level + character.checkMarks.div(3)
            CharacterPerksInfo(
                characterPerks = perks,
                avaliablePerks = avaliablePerks,
                avaliablePerksCount = max(0, allCount - perks.size)
            )
        }
    }
}