package com.rumpilstilstkin.gloomhavenhelper.ui.team

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.LevelIcon
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
        DifficultyLevel.EASY -> LevelIcon.Easy
        DifficultyLevel.NORMAL -> LevelIcon.Normal
        DifficultyLevel.HARD -> LevelIcon.Hard
        DifficultyLevel.VERY_HARD -> LevelIcon.Hero
    }
