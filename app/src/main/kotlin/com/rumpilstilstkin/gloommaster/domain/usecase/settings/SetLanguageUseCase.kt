package com.rumpilstilstkin.gloommaster.domain.usecase.settings

import com.rumpilstilstkin.gloommaster.data.LocaleRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

class SetLanguageUseCase @Inject constructor(
    private val localeRepository: LocaleRepository,
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(languageTag: String?) {
        localeRepository.setAppLocale(languageTag)
    }
}
