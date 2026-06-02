package com.rumpilstilstkin.gloomhavenhelper.bd.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val ALL_MIGRATIONS = arrayOf(
    object : Migration(4, 5) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL(
                """
                ALTER TABLE ScenarioGameStateBd ADD COLUMN level INTEGER NOT NULL DEFAULT 0
                """
                .trimIndent()
            )
        }
    }
)