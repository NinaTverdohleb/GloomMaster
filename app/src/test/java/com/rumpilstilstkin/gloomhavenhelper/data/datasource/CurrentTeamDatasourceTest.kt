package com.rumpilstilstkin.gloomhavenhelper.data.datasource

import android.content.SharedPreferences
import com.rumpilstilstkin.gloomhavenhelper.data.datasource.CurrentTeamDatasource.Companion.EMPTY_TEAM
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class CurrentTeamDatasourceTest {
    private val prefs: SharedPreferences = mockk()
    private val editor: SharedPreferences.Editor = mockk(relaxed = true)
    private val sut = CurrentTeamDatasource(prefs)

    @Test
    fun `given prefs has a stored team id when reading currentTeam then the stored value is returned`() {
        // Given
        every { prefs.getInt(CURRENT_TEAM_KEY, EMPTY_TEAM) } returns 42

        // When
        val value = sut.currentTeam

        // Then
        expectThat(value).isEqualTo(42)
    }

    @Test
    fun `given prefs has no stored team id when reading currentTeam then EMPTY_TEAM is returned`() {
        // Given
        every { prefs.getInt(CURRENT_TEAM_KEY, EMPTY_TEAM) } returns EMPTY_TEAM

        // When
        val value = sut.currentTeam

        // Then
        expectThat(value).isEqualTo(EMPTY_TEAM)
    }

    @Test
    fun `given a new team id when writing currentTeam then prefs editor receives putInt with that value`() {
        // Given
        every { prefs.edit() } returns editor

        // When
        sut.currentTeam = 7

        // Then
        verify { editor.putInt(CURRENT_TEAM_KEY, 7) }
    }

    companion object {
        private const val CURRENT_TEAM_KEY = "currentTeam"
    }
}
