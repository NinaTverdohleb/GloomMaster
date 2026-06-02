package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.goods

import com.rumpilstilstkin.gloomhavenhelper.data.GoodsRepository
import com.rumpilstilstkin.gloomhavenhelper.data.LocaleRepository
import com.rumpilstilstkin.gloomhavenhelper.data.TeamRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Good
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import kotlin.collections.emptyList

class GetGoodsForCurrentTeamUseCase @Inject constructor(
    private val teamRepository: TeamRepository,
    private val goodsRepository: GoodsRepository,
    private val localeRepository: LocaleRepository,
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<List<Good>> =
        localeRepository.observeLocale.flatMapLatest { locale ->
            teamRepository.currentTeam
                .flatMapLatest { team ->
                    team?.let {
                        combine(
                            goodsRepository.getCharacterGoodIds(team.aliveCharacterIds),
                            goodsRepository.getGoodsForTeam(team.teamId, locale),
                        ) { characterGoodIds, allGoods ->
                            allGoods.filter { good -> good.id !in characterGoodIds }
                        }
                    } ?: flowOf(emptyList())
                }
        }
}
