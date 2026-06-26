package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.components

import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.game.GameIcon
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterStatType

fun MonsterStatType.toGameIcon(): GameIcon =
    when (this) {
        MonsterStatType.ATTACK -> GameIcon.ATTACK
        MonsterStatType.MOVE -> GameIcon.MOVE
        MonsterStatType.RANGE -> GameIcon.RANGED_ATTACK
        MonsterStatType.SHIELD -> GameIcon.SHIELD
        MonsterStatType.RETALIATE -> GameIcon.RETALIATE
        MonsterStatType.TARGET -> GameIcon.TARGET
        MonsterStatType.POISON -> GameIcon.POISON
        MonsterStatType.WOUND -> GameIcon.WOUND
        MonsterStatType.MUDDLE -> GameIcon.CONFUSE
        MonsterStatType.STUN -> GameIcon.STUN
        MonsterStatType.IMMOBILIZE -> GameIcon.IMMOBILIZE
        MonsterStatType.DISARM -> GameIcon.DISARM
        MonsterStatType.CURSE -> GameIcon.CURSE
        MonsterStatType.STRENGTHEN -> GameIcon.STRENGTH
        MonsterStatType.INVISIBLE -> GameIcon.INVISIBILITY
        MonsterStatType.HEAL -> GameIcon.HEAL
        MonsterStatType.PUSH -> GameIcon.PUSH
        MonsterStatType.BLESS -> GameIcon.BLESS
        MonsterStatType.PULL -> GameIcon.PULL
        MonsterStatType.PIERCE -> GameIcon.PIERCE
        MonsterStatType.REGENERATE -> GameIcon.REGENERATE
    }
