package com.rumpilstilstkin.gloommaster.screens.settings.language

import androidx.compose.runtime.Immutable
import com.rumpilstilstkin.gloommaster.domain.entity.LanguageItem
import kotlinx.collections.immutable.ImmutableList

@Immutable
data class SelectLanguageDialogState(
    val languages: ImmutableList<LanguageItem>,
)

sealed interface SelectLanguageDialogAction {
    data class SelectLanguage(
        val languageTag: String?,
    ) : SelectLanguageDialogAction
}

data object SelectLanguageDialogComplete
