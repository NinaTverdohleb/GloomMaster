package com.rumpilstilstkin.gloomhavenhelper.ui.team

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AdminPanelSettings
import androidx.compose.material.icons.rounded.Eco
import androidx.compose.material.icons.rounded.ElectricBolt
import androidx.compose.material.icons.rounded.Whatshot
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.DifficultyLevel

@Composable
fun DifficultyLevel.toLabel() =
    when (this) {
        DifficultyLevel.EASY -> stringResource(R.string.difficulty_easy)
        DifficultyLevel.NORMAL -> stringResource(R.string.difficulty_normal)
        DifficultyLevel.HARD -> stringResource(R.string.difficulty_hard)
        DifficultyLevel.VERY_HARD -> stringResource(R.string.difficulty_very_hard)
    }

@Composable
fun DifficultyLevel.toImage() =
    when (this) {
        DifficultyLevel.EASY -> Icons.Rounded.Eco to Color(0xFF3B6D11)
        DifficultyLevel.NORMAL -> Icons.Rounded.Whatshot to Color(0xFF185FA5)
        DifficultyLevel.HARD -> Icons.Rounded.ElectricBolt to Color(0xFF854F0B)
        DifficultyLevel.VERY_HARD -> Icons.Rounded.AdminPanelSettings to Color(0xFFA32D2D)
    }
