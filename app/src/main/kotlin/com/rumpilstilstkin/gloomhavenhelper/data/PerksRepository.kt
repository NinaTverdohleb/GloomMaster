package com.rumpilstilstkin.gloomhavenhelper.data

import com.rumpilstilstkin.gloomhavenhelper.bd.dao.PerksDao
import com.rumpilstilstkin.gloomhavenhelper.data.mappers.localized
import com.rumpilstilstkin.gloomhavenhelper.data.mappers.toDomain
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterClassType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Perk
import com.rumpilstilstkin.gloomhavenhelper.localization.LocaleSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PerksRepository @Inject constructor(
    private val perksDao: PerksDao,
    private val translationRepository: TranslationRepository,
    private val localeSource: LocaleSource,
) {
    /**
     * Available perks for a class, with text resolved for the active content locale. Uses a
     * one-off snapshot resolver because this is a suspend (non-stream) read; the calling perks
     * flow re-runs on language change, so available perks still switch live.
     */
    suspend fun getPerksForCharacterClass(characterType: CharacterClassType): List<Perk> {
        val resolver = translationRepository.resolver(localeSource.current)
        return perksDao
            .getPerksByCharacterClass(characterType.name)
            .map { it.toDomain().localized(resolver) }
    }
}