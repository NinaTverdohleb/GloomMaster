package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.settings

import com.rumpilstilstkin.gloomhavenhelper.data.LocaleRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.LanguageItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import java.util.Locale
import javax.inject.Inject
import kotlin.to

class LanguagesListUseCase @Inject constructor(
    private val localeRepository: LocaleRepository,
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<Pair<String, List<LanguageItem>>> =
        localeRepository.observeLocaleUnic.mapLatest { locale ->
            val currentLocale = Locale.forLanguageTag(locale)
            val selected = if (localeRepository.isLanguageDefault) null else locale
            val list =
                listOf(
                    LanguageItem(
                        selected = selected == null,
                        languageName = "",
                    ),
                ) +
                    localeRepository.avaliableLanguages.map { tag ->
                        LanguageItem(
                            languageTag = tag,
                            selected = selected == tag,
                            languageName =
                                Locale.forLanguageTag(tag).let {
                                    it.getDisplayLanguage(it)
                                },
                        )
                    }
            currentLocale.getDisplayLanguage(currentLocale) to list
        }
}
