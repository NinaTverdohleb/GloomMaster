package com.rumpilstilstkin.gloomhavenhelper.data

import com.rumpilstilstkin.gloomhavenhelper.bd.dao.TeamCharacterClassDao
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.TeamCharacterClassBd
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterClassType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterClassRepository @Inject constructor(
    private val teamCharacterClassDao: TeamCharacterClassDao,
) {
    fun getAllClasses(): List<CharacterClassType> = CharacterClassType.entries

    fun getAvailableClassesForTeam(teamId: Int): Flow<List<CharacterClassType>> =
        teamCharacterClassDao
            .getClassTypesForTeam(teamId)
            .map { list -> list.map { CharacterClassType.valueOf(it) } }

    suspend fun addAvailableClass(teamId: Int, type: CharacterClassType) {
        teamCharacterClassDao.insert(
            TeamCharacterClassBd(
                teamId = teamId,
                characterType = type.name,
            )
        )
    }

    suspend fun removeAvailableClass(teamId: Int, type: CharacterClassType) {
        teamCharacterClassDao.delete(teamId, type.name)
    }

    suspend fun addAvailableClasses(teamId: Int, types: List<CharacterClassType>) {
        val entities = types.map {
            TeamCharacterClassBd(
                teamId = teamId,
                characterType = it.name,
            )
        }.toTypedArray()
        teamCharacterClassDao.insertAll(*entities)
    }
}