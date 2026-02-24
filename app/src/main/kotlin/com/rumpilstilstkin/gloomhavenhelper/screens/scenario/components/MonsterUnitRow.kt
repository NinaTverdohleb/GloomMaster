package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.unit.sp
import com.rumpilstilstkin.gloomhavenhelper.screens.models.ActionType
import com.rumpilstilstkin.gloomhavenhelper.screens.models.MonsterUnit
import com.rumpilstilstkin.gloomhavenhelper.screens.models.UnitStat
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenHalperTheme
import com.rumpilstilstkin.gloomhavenhelper.ui.view.NumberPicker
import com.rumpilstilstkin.gloomhavenhelper.ui.view.PickerSize

@Composable
fun MonsterUnitRow(
    unit: MonsterUnit,
    changeUnitLife: (value: Int) -> Unit,
    modifier: Modifier = Modifier
) = Row(
    modifier = modifier
        .fillMaxWidth()
        .background(
            color = if(unit.isSpecial) {
                MaterialTheme.colorScheme.secondaryContainer
            } else {
                MaterialTheme.colorScheme.onBackground
            },
        )
        .padding(6.dp),
    verticalAlignment = Alignment.CenterVertically,
) {
    Text(
        fontSize = 22.sp,
        text = "${unit.number}.",
        style = MaterialTheme.typography.bodyLarge
    )
    Spacer(modifier = Modifier.width(16.dp))
    unit.stats.forEach { stat ->
        Row {
            Icon(
                modifier = Modifier.size(22.dp),
                painter = painterResource(id = stat.type.iconRes),
                contentDescription = stat.type.title
            )
            Spacer(modifier = Modifier.width(2.dp))
            Text(text = stat.value.toString())
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
    Spacer(
        modifier = Modifier.weight(1f)
    )

    NumberPicker(
        value = unit.currentLife,
        intRange = 0..unit.maxLife,
        size = PickerSize.S,
        onValueChange = changeUnitLife
    )
}

@Preview
@Composable
private fun MonsterUnitRowPreview() {
    GloomhavenHalperTheme {
        MonsterUnitRow(
            unit = MonsterUnit(
                number = 2,
                isSpecial = true,
                currentLife = 10,
                maxLife = 10,
                stats = listOf(
                    UnitStat(
                        type = ActionType.MOVE,
                        value = 3
                    ),
                    UnitStat(
                        type = ActionType.ATTACK,
                        value = 4
                    ),
                    UnitStat(
                        type = ActionType.SHIELD,
                        value = 2
                    ),
                )
            ),
            changeUnitLife = {}
        )
    }
}