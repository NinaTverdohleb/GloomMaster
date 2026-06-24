package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.text.image.TextWithImagesByCode
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterAction
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterStatType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.MonsterUnit
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomSize
import com.rumpilstilstkin.gloomhavenhelper.ui.components.NumberPickerProgress
import com.rumpilstilstkin.gloomhavenhelper.ui.icons.GameIcon.Companion.toGameIcon

@Composable
fun MonsterUnitCard(
    unit: MonsterUnit,
    availableEffects: Set<MonsterStatType>,
    modifier: Modifier = Modifier,
    isBoss: Boolean = false,
    changeLife: (unitNumber: Int, life: Int) -> Unit,
    switchEffect: (unitNumber: Int, effect: MonsterStatType) -> Unit,
    levelClick: (unit: MonsterUnit) -> Unit,
    deleteUnit: (unitNumber: Int) -> Unit,
) {
    UnitCard(modifier = modifier.clickable { levelClick(unit) }) {
        if (unit.immunity.isNotEmpty()) {
            Row(
                modifier =
                    Modifier
                        .padding(vertical = 4.dp, horizontal = 16.dp)
                        .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                unit.immunity.forEach { effect ->
                    val icon = effect.toGameIcon()
                    Icon(
                        modifier = Modifier.size(32.dp),
                        painter = painterResource(id = icon.imageRes),
                        contentDescription = null,
                        tint = icon.color ?: Color.White,
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                }
            }
            Spacer(
                modifier.height(8.dp),
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            if (!isBoss) {
                UnitNumberBadge(unit.number, unit.isSpecial)
            }

            Box(
                modifier =
                    Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp),
                contentAlignment = Alignment.Center,
            ) {
                NumberPickerProgress(
                    modifier = Modifier.fillMaxWidth(),
                    value = unit.currentLife,
                    onValueChange = { value -> changeLife(unit.number, value) },
                    intRange = 0..unit.maxLife,
                    size = GloomSize.S,
                    progressColor = MaterialTheme.colorScheme.error,
                    numberBack = {
                        Icon(
                            modifier = Modifier.size(52.dp),
                            painter = painterResource(id = R.drawable.ic_life),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.error,
                        )
                    },
                )
            }
            if (!isBoss) {
                Spacer(
                    modifier.width(8.dp),
                )
                IconButton(
                    onClick = { deleteUnit(unit.number) },
                    modifier = Modifier.size(24.dp),
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(24.dp),
                    )
                }
            }
        }
        Spacer(
            modifier.height(16.dp),
        )

        Row(
            modifier =
                Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            val immunitySet = unit.immunity.toSet()
            availableEffects.filter { it !in immunitySet }.forEach { effect ->
                val icon = effect.toGameIcon()
                val tint =
                    if (unit.effects.contains(effect)) {
                        icon.color
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    }
                IconButton(
                    onClick = { switchEffect(unit.number, effect) },
                    modifier = Modifier.size(36.dp),
                ) {
                    Icon(
                        modifier = Modifier.fillMaxSize(),
                        painter = painterResource(id = icon.imageRes),
                        contentDescription = null,
                        tint = tint ?: Color.White,
                    )
                }
            }
        }
        Spacer(
            modifier.height(8.dp),
        )
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors =
                cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                ),
        ) {
            Row(
                modifier =
                    Modifier
                        .padding(vertical = 4.dp, horizontal = 16.dp)
                        .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround,
            ) {
                unit.stats.forEach { stat ->
                    if (stat is MonsterAction.Action) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            val icon = stat.statType.toGameIcon()
                            Icon(
                                modifier = Modifier.size(28.dp),
                                painter = painterResource(id = icon.imageRes),
                                contentDescription = null,
                                tint = icon.color ?: Color.White,
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = stat.modifier,
                                fontSize = 24.sp,
                                color = MaterialTheme.colorScheme.onSurface,
                            )
                        }
                    }
                }
            }
        }

        val texts = unit.stats.filterIsInstance<MonsterAction.Text>()
        if (texts.isNotEmpty()) {
            Spacer(
                modifier.height(16.dp),
            )
            Card(
                modifier =
                    Modifier
                        .fillMaxWidth(),
                colors =
                    cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    ),
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                ) {
                    texts.forEachIndexed { index, stat ->
                        TextWithImagesByCode(
                            text = stat.content,
                        )
                        if (index != texts.lastIndex) {
                            Spacer(
                                modifier = Modifier.height(8.dp),
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EmptyMonsterUnitCard(modifier: Modifier = Modifier) {
    UnitCard(
        modifier = modifier,
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_no_units),
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colorScheme.primary,
            )
            Text(
                text = stringResource(R.string.no_enemies_added),
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
            )
            Text(
                text = stringResource(R.string.add_enemies_hint),
                fontSize = 9.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
private fun UnitCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) = Card(
    modifier = modifier,
    colors =
        cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
    border =
        BorderStroke(
            0.5.dp,
            MaterialTheme.colorScheme.outlineVariant,
        ),
) {
    Column(
        modifier = Modifier.padding(16.dp),
        content = content,
    )
}

@Composable
private fun UnitNumberBadge(
    number: Int,
    isElite: Boolean,
) {
    val textColor =
        if (isElite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
    val color =
        if (isElite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant

    Box(
        modifier =
            Modifier
                .size(36.dp)
                .border(
                    shape = RoundedCornerShape(10.dp),
                    color = color,
                    width = 1.dp,
                ).background(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = RoundedCornerShape(10.dp),
                ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = number.toString(),
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = textColor,
        )
    }
}

@Preview
@Composable
private fun MonsterUnitPreview() {
    GloomhavenMasterTheme {
        MonsterUnitCard(
            unit = MonsterUnit.fixture(1),
            isBoss = false,
            changeLife = { _, _ -> },
            switchEffect = { _, _ -> },
            deleteUnit = { _ -> },
            levelClick = {},
            availableEffects = MonsterStatType.mainEffectsPack,
        )
    }
}

@Preview
@Composable
private fun MonsterUnitBossPreview() {
    GloomhavenMasterTheme {
        MonsterUnitCard(
            unit = MonsterUnit.fixture(1),
            isBoss = true,
            changeLife = { _, _ -> },
            switchEffect = { _, _ -> },
            deleteUnit = { _ -> },
            levelClick = {},
            availableEffects = MonsterStatType.mainEffectsPack + MonsterStatType.fcEffectsPack,
        )
    }
}

@Preview
@Composable
private fun EmptyMonsterUnitCardPreview() {
    GloomhavenMasterTheme {
        EmptyMonsterUnitCard()
    }
}
