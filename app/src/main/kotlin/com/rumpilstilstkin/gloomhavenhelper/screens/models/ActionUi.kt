package com.rumpilstilstkin.gloomhavenhelper.screens.models

import androidx.annotation.DrawableRes
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterStatType
import java.util.EnumSet

enum class ActionUi(
    val title: String,
    @DrawableRes val iconRes: Int,
) {
    ATTACK(
        title = "Атака",
        iconRes = R.drawable.attack
    ),
    MOVE(
        title = "Движение",
        iconRes = R.drawable.move
    ),
    SHIELD(
        title = "Защита",
        iconRes = R.drawable.ic_shield
    ),
    RETALIATE(
        title = "Ответный удар",
        iconRes = R.drawable.retaliate
    ),
    STRENGTH(
        title = "Усиление",
        iconRes = R.drawable.strengthen
    ),
    POISON(
        title = "Отравление",
        iconRes = R.drawable.poison
    ),
    RANGED_ATTACK(
        title = "Дальний удар",
        iconRes = R.drawable.range
    ),
    TARGET(
        title = "Цели",
        iconRes = R.drawable.targets
    ),
    STUN(
        title = "Оцепенение",
        iconRes = R.drawable.stun
    ),
    WOUND(
        title = "Рана",
        iconRes = R.drawable.wound
    ),
    HEAL(
        title = "Лечение",
        iconRes = R.drawable.hp
    ),
    PUSH(
        title = "Толкнуть",
        iconRes = R.drawable.push
    ),
    MUDDLE(
        title = "Замешательство",
        iconRes = R.drawable.muddle
    ),
    IMMOBILIZE(
        title = "Обездвижить",
        iconRes = R.drawable.immobilize
    ),
    INVISIBLE(
        title = "Невидимость",
        iconRes = R.drawable.invisible
    ),
    DISARM(
        title = "Обезоружить",
        iconRes = R.drawable.disarm
    ),
    CURSE(
        title = "Проклятье",
        iconRes = R.drawable.curse
    ),
    BLESS(
        title = "Благословение",
        iconRes = R.drawable.curse // TODO
    ),
    PULL(
        title = "Притянуть",
        iconRes = R.drawable.curse // TODO
    ),
    PIERCE(
        title = "Пробитие",
        iconRes = R.drawable.curse // TODO
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