package com.rumpilstilstkin.gloommaster.screens.characters.start.perks.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.rumpilstilstkin.gloommaster.R
import com.rumpilstilstkin.gloommaster.designsystem.components.empty.EmptyView
import com.rumpilstilstkin.gloommaster.designsystem.icons.EmptyIcon
import com.rumpilstilstkin.gloommaster.designsystem.theme.GloomhavenMasterTheme

@Composable
fun EmptyPerks(modifier: Modifier = Modifier) =
    EmptyView(
        modifier = modifier,
        icon = EmptyIcon.Perks,
        title = stringResource(R.string.empty_character_perks),
        description = stringResource(R.string.empty_character_perks_description),
    )

@Preview
@Composable
private fun EmptyPerksPreview() {
    GloomhavenMasterTheme {
        EmptyPerks()
    }
}
