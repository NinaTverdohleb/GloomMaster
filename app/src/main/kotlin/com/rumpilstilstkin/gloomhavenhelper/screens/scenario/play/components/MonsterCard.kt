@file:JvmName("MonsterCardKt")

package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.GloomCard
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.items.LeftItemNumber
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.ActionIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.AppIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterCard
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.state.sampleDeck

@Composable
fun MonsterCard(
    card: MonsterCard?,
    name: String,
    isFly: Boolean,
    modifier: Modifier = Modifier,
    delete: () -> Unit,
    onAddUnit: (() -> Unit)? = null,
) = GloomCard {
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            LeftItemNumber(
                number = card?.initiative?.toString() ?: "?",
                contentColor = MaterialTheme.colorScheme.surfaceTint,
            )
            Spacer(Modifier.width(12.dp))
            if (isFly) {
                Icon(
                    modifier = Modifier.size(28.dp),
                    painter = ActionIcon.Fly.painter(),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface,
                )
                Spacer(Modifier.width(4.dp))
            }
            Text(
                text = name,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Spacer(Modifier.weight(1f))
            if (onAddUnit != null) {
                IconButton(
                    onClick = onAddUnit,
                    modifier = Modifier.size(28.dp),
                ) {
                    Icon(
                        painter = AppIcon.Plus.painter(),
                        contentDescription = stringResource(R.string.add),
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.size(24.dp),
                    )
                }
                Spacer(Modifier.width(20.dp))
            }
            IconButton(
                onClick = delete,
                modifier = Modifier.size(28.dp),
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(R.string.delete),
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(24.dp),
                )
            }
        }
        if (card != null) {
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.outline,
            )
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                card.actions.forEach {
                    ActionMonsterEffect(it)
                }
            }
        }
    }
}

@Preview(heightDp = 2000)
@Composable
private fun MonsterCardPreview() {
    GloomhavenMasterTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            MonsterCard(
                card = null,
                delete = {},
                name = "Bandit Guard",
                isFly = true,
                onAddUnit = {},
            )
            sampleDeck.forEach { card ->
                MonsterCard(
                    card = card,
                    delete = {},
                    name = "Bandit Guard",
                    isFly = true,
                    onAddUnit = {},
                )
            }
        }
    }
}
