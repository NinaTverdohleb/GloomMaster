package com.rumpilstilstkin.gloommaster.domain.usecase.settings

import com.rumpilstilstkin.gloommaster.data.LocaleRepository
import com.rumpilstilstkin.gloommaster.domain.entity.LanguageItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import java.util.Locale
import javax.inject.Inject

class LanguagesListUseCase @Inject constructor(
    private val localeRepository: LocaleRepository,
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<Pair<LanguageItem, List<LanguageItem>>> =
        localeRepository.observeLocale.mapLatest { locale ->
            val currentLocale = Locale.forLanguageTag(locale)
            val selected = if (localeRepository.isLanguageDefault) null else locale
            val list =
                listOf(
                    LanguageItem(
                        selected = selected == null,
                        languageName = "",
                    ),
                ) +
                    localeRepository.availableLanguages.map { tag ->
                        LanguageItem(
                            languageTag = tag,
                            selected = selected == tag,
                            languageName =
                                Locale.forLanguageTag(tag).let {
                                    it.getDisplayLanguage(it)
                                },
                        )
                    }
            LanguageItem(
                languageTag = locale,
                selected = true,
                languageName =
                    currentLocale.let {
                        it.getDisplayLanguage(it)
                    },
            ) to list
        }
}
