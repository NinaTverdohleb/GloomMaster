package com.rumpilstilstkin.gloommaster.ui.characters

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloommaster.designsystem.components.items.GloomListFilledItem
import com.rumpilstilstkin.gloommaster.designsystem.components.items.GloomListItem
import com.rumpilstilstkin.gloommaster.designsystem.components.items.LeftItemImage
import com.rumpilstilstkin.gloommaster.designsystem.components.items.RightItemNumber
import com.rumpilstilstkin.gloommaster.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloommaster.screens.models.CharacterUI

@Composable
fun CharacterItemFilled(
    character: CharacterUI,
    modifier: Modifier = Modifier,
    onClick: (CharacterUI) -> Unit,
) = GloomListFilledItem(
    modifier = modifier,
    title = character.name,
    description = stringResource(character.characterClass.titleRes),
    leftComponent = {
        LeftItemImage(
            icon = character.characterClass.image,
        )
    },
    rightComponent = {
        RightItemNumber(
            number = character.level.toString(),
        )
    },
    active = character.isAlive,
    onClick = { onClick(character) },
)

@Composable
fun CharacterItem(
    character: CharacterUI,
    modifier: Modifier = Modifier,
) = GloomListItem(
    modifier = modifier,
    title = character.name,
    description = stringResource(character.characterClass.titleRes),
    leftComponent = {
        LeftItemImage(
            icon = character.characterClass.image,
        )
    },
)

@Composable
fun CharacterHeaderItem(
    character: CharacterUI,
    onClick: (CharacterUI) -> Unit,
    modifier: Modifier = Modifier,
) = GloomListItem(
    modifier =
        modifier
            .padding(16.dp),
    title = character.name,
    description = stringResource(character.characterClass.titleRes),
    leftComponent = {
        LeftItemImage(
            icon = character.characterClass.image,
        )
    },
    rightComponent = {
        RightItemNumber(
            number = character.level.toString(),
        )
    },
    onClick = { onClick(character) },
)

@Preview
@Composable
private fun CharacterItemPreview() {
    GloomhavenMasterTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            CharacterItem(
                character = CharacterUI.fixture(),
            )
            CharacterItemFilled(
                character = CharacterUI.fixture(),
                onClick = {},
            )

            CharacterHeaderItem(
                character = CharacterUI.fixture(),
                onClick = {},
            )
        }
    }
}
