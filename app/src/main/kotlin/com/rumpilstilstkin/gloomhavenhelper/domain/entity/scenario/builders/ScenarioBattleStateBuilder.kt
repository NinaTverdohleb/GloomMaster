package com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.builders

import com.rumpilstilstkin.gloomhavenhelper.domain.entity.LevelInfo
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Magic
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.PackType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioGameState
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioGameStateMagic
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.TeamInfo
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.Monster
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterStatType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.MagicChargeState
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.MonsterDeckState
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.ScenarioBattleState

suspend inline fun buildScenarioBattleState(crossinline builderAction: suspend ScenarioBattleStateBuilder.() -> Unit): ScenarioBattleState =
    ScenarioBattleStateBuilder()
        .apply {
            builderAction()
        }.build()

class ScenarioBattleStateBuilder {
    private var generalLevel: Int = 0
    private var scenarioNumber: Int? = null
    private var name: String = ""
    private var monsters: Map<String, Monster> = emptyMap()
    private var golds: Int = 0
    private var exp: Int = 0
    private var trapDamage: Int = 0
    private var gamersCount: Int = 0
    private var newLevel: Int = 0
    private var deck: MonsterDeckState = MonsterDeckState.create(emptyList())
    private var activeMonsterBuilder: ActiveMonstersBuilder = ActiveMonstersBuilder()
    private var round: Int = 0
    private var magicState: MagicChargeState = MagicChargeState.initial()
    private var availableEffects: Set<MonsterStatType> = emptySet()
    private var getMonster: suspend (level: Int, slug: String) -> Monster? = { _, _ -> null }

    fun levelInfo(level: LevelInfo) =
        apply {
            this.golds = level.goldCount
            this.exp = level.experience
            this.trapDamage = level.trapDamage
            this.newLevel = level.monsterLevel
            this.generalLevel = level.level
        }

    fun team(team: TeamInfo) =
        apply {
            this.gamersCount = team.aliveCharacters.size
            availableEffects(team.packs)
        }

    fun gameState(state: ScenarioGameState) =
        apply {
            this.round = state.round
            this.scenarioNumber = state.scenarioNumber
            magicState(state.magicCharges)

            val cardByKey =
                monsters
                    .values
                    .asSequence()
                    .flatMap { it.cards.asSequence() }
                    .associateBy { it.deckName to it.cardId }

            this.deck =
                MonsterDeckState.create(
                    state.availableCards.mapNotNull { (deck, cardId) -> cardByKey[deck to cardId] },
                )

            activeMonsterBuilder.apply {
                activeMonsters(state.activeMonsters)
                cards(cardByKey)
                levels(state.level to newLevel)
            }
        }

    fun name(name: String) = apply { this.name = name }

    fun monsters(monsters: Map<String, Monster>) =
        apply {
            this.monsters = monsters
            activeMonsterBuilder.scenarioMonsters(monsters)
        }

    fun getAdditionalMonster(getMonster: suspend (level: Int, slug: String) -> Monster?) =
        apply {
            this.getMonster = getMonster
        }

    private fun magicState(magicState: List<ScenarioGameStateMagic>) =
        apply {
            this.magicState =
                MagicChargeState.restore(
                    magicState.associate { state -> Magic.valueOf(state.name) to state.value },
                )
        }

    private fun availableEffects(packs: List<PackType>) =
        apply {
            this.availableEffects =
                packs
                    .flatMapTo(mutableSetOf()) { pack ->
                        when (pack) {
                            PackType.MAIN -> MonsterStatType.mainEffectsPack
                            PackType.FORGOTTEN_CIRCLES -> MonsterStatType.fcEffectsPack
                        }
                    }.toSet()
        }

    suspend fun build(): ScenarioBattleState =
        ScenarioBattleState(
            generalLevel = generalLevel,
            scenarioNumber = scenarioNumber,
            name = name,
            monsters = monsters,
            golds = golds,
            exp = exp,
            trapDamage = trapDamage,
            gamersCount = gamersCount,
            monsterLevel = newLevel,
            deck = deck,
            activeMonsters = activeMonsterBuilder.build(gamersCount, getMonster),
            round = round,
            magicState = magicState,
            availableEffects = availableEffects,
        )
}
