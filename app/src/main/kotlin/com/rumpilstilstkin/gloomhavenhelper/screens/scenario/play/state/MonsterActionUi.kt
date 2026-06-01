package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.state

import androidx.compose.runtime.Immutable
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.IconCode.Companion.toIconCode
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterCardAction
import com.rumpilstilstkin.gloomhavenhelper.ui.icons.GameIcon
import com.rumpilstilstkin.gloomhavenhelper.ui.icons.GameIcon.Companion.toGameIcon
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class MonsterActionUi(
    val text: String,
    val endEffect: GameIcon? = null,
    val startEffect: GameIcon? = null,
    val subAction: ImmutableList<MonsterActionUi> = persistentListOf()
) {
    companion object {
        fun fixture(
            text: String = "Толкнуть #08 1",
            endEffect: GameIcon? = null,
            startEffect: GameIcon? = null,
            subAction: ImmutableList<MonsterActionUi> = persistentListOf(
                MonsterActionUi(
                    text = "Цели все соседние враги",
                    endEffect = null,
                    startEffect = null,
                    subAction = persistentListOf()
                )
            )
        ) = MonsterActionUi(
            text = text,
            endEffect = endEffect,
            startEffect = startEffect,
            subAction = subAction
        )
    }
}

fun MonsterCardAction.toUi() : MonsterActionUi = MonsterActionUi(
    text = text,
    endEffect = endEffect?.toIconCode()?.toGameIcon(),
    startEffect = startEffect?.toIconCode()?.toGameIcon(),
    subAction = subAction.map { it.toUi() }.toPersistentList()
)
