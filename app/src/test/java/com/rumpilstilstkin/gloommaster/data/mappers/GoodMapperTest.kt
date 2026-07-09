package com.rumpilstilstkin.gloommaster.data.mappers

import com.rumpilstilstkin.gloommaster.bd.entity.GoodBd
import com.rumpilstilstkin.gloommaster.bd.entity.GoodWithTranslation
import com.rumpilstilstkin.gloommaster.domain.entity.GoodType
import com.rumpilstilstkin.gloommaster.domain.entity.PackType
import com.rumpilstilstkin.gloommaster.data.mappers.toDomain
import org.junit.Test
import org.junit.Assert.assertThrows
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class GoodMapperTest {
    @Test
    fun `given GoodWithTranslation when toDomain then nested good fields are propagated and enums are decoded`() {
        // Given
        val source = withTranslationFixture(
            good = goodBdFixture(
                goodId = 11,
                displayNumber = 7,
                type = "Body",
                cost = 25,
                image = "armor.png",
                pack = "FORGOTTEN_CIRCLES",
            ),
            name = "Plate Armor",
        )

        // When
        val good = source.toDomain()

        // Then
        expectThat(good.id).isEqualTo(11)
        expectThat(good.displayNumber).isEqualTo(7)
        expectThat(good.name).isEqualTo("Plate Armor")
        expectThat(good.type).isEqualTo(GoodType.Body)
        expectThat(good.cost).isEqualTo(25)
        expectThat(good.image).isEqualTo("armor.png")
        expectThat(good.pack).isEqualTo(PackType.FORGOTTEN_CIRCLES)
    }

    @Test
    fun `given invalid goodType when toDomain then IllegalArgumentException is thrown`() {
        // Given
        val source = withTranslationFixture(
            good = goodBdFixture(type = "NotARealGoodType"),
        )

        // When / Then
        assertThrows(IllegalArgumentException::class.java) { source.toDomain() }
    }

    @Test
    fun `given invalid pack when toDomain then IllegalArgumentException is thrown`() {
        // Given
        val source = withTranslationFixture(
            good = goodBdFixture(pack = "NOT_A_PACK"),
        )

        // When / Then
        assertThrows(IllegalArgumentException::class.java) { source.toDomain() }
    }

    companion object {
        fun goodBdFixture(
            goodId: Int = 1,
            displayNumber: Int = 1,
            type: String = "Body",
            cost: Int = 10,
            image: String = "img.png",
            pack: String = "MAIN",
            isDrawing: Boolean = false,
        ): GoodBd = GoodBd(
            goodId = goodId,
            displayNumber = displayNumber,
            type = type,
            cost = cost,
            image = image,
            pack = pack,
            isDrawing = isDrawing,
        )

        fun withTranslationFixture(
            good: GoodBd = goodBdFixture(),
            name: String = "Translated",
        ): GoodWithTranslation = GoodWithTranslation(
            good = good,
            name = name,
        )
    }
}
