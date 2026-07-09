package com.rumpilstilstkin.gloommaster.ui.perks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloommaster.designsystem.components.items.GloomItemActionIcon
import com.rumpilstilstkin.gloommaster.designsystem.components.items.GloomSwipeableListItem
import com.rumpilstilstkin.gloommaster.designsystem.components.items.RightItemChecker
import com.rumpilstilstkin.gloommaster.designsystem.components.items.TextImageFilledItem
import com.rumpilstilstkin.gloommaster.designsystem.components.items.TextImageOutlineItem
import com.rumpilstilstkin.gloommaster.designsystem.icons.AppIcon
import com.rumpilstilstkin.gloommaster.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloommaster.screens.models.PerkUI
import com.rumpilstilstkin.gloommaster.testtags.screens.characters.start.perks.CharacterPerksTabTestTags

@Composable
fun PerkActionItem(
    perk: PerkUI,
    delete: (PerkUI) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.testTag(CharacterPerksTabTestTags.PERK_ITEM)) {
        GloomSwipeableListItem(
            menuContent = {
                GloomItemActionIcon(
                    modifier =
                        Modifier
                            .fillMaxHeight()
                            .testTag(CharacterPerksTabTestTags.DELETE_ACTION),
                    icon = AppIcon.Delete,
                    isError = true,
                    onClick = { delete(perk) },
                )
            },
            item = {
                TextImageFilledItem(perk.text)
            },
        )
    }
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
                onCheckedChange = check,
            )
        },
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
