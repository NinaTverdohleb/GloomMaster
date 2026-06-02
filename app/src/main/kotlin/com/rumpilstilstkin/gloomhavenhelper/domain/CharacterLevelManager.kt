package com.rumpilstilstkin.gloomhavenhelper.domain

fun getNextLevel(level: Int) =
    when (level) {
        1 -> 45
        2 -> 95
        3 -> 150
        4 -> 210
        5 -> 275
        6 -> 345
        7 -> 420
        else -> 500
    }

fun getExpForLevel(level: Int) =
    when (level) {
        1 -> 0
        2 -> 45
        3 -> 95
        4 -> 150
        5 -> 210
        6 -> 275
        7 -> 345
        8 -> 420
        else -> 500
    }
