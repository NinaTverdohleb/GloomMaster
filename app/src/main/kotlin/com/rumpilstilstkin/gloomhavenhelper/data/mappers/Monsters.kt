package com.rumpilstilstkin.gloomhavenhelper.data.mappers

import com.rumpilstilstkin.gloomhavenhelper.bd.entity.MonsterAbilityCardBd
import com.rumpilstilstkin.gloomhavenhelper.data.TextResolver
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.Monster
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterAction
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterCard
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterStats

fun MonsterAbilityCardBd.toDomain(): MonsterCard = MonsterCard(
    cardId = cardId,
    imageName = imageName,
    needsShuffle = needsShuffle,
    deckName = deckName,
    initiative = initiative
)

/**
 * Localizes a monster for the active locale: sets the display-only [Monster.displayName] from the
 * stable [Monster.key], and localizes the ability/special text embedded in its stats. The key is
 * untouched, so scenario monster lists and saved game state stay language-independent.
 */
fun Monster.localized(resolver: TextResolver): Monster = copy(
    displayName = resolver.resolveMonster(key),
    stats = stats.localizedActions(resolver),
    eliteStats = eliteStats.localizedActions(resolver),
)

fun MonsterStats.localized(resolver: TextResolver): MonsterStats = copy(
    stats = stats.localizedActions(resolver),
)

/**
 * Localizes the embedded [MonsterAction.Text] content (recursively, through sub-actions) while
 * leaving numeric [MonsterAction.Action] entries untouched. Symbol placeholders (e.g. "#30") are
 * preserved by the translations, so game notation renders identically in every language.
 */
fun List<MonsterAction>.localizedActions(resolver: TextResolver): List<MonsterAction> =
    map { it.localized(resolver) }

private fun MonsterAction.localized(resolver: TextResolver): MonsterAction = when (this) {
    is MonsterAction.Action -> copy(subAction = subAction?.localizedActions(resolver))
    is MonsterAction.Text -> copy(
        content = resolver.resolveMonsterText(content),
        subAction = subAction?.localizedActions(resolver),
    )
}