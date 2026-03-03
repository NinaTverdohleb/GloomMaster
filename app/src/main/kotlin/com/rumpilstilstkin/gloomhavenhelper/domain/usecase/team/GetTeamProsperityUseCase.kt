package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team

import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Prosperity
import javax.inject.Inject

class GetTeamProsperityUseCase @Inject constructor() {
    operator fun invoke(prosperityValue: Int): Prosperity = when (prosperityValue) {
        in 0..3 -> Prosperity(
            prosperitySource = prosperityValue,
            prosperityLevel = 1,
            prosperityLevelValue = prosperityValue,
            prosperityRange = IntRange(0, 3)
        )

        in 4..8 -> Prosperity(
            prosperitySource = prosperityValue,
            prosperityLevel = 2,
            prosperityLevelValue = prosperityValue.minus(4),
            prosperityRange = IntRange(0, 4)
        )

        in 9..14 -> Prosperity(
            prosperitySource = prosperityValue,
            prosperityLevel = 3,
            prosperityLevelValue = prosperityValue.minus(9),
            prosperityRange = IntRange(0, 5)
        )

        in 15..21 -> Prosperity(
            prosperitySource = prosperityValue,
            prosperityLevel = 4,
            prosperityLevelValue = prosperityValue.minus(15),
            prosperityRange = IntRange(0, 6)
        )

        in 22..29 -> Prosperity(
            prosperitySource = prosperityValue,
            prosperityLevel = 5,
            prosperityLevelValue = prosperityValue.minus(22),
            prosperityRange = IntRange(0, 7)
        )

        in 30..38 -> Prosperity(
            prosperitySource = prosperityValue,
            prosperityLevel = 6,
            prosperityLevelValue = prosperityValue.minus(30),
            prosperityRange = IntRange(0, 8)
        )

        in 39..49 -> Prosperity(
            prosperitySource = prosperityValue,
            prosperityLevel = 7,
            prosperityLevelValue = prosperityValue.minus(39),
            prosperityRange = IntRange(0, 10)
        )

        in 50..63 -> Prosperity(
            prosperitySource = prosperityValue,
            prosperityLevel = 8,
            prosperityLevelValue = prosperityValue.minus(50),
            prosperityRange = IntRange(0, 13)
        )

        else -> Prosperity(
            prosperitySource = prosperityValue,
            prosperityLevel = 9,
            prosperityLevelValue = 0,
            prosperityRange = IntRange(0, 0)
        )
    }
}