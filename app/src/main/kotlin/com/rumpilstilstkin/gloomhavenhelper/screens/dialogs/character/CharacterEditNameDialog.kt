package com.rumpilstilstkin.gloomhavenhelper.screens.dialogs.character

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomAlertDialog
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenHalperTheme

@Composable
fun CharacterEditNameDialog(
    currentName: String,
    onDismiss: () -> Unit,
    onSave: (String) -> Unit
) {
    var name by rememberSaveable { mutableStateOf(currentName) }

    GloomAlertDialog(
        onDismissRequest = onDismiss,
        title = "Изменить имя",
        confirmText = "Сохранить",
        onConfirmRequest = { onSave(name) },
        content = {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Имя персонажа") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        },
    )
}

@Preview
@Composable
private fun CharacterEditNameDialogPreview() {
    GloomhavenHalperTheme {
        CharacterEditNameDialog(
            currentName = "Персонаж",
            onDismiss = {},
            onSave = {}
        )
    }
}