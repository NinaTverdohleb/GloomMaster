package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team

import javax.inject.Inject

class GetDiscountByReputationUseCase @Inject constructor() {
    operator fun invoke(reputation: Int): Int = when (reputation) {
        in -20..-19 -> 5
        in -18..-15 -> 4
        in -14..-11 -> 3
        in -10..-7 -> 2
        in -6..-3 -> 1
        in -2..2 -> 0
        in 3..6 -> -1
        in 7..10 -> -2
        in 11..14 -> -3
        in 15..18 -> -4
        in 19..20 -> -5
        else -> 0
    }
}