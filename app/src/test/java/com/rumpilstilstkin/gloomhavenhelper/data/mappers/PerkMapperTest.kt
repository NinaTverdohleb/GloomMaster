package com.rumpilstilstkin.gloomhavenhelper.data.mappers

import com.rumpilstilstkin.gloomhavenhelper.bd.entity.CharacterPerkBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.CharacterPerkWithNameBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.PerkTranslationBd
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class PerkMapperTest {
    @Test
    fun `given CharacterPerkWithNameBd when toDomain then perkId and text map onto Perk`() {
        // Given
        val source = CharacterPerkWithNameBd(
            perk = CharacterPerkBd(id = 99, characterId = 1, perkId = 42),
            text = "Add two +1 cards",
        )

        // When
        val perk = source.toDomain()

        // Then
        expectThat(perk.id).isEqualTo(42)
        expectThat(perk.text).isEqualTo("Add two +1 cards")
    }

    @Test
    fun `given PerkTranslationBd when toDomain then perkId and text map onto Perk`() {
        // Given
        val source = PerkTranslationBd(
            perkId = 7,
            locale = "en",
            text = "Remove four +0 cards",
            characterType = "Brute",
        )

        // When
        val perk = source.toDomain()

        // Then
        expectThat(perk.id).isEqualTo(7)
        expectThat(perk.text).isEqualTo("Remove four +0 cards")
    }
}
