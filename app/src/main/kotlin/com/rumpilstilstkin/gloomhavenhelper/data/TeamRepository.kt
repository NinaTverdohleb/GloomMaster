package com.rumpilstilstkin.gloomhavenhelper.data

import android.content.res.Resources.NotFoundException
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.CharacterDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.TeamDao
import com.rumpilstilstkin.gloomhavenhelper.data.datasource.CurrentTeamDatasource
import com.rumpilstilstkin.gloomhavenhelper.data.mappers.toBd
import com.rumpilstilstkin.gloomhavenhelper.data.mappers.toDomain
import com.rumpilstilstkin.gloomhavenhelper.di.ApplicationScope
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.PackType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ShortTeamInfo
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Team
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.TeamInfoForSave
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.TeamInfoWithScenario
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
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
    private val scenarioRepository: ScenarioRepository,
    private val scenarioGameStateRepository: ScenarioGameStateRepository
) {
    private val _currentTeam: MutableStateFlow<Result<Int>> =
        MutableStateFlow(Result.failure(NotFoundException()))

    @OptIn(ExperimentalCoroutinesApi::class)
    val currentTeam: Flow<ShortTeamInfo?> =
        _currentTeam
            .flatMapLatest { result ->
                result.fold(
                    onSuccess = { teamId ->
                        combine(
                            teamDao.getTeamFlow(teamId),
                            characterDao.findByTeamIdFlow(teamId),
                        ) { team, characters ->
                            team.toDomain(characters.filter { it.isAlive }.map { it.characterId })
                        }
                    },
                    onFailure = { flowOf(null) }
                )
            }

    init {
        externalScope.launch {
            updateCurrentTeam()
        }
    }

    suspend fun getTeamWithScenarioFlow(id: Int): Flow<TeamInfoWithScenario> {
        return teamDao.getTeamFlow(id)
            .combine(scenarioRepository.getTeamScenariosFlow(id)) { team, scenarios ->
                team.toDomain(scenarios)
            }
    }

    suspend fun setCurrentTeam(teamId: Int) {
        currentTeamDatasource.saveCurrentTeam(teamId)
        scenarioGameStateRepository.delete()
        updateCurrentTeam()
    }

    fun getTeams(): Flow<List<Team>> =
        teamDao.getAllFlow().map { teams ->
            teams.map { teamBd ->
                Team(
                    teamId = teamBd.teamId,
                    name = teamBd.name,
                    packs = teamBd.packs.map { PackType.valueOf(it) }
                )
            }
        }

    suspend fun getTeamInfo(teamId: Int): ShortTeamInfo? =
        teamDao.findById(teamId)?.let { team ->
            val characters =
                characterDao.findByTeamId(team.teamId).filter { it.isAlive }.map { it.characterId }
            team.toDomain(characters)
        }


    suspend fun saveTeam(team: TeamInfoForSave): Int {
        val savedTeamId = teamDao.insert(team.toBd()).toInt()
        team.characters.forEach {
            characterDao.insert(it.copy(teamId = savedTeamId).toBd())
        }
        setCurrentTeam(teamId = savedTeamId)
        return savedTeamId
    }

    suspend fun updateReputation(reputation: Int) {
        _currentTeam.value.onSuccess {
            teamDao.updateReputation(it, reputation)
        }
    }

    suspend fun updateProsperity(teamId: Int, prosperity: Int) {
        teamDao.updateProsperity(teamId, prosperity)
    }

    suspend fun donate(teamId: Int): Int {
        val team = teamDao.findById(teamId)
        val newValue = team?.churchValue?.plus(10) ?: 0
        teamDao.updateDonateValue(teamId, team?.churchValue?.plus(10) ?: 0)
        return newValue
    }

    suspend fun updateTeam(team: ShortTeamInfo) {
        teamDao.update(team.toBd())
    }

    suspend fun deleteTeam(team: ShortTeamInfo) {
        val teams = teamDao.getAll()
        val newTeamId = teams.firstOrNull { it.teamId != team.teamId }?.teamId
            ?: CurrentTeamDatasource.EMPTY_TEAM
        setCurrentTeam(newTeamId)
        teamDao.delete(team.teamId)
    }

    private suspend fun updateCurrentTeam() {
        val currentTeamId = currentTeamDatasource.getCurrentTeam()
        val team = teamDao.findById(currentTeamId)
        if (currentTeamId != CurrentTeamDatasource.EMPTY_TEAM && team != null) {
            _currentTeam.emit(
                Result.success(team.teamId)
            )
        } else {
            _currentTeam.emit(Result.failure(NotFoundException()))
        }
    }
}