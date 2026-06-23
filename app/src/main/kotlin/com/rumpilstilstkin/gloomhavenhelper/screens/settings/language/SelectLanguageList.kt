package com.rumpilstilstkin.gloomhavenhelper.screens.settings.language

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.LanguageItem
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.items.GloomListItem
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.items.RightItemIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.AppIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme
import kotlinx.collections.immutable.persistentListOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectLanguageList(
    state: SelectLanguageDialogState,
    selectLanguage: (String?) -> Unit,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(16.dp),
    ) {
        items(
            items = state.languages,
            key = { it.languageTag ?: SYSTEM_LANGUAGE_KEY },
        ) { language ->
            LanguageItemRow(
                language = language,
                selectLanguage = selectLanguage
            )
        }
    }
}

@Composable
private fun LanguageItemRow(
    language: LanguageItem,
    modifier: Modifier = Modifier,
    selectLanguage: (String?) -> Unit,
) {
    val languageName =
        if (language.languageTag == null) {
            stringResource(R.string.system_language)
        } else {
            language.languageName
        }
    GloomListItem(
        modifier = modifier.fillMaxWidth(),
        title = languageName,
        onClick = { selectLanguage(language.languageTag) },
        rightComponent = {
            if (language.selected) {
                RightItemIcon(AppIcon.Check)
            }
        }
    )
}

private const val SYSTEM_LANGUAGE_KEY = "system"

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, backgroundColor = 0xFF1A1C24)
@Composable
private fun SelectLanguageDialogPreview() {
    GloomhavenMasterTheme {
        SelectLanguageList(
            state =
                SelectLanguageDialogState(
                    persistentListOf(
                        LanguageItem(
                            languageTag = null,
                            languageName = "System",
                            selected = false,
                        ),
                        LanguageItem(
                            languageTag = "en",
                            languageName = "English",
                            selected = true,
                        ),
                        LanguageItem(
                            languageTag = "ru",
                            languageName = "Русский",
                            selected = false,
                        ),
                    ),
                ),
            selectLanguage = {},
        )
    }
}
