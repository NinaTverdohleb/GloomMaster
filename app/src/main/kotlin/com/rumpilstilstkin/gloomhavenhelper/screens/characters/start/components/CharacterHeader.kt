package com.rumpilstilstkin.gloomhavenhelper.screens.characters.start.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.screens.models.CharacterClassTypeUI
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomSize
import com.rumpilstilstkin.gloomhavenhelper.ui.components.RoundButton
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenHalperTheme


@Composable
internal fun CharacterHeader(
    characterClass: CharacterClassTypeUI,
    name: String,
    level: Int,
    modifier: Modifier = Modifier,
    onNameClick: () -> Unit,
    clickLevel: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                painter = painterResource(characterClass.image),
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clickable { onNameClick() },
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = characterClass.title,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            RoundButton(
                text = level.toString(),
                size = GloomSize.M,
                onClick = clickLevel
            )

        }
    }
}

@Preview
@Composable
private fun CharacterHeaderPreview() {
    GloomhavenHalperTheme {
        CharacterHeader(
            characterClass = CharacterClassTypeUI.Brute,
            name = "Warrior",
            level = 10,
            onNameClick = { },
            clickLevel = { }
        )
    }

}