package com.rumpilstilstkin.gloommaster.data.mappers

import com.rumpilstilstkin.gloommaster.bd.entity.GameLevelInfoBd
import com.rumpilstilstkin.gloommaster.data.mappers.toDomain
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class LevelMapperTest {
    @Test
    fun `given GameLevelInfoBd when toDomain then copies all five fields 1 to 1`() {
        // Given
        val bd = GameLevelInfoBd.fixture(
            level = 3,
            monsterLevel = 4,
            goldCount = 60,
            trapDamage = 5,
            experience = 12,
        )

        // When
        val domain = bd.toDomain()

        // Then
        expectThat(domain.level).isEqualTo(3)
        expectThat(domain.monsterLevel).isEqualTo(4)
        expectThat(domain.goldCount).isEqualTo(60)
        expectThat(domain.trapDamage).isEqualTo(5)
        expectThat(domain.experience).isEqualTo(12)
    }
}
