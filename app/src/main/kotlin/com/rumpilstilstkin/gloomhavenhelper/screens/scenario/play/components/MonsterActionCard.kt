package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.screens.models.MonsterAbilityCard
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenMasterTheme

@Composable
fun MonsterActionCard(
    card: MonsterAbilityCard?,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        if (card == null) {
            Image(
                modifier= Modifier.size(width = 360.dp, height = 280.dp),
                painter = painterResource(id = R.drawable.ic_deck_back),
                contentDescription = "null"
            )
        } else {
            AsyncImage(
                modifier= Modifier.size(width = 360.dp, height = 280.dp),
                model = card.imagePath,
                contentDescription = null
            )
        }
    }
}

@Preview
@Composable
private fun MonsterActionCardPreview() {
    GloomhavenMasterTheme {
        MonsterActionCard(
            card = null
        )
    }
}