package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.goods

import com.rumpilstilstkin.gloomhavenhelper.data.GoodsRepository
import com.rumpilstilstkin.gloomhavenhelper.data.TeamRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Good
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.collections.distinctBy

class GetGoodsForCurrentTeamUseCase @Inject constructor(
    private val teamRepository: TeamRepository,
    private val goodsRepository: GoodsRepository,
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<List<Good>> =
        teamRepository.currentTeamId
            .flatMapLatest { teamId ->
                goodsRepository.getGoodsForTeam(teamId)
                    .map { goods -> goods.distinctBy { it.number } }
            }
}
