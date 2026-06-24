package com.rumpilstilstkin.gloomhavenhelper.screens.characters.dialogs.menu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.buttons.GloomOutlineButton
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.counter.GloomCounterFull
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.text.GloomHeader
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.AppIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloomhavenhelper.screens.models.CharacterUI
import com.rumpilstilstkin.gloomhavenhelper.ui.characters.CharacterItem

@Composable
fun MenuCharacterDialog(
    character: CharacterUI,
    level: Int,
    deleteCharacter: () -> Unit,
    updateLevel: (Int) -> Unit,
    leaveCharacter: () -> Unit,
    makeCharacterAlive: () -> Unit,
    openCharacterDetails: () -> Unit,
) {
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        CharacterItem(character = character)

        if (character.isAlive) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                GloomHeader(
                    text = stringResource(R.string.character_level_label),
                )

                GloomCounterFull(
                    value = level,
                    intRange = IntRange(1, 9),
                ) { updateLevel(it) }
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            if (character.isAlive) {
                GloomOutlineButton(
                    text = stringResource(R.string.character_details),
                    onClick = openCharacterDetails,
                    modifier = Modifier.fillMaxWidth(),
                    isError = false,
                    icon = AppIcon.Settings,
                )
                GloomOutlineButton(
                    text = stringResource(R.string.leave_character),
                    onClick = leaveCharacter,
                    modifier = Modifier.fillMaxWidth(),
                    isError = false,
                    icon = AppIcon.Leave,
                )
            } else {
                GloomOutlineButton(
                    text = stringResource(R.string.make_alive),
                    onClick = makeCharacterAlive,
                    modifier = Modifier.fillMaxWidth(),
                    isError = false,
                    icon = AppIcon.Restore,
                )
            }

            GloomOutlineButton(
                text = stringResource(R.string.delete_character),
                onClick = deleteCharacter,
                modifier = Modifier.fillMaxWidth(),
                isError = true,
                icon = AppIcon.Delete,
            )
        }
    }
}

@Preview
@Composable
private fun MenuCharacterDialogPreview() {
    GloomhavenMasterTheme {
        MenuCharacterDialog(
            character =
                CharacterUI.fixture(
                    isAlive = false,
                ),
            level = 2,
            deleteCharacter = {},
            updateLevel = {},
            leaveCharacter = {},
            makeCharacterAlive = {},
            openCharacterDetails = {},
        )
    }
}
