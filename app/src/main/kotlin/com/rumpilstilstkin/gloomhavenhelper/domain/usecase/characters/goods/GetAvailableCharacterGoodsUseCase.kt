package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.goods

import com.rumpilstilstkin.gloomhavenhelper.data.CharacterRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Good
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.goods.GetGoodsForCurrentTeamUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetAvailableCharacterGoodsUseCase @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val getGoodsForCurrentTeamUseCase: GetGoodsForCurrentTeamUseCase
) {
    operator fun invoke(characterId: Int): Flow<List<Good>> =
        combine(
            getGoodsForCurrentTeamUseCase(),
            characterRepository.getCharacterGoodsFlow(characterId)
        ) { teamGoods, characterGoods ->
            teamGoods.filter { good -> good.id !in characterGoods.map { it.id } }
        }
}