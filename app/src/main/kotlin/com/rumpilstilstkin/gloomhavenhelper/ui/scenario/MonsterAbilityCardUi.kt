package com.rumpilstilstkin.gloomhavenhelper.ui.scenario

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
            ),
            MonsterActionUi(
                text = "Attack #41 +0",
            ),
            MonsterActionUi(
                text = "#20",
            )
        )
    ),

    MonsterAbilityCardUi(
        cardId = 2,
        needsShuffle = true,
        initiative = 42,
        actions = persistentListOf(
            MonsterActionUi(
                text = "Move #38 +1",
            ),
            MonsterActionUi(
                text = "Attack #41 +0",

                ),
            MonsterActionUi(
                text = "#22",

                )
        )
    ),

    MonsterAbilityCardUi(
        cardId = 3,
        needsShuffle = true,
        initiative = 42,
        actions = persistentListOf(
            MonsterActionUi(
                text = "Attack #41 +3",
                subAction = persistentListOf(
                    MonsterActionUi(
                        text = "Pierce #09 2",

                        )
                )
            ),
            MonsterActionUi(
                text = "#22",

                )
        )
    ),

    MonsterAbilityCardUi(
        cardId = 4,
        needsShuffle = true,
        initiative = 42,
        actions = persistentListOf(
            MonsterActionUi(
                text = "Move #38 -1",

                ),
            MonsterActionUi(
                text = "Attack #41 -1",
                subAction = persistentListOf(
                    MonsterActionUi(
                        text = "Range #37 3",

                        ),
                    MonsterActionUi(
                        text = "Immobilize #18",

                        )
                )
            )
        )
    ),

    MonsterAbilityCardUi(
        cardId = 5,
        needsShuffle = true,
        initiative = 42,
        actions = persistentListOf(
            MonsterActionUi(
                text = "Move #38 +0",

                ),
            MonsterActionUi(
                text = "Attack #41 +0",
                subAction = persistentListOf(
                    MonsterActionUi(
                        text = "#50 : Disarm #11",

                        )
                )
            )
        )
    ),

    MonsterAbilityCardUi(
        cardId = 6,
        needsShuffle = true,
        initiative = 42,
        actions = persistentListOf(
            MonsterActionUi(
                text = "Move #38 +0",

                ),
            MonsterActionUi(
                text = "Attack #41 +0",

                ),
            MonsterActionUi(
                text = "#46 : Invisible #24 (Self)",

                )
        )
    ),

    MonsterAbilityCardUi(
        cardId = 7,
        needsShuffle = true,
        initiative = 42,
        actions = persistentListOf(
            MonsterActionUi(
                text = "Move #38 -1",

                ),
            MonsterActionUi(
                text = "Attack #41 +2",
                subAction = persistentListOf(
                    MonsterActionUi(
                        text = "#50 : Wound #14",

                        ),
                    MonsterActionUi(
                        text = "#46 : Curse #26",

                        )
                )
            )
        )
    ),

    MonsterAbilityCardUi(
        cardId = 8,
        needsShuffle = true,
        initiative = 42,
        actions = persistentListOf(
            MonsterActionUi(
                text = "Focus on the enemy with the highest initiative",

                ),
            MonsterActionUi(
                text = "Teleport to the closest unoccupied hex adjacent to the focus",

                ),
            MonsterActionUi(
                text = "Attack #41 -2",

                ),
            MonsterActionUi(
                text = "#20",

                )
        )
    ),
)
