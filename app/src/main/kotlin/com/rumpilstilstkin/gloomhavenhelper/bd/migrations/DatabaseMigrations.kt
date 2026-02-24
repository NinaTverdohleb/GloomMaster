package com.rumpilstilstkin.gloomhavenhelper.bd.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * This file contains all database migrations for the GlHelperDatabase.
 * 
 * When adding a new migration:
 * 1. Increment the database version in GlHelperDatabase.kt
 * 2. Create a new Migration object (e.g., MIGRATION_2_3 for migrating from version 2 to 3)
 * 3. Implement the migration logic in the migrate() method
 * 4. Add the new migration to the ALL_MIGRATIONS list
 * 5. Export the schema for the new version by building the project
 * 6. Test the migration thoroughly
 */

/**
 * Migration from version 1 to 2.
 * Adds monster management tables: MonsterBd, MonsterStatsBd, MonsterAbilityCardBd, ScenarioMonsterBd
 */
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // Create MonsterBd table
        db.execSQL("""
            CREATE TABLE IF NOT EXISTS MonsterBd (
                monsterId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                name TEXT NOT NULL
            )
        """)

        // Create MonsterStatsBd table
        db.execSQL("""
            CREATE TABLE IF NOT EXISTS MonsterStatsBd (
                monsterId INTEGER NOT NULL,
                scenarioLevel INTEGER NOT NULL,
                isElite INTEGER NOT NULL,
                stats TEXT NOT NULL,
                PRIMARY KEY (monsterId, scenarioLevel, isElite),
                FOREIGN KEY (monsterId) REFERENCES MonsterBd(monsterId) ON DELETE CASCADE
            )
        """)
        db.execSQL("CREATE INDEX IF NOT EXISTS index_MonsterStatsBd_monsterId ON MonsterStatsBd(monsterId)")

        // Create MonsterAbilityCardBd table
        db.execSQL("""
            CREATE TABLE IF NOT EXISTS MonsterAbilityCardBd (
                cardId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                monsterId INTEGER NOT NULL,
                initiative INTEGER NOT NULL,
                actions TEXT NOT NULL,
                needsShuffle INTEGER NOT NULL DEFAULT 0,
                FOREIGN KEY (monsterId) REFERENCES MonsterBd(monsterId) ON DELETE CASCADE
            )
        """)
        db.execSQL("CREATE INDEX IF NOT EXISTS index_MonsterAbilityCardBd_monsterId ON MonsterAbilityCardBd(monsterId)")

        // Create ScenarioMonsterBd table
        db.execSQL("""
            CREATE TABLE IF NOT EXISTS ScenarioMonsterBd (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                scenarioNumber INTEGER NOT NULL,
                monsterId INTEGER NOT NULL,
                FOREIGN KEY (scenarioNumber) REFERENCES ScenarioBd(scenarioNumber) ON DELETE CASCADE,
                FOREIGN KEY (monsterId) REFERENCES MonsterBd(monsterId) ON DELETE CASCADE
            )
        """)
        db.execSQL("CREATE INDEX IF NOT EXISTS index_ScenarioMonsterBd_scenarioNumber ON ScenarioMonsterBd(scenarioNumber)")
        db.execSQL("CREATE INDEX IF NOT EXISTS index_ScenarioMonsterBd_monsterId ON ScenarioMonsterBd(monsterId)")
    }
}

/**
 * List of all migrations to be applied to the database.
 * Add new migrations to this list as they are created.
 */
val ALL_MIGRATIONS = arrayOf<Migration>(
    MIGRATION_1_2,
)