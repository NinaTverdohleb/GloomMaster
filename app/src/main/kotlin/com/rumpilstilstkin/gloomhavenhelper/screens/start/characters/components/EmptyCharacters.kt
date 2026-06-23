package com.rumpilstilstkin.gloomhavenhelper.screens.start.characters.components

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.empty.EmptyView
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.EmptyIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme

@Composable
internal fun EmptyCharacters(modifier: Modifier = Modifier) =
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
    EmptyView(
        icon = EmptyIcon.Characters,
        title = stringResource(R.string.empty_active_characters),
        description = stringResource(R.string.add_character_to_company)
    )
}


@Preview(showBackground = true, backgroundColor = 0xFF1A1C24)
@Composable
private fun EmptyCharactersPreview() {
    GloomhavenMasterTheme {
        EmptyCharacters()
    }
}
