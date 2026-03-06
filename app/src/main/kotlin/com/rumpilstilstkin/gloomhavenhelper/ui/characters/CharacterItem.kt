package com.rumpilstilstkin.gloomhavenhelper.ui.characters

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.screens.models.CharacterUI
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomCard
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomSize
import com.rumpilstilstkin.gloomhavenhelper.ui.components.RoundButton
import com.rumpilstilstkin.gloomhavenhelper.ui.icons.toImage
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenHalperTheme

@Composable
fun CharacterItem(
    character: CharacterUI,
    modifier: Modifier = Modifier,
    onItemClick: (Int) -> Unit,
    onLevelClick: (Int) -> Unit = {}
) = GloomCard(
    modifier = modifier
        .clickable {
            onItemClick.invoke(character.id)
        },
    active = character.isAlive
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = modifier
                .size(52.dp)
                .background(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = RoundedCornerShape(8.dp),
                )
                .border(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.outline,
                    width = 1.dp
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.size(40.dp),
                painter = painterResource(character.characterClass.classType.toImage()),
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = character.name,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = character.characterClass.name,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        RoundButton(
            size = GloomSize.M,
            text = character.level.toString(),
            onClick = { onLevelClick(character.id) }
        )
    }
}

@Composable
fun CharacterItemWithDialog(
    character: CharacterUI,
    modifier: Modifier = Modifier,
    onSave: (Int, Int) -> Unit,
    onDelete: (Int) -> Unit,
    onLeave: (Int) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        CharacterEditDialog(
            character = character,
            onDismiss = {
                showDialog = false
            },
            onSave = { newLevel ->
                onSave(character.id, newLevel)
                showDialog = false
            },
            onDelete = {
                onDelete(character.id)
                showDialog = false
            },
            onLeave = {
                onLeave(character.id)
                showDialog = false
            }
        )
    }

    CharacterItem(
        character = character,
        modifier = modifier,
        onItemClick = { showDialog = true }
    )
}

@Preview
@Composable
private fun Sample() {
    GloomhavenHalperTheme {
        CharacterItem(
            character = CharacterUI.fixture(),
            onItemClick = {}
        )
    }
}