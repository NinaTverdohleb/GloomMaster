package com.rumpilstilstkin.gloommaster.screens.characters.start.perks.add

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloommaster.R
import com.rumpilstilstkin.gloommaster.designsystem.components.buttons.GloomOutlineButton
import com.rumpilstilstkin.gloommaster.designsystem.icons.AppIcon
import com.rumpilstilstkin.gloommaster.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloommaster.screens.models.PerkUI
import com.rumpilstilstkin.gloommaster.ui.perks.PerkCheckItem
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun AddPerksDialog(
    avaliablePerks: ImmutableList<PerkUI>,
    onPerkSelected: (List<Int>) -> Unit,
) {
    var checkedPerks by remember { mutableStateOf(listOf<Int>()) }
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f, fill = false),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(
                items = avaliablePerks,
                key = { it.id },
            ) { perk ->
                PerkCheckItem(
                    perk = perk,
                    checked = checkedPerks.contains(perk.id),
                    check = { checked ->
                        checkedPerks =
                            if (checked) {
                                checkedPerks.plus(perk.id)
                            } else {
                                checkedPerks.filter { id -> id != perk.id }
                            }
                    },
                )
            }
        }

        GloomOutlineButton(
            modifier = Modifier.fillMaxWidth(),
            icon = AppIcon.Plus,
            text = stringResource(R.string.add),
            onClick = { onPerkSelected(checkedPerks) },
        )
    }
}

@Preview
@Composable
private fun AddPerksDialogPreview() {
    GloomhavenMasterTheme {
        Box(
            modifier = Modifier.background(MaterialTheme.colorScheme.surfaceContainerHigh),
        ) {
            AddPerksDialog(
                avaliablePerks =
                    persistentListOf(
                        PerkUI(1, "Remove two cards #01"),
                        PerkUI(2, "Replace one card #01 with one card #03"),
                        PerkUI(3, "Add two cards #03"),
                    ),
                onPerkSelected = {},
            )
        }
    }
}
