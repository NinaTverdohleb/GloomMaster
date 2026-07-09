# Database Migration Strategy

This document outlines the database migration strategy for the GloomhavenHalper application.

## Overview

The application uses Room for database operations. Room provides a migration mechanism to handle database schema changes between app versions. This ensures that users' data is preserved when the app is updated with schema changes.

## Current Implementation

The migration strategy is implemented in the following files:

- `DatabaseMigrations.kt`: Contains all migration classes and the `ALL_MIGRATIONS` array
- `GlHelperDatabase.kt`: Configures the database to use the migrations

## How Migrations Work

1. Each migration is defined as an object that extends the `Migration` class
2. Migrations specify the start and end versions (e.g., `Migration(1, 2)` migrates from version 1 to 2)
3. The `migrate()` method contains SQL statements to transform the database schema
4. All migrations are added to the `ALL_MIGRATIONS` array
5. The database builder is configured to use these migrations with `.addMigrations(*ALL_MIGRATIONS)`

## Adding a New Migration

When you need to make changes to the database schema, follow these steps:

1. Increment the database version in `GlHelperDatabase.kt` (e.g., from `version = 1` to `version = 2`)
2. Create a new migration in `DatabaseMigrations.kt` (e.g., `MIGRATION_1_2` for migrating from version 1 to 2)
3. Implement the migration logic in the `migrate()` method using SQL statements
4. Add the new migration to the `ALL_MIGRATIONS` array
5. Build the project to export the new schema version to the `app/schemas` directory
6. Test the migration thoroughly

Example migration:

```kotlin
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Add a new column to the Character table
        database.execSQL("ALTER TABLE CharacterBd ADD COLUMN health INTEGER NOT NULL DEFAULT 0")
    }
}
```

## Testing Migrations

To test migrations:

1. Install the previous version of the app on a device/emulator
2. Create some data in the app
3. Install the new version with the migration
4. Verify that the data is preserved and the app works correctly

For automated testing, you can use Room's testing utilities:

```kotlin
@Test
fun migrateFrom1To2() {
    val db = Room.inMemoryDatabaseBuilder(
        ApplicationProvider.getApplicationContext(),
        GlHelperDatabase::class.java
    ).build()
    
    // Create test data in version 1 schema
    
    // Close the database
    db.close()
    
    // Reopen with version 2 schema and verify data
    val migratedDb = Room.inMemoryDatabaseBuilder(
        ApplicationProvider.getApplicationContext(),
        GlHelperDatabase::class.java
    )
    .addMigrations(MIGRATION_1_2)
    .build()
    
    // Verify that the data was migrated correctly
}
```

## Fallback Strategy

In development environments, you can use `.fallbackToDestructiveMigration()` to allow Room to recreate the database if no migration path is found. This is commented out in the current implementation and should only be used during development or in extreme cases.

## Best Practices

1. Always increment the database version when making schema changes
2. Write migrations for all schema changes, no matter how small
3. Test migrations thoroughly before releasing
4. Keep migrations simple and focused on specific schema changes
5. Document complex migrations with comments
6. Export and commit schema files to version control
7. Consider the impact of migrations on app performance, especially for large databases