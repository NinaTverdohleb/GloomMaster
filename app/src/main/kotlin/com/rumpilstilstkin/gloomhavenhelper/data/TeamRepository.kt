package com.rumpilstilstkin.gloomhavenhelper.data

import android.content.res.Resources.NotFoundException
import android.util.Log
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.CharacterDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.TeamDao
import com.rumpilstilstkin.gloomhavenhelper.data.datasource.CurrentTeamDatasource
import com.rumpilstilstkin.gloomhavenhelper.data.mappers.toBd
import com.rumpilstilstkin.gloomhavenhelper.data.mappers.toDomain
import com.rumpilstilstkin.gloomhavenhelper.di.ApplicationScope
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterForSave
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ShortTeamInfo
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.TeamInfoForSave
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.TeamInfoWithScenario
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TeamRepository @Inject constructor(
    @param:ApplicationScope private val externalScope: CoroutineScope,
    private val currentTeamDatasource: CurrentTeamDatasource,
    private val teamDao: TeamDao,
    private val characterDao: CharacterDao,
    private val characterRepository: CharacterRepository
) {
    private val _currentTeam: MutableStateFlow<Result<Int>> =
        MutableStateFlow(Result.failure(NotFoundException()))

    val currentTeamId: Flow<Int> = _currentTeam.map { it.getOrNull() }.filterNotNull()

    init {
        externalScope.launch {
            updateCurrentTeam()
        }
    }

    fun getTeamWithScenarioFlow(id: Int): Flow<TeamInfoWithScenario> {
        return teamDao.getTeamWithScenariosFlow(id).map { it.toDomain() }
    }

    suspend fun getTeam(id: Int): ShortTeamInfo = teamDao.findById(id).toDomain()

    fun getTeams(): Flow<List<ShortTeamInfo>> = teamDao.getAllFlow().map { teams -> teams.map { it.toDomain() } }

    suspend fun saveTeam(team: TeamInfoForSave): Int {
        val savedTeamId = teamDao.insert(team.toBd()).toInt()
        Log.d("PREFS", "Save team: $savedTeamId")
        currentTeamDatasource.saveCurrentTeam(savedTeamId)
        team.characters.forEach {
            characterDao.insert(it.toBd(savedTeamId))
        }
        updateCurrentTeam()
        return savedTeamId
    }

    suspend fun updateReputation(reputation: Int) {
        _currentTeam.value.onSuccess {
            teamDao.updateReputation(it, reputation)
        }
    }

    suspend fun updateProsperity( prosperity: Int) {
        _currentTeam.value.onSuccess {
            teamDao.updateProsperity(it, prosperity)
        }
    }

    suspend fun updateTeam(team: ShortTeamInfo) {
        teamDao.update(team.toBd())
    }

    suspend fun addCharacterForCurrentTeam(character: CharacterForSave) {
        _currentTeam.value.onSuccess {
            characterRepository.addCharacter(character, it)
        }
    }

    private suspend fun updateCurrentTeam() {
        val currentTeam = currentTeamDatasource.getCurrentTeam()
        if (currentTeam != CurrentTeamDatasource.EMPTY_TEAM) {
            teamDao.findById(currentTeam).let { team ->
                _currentTeam.emit(
                    Result.success(team.teamId)
                )
            }
        }
    }
}