package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.GloomCard
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.counter.GloomCounterFull
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.counter.GloomGloomCounterProgress
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.stiker.GloomStickerContent
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.stiker.GloomStickerText
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.stiker.GloomStickerType
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterAction
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterStatType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.MonsterUnit
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.toPersistentSet

@Composable
fun RegularMonsterUnit(
    unit: MonsterUnit,
    effects: ImmutableSet<MonsterStatType>,
    modifier: Modifier = Modifier,
    changeLife: (unit: MonsterUnit, life: Int) -> Unit,
    switchEffect: (unit: MonsterUnit, effect: MonsterStatType) -> Unit,
    levelClick: (unit: MonsterUnit) -> Unit,
    deleteUnit: (unit: MonsterUnit) -> Unit,
) {
    val availableEffects = (effects - unit.immunity).toPersistentSet()
    Column(modifier = modifier.fillMaxWidth()) {

        TopStickers(
            isNew = unit.isNew,
            level = unit.level,
            onLevel = { levelClick(unit) },
            delete = { deleteUnit(unit) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
        )
        GloomCard {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Life(
                    unitNumber = unit.number,
                    isSpecial = unit.isSpecial,
                    currentLife = unit.currentLife,
                    maxLife = unit.maxLife,
                    changeLife = { changeLife(unit, it) }
                )
                ActionsRow(actions = unit.stats)

                EffectsRow(
                    available = availableEffects,
                    active = unit.effects,
                    onToggle = { effect -> switchEffect(unit, effect) },
                )
            }
        }
        if (unit.immunity.isNotEmpty()) {
            BottomStickers(
                immunity = unit.immunity,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            )
        }
    }
}

@Composable
private fun Life(
    modifier: Modifier = Modifier,
    unitNumber: Int,
    isSpecial: Boolean,
    currentLife: Int,
    maxLife: Int,
    changeLife: (Int) -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            UnitNumber(
                unitNumber = unitNumber,
                isSpecial = isSpecial
            )
            Text(
                text = "Unit",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f),
            )

            GloomCounterFull(
                value = currentLife,
                intRange = 0..maxLife,
                onValueChange = changeLife,
            )
        }
        GloomGloomCounterProgress(
            value = currentLife,
            intRange = 0..maxLife,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun ActionsRow(actions: ImmutableList<MonsterAction>) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        actions.forEach { stat ->
            if (stat is MonsterAction.Action) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    val icon = stat.statType.toGameIcon()
                    Icon(
                        modifier = Modifier.size(26.dp),
                        painter = icon.image.painter(),
                        contentDescription = stringResource(icon.titleRes),
                        tint = MaterialTheme.colorScheme.onSurface,
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun EffectsRow(
    available: ImmutableSet<MonsterStatType>,
    active: ImmutableSet<MonsterStatType>,
    onToggle: (MonsterStatType) -> Unit,
) {
    Row(
        modifier =
            Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        available.forEach { effect ->
            val icon = effect.toGameIcon()
            val tint =
                if (active.contains(effect)) {
                    icon.color
                } else {
                    MaterialTheme.colorScheme.surfaceBright
                }
            IconButton(
                onClick = { onToggle(effect) },
                modifier = Modifier.size(28.dp),
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    painter = icon.image.painter(),
                    contentDescription = null,
                    tint = tint,
                )
            }
        }
    }
}

@Composable
private fun TopStickers(
    isNew: Boolean,
    level: Int,
    onLevel: () -> Unit,
    delete: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top,
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (isNew) {
                GloomStickerText(
                    text = "New",
                    backgroundColor = MaterialTheme.colorScheme.primary,
                    textColor = MaterialTheme.colorScheme.surfaceVariant
                )
            }
            GloomStickerText(
                text = "Level $level",
                onClick = onLevel
            )
        }

        GloomStickerText(
            backgroundColor = MaterialTheme.colorScheme.error,
            textColor = MaterialTheme.colorScheme.onError,
            text = stringResource(R.string.delete),
            onClick = delete
        )
    }
}

@Composable
private fun BottomStickers(
    immunity: ImmutableSet<MonsterStatType>,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.Top,
    ) {
        GloomStickerContent(
            type = GloomStickerType.Bottom,
            body = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Immunity",
                        color = MaterialTheme.colorScheme.outline,
                        style = MaterialTheme.typography.bodySmall
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        immunity.forEach { effect ->
                            val icon = effect.toGameIcon()
                            Icon(
                                modifier = Modifier.size(20.dp),
                                painter = icon.image.painter(),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.outline,
                            )
                        }
                    }
                }
            }
        )
    }
}

@Preview
@Composable
private fun RegularMonsterUnitPreview() {
    GloomhavenMasterTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            RegularMonsterUnit(
                unit = MonsterUnit.fixture(1),
                changeLife = { _, _ -> },
                switchEffect = { _, _ -> },
                deleteUnit = { _ -> },
                levelClick = {},
                effects = (MonsterStatType.mainEffectsPack + MonsterStatType.fcEffectsPack).toPersistentSet(),
            )
        }
    }
}
