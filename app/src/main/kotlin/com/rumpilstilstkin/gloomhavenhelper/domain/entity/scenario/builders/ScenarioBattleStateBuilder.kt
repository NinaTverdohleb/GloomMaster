package com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.builders

import com.rumpilstilstkin.gloomhavenhelper.domain.entity.LevelInfo
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Magic
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.PackType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioGameStateMagic
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.Monster
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterCard
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterStatType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.MagicChargeState
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.MonsterDeckState
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.ScenarioBattleState

suspend inline fun buildScenarioBattleState(builderAction: ScenarioBattleStateBuilder.() -> Unit): ScenarioBattleState {
    return ScenarioBattleStateBuilder().apply(builderAction).build()
}

class ScenarioBattleStateBuilder {
    private var generalLevel: Int = 0
    private var scenarioNumber: Int? = null
    private var name: String = ""
    private var monsters: Map<String, Monster> = emptyMap()
    private var golds: Int = 0
    private var exp: Int = 0
    private var trapDamage: Int = 0
    private var gamersCount: Int = 0
    private var monsterLevel: Int = 0
    private var deck: MonsterDeckState = MonsterDeckState.create(emptyList())
    private var activeMonsters: ActiveMonstersBuilder = ActiveMonstersBuilder()
    private var round: Int = 0
    private var magicState: MagicChargeState = MagicChargeState.initial()
    private var availableEffects: Set<MonsterStatType> = emptySet()

    fun levelInfo(level: LevelInfo) {
        golds = level.goldCount
        exp = level.experience
        trapDamage = level.trapDamage
        monsterLevel = level.monsterLevel
        generalLevel = level.level
    }

    fun scenarioNumber(scenarioNumber: Int?) = apply { this.scenarioNumber = scenarioNumber }
    fun name(name: String) = apply { this.name = name }
    fun monsters(monsters: Map<String, Monster>) = apply { this.monsters = monsters }
    fun gamersCount(gamersCount: Int) = apply { this.gamersCount = gamersCount }
    fun cards(cards: List<MonsterCard>) = apply { this.deck = MonsterDeckState.create(cards) }
    fun round(round: Int) = apply { this.round = round }
    fun magicState(magicState: List<ScenarioGameStateMagic>) = apply {
        this.magicState = MagicChargeState.restore(
            magicState.associate { state -> Magic.valueOf(state.name) to state.value }
        )
    }

    fun activeMonsters(
        body: ActiveMonstersBuilder.() -> Unit
    ) {
        activeMonsters { body() }
    }

    fun availableEffects(packs: List<PackType>) =
        apply {
            this.availableEffects = packs
                .flatMapTo(mutableSetOf()) { pack ->
                    when (pack) {
                        PackType.MAIN -> MonsterStatType.mainEffectsPack
                        PackType.FORGOTTEN_CIRCLES -> MonsterStatType.fcEffectsPack
                    }
                }.toSet()
        }

    suspend fun build(): ScenarioBattleState {
        return ScenarioBattleState(
            generalLevel = generalLevel,
            scenarioNumber = scenarioNumber,
            name = name,
            monsters = monsters,
            golds = golds,
            exp = exp,
            trapDamage = trapDamage,
            gamersCount = gamersCount,
            monsterLevel = monsterLevel,
            deck = deck,
            activeMonsters = activeMonsters.build(gamersCount),
            round = round,
            magicState = magicState,
            availableEffects = availableEffects
        )
    }
}