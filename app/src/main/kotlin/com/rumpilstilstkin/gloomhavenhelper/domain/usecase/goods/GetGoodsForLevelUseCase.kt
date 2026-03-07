package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.goods

import javax.inject.Inject

class GetGoodsForLevelUseCase @Inject constructor() {
    operator fun invoke(prosperityLevel: Int): List<Int> = when(prosperityLevel){
        1 -> (1..14).toList()
        2 -> (15..21).toList()
        3 -> (22..28).toList()
        4 -> (29..35).toList()
        5 -> (36..42).toList()
        6 -> (43..49).toList()
        7 -> (50..56).toList()
        8 -> (57..63).toList()
        9 -> (64..70).toList()
        else -> emptyList()
    }
}