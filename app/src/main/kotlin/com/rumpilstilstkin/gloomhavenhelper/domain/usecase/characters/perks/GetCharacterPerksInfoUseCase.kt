package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.perks

import com.rumpilstilstkin.gloomhavenhelper.data.CharacterRepository
import com.rumpilstilstkin.gloomhavenhelper.data.LocaleRepository
import com.rumpilstilstkin.gloomhavenhelper.data.PerksRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterPerksInfo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

class GetCharacterPerksInfoUseCase @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val perksRepository: PerksRepository,
    private val localeRepository: LocaleRepository,
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(characterId: Int): Flow<CharacterPerksInfo> {
        return localeRepository.observeLocale.flatMapLatest { locale ->
            combine(
                characterRepository.getCharacterByIdFlow(characterId),
                characterRepository.getCharacterPerksFlow(characterId, locale),
            ) { character, acquiredPerks ->
                if (character == null) {
                    return@combine CharacterPerksInfo(emptyList(), emptyList(), 0)
                }
                val acquiredIds = acquiredPerks.map { it.id }.toSet()

                val allClassPerks = perksRepository.getPerksForCharacterClass(character.characterType, locale)
                val avaliablePerks = allClassPerks.filter { it.id !in acquiredIds }

                val allCount = character.level + (character.checkMarkCount / 3) - 1

                CharacterPerksInfo(
                    characterPerks = acquiredPerks,
                    avaliablePerks = avaliablePerks,
                    avaliablePerksCount = maxOf(0, allCount - acquiredPerks.size + character.additionalContOfPerks),
                )
            }
        }
    }
}
