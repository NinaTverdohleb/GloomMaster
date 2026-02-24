# Gloomhaven Helper - Project Backlog

## Legend

| Priority | Description |
|----------|-------------|
| 🔴 | Critical - blocks development or causes data loss |
| 🟠 | High - significant improvement to architecture/UX |
| 🟡 | Medium - technical debt or enhancement |
| 🟢 | Low - nice to have |

| Type | Description |
|------|-------------|
| 🐛 | Bug fix |
| 🏗️ | Architecture |
| 📦 | Database |
| ✨ | Feature |
| 📝 | Documentation |
| 🔧 | Refactoring |

---

## Database Layer

### 📦 DB-001: Add Foreign Key Constraints to CharacterBd
**Priority:** 🟠 High

`CharacterBd.teamId` and `CharacterBd.characterType` lack explicit FK constraints in Room entity definition.

**Current state:**
```kotlin
@Entity
data class CharacterBd(
    val characterType: TEXT,  // No @ForeignKey
    val teamId: INTEGER,      // No @ForeignKey
)
```

**Tasks:**
- [ ] Add `@ForeignKey` annotation for `teamId` → `TeamBd.teamId`
- [ ] Add `@ForeignKey` annotation for `characterType` → `CharacterClassBd.characterType`
- [ ] Create migration from version 1 to 2
- [ ] Test cascading delete behavior

---

### 📦 DB-002: Eliminate Data Duplication in TeamScenarioBd
**Priority:** 🟡 Medium

`TeamScenarioBd` stores `scenarioName` and `scenarioRequirements` which duplicates data from `ScenarioBd`.

**Current state:**
```
TeamScenarioBd:
  - scenarioNumber (FK)
  - scenarioName       ← duplicate
  - scenarioRequirements ← duplicate
```

**Tasks:**
- [ ] Remove `scenarioName` and `scenarioRequirements` columns
- [ ] Use `@Relation` with `ScenarioBd` for joined queries
- [ ] Create migration
- [ ] Update `TeamScenarioDao` queries
- [ ] Update mappers in `ScenarioRepository`

---

### 📦 DB-003: Add Database Migration Framework
**Priority:** 🟠 High

Database is at version 1 with no migration infrastructure for future updates.

**Tasks:**
- [ ] Create `migrations/` package structure
- [ ] Implement `Migration` classes template
- [ ] Add `fallbackToDestructiveMigration()` for debug builds only
- [ ] Document migration process in ARCHITECTURE.md
- [ ] Add migration tests with `MigrationTestHelper`

---

### 📦 DB-004: Add Index on CharacterBd.characterType
**Priority:** 🟡 Medium

Queries filtering by `characterType` lack index optimization.

**Tasks:**
- [ ] Add `@Index` annotation on `characterType` column
- [ ] Create migration
- [ ] Benchmark query performance before/after

---

### 📦 DB-005: Implement Database Export/Import
**Priority:** 🟢 Low

**Tasks:**
- [ ] Create `DatabaseBackupManager` class
- [ ] Implement JSON export of all tables
- [ ] Implement JSON import with validation
- [ ] Add UI for export/import in settings
- [ ] Handle version compatibility

---

## Architecture

### 🏗️ ARCH-001: Split CharacterRepository
**Priority:** 🟠 High

`CharacterRepository` has 6 DAO dependencies, violating Single Responsibility Principle.

**Current dependencies:**
- `CharacterDao`
- `CharacterClassDao`
- `TeamDao`
- `CharacterGoodsDao`
- `CharacterPerksDao`
- `CharacterPersonalQuestDao`

**Tasks:**
- [ ] Extract `CharacterGoodsRepository` (goods operations)
- [ ] Extract `CharacterPerksRepository` (perks operations)
- [ ] Extract `CharacterQuestRepository` (quest operations)
- [ ] Keep `CharacterRepository` for core character CRUD
- [ ] Update DI module
- [ ] Update dependent UseCases

---

### 🏗️ ARCH-002: Consistent UseCase Pattern in ViewModels
**Priority:** 🟡 Medium

Some ViewModels inject repositories directly instead of UseCases.

**Violations:**
- `MainViewModel` → `LevelInfoRepository`, `ClassRepository`, `TeamRepository`, `CharacterRepository`
- `AddScenarioViewModel` → `ScenarioRepository`, `TeamRepository`
- `CharactersTabViewModel` → `CharacterRepository`

**Tasks:**
- [ ] Create missing UseCases for direct repository calls
- [ ] Refactor ViewModels to use UseCases only
- [ ] Document UseCase naming convention

---

### 🏗️ ARCH-003: Implement Error Handling Strategy
**Priority:** 🟠 High

