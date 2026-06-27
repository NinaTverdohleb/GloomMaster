package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector2D
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.round
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.text.GloomHeader
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.state.UnitCompact
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.coroutines.launch

private val CIRCLE_SIZE = 56.dp
private val GRID_GAP = 12.dp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun UnitSelector(
    units: ImmutableSet<UnitCompact>,
    modifier: Modifier = Modifier,
    columns: Int = 10,
    initiallyOpenNumber: Int? = units.firstOrNull()?.number,
    onSelect: (UnitCompact) -> Unit
) {
    var selectedNumber by remember {
        mutableStateOf(initiallyOpenNumber)
    }

    val selected =
        remember(units, selectedNumber) {
            units.firstOrNull { it.number == selectedNumber } ?: units.firstOrNull()
        }

    val ordered =
        remember(units, selected?.number) {
            val unit = selected ?: return@remember units
            listOf(unit) + units.filterNot { it.number == unit.number }
        }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        GloomHeader(text = stringResource(R.string.choose_unit))
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            maxItemsInEachRow = columns,
            horizontalArrangement = Arrangement.spacedBy(GRID_GAP),
            verticalArrangement = Arrangement.spacedBy(GRID_GAP),
        ) {
            ordered.forEach { unit ->
                key(unit.number) {
                    UnitCircle(
                        unit = unit,
                        selected = unit == selected,
                        modifier = Modifier.animatePlacement(),
                        onClick = {
                            onSelect(unit)
                            selectedNumber = unit.number
                        },
                    )
                }
            }
        }
    }
}

@Composable
private fun UnitCircle(
    unit: UnitCompact,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    selected: Boolean = false,
) {
    Box(
        modifier =
            modifier
                .size(CIRCLE_SIZE)
                .clip(CircleShape)
                .clickable { onClick() },
        contentAlignment = Alignment.Center,
    ) {
        UnitNumber(
            selected = selected,
            unitNumber = unit.number,
            isSpecial = unit.isSpecial,
        )
    }
}

private fun Modifier.animatePlacement(): Modifier =
    composed {
        val scope = rememberCoroutineScope()
        var targetOffset by remember { mutableStateOf(IntOffset.Zero) }
        var animatable by remember {
            mutableStateOf<Animatable<IntOffset, AnimationVector2D>?>(null)
        }
        this
            .onPlaced { coordinates ->
                targetOffset = coordinates.positionInParent().round()
            }
            .offset {
                val anim =
                    animatable
                        ?: Animatable(targetOffset, IntOffset.VectorConverter).also {
                            animatable = it
                        }
                if (anim.targetValue != targetOffset) {
                    scope.launch {
                        anim.animateTo(targetOffset, spring(stiffness = Spring.StiffnessMediumLow))
                    }
                }
                anim.value - targetOffset
            }
    }

@Preview
@Composable
private fun MonsterUnitSelectorPreview() {
    val units =
        persistentSetOf(
            UnitCompact(1, true),
            UnitCompact(2, true),
            UnitCompact(3, false),
            UnitCompact(4, false),
            UnitCompact(5, false),
            UnitCompact(6, false),
            UnitCompact(7, false),
        )

    GloomhavenMasterTheme {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surfaceContainer)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
        ) {
            UnitSelector(
                units = units,
                onSelect = {}
            )
        }
    }
}
