package com.rumpilstilstkin.gloommaster.screens.characters.dialogs.add

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloommaster.R
import com.rumpilstilstkin.gloommaster.designsystem.components.GloomCard
import com.rumpilstilstkin.gloommaster.designsystem.components.buttons.GloomOutlineButton
import com.rumpilstilstkin.gloommaster.designsystem.components.counter.GloomCounterFull
import com.rumpilstilstkin.gloommaster.designsystem.components.text.GloomHeader
import com.rumpilstilstkin.gloommaster.designsystem.components.text.GloomOutlinedTextField
import com.rumpilstilstkin.gloommaster.designsystem.icons.AppIcon
import com.rumpilstilstkin.gloommaster.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloommaster.screens.models.CharacterClassTypeUI
import com.rumpilstilstkin.gloommaster.testtags.screens.characters.dialogs.add.AddCharacterDialogTestTags

@Composable
fun AddCharacterDialog(
    state: AddCharacterDialogState,
    createCharacter: () -> Unit,
    updateName: (String) -> Unit,
    updateLevel: (Int) -> Unit,
    changeType: (CharacterClassTypeUI) -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        GloomCard {
            LazyVerticalGrid(
                modifier = Modifier.padding(16.dp),
                columns = GridCells.Adaptive(48.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                itemsIndexed(items = state.avaliableClasses) { index, classType ->
                    val isSelected = classType == state.selectedClass
                    Icon(
                        painter = classType.image.painter(),
                        contentDescription = stringResource(classType.titleRes),
                        modifier =
                            Modifier
                                .testTag(AddCharacterDialogTestTags.type(index))
                                .size(32.dp)
                                .clickable {
                                    changeType(classType)
                                },
                        tint =
                            if (isSelected) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.outlineVariant
                            },
                    )
                }
            }
        }
        Text(
            text = stringResource(state.selectedClass.titleRes),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface,
        )

        GloomOutlinedTextField(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .testTag(AddCharacterDialogTestTags.NAME_FIELD),
            value = state.name,
            onValueChange = { updateName(it) },
            label = stringResource(R.string.name_label),
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            GloomHeader(
                text = stringResource(R.string.character_level_label),
            )

            GloomCounterFull(
                value = state.level,
                intRange = IntRange(1, 9),
            ) { updateLevel(it) }
        }

        GloomOutlineButton(
            text = stringResource(R.string.add_character),
            onClick = createCharacter,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .testTag(AddCharacterDialogTestTags.ADD_BUTTON),
            isError = false,
            icon = AppIcon.Check,
        )
    }
}

@Preview
@Composable
private fun AddCharacterDialogPreview() {
    GloomhavenMasterTheme {
        AddCharacterDialog(
            state = AddCharacterDialogState.fixture(),
            createCharacter = {},
            updateName = {},
            updateLevel = {},
            changeType = {},
        )
    }
}