No unified error handling pattern across layers.

**Tasks:**
- [ ] Create `Result<T, E>` sealed class or use `kotlin.Result`
- [ ] Define domain-specific error types (`CharacterError`, `TeamError`, etc.)
- [ ] Wrap repository operations in try-catch
- [ ] Propagate errors to UI layer
- [ ] Add error UI states to all ViewModels
- [ ] Implement Snackbar/Toast error display

---

### 🏗️ ARCH-004: Add Repository Scope Annotations
**Priority:** 🟡 Medium

Not all repositories have `@Singleton` annotation, causing potential duplicate instances.

**Missing annotations:**
- `CharacterRepository`
- `ClassRepository`
- `ScenarioRepository`
- `GoodsRepository`
- `PerksRepository`
- `QuestsRepository`

**Tasks:**
- [ ] Add `@Singleton` to all repository classes
- [ ] Verify single instance creation via logging

---

### 🏗️ ARCH-005: Implement Repository Caching Strategy
**Priority:** 🟡 Medium

Only `LevelInfoRepository` implements caching. Other static data (classes, goods, perks) loaded on each request.

**Tasks:**
- [ ] Add in-memory cache to `ClassRepository`
- [ ] Add in-memory cache to `GoodsRepository`
- [ ] Add in-memory cache to `PerksRepository`
- [ ] Implement cache invalidation on data change
- [ ] Consider `StateFlow` for cached data exposure

---

### 🏗️ ARCH-006: Extract Mapping Logic to Dedicated Mappers
**Priority:** 🟢 Low

Domain ↔ Entity mapping scattered across repositories.

**Tasks:**
- [ ] Create `mappers/` package in data layer
- [ ] Extract all `toDomain()` extension functions
- [ ] Extract all `toEntity()` extension functions
- [ ] Unit test mappers

---

## Code Quality

### 🔧 REF-001: Fix Typo in UseCase Name
**Priority:** 🔴 Critical (before public API)

`GetAvaliableCharacterGoodsUseCase` → `GetAvailableCharacterGoodsUseCase`

**Tasks:**
- [ ] Rename class
- [ ] Update all references
- [ ] Git history note

---

### 🔧 REF-002: Standardize DAO Query Operators
**Priority:** 🟢 Low

Inconsistent use of `LIKE` vs `=` for exact matches in queries.

**Examples:**
```sql
WHERE teamId LIKE :teamId    -- should be =
WHERE teamId = :teamId       -- correct
```

**Tasks:**
- [ ] Audit all DAO queries
- [ ] Replace `LIKE` with `=` for exact ID matches
- [ ] Use `LIKE` only for pattern matching

---

### 🔧 REF-003: Add KtLint/Detekt Configuration
**Priority:** 🟡 Medium

**Tasks:**
- [ ] Add ktlint Gradle plugin
- [ ] Configure rules in `.editorconfig`
- [ ] Add detekt for static analysis
- [ ] Add pre-commit hook
- [ ] Fix existing violations

---

### 🔧 REF-004: Unify State Class Patterns
**Priority:** 🟢 Low

Mixed patterns for UI state: `sealed interface`, `sealed class`, `data class`.

**Tasks:**
- [ ] Document preferred pattern (sealed interface recommended)
- [ ] Refactor inconsistent state classes
- [ ] Add `Loading`, `Error`, `Success` base states

---

## Features

### ✨ FEAT-001: Implement Monster Management (ScenarioScreen)
**Priority:** 🟠 High

ScenarioScreen UI exists but lacks data layer and business logic.

**Tasks:**
- [ ] Create `MonsterBd` entity
- [ ] Create `MonsterCardBd` entity
- [ ] Create `ScenarioMonsterBd` junction table
- [ ] Create `MonsterDao`
- [ ] Create `MonsterRepository`
- [ ] Create `ScenarioMonsterRepository`
- [ ] Create UseCases:
  - [ ] `GetScenarioMonstersUseCase`
  - [ ] `AddMonsterToScenarioUseCase`
  - [ ] `UpdateMonsterLifeUseCase`
  - [ ] `DrawMonsterCardUseCase`
- [ ] Create `ScenarioViewModel`
- [ ] Connect UI to ViewModel
- [ ] Add monster data filler

---

### ✨ FEAT-002: Implement Team Deletion
**Priority:** 🟡 Medium

No ability to delete teams.

**Tasks:**
- [ ] Add `deleteTeam()` to `TeamDao`
- [ ] Add `deleteTeam()` to `TeamRepository`
- [ ] Create `DeleteTeamUseCase`
- [ ] Add confirmation dialog
- [ ] Handle cascade deletion (characters, scenarios)

