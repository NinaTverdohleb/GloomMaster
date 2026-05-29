package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.goods

import com.rumpilstilstkin.gloomhavenhelper.data.GoodsRepository
import com.rumpilstilstkin.gloomhavenhelper.data.LocaleRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Good
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

class GetCharacterGoodsUseCase @Inject constructor(
    private val goodsRepository: GoodsRepository,
    private val localeRepository: LocaleRepository

) {

    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(characterId: Int): Flow<List<Good>> =
        localeRepository.observeLocale.flatMapLatest { locale ->
            goodsRepository.getCharacterGoods(characterId, locale)
        }
}