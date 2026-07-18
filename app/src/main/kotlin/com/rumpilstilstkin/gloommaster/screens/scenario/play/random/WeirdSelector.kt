package com.rumpilstilstkin.gloommaster.screens.scenario.play.random

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.rumpilstilstkin.gloommaster.R

@Composable
fun Weird(result: Int): String =
    when (result) {
        1 -> stringResource(R.string.weird_1)
        2 -> stringResource(R.string.weird_2)
        3 -> stringResource(R.string.weird_3)
        4 -> stringResource(R.string.weird_4)
        5 -> stringResource(R.string.weird_5)
        6 -> stringResource(R.string.weird_6)
        7 -> stringResource(R.string.weird_7)
        8 -> stringResource(R.string.weird_8)
        9 -> stringResource(R.string.weird_9)
        10 -> stringResource(R.string.weird_10)
        11 -> stringResource(R.string.weird_11)
        12 -> stringResource(R.string.weird_12)
        13 -> stringResource(R.string.weird_13)
        14 -> stringResource(R.string.weird_14)
        15 -> stringResource(R.string.weird_15)
        else -> stringResource(R.string.weird_16)
    }
