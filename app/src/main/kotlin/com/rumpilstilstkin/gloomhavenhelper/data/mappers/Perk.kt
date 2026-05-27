package com.rumpilstilstkin.gloomhavenhelper.data.mappers

import com.rumpilstilstkin.gloomhavenhelper.bd.entity.CharacterPerkDetailsBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.PerkBd
import com.rumpilstilstkin.gloomhavenhelper.data.TextResolver
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterClassType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Perk
import com.rumpilstilstkin.gloomhavenhelper.localization.TranslationKeys

// [Perk.text] is display-only and always populated by [localized]; it starts blank because the
// canonical perk text no longer lives in the catalog.
fun PerkBd.toDomain() = Perk(
    id = this.perkId,
    text = "",
    characterType = CharacterClassType.valueOf(this.characterType),
)

fun CharacterPerkDetailsBd.toDomain() = Perk(
    id = this.perk.perkId,
    text = "",
    characterType = CharacterClassType.valueOf(this.perk.characterType),
    characterPerkId = this.characterPerk.id
)

/**
 * Replaces the display-only [Perk.text] with text for the active locale, keyed by the stable
 * [Perk.id] (the perk's db id — already the foreign key for saved character perks, so it is the
 * canonical perk identity). The embedded #NN icon tokens live inside the translated string and
 * are reproduced verbatim per locale, so game notation renders identically in every language.
 * Identity fields (id, characterType, characterPerkId) keep their canonical values, so
 * selected/owned perks are unaffected by language.
 */
fun Perk.localized(resolver: TextResolver) = copy(
    text = resolver.resolve(TranslationKeys.PERK, id.toString(), TranslationKeys.FIELD_TEXT)
)