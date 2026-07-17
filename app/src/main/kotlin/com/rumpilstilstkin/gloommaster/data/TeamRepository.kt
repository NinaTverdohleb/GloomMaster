package com.rumpilstilstkin.gloommaster.data

import android.content.res.Resources.NotFoundException
import com.rumpilstilstkin.gloommaster.bd.dao.CharacterDao
import com.rumpilstilstkin.gloommaster.bd.dao.TeamDao
import com.rumpilstilstkin.gloommaster.data.datasource.CurrentTeamDatasource
import com.rumpilstilstkin.gloommaster.data.mappers.toBd
import com.rumpilstilstkin.gloommaster.data.mappers.toDomain
import com.rumpilstilstkin.gloommaster.di.ApplicationScope
import com.rumpilstilstkin.gloommaster.domain.entity.DifficultyLevel
import com.rumpilstilstkin.gloommaster.domain.entity.PackType
import com.rumpilstilstkin.gloommaster.domain.entity.ShortTeamInfo
import com.rumpilstilstkin.gloommaster.domain.entity.Team
import com.rumpilstilstkin.gloommaster.domain.entity.TeamInfoForSave
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
    private val scenarioGameStateRepository: ScenarioGameStateRepository,
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
                            team.toDomain(characters)
                        }
                    },
                    onFailure = { flowOf(null) },
                )
            }

    init {
        externalScope.launch {
            updateCurrentTeam()
        }
    }

    suspend fun setCurrentTeam(teamId: Int) {
        scenarioGameStateRepository.delete()
        currentTeamDatasource.currentTeam = teamId
        updateCurrentTeam()
    }

    fun getTeams(): Flow<List<Team>> =
        teamDao.getAllFlow().map { teams ->
            teams.map { teamBd ->
                Team(
                    teamId = teamBd.teamId,
                    name = teamBd.name,
                    packs = teamBd.packs.map { PackType.valueOf(it) },
                    difficultyLevel = DifficultyLevel.fromValue(teamBd.difficultyLevel),
                )
            }
        }

    suspend fun getTeamInfo(teamId: Int): ShortTeamInfo? =
        teamDao.findById(teamId)?.let { team ->
            val characters = characterDao.findByTeamId(team.teamId)
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

    suspend fun updateProsperity(
        teamId: Int,
        prosperity: Int,
    ) {
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

    suspend fun deleteTeam(teamId: Int) {
        val currentTeamId = currentTeamDatasource.currentTeam
        if (teamId == currentTeamId) {
            val teams = teamDao.getAll()
            val newTeamId =
                teams.firstOrNull { it.teamId != teamId }?.teamId
                    ?: CurrentTeamDatasource.EMPTY_TEAM
            setCurrentTeam(newTeamId)
        }
        characterDao.deleteByTeamId(teamId)
        teamDao.delete(teamId)
    }

    private suspend fun updateCurrentTeam() {
        val currentTeamId = currentTeamDatasource.currentTeam
        val team = teamDao.findById(currentTeamId)
        if (currentTeamId != CurrentTeamDatasource.EMPTY_TEAM && team != null) {
            _currentTeam.emit(
                Result.success(team.teamId),
            )
        } else {
            _currentTeam.emit(Result.failure(NotFoundException()))
        }
    }
}
