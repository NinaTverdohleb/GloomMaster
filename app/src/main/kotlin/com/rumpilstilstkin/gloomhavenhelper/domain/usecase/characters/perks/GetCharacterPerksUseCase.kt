package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.perks

import com.rumpilstilstkin.gloomhavenhelper.data.CharacterRepository
import com.rumpilstilstkin.gloomhavenhelper.data.LocaleRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Perk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

class GetCharacterPerksUseCase @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val localeRepository: LocaleRepository,
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(characterId: Int): Flow<List<Perk>> =
        localeRepository.observeLocale.flatMapLatest { locale ->
            characterRepository.getCharacterPerksFlow(characterId, locale)
        }
}
