package com.rumpilstilstkin.gloomhavenhelper.screens.dialogs.character

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.screens.models.PerkUI
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomAlertDialog
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomCard
import com.rumpilstilstkin.gloomhavenhelper.ui.icons.text.TextWithImagesByCode
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenHalperTheme

@Composable
fun AddPerksDialog(
    avaliablePerks: List<PerkUI>,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onPerkSelected: (List<Int>) -> Unit,
) {
    var checkedPerks by remember { mutableStateOf(listOf<Int>()) }
    GloomAlertDialog(
        onDismissRequest = { onDismiss.invoke() },
        title = "Доступные навыки",
        content = {
            LazyColumn(
                modifier = modifier.height(320.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(avaliablePerks) { perk ->
                    AddPerkDialogItem(
                        perk = perk,
                        checked = checkedPerks.contains(perk.id),
                        onSelectedChanged = {
                            checkedPerks = if (checkedPerks.contains(it)) {
                                checkedPerks.filter { id -> id != it }
                            } else {
                                checkedPerks.plus(it)
                            }
                        }
                    )
                }
            }
        },
        onConfirmRequest = {
            onPerkSelected(checkedPerks)
        },
        confirmEnabled = checkedPerks.isNotEmpty(),
        confirmText = "Добавить",
    )
}


@Composable
private fun AddPerkDialogItem(
    perk: PerkUI,
    checked: Boolean,
    modifier: Modifier = Modifier,
    onSelectedChanged: (Int) -> Unit,
) = GloomCard(
    modifier = modifier
        .fillMaxWidth()
        .clickable {
            onSelectedChanged(perk.id)
        }
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            modifier = Modifier.padding(),
            checked = checked,
            onCheckedChange = {
                onSelectedChanged(perk.id)
            },
            colors = CheckboxDefaults.colors().copy(
                uncheckedBorderColor = MaterialTheme.colorScheme.primary,
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        TextWithImagesByCode(text = perk.text)
    }
}


@Preview
@Composable
private fun AddPerksDialogPreview() {
    GloomhavenHalperTheme {
        AddPerksDialog(
            avaliablePerks = listOf(
                PerkUI(1, "Уберите две карты #01"),
                PerkUI(2, "Поменяйте 1 карту #01 на 1 карту #03 "),
                PerkUI(3, "Добавьте две карты #03"),
            ),
            onDismiss = {},
            onPerkSelected = {}
        )
    }
}

@Preview
@Composable
private fun AddPerkDialogItemPreview() {
    GloomhavenHalperTheme {
        AddPerkDialogItem(
            perk = PerkUI(1, "Уберите две карты #01"),
            checked = true,
            onSelectedChanged = {}
        )
    }
}