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
val ALL_MIGRATIONS = arrayOf<Migration>(
    object : Migration(1, 2) {
        override fun migrate(db: androidx.sqlite.db.SupportSQLiteDatabase) {
            db.execSQL("ALTER TABLE TeamBd ADD COLUMN difficultyLevel INTEGER NOT NULL DEFAULT 0")
        }
    },
    // Adds the localized-text store. Existing content/user tables are untouched, so teams,
    // characters and scenario progress are preserved on upgrade. The table is populated by
    // the database filler (see DatabaseFiller).
    object : Migration(2, 3) {
        override fun migrate(db: androidx.sqlite.db.SupportSQLiteDatabase) {
            db.execSQL(
                "CREATE TABLE IF NOT EXISTS `TranslationBd` (" +
                    "`entityType` TEXT NOT NULL, " +
                    "`entityKey` TEXT NOT NULL, " +
                    "`fieldName` TEXT NOT NULL, " +
                    "`locale` TEXT NOT NULL, " +
                    "`text` TEXT NOT NULL, " +
                    "PRIMARY KEY(`entityType`, `entityKey`, `fieldName`, `locale`))"
            )
            db.execSQL(
                "CREATE INDEX IF NOT EXISTS `index_TranslationBd_locale` " +
                    "ON `TranslationBd` (`locale`)"
            )
        }
    }
)