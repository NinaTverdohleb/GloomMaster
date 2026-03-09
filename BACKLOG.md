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

`CharacterRepository` has 5 DAO dependencies, violating Single Responsibility Principle.

**Current dependencies:**
- `CharacterDao`
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

### 🏗️ ARCH-005: Implement Repository Caching Strategy
**Priority:** 🟡 Medium

Only `LevelInfoRepository` implements caching. Other static data (classes, goods, perks, achievements) loaded on each request.

**Tasks:**
- [ ] Add in-memory cache to `CharacterClassRepository`
- [ ] Add in-memory cache to `GoodsRepository`
- [ ] Add in-memory cache to `PerksRepository`
- [ ] Add in-memory cache to `AchievementRepository`
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

---

## Code Quality

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

### 🔧 REF-004: Unify State Class Patterns
**Priority:** 🟢 Low

Mixed patterns for UI state: `sealed interface`, `sealed class`, `data class`.

**Tasks:**
- [ ] Document preferred pattern (sealed interface recommended)
- [ ] Refactor inconsistent state classes
- [ ] Add `Loading`, `Error`, `Success` base states

---

## Features

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

## DevOps

### 🔧 DEV-001: Set Up CI/CD Pipeline
**Priority:** 🟡 Medium

**Tasks:**
- [ ] Create GitHub Actions workflow
- [ ] Run lint on PR
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

| Priority | Count  |
|----------|--------|
| 🔴 Critical | 0      |
| 🟠 High | 2      |
| 🟡 Medium | 3      |
| 🟢 Low | 6      |
| **Total** | **11** |

## Recommended Execution Order

### Phase 1: Foundation (Critical + High Priority)
1. ARCH-003: Implement error handling strategy
2. ARCH-001: Split CharacterRepository

### Phase 2: Quality (Medium Priority)
3. ARCH-005: Implement caching strategy
4. DEV-001: CI/CD pipeline
5. DEV-002: ProGuard rules

### Phase 3: Enhancement (Low Priority)
6. REF-002: Standardize DAO queries
7. REF-004: Unify state class patterns
8. ARCH-006: Extract mapping logic
9. DB-005: Database export/import
10. FEAT-005: Scenario rewards
11. FEAT-006: Cloud sync
