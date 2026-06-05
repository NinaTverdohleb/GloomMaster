package com.rumpilstilstkin.gloomhavenhelper.screens.dialogs.language

import androidx.compose.runtime.Immutable
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.LanguageItem
import kotlinx.collections.immutable.ImmutableList

@Immutable
data class SelectLanguageDialogState(
    val languages: ImmutableList<LanguageItem>,
)

sealed interface SelectLanguageDialogAction {
    data class SelectLanguage(
        val languageTag: String?,
    ) : SelectLanguageDialogAction

    data object Back : SelectLanguageDialogAction
}
