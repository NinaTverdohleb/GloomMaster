package com.rumpilstilstkin.gloomhavenhelper.data

import com.rumpilstilstkin.gloomhavenhelper.bd.dao.PerksDao
import com.rumpilstilstkin.gloomhavenhelper.data.mappers.toDomain
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterClassType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PerksRepository @Inject constructor(
    private val perksDao: PerksDao
) {
    suspend fun getPerksForCharacterClass(characterType: CharacterClassType) =
        perksDao
            .getPerksByCharacterClass(characterType.name)
            .map { it.toDomain() }
}