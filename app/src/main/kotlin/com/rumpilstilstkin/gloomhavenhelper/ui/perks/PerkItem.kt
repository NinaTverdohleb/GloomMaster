package com.rumpilstilstkin.gloomhavenhelper.ui.perks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.items.GloomItemActionIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.items.GloomSwipeableListItem
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.items.RightItemChecker
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.items.TextImageFilledItem
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.items.TextImageOutlineItem
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.AppIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloomhavenhelper.screens.models.PerkUI

@Composable
fun PerkActionItem(
    perk: PerkUI,
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
            TextImageFilledItem(perk.text)
        }
    )
}

@Composable
fun PerkCheckItem(
    perk: PerkUI,
    checked: Boolean,
    check: (Boolean) -> Unit,
) {
    TextImageOutlineItem(
        text = perk.text,
        rightComponent = {
            RightItemChecker(
                checked = checked,
                onCheckedChange= check,
            )
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
            PerkCheckItem(
                perk = PerkUI.fixture(),
                checked = true,
            ) { }
        }
    }
}