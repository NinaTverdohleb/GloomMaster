package com.rumpilstilstkin.gloomhavenhelper.screens.characters.start.general.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.GloomCard
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.buttons.GloomOutlineButton
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.text.GloomHeader
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.AppIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloomhavenhelper.testtags.screens.characters.start.CharacterGeneralTabTestTags

@Composable
fun NotesRow(
    notes: String,
    modifier: Modifier = Modifier,
    openNotes: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        GloomHeader(stringResource(R.string.text_label))

        if (notes.isNotEmpty()) {
            GloomCard(
                modifier =
                    modifier
                        .fillMaxWidth()
                        .testTag(CharacterGeneralTabTestTags.OPEN_NOTES)
                        .clickable { openNotes() },
            ) {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = notes,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
        } else {
            GloomOutlineButton(
                modifier = Modifier.testTag(CharacterGeneralTabTestTags.OPEN_NOTES),
                icon = AppIcon.Plus,
                text = stringResource(R.string.edit_text_button),
                onClick = openNotes,
            )
        }
    }
}

@Preview
@Composable
private fun NotesRowPreview() {
    GloomhavenMasterTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            NotesRow(
                notes = "",
                openNotes = {},
            )

            NotesRow(
                notes = "fsadfasdfsasdf",
                openNotes = {},
            )
        }
    }
}