---

### ✨ FEAT-003: Implement Character Retirement
**Priority:** 🟡 Medium

Characters can die (`isAlive`) but no retirement flow.

**Tasks:**
- [ ] Add `isRetired` field to `CharacterBd`
- [ ] Create migration
- [ ] Add `retireCharacter()` to `CharacterRepository`
- [ ] Create `RetireCharacterUseCase`
- [ ] Update UI to show retired characters differently
- [ ] Unlock new character class on retirement (game rule)

---

### ✨ FEAT-004: Add Achievements Management
**Priority:** 🟡 Medium

`teamAchievement` and `globalAchievement` stored as strings, no proper management.

**Tasks:**
- [ ] Create `AchievementBd` entity
- [ ] Create `TeamAchievementBd` junction table
- [ ] Migrate string data to relations
- [ ] Create `AchievementRepository`
- [ ] Create achievement management UI
- [ ] Pre-fill achievements from game data

---

### ✨ FEAT-005: Add Scenario Rewards Processing
**Priority:** 🟢 Low

`ScenarioBd` has `newScenarios`, `teamAchievement`, `globalAchievement` but not processed on completion.

**Tasks:**
- [ ] Parse `newScenarios` field
- [ ] Auto-unlock new scenarios on completion
- [ ] Auto-add achievements on completion
- [ ] Show rewards dialog after scenario completion

---

### ✨ FEAT-006: Add Data Sync/Backup to Cloud
**Priority:** 🟢 Low

**Tasks:**
- [ ] Evaluate Firebase/Supabase options
- [ ] Implement authentication
- [ ] Implement cloud backup
- [ ] Implement restore from cloud
- [ ] Handle offline/online sync

---

## Documentation

### 📝 DOC-001: Add KDoc to Public APIs
**Priority:** 🟡 Medium

**Tasks:**
- [ ] Document all public repository methods
- [ ] Document all UseCase classes
- [ ] Document ViewModel actions and states
- [ ] Generate documentation with Dokka

---

### 📝 DOC-002: Create Developer Onboarding Guide
**Priority:** 🟢 Low

**Tasks:**
- [ ] Document project setup steps
- [ ] Document architecture decisions
- [ ] Document coding conventions
- [ ] Add contribution guidelines

---

## DevOps

### 🔧 DEV-001: Set Up CI/CD Pipeline
**Priority:** 🟡 Medium

**Tasks:**
- [ ] Create GitHub Actions workflow
- [ ] Run lint on PR
- [ ] Run unit tests on PR
- [ ] Run instrumented tests on PR
- [ ] Build APK on merge to main
- [ ] Automate version bumping

---

### 🔧 DEV-002: Add ProGuard/R8 Rules
**Priority:** 🟡 Medium

**Tasks:**
- [ ] Configure R8 for release builds
- [ ] Add keep rules for Room entities
- [ ] Add keep rules for Kotlin serialization
- [ ] Test release build functionality

---

## Summary by Priority

| Priority | Count |
|----------|-------|
| 🔴 Critical | 1 |
| 🟠 High | 8 |
| 🟡 Medium | 14 |
| 🟢 Low | 9 |
| **Total** | **32** |

## Recommended Execution Order

### Phase 1: Foundation (Critical + High Priority)
1. REF-001: Fix typo in UseCase name
2. ARCH-003: Implement error handling strategy
3. DB-003: Add database migration framework
4. TEST-001: Add Room database tests
5. TEST-002: Add repository unit tests
6. DB-001: Add FK constraints to CharacterBd
7. ARCH-001: Split CharacterRepository
8. FEAT-001: Implement monster management

### Phase 2: Quality (Medium Priority)
9. ARCH-002: Consistent UseCase pattern
10. ARCH-004: Add repository scope annotations
11. ARCH-005: Implement caching strategy
12. REF-003: Add KtLint/Detekt
13. DB-002: Eliminate data duplication
14. DB-004: Add index on characterType
15. TEST-003: Add UseCase tests
16. TEST-004: Add ViewModel tests
17. FEAT-002: Team deletion
18. FEAT-003: Character retirement
19. FEAT-004: Achievements management
20. DOC-001: Add KDoc
21. DEV-001: CI/CD pipeline
22. DEV-002: ProGuard rules

### Phase 3: Enhancement (Low Priority)
23. REF-002: Standardize DAO queries
24. REF-004: Unify state class patterns
25. ARCH-006: Extract mapping logic
26. DB-005: Database export/import
27. TEST-005: UI tests
28. FEAT-005: Scenario rewards
29. FEAT-006: Cloud sync
30. DOC-002: Developer guide
