package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team

import android.util.Log
import com.rumpilstilstkin.gloomhavenhelper.data.CharacterClassRepository
import com.rumpilstilstkin.gloomhavenhelper.data.CharacterRepository
import com.rumpilstilstkin.gloomhavenhelper.data.GoodsRepository
import com.rumpilstilstkin.gloomhavenhelper.data.QuestsRepository
import com.rumpilstilstkin.gloomhavenhelper.data.ScenarioRepository
import com.rumpilstilstkin.gloomhavenhelper.data.TeamRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterClassType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterForSave
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.TeamInfoForSave
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.export.TeamExportDto
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.perks.AddPerksForCharacterUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.goods.AddGoodForCharacterUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.goods.AddGoodsToTeamByNumbersUseCase
import kotlinx.serialization.json.Json
import javax.inject.Inject

class ImportTeamUseCase @Inject constructor(
    private val teamRepository: TeamRepository,
    private val scenarioRepository: ScenarioRepository,
    private val characterClassRepository: CharacterClassRepository,
    private val characterRepository: CharacterRepository,
    private val questsRepository: QuestsRepository,
    private val addPerksForCharacterUseCase: AddPerksForCharacterUseCase,
    private val addGoodForCharacterUseCase: AddGoodForCharacterUseCase,
    private val goodsRepository: GoodsRepository
) {
    private val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
        coerceInputValues = true
    }

    suspend operator fun invoke(jsonString: String): Result<Unit> =
        runCatching { json.decodeFromString<TeamExportDto>(jsonString) }.map { data ->
            val teamId = teamRepository.saveTeam(
                TeamInfoForSave(
                    name = data.team.name,
                    packs = data.team.packs,
                    characters = emptyList(),
                    difficultyLevel = data.team.difficultyLevel
                )
            )
            teamRepository.updateTeam(data.team.copy(teamId = teamId))
            characterClassRepository.addAvailableClasses(
                teamId,
                data.unlockedClasses.map { CharacterClassType.valueOf(it) }
            )
            scenarioRepository.addTeamScenarios(
                data.teamScenarios.map { it.scenarioNumber to it.completed },
                teamId
            )

            val availableIdsMap = goodsRepository.getGoodIdsByNumbers(data.teamGoods.distinct())
            val idAvaliableQueues = availableIdsMap.mapValues { it.value.toMutableList() }
            val teamGoods: Map<Int, List<Int>> = data.teamGoods
                .mapNotNull { number ->
                    val queue = idAvaliableQueues[number]
                    if (!queue.isNullOrEmpty()) {
                        number to queue.removeAt(0)
                    } else {
                        null
                    }
                }
                .groupBy(
                    keySelector = { it.first },
                    valueTransform = { it.second }
                )
            goodsRepository.addGoodsToTeam(teamId, teamGoods.values.flatten())

            val teamQueues = teamGoods.mapValues { it.value.toMutableList() }
            data.characters.forEach { character ->
                val goods = character.goodDisplayNumbers
                    .mapNotNull { number ->
                        val queue = teamQueues[number]
                        if (!queue.isNullOrEmpty()) {
                            queue.removeAt(0)
                        } else {
                            null
                        }
                    }
                val characterId = characterRepository.addCharacter(
                    CharacterForSave(
                        name = character.generalInfo.name,
                        level = character.generalInfo.level,
                        characterType = character.generalInfo.characterType,
                        teamId = teamId,
                        experience = character.generalInfo.experience,
                        goldCount = character.generalInfo.goldCount,
                        isAlive = character.generalInfo.isAlive,
                        notes = character.generalInfo.notes,
                        checkMarkCount = character.generalInfo.checkMarkCount,
                    )
                )
                character.personalQuest?.also {
                    questsRepository.updateCharacterQuest(
                        it,
                        characterId
                    )
                }
                addPerksForCharacterUseCase(character.perks, characterId)
                addGoodForCharacterUseCase(goods, characterId)
            }
        }
}