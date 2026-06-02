package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario

import com.rumpilstilstkin.gloomhavenhelper.data.LocaleRepository
import com.rumpilstilstkin.gloomhavenhelper.data.MonsterRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterStats
import javax.inject.Inject

class GetMonsterStatsForLevelUseCase @Inject constructor(
    private val monsterRepository: MonsterRepository,
    private val localeRepository: LocaleRepository,
) {
    suspend operator fun invoke(
        monsterSlug: String,
        level: Int,
        isElite: Boolean,
    ): MonsterStats =
        monsterRepository.getMonsterStats(
            monsterSlug = monsterSlug,
            level = level,
            isElite = isElite,
            locale = localeRepository.getCurrentLocale(),
        )
}
