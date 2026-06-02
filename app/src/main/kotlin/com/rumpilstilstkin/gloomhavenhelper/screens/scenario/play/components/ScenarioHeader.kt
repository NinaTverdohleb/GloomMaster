package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Magic
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.state.getChargeImage
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.state.toIcon
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenMasterTheme

@Composable
internal fun ScenarioHeader(
    level: Int,
    exp: Int,
    gold: Int,
    trapDamage: Int,
    magics: Map<Magic, Int>,
    title: String,
    modifier: Modifier = Modifier,
    clickMagic: (magic: Magic) -> Unit,
) = Column(
    modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp)
) {
    var showStatsDialog by remember { mutableStateOf(false) }

    if(showStatsDialog){
        ScenarioStatsDialog(
            level = level,
            exp = exp,
            gold = gold,
            trapDamage = trapDamage,
            onDismiss = { showStatsDialog = false }
        )
    }

    Text(
        text = title,
        style = MaterialTheme.typography.headlineSmall,
        color = MaterialTheme.colorScheme.onSurface
    )
    Spacer(
        modifier = Modifier.height(8.dp)
    )
    ScenarioStatsRow(
        modifier = Modifier.clickable{
            showStatsDialog = true
        },
        level = level,
        exp = exp,
        gold = gold,
        trapDamage = trapDamage
    )
    Spacer(
        modifier = Modifier.height(16.dp)
    )
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        magics.keys.forEach { magic ->
            IconButton(
                onClick = { clickMagic(magic) },
                modifier = Modifier.size(52.dp),
            ) {
                val charge =getChargeImage(magics[magic])
                charge?.let { image ->
                    Icon(
                        painter = painterResource(id = image),
                        contentDescription = null,
                        modifier = modifier
                            .size(52.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                val icon = magic.toIcon()
                Icon(
                    painter = painterResource(id = icon.imageRes),
                    contentDescription = stringResource(icon.titleRes),
                    modifier = modifier
                        .size(32.dp),
                    tint = icon.color?: Color.White
                )
            }
        }
    }
}

@Preview
@Composable
private fun ScenarioHeaderPreview() {
    GloomhavenMasterTheme {
        ScenarioHeader(
            level = 1,
            exp = 100,
            gold = 100,
            trapDamage = 3,
            magics = mapOf(
                Magic.FIRE to 0,
                Magic.FROST to 2,
                Magic.AIR to 0,
                Magic.EARTH to 2,
                Magic.SUN to 1,
                Magic.MOON to 2,
            ),
            title = "Black Barrow",
            clickMagic = {}
        )
    }
}