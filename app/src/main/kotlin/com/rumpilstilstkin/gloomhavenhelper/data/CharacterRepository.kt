package com.rumpilstilstkin.gloomhavenhelper.data

import com.rumpilstilstkin.gloomhavenhelper.bd.dao.CharacterDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.CharacterGoodsDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.CharacterPerksDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.CharacterPersonalQuestDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.TeamDao
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.CharacterGoodBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.CharacterPerkBd
import com.rumpilstilstkin.gloomhavenhelper.data.mappers.toBd
import com.rumpilstilstkin.gloomhavenhelper.data.mappers.toDomain
import com.rumpilstilstkin.gloomhavenhelper.data.mappers.toShortDomain
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterForSave
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterInfo
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterShortInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CharacterRepository @Inject constructor(
    private val characterDao: CharacterDao,
    private val teamDao: TeamDao,
    private val characterGoodsDao: CharacterGoodsDao,
    private val characterPerksDao: CharacterPerksDao,
    private val characterQuestDao: CharacterPersonalQuestDao
) {

    suspend fun addCharacterPerk(characterId: Int, perkId: Int) {
        characterPerksDao.insert(CharacterPerkBd(characterId = characterId, perkId = perkId))

    }

    suspend fun deleteCharacterPerk(characterPerkId: Int) {
        characterPerksDao.deleteById(characterPerkId)
    }

    fun getCharacterPerksFlow(characterId: Int) =
        characterPerksDao.getCharacterPerksFlow(characterId).map { perks ->
            perks.map { it.toDomain() }
        }

    suspend fun addCharacterGood(characterId: Int, goodId: Int) {
        characterGoodsDao.insert(CharacterGoodBd(characterId = characterId, goodId = goodId))

    }

    suspend fun deleteCharacterGood(characterGoodId: Int) {
        characterGoodsDao.deleteById(characterGoodId)
    }

    fun getCharacterGoodsFlow(characterId: Int) =
        characterGoodsDao.getCharacterGoodsFlow(characterId).map { goods ->
            goods.map { it.toDomain() }
        }

    suspend fun getCharacterGoods(characterId: Int) =
        characterGoodsDao.getCharacterGoods(characterId).map { it.toDomain() }

    suspend fun getCharacterGood(characterGoodId: Int) =
        characterGoodsDao.getCharacterGoodById(characterGoodId = characterGoodId).toDomain()

    fun getCharacterByTeamId(teamId: Int): Flow<List<CharacterInfo>> =
        characterDao.findByTeamIdFlow(teamId).map { list ->
            list.map {
                val team = teamDao.findById(teamId).toDomain()
                it.toDomain(team)
            }
        }

    fun getCharacterByIdFlow(id: Int): Flow<CharacterInfo> =
        characterDao.getCharacterByIdFlow(id).map { character ->
            val team = character.teamId?.let { teamDao.findById(it).toDomain() }
            character.toDomain(team)
        }

    fun getCharacterPersonalQuestFlow(characterId: Int) =
        characterQuestDao.getCharacterPersonalQuestFlow(characterId).map { it?.toDomain() }

    suspend fun getCharacterById(id: Int): CharacterShortInfo =
        characterDao.getCharacterById(id).toShortDomain()

    suspend fun addCharacter(character: CharacterForSave) {
        characterDao.insert(character.toBd())
    }

    suspend fun deleteCharacter(id: Int) {
        characterDao.deleteById(id)
    }

    suspend fun updateLevel(id: Int, level: Int) {
        characterDao.getCharacterById(id).let {
            characterDao.update(it.copy(level = level))
        }
    }

    suspend fun setLevel(
        id: Int,
        level: Int,
        experience: Int,
    ){
        characterDao.getCharacterById(id).let {
            characterDao.update(it.copy(level = level, experience = experience))
        }
    }

    suspend fun updateNotes(id: Int, notes: String) {
        characterDao.getCharacterById(id).let {
            characterDao.update(it.copy(notes = notes))
        }
    }

    suspend fun updateCheckMarks(id: Int, checkMarkCount: Int) {
        characterDao.getCharacterById(id).let {
            characterDao.update(it.copy(checkMarkCount = checkMarkCount))
        }
    }

    suspend fun updateExperience(id: Int, experience: Int) {
        characterDao.getCharacterById(id).let {
            characterDao.update(it.copy(experience = experience))
        }
    }

    suspend fun updateGold(id: Int, goldCount: Int) {
        characterDao.getCharacterById(id).let {
            characterDao.update(it.copy(goldCount = goldCount))
        }
    }

    suspend fun leaveCharacter(id: Int) {
        characterDao.getCharacterById(id).let {
            characterDao.update(it.copy(isAlive = false))
        }
    }

    suspend fun setTeam(characterId: Int, teamId: Int) {
        characterDao.getCharacterById(characterId).let {
            characterDao.update(it.copy(teamId = teamId))
        }
    }

    suspend fun updateName(id: Int, name: String) {
        characterDao.getCharacterById(id).let {
            characterDao.update(it.copy(name = name))
        }
    }
}
