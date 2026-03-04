package com.rumpilstilstkin.gloomhavenhelper.screens.start.team.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomCard
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenHalperTheme

@Composable
internal fun GlobalAchievement(
    globalAchievements: String,
    modifier: Modifier = Modifier
) = GloomCard(
    modifier = modifier
){
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = "Общие достижения",
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.primary,
        textAlign = TextAlign.Center
    )
    Spacer(
        modifier = Modifier.height(16.dp)
    )
    Text(
        text = globalAchievements
            .split(",")
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .joinToString("\n") { it.trim() },
        style = MaterialTheme.typography.bodyMedium,
    )
}

@Composable
internal fun TeamAchievement(
    teamAchievements: String,
    modifier: Modifier = Modifier
) = GloomCard(
    modifier = modifier
){
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = "Достижения отряда",
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.primary,
        textAlign = TextAlign.Center
    )
    Spacer(
        modifier = Modifier.height(16.dp)
    )
    Text(
        text = teamAchievements
            .split(",")
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .joinToString("\n") { it.trim() },
        style = MaterialTheme.typography.bodyMedium,
    )
}

@Preview
@Composable
private fun GlobalAchievementPreview() {
    GloomhavenHalperTheme {
        GlobalAchievement(
            modifier = Modifier.fillMaxWidth(),
            globalAchievements = " Нашествие мертвецов, Голос: умолк",
        )
    }
}

@Preview
@Composable
private fun TeamAchievementPreview() {
    GloomhavenHalperTheme {
        TeamAchievement(
            modifier = Modifier.fillMaxWidth(),
            teamAchievements = "Нашествие мертвецов, Голос: умолк",
        )
    }
}