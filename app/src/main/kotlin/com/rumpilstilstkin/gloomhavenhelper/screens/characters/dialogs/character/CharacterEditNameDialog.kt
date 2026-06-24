package com.rumpilstilstkin.gloomhavenhelper.screens.characters.dialogs.character

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomAlertDialog

@Composable
fun CharacterEditNameDialog(
    currentName: String,
    onDismiss: () -> Unit,
    onSave: (String) -> Unit,
) {
    var name by rememberSaveable { mutableStateOf(currentName) }

    GloomAlertDialog(
        onDismissRequest = onDismiss,
        title = stringResource(R.string.edit_name_title),
        confirmText = stringResource(R.string.save),
        onConfirmRequest = { onSave(name) },
        content = {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text(stringResource(R.string.character_name_label)) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
            )
        },
    )
}

@Preview
@Composable
private fun CharacterEditNameDialogPreview() {
    GloomhavenMasterTheme {
        CharacterEditNameDialog(
            currentName = "Character",
            onDismiss = {},
            onSave = {},
        )
    }
}
