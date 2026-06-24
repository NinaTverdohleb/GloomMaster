package com.rumpilstilstkin.gloomhavenhelper.screens.characters.dialogs.character

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloomhavenhelper.screens.models.CharacterClassTypeUI
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomAlertDialog
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomNumberPicker

@Composable
fun CharacterEditLevelDialog(
    characterLevel: Int,
    characterName: String,
    characterClass: CharacterClassTypeUI,
    dismiss: () -> Unit,
    changeLevel: (Int) -> Unit,
) {
    var level by remember { mutableIntStateOf(characterLevel) }
    GloomAlertDialog(
        onDismissRequest = dismiss,
        onConfirmRequest = { changeLevel(level) },
        onNeutralRequest = null,
        confirmText = stringResource(R.string.save),
        content = {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        painter = characterClass.image.painter(),
                        contentDescription = null,
                        modifier = Modifier.size(32.dp),
                        tint = MaterialTheme.colorScheme.primary,
                    )
                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = characterName,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    GloomNumberPicker(
                        modifier = Modifier.fillMaxWidth(),
                        value = level,
                        intRange = IntRange(0, 9),
                    ) {
                        level = it
                    }
                }
            }
        },
    )
}

@Preview
@Composable
private fun CharacterEditLevelDialogPreview() {
    GloomhavenMasterTheme {
        CharacterEditLevelDialog(
            characterLevel = 1,
            characterName = "Character",
            characterClass = CharacterClassTypeUI.Brute,
            dismiss = {},
            changeLevel = {},
        )
    }
}
