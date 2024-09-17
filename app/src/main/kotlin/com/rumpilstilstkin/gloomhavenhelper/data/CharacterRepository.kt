package com.rumpilstilstkin.gloomhavenhelper.data

import com.rumpilstilstkin.gloomhavenhelper.bd.dao.CharacterClassDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.CharacterDao
import com.rumpilstilstkin.gloomhavenhelper.data.mappers.toBd
import com.rumpilstilstkin.gloomhavenhelper.data.mappers.toDomain
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterForSave
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CharacterRepository @Inject constructor(
    private val characterDao: CharacterDao,
    private val classDao: CharacterClassDao
) {

    fun getCharacterByTeamId(teamId: Int): Flow<List<CharacterInfo>> =
        characterDao.findByTeamIdFlow(teamId).map { list ->
            list.map {
                val classBd = classDao.findById(it.classId)
                it.toDomain(classBd)
            }
        }

    suspend fun addCharacter(character: CharacterForSave, teamId: Int) {
            characterDao.insert(character.toBd(teamId))
    }

    suspend fun deleteCharacter(id: Int) {
        characterDao.deleteById(id)
    }

    suspend fun updateLevel(id: Int, level: Int) {
        characterDao.getCharacterById(id).let {
            characterDao.update(it.copy(level = level))
        }
    }

    suspend fun leaveCharacter(id: Int) {
        characterDao.getCharacterById(id).let {
            characterDao.update(it.copy(isAlive = false))
        }
    }
}