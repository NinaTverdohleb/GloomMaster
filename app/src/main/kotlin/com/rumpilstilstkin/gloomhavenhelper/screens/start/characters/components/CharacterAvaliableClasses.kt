package com.rumpilstilstkin.gloomhavenhelper.screens.start.characters.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.screens.models.CharacterClassTypeUI
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomVariantCard
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenMasterTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun CharacterAvailableClasses(
    availableClasses: ImmutableList<CharacterClassTypeUI>,
    onToggle: (CharacterClassTypeUI) -> Unit,
    modifier: Modifier = Modifier,
) {
    val availableTypes = availableClasses.toSet()

    GloomVariantCard(modifier = modifier) {
        LazyVerticalGrid(
            modifier = Modifier.padding(4.dp),
            columns = GridCells.Adaptive(48.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(CharacterClassTypeUI.entries) { classType ->
                val isAvailable = classType in availableTypes
                Icon(
                    painter = painterResource(id = classType.image),
                    contentDescription = classType.title,
                    modifier = Modifier
                        .size(32.dp)
                        .clickable { onToggle(classType) },
                    tint = if (isAvailable) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.outlineVariant
                    },
                )
            }
        }
    }
}

@Preview
@Composable
private fun CharacterAvailableClassesPreview() {
    GloomhavenMasterTheme {
        CharacterAvailableClasses(
            availableClasses = persistentListOf(
                CharacterClassTypeUI.Brute,
                CharacterClassTypeUI.Spellweaver,
                CharacterClassTypeUI.Scoundrel,
            ),
            onToggle = {},
        )
    }
}