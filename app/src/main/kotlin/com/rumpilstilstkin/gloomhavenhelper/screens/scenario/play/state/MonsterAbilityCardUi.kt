package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.state

import androidx.compose.runtime.Immutable
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterCard
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class MonsterAbilityCardUi(
    val cardId: Int,
    val needsShuffle: Boolean = false,
    val initiative: Int,
    val actions: ImmutableList<MonsterActionUi>
) {
    companion object {
        fun fixture(
            cardId: Int = 1,
            needsShuffle: Boolean = false,
            initiative: Int = 42,
            actions: ImmutableList<MonsterActionUi> = persistentListOf(MonsterActionUi.fixture())
        ) = MonsterAbilityCardUi(
            cardId = cardId,
            needsShuffle = needsShuffle,
            initiative = initiative,
            actions = actions,
        )
    }
}

fun MonsterCard.toUi() = MonsterAbilityCardUi(
    cardId = cardId,
    needsShuffle = needsShuffle,
    initiative = initiative,
    actions = actions.map { action -> action.toUi() }.toImmutableList()
)

val sampleDeck = listOf(
    MonsterAbilityCardUi(
        cardId = 1,
        needsShuffle = true,
        initiative = 42,
        actions = persistentListOf(
            MonsterActionUi(
                text = "Move #38 +1",
                subAction = persistentListOf()
            ),
            MonsterActionUi(
                text = "Attack #41 -1",
                subAction = persistentListOf()
            )
        )),

    MonsterAbilityCardUi(
        cardId = 2,
        needsShuffle = true,
        initiative = 42,
        actions = persistentListOf(
            MonsterActionUi(
                text = "Move #38 +0",
                subAction = persistentListOf()
            ),
            MonsterActionUi(
                text = "Attack #41 +0",
                subAction = persistentListOf()
            )
        )),

    MonsterAbilityCardUi(
        cardId = 3,
        needsShuffle = true,
        initiative = 42,
        actions = persistentListOf(
            MonsterActionUi(
                text = "Attack #41 +0",
                subAction = persistentListOf(
                    MonsterActionUi(
                        text = "Target #13 2",
                        subAction = persistentListOf()
                    ),
                    MonsterActionUi(
                        text = "Poison #25",
                        subAction = persistentListOf()
                    )
                )
            )
        )),

    MonsterAbilityCardUi(
        cardId = 4,
        needsShuffle = true,
        initiative = 42,
        actions = persistentListOf(
            MonsterActionUi(
                text = "Move #38 -1",
                subAction = persistentListOf()
            ),
            MonsterActionUi(
                text = "Attack #41 +1",
                subAction = persistentListOf(
                    MonsterActionUi(
                        text = "Range #37 +1",
                        subAction = persistentListOf()
                    )
                )
            )
        )),

    MonsterAbilityCardUi(
        cardId = 5,
        needsShuffle = true,
        initiative = 42,
        actions = persistentListOf(
            MonsterActionUi(
                text = "Ooze offers 2 damage",
                subAction = persistentListOf()
            ),
            MonsterActionUi(
                text = "Summon normal Ooze with a hit point value equals to the summoning Ooze's current hit point value (limited by a normal Ooze's specified maximum hit point value)",
                subAction = persistentListOf()
            )
        )),

    MonsterAbilityCardUi(
        cardId = 6,
        needsShuffle = true,
        initiative = 42,
        actions = persistentListOf(
            MonsterActionUi(
                text = "Ooze offers 2 damage",
                subAction = persistentListOf()
            ),
            MonsterActionUi(
                text = "Summon normal Ooze with a hit point value equals to the summoning Ooze's current hit point value (limited by a normal Ooze's specified maximum hit point value)",
                subAction = persistentListOf()
            )
        )),

    MonsterAbilityCardUi(
        cardId = 7,
        needsShuffle = true,
        initiative = 42,
        actions = persistentListOf(
            MonsterActionUi(
                text = "Move #38 -1",
                subAction = persistentListOf()
            ),
            MonsterActionUi(
                text = "Loot #52 1",
                subAction = persistentListOf()
            ),
            MonsterActionUi(
                text = "Heal #40 2 (Self)",
                subAction = persistentListOf()
            )
        )),

    MonsterAbilityCardUi(
        cardId = 8,
        needsShuffle = true,
        initiative = 42,
        actions = persistentListOf(
            MonsterActionUi(
                text = "Push #08 1 and Poison #25",
                subAction = persistentListOf(
                    MonsterActionUi(
                        text = "Target all adjacent enemies",
                        subAction = persistentListOf()
                    )
                )
            ),
            MonsterActionUi(
                text = "Attack #41 +1",
                subAction = persistentListOf(
                    MonsterActionUi(
                        text = "Range #37 -1",
                        subAction = persistentListOf()
                    )
                )
            )
        )),

    )
