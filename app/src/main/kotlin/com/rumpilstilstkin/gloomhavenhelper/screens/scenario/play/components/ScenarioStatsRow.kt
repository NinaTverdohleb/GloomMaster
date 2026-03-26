package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomVariantCard
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenMasterTheme

@Composable
fun ScenarioStatsRow(
    level: Int,
    exp: Int,
    gold: Int,
    trapDamage: Int,
    modifier: Modifier = Modifier
) = GloomVariantCard(
    modifier = modifier.fillMaxWidth(),
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        StatItem(
            value = level.toString(),
            image = R.drawable.ic_level
        )

        StatItem(
            value = exp.toString(),
            image = R.drawable.ic_exp
        )

        StatItem(
            value = gold.toString(),
            image = R.drawable.ic_gold
        )

        StatItem(
            value = trapDamage.toString(),
            image = R.drawable.ic_trap
        )

    }
}


@Composable
internal fun StatItem(
    value: String,
    image: Int,
) {
    Row {
        Icon(painter = painterResource(id = image), contentDescription = null, tint = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.width(4.dp))
        Text(value)
    }

}

@Preview
@Composable
private fun ScenarioStatsRowPreview() {
    GloomhavenMasterTheme {
        ScenarioStatsRow(
            level = 1,
            exp = 100,
            gold = 100,
            trapDamage = 10
        )
    }
}