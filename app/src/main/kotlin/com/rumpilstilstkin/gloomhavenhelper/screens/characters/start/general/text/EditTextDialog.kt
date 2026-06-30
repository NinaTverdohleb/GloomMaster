package com.rumpilstilstkin.gloomhavenhelper.screens.characters.start.general.text

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.text.GloomOutlinedTextField
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme

@Composable
fun EditTextDialog(
    text: String,
    onTextChanged: (String) -> Unit,
) {
    GloomOutlinedTextField(
        modifier =
            Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .defaultMinSize(minHeight = 240.dp),
        value = text,
        singleLine = false,
        onValueChange = onTextChanged,
        label = stringResource(R.string.notes_title),
    )
}

@Preview
@Composable
private fun EditTextDialogPreview() {
    GloomhavenMasterTheme {
        EditTextDialog(
            text = "text",
            onTextChanged = {},
        )
    }
}
