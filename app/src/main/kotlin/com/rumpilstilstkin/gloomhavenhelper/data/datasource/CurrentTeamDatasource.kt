package com.rumpilstilstkin.gloomhavenhelper.data.datasource

import android.content.SharedPreferences
import android.util.Log
import javax.inject.Inject
import javax.inject.Singleton
import androidx.core.content.edit

@Singleton
class CurrentTeamDatasource @Inject constructor(
    private val preference: SharedPreferences
) {
    var currentTeam: Int
        get() = preference.getInt(CURRENT_TEAM, EMPTY_TEAM)
        set(value) {
            preference.edit { putInt(CURRENT_TEAM, value) }
        }

    companion object {
        private const val CURRENT_TEAM = "currentTeam"
        const val EMPTY_TEAM = -1
    }
}