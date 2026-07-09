package com.rumpilstilstkin.gloommaster.domain.usecase.team

import javax.inject.Inject

class GetNextChurchValueUseCase @Inject constructor() {
    operator fun invoke(churchValue: Int): Int =
        when {
            churchValue < 150 -> 150
            churchValue < 200 -> 200
            churchValue < 250 -> 250
            churchValue < 300 -> 300
            churchValue < 350 -> 350
            churchValue < 400 -> 400
            churchValue < 500 -> 500
            churchValue < 600 -> 600
            churchValue < 700 -> 700
            churchValue < 800 -> 800
            churchValue < 900 -> 900
            churchValue < 1000 -> 1000
            else -> -1
        }
}
