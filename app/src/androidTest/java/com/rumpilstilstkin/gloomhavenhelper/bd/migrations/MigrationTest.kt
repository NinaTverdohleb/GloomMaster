package com.rumpilstilstkin.gloomhavenhelper.bd.migrations

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import androidx.room.Room
import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.rumpilstilstkin.gloomhavenhelper.bd.GlHelperDatabase
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

/**
 * Test class for database migrations.
 * 
 * This class demonstrates how to test Room database migrations.
 * It uses the MigrationTestHelper to create and validate migrations.
 * 
 * Note: This is a template for future migration tests. Since we currently
 * only have version 1 of the database, there are no actual migrations to test yet.
 * When you add a new migration, uncomment and modify the test methods as needed.
 */
@RunWith(AndroidJUnit4::class)
class MigrationTest {
    private val TEST_DB = "migration-test"

    // Helper for creating Room databases and migrations
    @get:Rule
    val helper: MigrationTestHelper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        GlHelperDatabase::class.java.canonicalName,
        FrameworkSQLiteOpenHelperFactory()
    )

    /**
     * Example test for migration from version 1 to 2.
     * Uncomment and modify when implementing the first migration.
     */
    /*
    @Test
    @Throws(IOException::class)
    fun migrate1To2() {
        // Create version 1 of the database
        val db = helper.createDatabase(TEST_DB, 1).apply {
            // Insert sample data for version 1
            val values = ContentValues().apply {
                put("teamId", 1)
                put("name", "Test Team")
                put("teamAchievement", "")
                put("globalAchievement", "")
                put("reputation", 0)
                put("prosperity", 0)
            }
            insert("TeamBd", SQLiteDatabase.CONFLICT_REPLACE, values)
            
            // Close the database
            close()
        }

        // Migrate to version 2
        val migratedDb = helper.runMigrationsAndValidate(TEST_DB, 2, true, MIGRATION_1_2)

        // Verify that the data is still accessible and correct
        val cursor = migratedDb.query("SELECT * FROM TeamBd WHERE teamId = 1")
        cursor.use {
            it.moveToFirst()
            val nameIndex = it.getColumnIndex("name")
            assert(nameIndex != -1)
            assert(it.getString(nameIndex) == "Test Team")
            
            // If you added new columns in the migration, verify they have the expected default values
            // val newColumnIndex = it.getColumnIndex("new_column")
            // assert(newColumnIndex != -1)
            // assert(it.getInt(newColumnIndex) == 0)
        }
    }
    */

    /**
     * Test that verifies all migrations can be applied sequentially.
     * Uncomment and modify when you have multiple migrations.
     */
    /*
    @Test
    @Throws(IOException::class)
    fun migrateAll() {
        // Create the oldest version of the database
        helper.createDatabase(TEST_DB, 1).close()

        // Open the latest version of the database
        Room.databaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            GlHelperDatabase::class.java,
            TEST_DB
        ).addMigrations(*ALL_MIGRATIONS).build().apply {
            // Verify the database is open
            openHelper.writableDatabase
            close()
        }
    }
    */
}
