package com.rumpilstilstkin.gloomhavenhelper.screens.characters.dialogs.character

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.buttons.GloomOutlineButton
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.text.GloomOutlinedTextField
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.AppIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme

@Composable
fun CharacterEditNameDialog(
    currentName: String,
    save: (String) -> Unit,
) {
    var name by rememberSaveable(currentName) { mutableStateOf(currentName) }

    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        GloomOutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = stringResource(R.string.character_name_label),
            modifier = Modifier.fillMaxWidth(),
        )

        GloomOutlineButton(
            modifier = Modifier.fillMaxWidth(),
            icon = AppIcon.Check,
            text = stringResource(R.string.save),
            onClick = { save(name) },
        )
    }
}

@Preview
@Composable
private fun CharacterEditNameDialogPreview() {
    GloomhavenMasterTheme {
        CharacterEditNameDialog(
            currentName = "Character",
            save = {},
        )
    }
}
