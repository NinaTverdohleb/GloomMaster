package com.rumpilstilstkin.gloomhavenhelper.screens.models

import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterStatType
import com.rumpilstilstkin.gloomhavenhelper.ui.icons.GameIcon
import java.util.EnumSet

enum class ActionUi(
    val icon: GameIcon,
) {
    ATTACK(
        icon = GameIcon.ATTACK,
    ),
    MOVE(
        icon = GameIcon.MOVE,
    ),
    SHIELD(
        icon = GameIcon.SHIELD,
    ),
    RETALIATE(
        icon = GameIcon.RETALIATE,
    ),
    STRENGTH(
        icon = GameIcon.STRENGTH,
    ),
    POISON(
        icon = GameIcon.POISON,
    ),
    RANGED_ATTACK(
        icon = GameIcon.RANGED_ATTACK,
    ),
    TARGET(
        icon = GameIcon.TARGET,
    ),
    STUN(
        icon = GameIcon.STUN,
    ),
    WOUND(
        icon = GameIcon.WOUND,
    ),
    HEAL(
        icon = GameIcon.HEAL,
    ),
    PUSH(
        icon = GameIcon.PUSH,
    ),
    MUDDLE(
        icon = GameIcon.CONFUSE,
    ),
    IMMOBILIZE(
        icon = GameIcon.IMMOBILIZE,
    ),
    INVISIBLE(
        icon = GameIcon.INVISIBILITY,
    ),
    DISARM(
        icon = GameIcon.DISARM,
    ),
    CURSE(
        icon = GameIcon.CURSE,
    ),
    BLESS(
        icon = GameIcon.BLESS,
    ),
    PULL(
        icon = GameIcon.PULL,
    ),
    PIERCE(
        icon = GameIcon.PIERCE,
    )
    ;

    companion object {

        fun fromMonsterStatType(type: MonsterStatType): ActionUi = when (type) {
            MonsterStatType.ATTACK -> ATTACK
            MonsterStatType.MOVE -> MOVE
            MonsterStatType.RANGE -> RANGED_ATTACK
            MonsterStatType.SHIELD -> SHIELD
            MonsterStatType.RETALIATE -> RETALIATE
            MonsterStatType.TARGET -> TARGET
            MonsterStatType.POISON -> POISON
            MonsterStatType.WOUND -> WOUND
            MonsterStatType.MUDDLE -> MUDDLE
            MonsterStatType.STUN -> STUN
            MonsterStatType.IMMOBILIZE -> IMMOBILIZE
            MonsterStatType.DISARM -> DISARM
            MonsterStatType.CURSE -> CURSE
            MonsterStatType.STRENGTHEN -> STRENGTH
            MonsterStatType.INVISIBLE -> INVISIBLE
            MonsterStatType.HEAL -> HEAL
            MonsterStatType.PUSH -> PUSH
            MonsterStatType.BLESS -> BLESS
            MonsterStatType.PULL -> PULL
            MonsterStatType.PIERCE -> PIERCE
        }
    }
}

object ActionGroups {
    val effectsPack = EnumSet.of(
        ActionUi.POISON,
        ActionUi.WOUND,
        ActionUi.IMMOBILIZE,
        ActionUi.DISARM,
        ActionUi.STUN,
        ActionUi.MUDDLE,
        ActionUi.STRENGTH
    )
}