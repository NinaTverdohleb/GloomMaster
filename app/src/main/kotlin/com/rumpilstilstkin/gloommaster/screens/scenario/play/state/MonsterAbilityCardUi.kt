@file:Suppress("ktlint:standard:max-line-length")

package com.rumpilstilstkin.gloommaster.screens.scenario.play.state

import com.rumpilstilstkin.gloommaster.domain.entity.monster.MonsterCard
import com.rumpilstilstkin.gloommaster.domain.entity.monster.MonsterCardAction
import kotlinx.collections.immutable.persistentListOf

val sampleDeck =
    listOf(
        MonsterCard(
            cardId = 1,
            needsShuffle = true,
            initiative = 42,
            deckName = "",
            actions =
                persistentListOf(
                    MonsterCardAction(
                        text = "Move #38 +1",
                        subAction = persistentListOf(),
                    ),
                    MonsterCardAction(
                        text = "Attack #41 -1",
                        subAction = persistentListOf(),
                    ),
                ),
        ),
        MonsterCard(
            cardId = 2,
            needsShuffle = true,
            initiative = 42,
            deckName = "",
            actions =
                persistentListOf(
                    MonsterCardAction(
                        text = "Move #38 +0",
                        subAction = persistentListOf(),
                    ),
                    MonsterCardAction(
                        text = "Attack #41 +0",
                        subAction = persistentListOf(),
                    ),
                ),
        ),
        MonsterCard(
            cardId = 3,
            needsShuffle = true,
            initiative = 42,
            deckName = "",
            actions =
                persistentListOf(
                    MonsterCardAction(
                        text = "Attack #41 +0",
                        subAction =
                            persistentListOf(
                                MonsterCardAction(
                                    text = "Target #13 2",
                                    subAction = persistentListOf(),
                                ),
                                MonsterCardAction(
                                    text = "Poison #25",
                                    subAction = persistentListOf(),
                                ),
                            ),
                    ),
                ),
        ),
        MonsterCard(
            cardId = 4,
            needsShuffle = true,
            initiative = 42,
            deckName = "",
            actions =
                persistentListOf(
                    MonsterCardAction(
                        text = "Move #38 -1",
                        subAction = persistentListOf(),
                    ),
                    MonsterCardAction(
                        text = "Attack #41 +1",
                        subAction =
                            persistentListOf(
                                MonsterCardAction(
                                    text = "Range #37 +1",
                                    subAction = persistentListOf(),
                                ),
                            ),
                    ),
                ),
        ),
        MonsterCard(
            cardId = 5,
            needsShuffle = true,
            initiative = 42,
            deckName = "",
            actions =
                persistentListOf(
                    MonsterCardAction(
                        text = "Ooze offers 2 damage",
                        subAction = persistentListOf(),
                    ),
                    MonsterCardAction(
                        text = "Summon normal Ooze with a hit point value equals to the summoning Ooze's current hit point value (limited by a normal Ooze's specified maximum hit point value)",
                        subAction = persistentListOf(),
                    ),
                ),
        ),
        MonsterCard(
            cardId = 6,
            needsShuffle = true,
            initiative = 42,
            deckName = "",
            actions =
                persistentListOf(
                    MonsterCardAction(
                        text = "Ooze offers 2 damage",
                        subAction = persistentListOf(),
                    ),
                    MonsterCardAction(
                        text = "Summon normal Ooze with a hit point value equals to the summoning Ooze's current hit point value (limited by a normal Ooze's specified maximum hit point value)",
                        subAction = persistentListOf(),
                    ),
                ),
        ),
        MonsterCard(
            cardId = 7,
            needsShuffle = true,
            initiative = 42,
            deckName = "",
            actions =
                persistentListOf(
                    MonsterCardAction(
                        text = "Move #38 -1",
                        subAction = persistentListOf(),
                    ),
                    MonsterCardAction(
                        text = "Loot #52 1",
                        subAction = persistentListOf(),
                    ),
                    MonsterCardAction(
                        text = "Heal #40 2 (Self)",
                        subAction = persistentListOf(),
                    ),
                ),
        ),
        MonsterCard(
            cardId = 8,
            needsShuffle = true,
            initiative = 42,
            deckName = "",
            actions =
                persistentListOf(
                    MonsterCardAction(
                        text = "Push #08 1 and Poison #25",
                        subAction =
                            persistentListOf(
                                MonsterCardAction(
                                    text = "Target all adjacent enemies",
                                    subAction = persistentListOf(),
                                ),
                            ),
                    ),
                    MonsterCardAction(
                        text = "Attack #41 +1",
                        subAction =
                            persistentListOf(
                                MonsterCardAction(
                                    text = "Range #37 -1",
                                    subAction = persistentListOf(),
                                ),
                            ),
                    ),
                ),
        ),
    )
