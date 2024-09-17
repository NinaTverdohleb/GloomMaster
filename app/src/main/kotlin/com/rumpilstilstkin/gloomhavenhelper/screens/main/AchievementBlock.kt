package com.rumpilstilstkin.gloomhavenhelper.screens.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenHalperTheme

@Composable
fun AchievementBlock(
    globalAchievements: String,
    teamAchievements: String,
    modifier: Modifier = Modifier,
    onGlobalAchievementsClick: () -> Unit = {},
    onTeamAchievementsClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
    ) {
        if (globalAchievements.isNotBlank()) {
            AchievementBlock(title = "Общие достижения", achievement = globalAchievements){
                onGlobalAchievementsClick()
            }
        }
        if (teamAchievements.isNotBlank()) {
            AchievementBlock(title = "Достижения отряда", achievement = teamAchievements){
                onTeamAchievementsClick()
            }
        }
    }
}

@Composable
fun AchievementBlock(
    title: String,
    achievement: String,
    modifier: Modifier = Modifier,
    onAchievementClick: () -> Unit = {}
) {
    Column(
        modifier = modifier.clickable { onAchievementClick() }
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
        )
        Text(
            text = achievement.split(",").map { it.trim() }.joinToString("\n"),
            style = MaterialTheme.typography.bodyMedium,
        )
        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider()
    }
}

@Preview
@Composable
private fun Sample() {
    GloomhavenHalperTheme {
        AchievementBlock(
            globalAchievements = "Нашествие мертвецов, Голос: умолк",
            teamAchievements = "Первые шаги, Сбежавшая торговка"
        )
    }

}