# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Gloomhaven Helper is an Android companion app for the Gloomhaven board game. It tracks teams, characters, scenarios, items, perks, personal quests, and active scenario gameplay using a local Room database.

## Build Commands

```bash
# Build the project
./gradlew build

# Build debug APK
./gradlew assembleDebug

# Build release APK (with ProGuard minification)
./gradlew assembleRelease

# Run unit tests
./gradlew test

# Run a single unit test class
./gradlew test --tests "com.rumpilstilstkin.gloomhavenhelper.YourTestClass"

# Run instrumented tests (requires connected device/emulator)
./gradlew connectedAndroidTest

# Clean build
./gradlew clean build

# Generate Room schema (exports to app/schemas/)
./gradlew kspDebugKotlin
```

## Architecture

The project follows Clean Architecture with four layers:

- **Presentation** (`screens/`): Jetpack Compose UI + ViewModels with `StateFlow<UiState>`
- **Domain** (`domain/`): Use cases for business logic, entity models, error types
- **Data** (`data/`): Repositories coordinating between domain and persistence, mappers
- **Persistence** (`bd/`): Room database with DAOs, entities, type converters, and JSON fillers

### Key Patterns

- **Reactive data flow**: Room DAOs return `Flow<Entity>` → Repositories transform to `Flow<DomainModel>` → ViewModels emit `StateFlow<UiState>` → Compose collects via `collectAsStateWithLifecycle()`
- **Navigation**: Type-safe routes defined in `navigation/GlHelperScreens.kt` using `@Serializable` data classes/objects
- **DI**: Hilt with `@Singleton` scope for database, DAOs, and repositories; modules in `di/`
- **Character classes**: Stored as `CharacterClassType` enum, not in database. `TeamCharacterClassBd` tracks unlocked classes per team

### Database

- Room database configured in `bd/GlHelperDatabase.kt`
- Schema exports to `app/schemas/` for migration validation
- Migrations in `bd/migrations/` - add to `ALL_MIGRATIONS` array
- Initial data loaded from JSON via fillers in `bd/filler/json/`
- Type converters in `bd/typeconverters/` for JSON serialization of complex types

When changing database schema:
1. Increment version in `GlHelperDatabase.kt`
2. Create migration object (e.g., `MIGRATION_1_2`) in `bd/migrations/`
3. Add migration to `ALL_MIGRATIONS` array
4. Build to export new schema

### Key Entities

- `TeamBd` - Team with achievements, packs, reputation, prosperity
- `CharacterBd` - Character stats, level, experience, gold
- `ScenarioBd` / `TeamScenarioBd` - Scenarios and team progress
- `MonsterBd` / `MonsterStatsBd` / `MonsterAbilityCardBd` - Monster data
- `ScenarioGameStateBd` - Active scenario state (monsters, rounds, cards)
- `GoodBd` / `PerkBd` / `PersonalQuestBd` - Items, perks, quests

## Tech Stack

- Kotlin 2.3, JDK 11
- Android SDK 36 (minSdk 31)
- Jetpack Compose with Material3
- Room for persistence
- Hilt for dependency injection
- Kotlin Coroutines + Flow for async
- Kotlin Serialization for JSON
- KSP for annotation processing
- Coil for image loading
- ProGuard for release minification

## Project Structure

```
app/src/main/kotlin/com/rumpilstilstkin/gloomhavenhelper/
├── bd/                    # Room database layer
│   ├── dao/               # DAO interfaces
│   ├── entity/            # Room entities
│   ├── filler/json/       # JSON data fillers for initial DB population
│   ├── migrations/        # Database migrations
│   └── typeconverters/    # JSON type converters
├── data/                  # Repository layer
│   ├── datasource/        # Data sources (e.g., SharedPreferences)
│   └── mappers/           # Entity to domain mappers
├── di/                    # Hilt DI modules
├── domain/                # Business logic layer
│   ├── entity/            # Domain models
│   ├── error/             # Error types
│   └── usecase/           # Use cases organized by feature
├── navigation/            # Navigation routes and nav host
│   └── events/            # Navigation events
├── screens/               # UI screens
│   ├── characters/        # Character details, goods, perks, quests
│   ├── dialogs/           # Reusable dialogs
│   ├── models/            # UI state models
│   ├── scenario/          # Scenario play and constructor
│   ├── start/             # Main tabs (team, characters, scenarios, shop)
│   └── teem/              # Team editing, achievements, goods
├── ui/                    # Shared UI components
│   ├── characters/        # Character UI components
│   ├── components/        # Generic components
│   ├── goods/             # Goods UI components
│   ├── icons/             # Custom icons (effects, goods, text)
│   ├── scenario/          # Scenario UI components
│   ├── team/              # Team UI components (dialogs)
│   └── theme/             # App theme
└── utils/                 # Utility functions
```

## Key Files

- `GlHelperDatabase.kt` - Database configuration and DAO providers
- `GlHelperModule.kt` - Main Hilt DI module
- `GlHelperScreens.kt` - Navigation route definitions
- `GlHelperNavHost.kt` - Navigation graph setup
- `MainActivity.kt` / `MainActivityViewModel.kt` - App entry point
