package com.rumpilstilstkin.gloomhavenhelper.bd.migrations

import androidx.room.migration.Migration

/**
 * This file contains all database migrations for the GlHelperDatabase.
 *
 * When adding a new migration:
 * 1. Increment the database version in GlHelperDatabase.kt
 * 2. Create a new Migration object (e.g., MIGRATION_1_2 for migrating from version 1 to 2)
 * 3. Implement the migration logic in the migrate() method
 * 4. Add the new migration to the ALL_MIGRATIONS list
 * 5. Export the schema for the new version by building the project
 * 6. Test the migration thoroughly
 */

/**
 * List of all migrations to be applied to the database.
 * Add new migrations to this list as they are created.
 */
val ALL_MIGRATIONS = arrayOf(
    object : Migration(1, 2) {
        override fun migrate(db: androidx.sqlite.db.SupportSQLiteDatabase) {
            db.execSQL("ALTER TABLE TeamBd ADD COLUMN difficultyLevel INTEGER NOT NULL DEFAULT 0")
        }
    }
)