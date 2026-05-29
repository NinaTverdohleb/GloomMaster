package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.perks

import com.rumpilstilstkin.gloomhavenhelper.data.CharacterRepository
import com.rumpilstilstkin.gloomhavenhelper.data.LocaleRepository
import com.rumpilstilstkin.gloomhavenhelper.data.PerksRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterPerksInfo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.math.max

class GetCharacterPerksInfoUseCase @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val perksRepository: PerksRepository,
    private val localeRepository: LocaleRepository
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(characterId: Int): Flow<CharacterPerksInfo> {
        return localeRepository.observeLocale.flatMapLatest { locale ->
            characterRepository.getCharacterPerksFlow(characterId, locale).map { perks ->
                characterRepository.getCharacterById(characterId)?.let { character ->
                    val avaliablePerks =
                        perksRepository.getPerksForCharacterClass(character.characterType, locale)
                            .filter { perk -> perk.id !in perks.map { it.id } }
                    val allCount = character.level + character.checkMarkCount.div(3) - 1
                    CharacterPerksInfo(
                        characterPerks = perks,
                        avaliablePerks = avaliablePerks,
                        avaliablePerksCount = max(0, allCount - perks.size)
                    )
                } ?: CharacterPerksInfo(
                    characterPerks = emptyList(),
                    avaliablePerks = emptyList(),
                    avaliablePerksCount = 0
                )
            }
        }
    }
}
