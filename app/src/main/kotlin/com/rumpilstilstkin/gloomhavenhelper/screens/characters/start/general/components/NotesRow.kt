package com.rumpilstilstkin.gloomhavenhelper.screens.characters.start.general.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.GloomCard
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.buttons.GloomOutlineButton
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.text.GloomHeader
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.AppIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomAlertDialog

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

@Composable
fun NotesDialog(
    text: String,
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onNotesChanged: (String) -> Unit,
) {
    if (showDialog) {
        var newNotes by rememberSaveable { mutableStateOf(text) }

        GloomAlertDialog(
            onDismissRequest = { onDismiss.invoke() },
            title = stringResource(R.string.notes_title),
            content = {
                Box {
                    OutlinedTextField(
                        modifier = Modifier.defaultMinSize(minHeight = 240.dp),
                        value = newNotes,
                        onValueChange = { newNotes = it },
                        label = { Text(stringResource(R.string.notes_title)) },
                    )
                }
            },
            onConfirmRequest = { onNotesChanged.invoke(newNotes) },
            onNeutralRequest = null,
            confirmText = stringResource(R.string.save),
        )
    }
}
