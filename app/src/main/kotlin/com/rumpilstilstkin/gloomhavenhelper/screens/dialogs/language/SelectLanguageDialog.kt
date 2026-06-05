package com.rumpilstilstkin.gloomhavenhelper.screens.dialogs.language

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.LanguageItem
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomAlertDialog
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenMasterTheme
import kotlinx.collections.immutable.persistentListOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectLanguageDialog(
    state: SelectLanguageDialogState,
    onDismiss: () -> Unit,
    selectLanguage: (String?) -> Unit,
    modifier: Modifier = Modifier,
) {
    GloomAlertDialog(
        modifier = modifier,
        content = {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(bottom = 16.dp),
            ) {
                items(
                    items = state.languages,
                    key = { it.languageTag ?: SYSTEM_LANGUAGE_KEY },
                ) { language ->
                    LanguageItemRow(
                        language = language,
                        modifier =
                            Modifier.clickable {
                                selectLanguage(language.languageTag)
                            },
                    )
                }
            }
        },
        onDismissRequest = { onDismiss() },
    )
}

@Composable
private fun LanguageItemRow(
    language: LanguageItem,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            modifier
                .padding(8.dp)
                .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val languageName =
            if (language.languageTag == null) {
                stringResource(R.string.system_language)
            } else {
                language.languageName
            }

        Text(
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.headlineSmall,
            text = languageName,
            color = MaterialTheme.colorScheme.onSurface,
        )
        if (language.selected) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.size(20.dp),
            )
        }
    }
}

private const val SYSTEM_LANGUAGE_KEY = "system"

@Preview
@Composable
private fun SelectLanguageDialogPreview() {
    GloomhavenMasterTheme {
        SelectLanguageDialog(
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
            onDismiss = {},
            selectLanguage = {},
        )
    }
}
