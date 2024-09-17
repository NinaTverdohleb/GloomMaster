package com.rumpilstilstkin.gloomhavenhelper.ui.characters

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter.Companion.tint
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenHalperTheme


@Composable
fun CharacterItem(
    characterId: Int,
    @DrawableRes
    imageRes: Int,
    name: String,
    level: Int,
    modifier: Modifier = Modifier,
    isAlive: Boolean = true,
    onClick: (Int) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(
                enabled = isAlive,
            ) {
                onClick.invoke(characterId)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = "",
            colorFilter = tint(MaterialTheme.colorScheme.onBackground),
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = name,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = level.toString(),
            textAlign = TextAlign.End,
        )
    }
}

@Composable
fun CharacterItemWithDialog(
    characterId: Int,
    @DrawableRes
    imageRes: Int,
    name: String,
    level: Int,
    modifier: Modifier = Modifier,
    isAlive: Boolean = true,
    onSave: (Int, Int) -> Unit,
    onDelete: (Int) -> Unit,
    onLeave: (Int) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    CharacterEditDialog(
        characterName = name,
        classImage = imageRes,
        showDialog = showDialog,
        startLevel = level,
        onDismiss = {
            showDialog = false
        },
        onSave = { newLevel ->
            onSave(characterId, newLevel)
            showDialog = false
        },
        onDelete = {
            onDelete(characterId)
            showDialog = false
        },
        onLeave = {
            onLeave(characterId)
            showDialog = false
        }
    )

    CharacterItem(
        characterId = characterId,
        imageRes = imageRes,
        name = name,
        level = level,
        isAlive = isAlive,
        modifier = modifier
    ) {
        showDialog = true
    }
}

@Preview
@Composable
private fun Sample() {
    GloomhavenHalperTheme {
        CharacterItemWithDialog(
            imageRes = R.drawable.br,
            name = "Супер мега длинное имя пипец какое невыносимо огромное",
            level = 6,
            characterId = 0,
            onSave = { _, _ -> },
            onDelete = { _ -> },
            onLeave = { _ -> }
        )
    }

}