package com.rumpilstilstkin.gloomhavenhelper.ui.perks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.GloomCard
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.items.GloomItemActionIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.items.GloomSwipeableListItem
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.AppIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloomhavenhelper.screens.models.PerkUI
import com.rumpilstilstkin.gloomhavenhelper.ui.icons.text.TextWithImagesByCode

@Composable
private fun PerkActionItem(
    perk: PerkUI,
    modifier: Modifier = Modifier,
    delete: (PerkUI) -> Unit,
) {
    GloomSwipeableListItem(
        menuContent = {
            GloomItemActionIcon(
                modifier = Modifier.fillMaxHeight(),
                icon = AppIcon.Delete,
                isError = true,
                onClick = { delete(perk) },
            )
        },
        item = {
            GloomCard(
                modifier = modifier.fillMaxWidth(),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 12.dp)
                ) {
                    TextWithImagesByCode(text = perk.text)
                }
            }
        }
    )
}

@Preview
@Composable
private fun PerkItemPreview() {
    GloomhavenMasterTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            PerkActionItem(
                perk = PerkUI.fixture(),
                delete = {},
            )
        }
    }
}