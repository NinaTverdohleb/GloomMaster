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
| 🔧 | Refactoring |

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

Only `LevelInfoRepository` implements caching. Other static data (goods, perks, achievements) loaded on each request.

**Tasks:**
- [ ] Add in-memory cache to `GoodsRepository`
- [ ] Add in-memory cache to `PerksRepository`
- [ ] Add in-memory cache to `AchievementRepository`
- [ ] Implement cache invalidation on data change
- [ ] Consider `StateFlow` for cached data exposure

---

## Features

### ✨ FEAT-001: Scenario Rewards Distribution
**Priority:** 🟡 Medium

Auto-calculate and distribute rewards after scenario completion.

**Tasks:**
- [ ] Calculate gold per character based on scenario level
- [ ] Calculate experience rewards
- [ ] Show reward summary dialog
- [ ] Apply rewards to characters

---

### ✨ FEAT-002: Database Export/Import
**Priority:** 🟢 Low

**Tasks:**
- [ ] Create `DatabaseBackupManager` class
- [ ] Implement JSON export of all tables
- [ ] Implement JSON import with validation
- [ ] Add UI for export/import in settings
- [ ] Handle version compatibility

---

### ✨ FEAT-003: Cloud Sync/Backup
**Priority:** 🟢 Low

**Tasks:**
- [ ] Evaluate Firebase/Supabase options
- [ ] Implement authentication
- [ ] Implement cloud backup
- [ ] Implement restore from cloud
- [ ] Handle offline/online sync

---

## Code Quality

### 🔧 REF-001: Unify State Class Patterns
**Priority:** 🟢 Low

Mixed patterns for UI state: `sealed interface`, `sealed class`, `data class`.

**Tasks:**
- [ ] Document preferred pattern (sealed interface recommended)
- [ ] Refactor inconsistent state classes
- [ ] Add `Loading`, `Error`, `Success` base states

---

## Summary by Priority

| Priority | Count |
|----------|-------|
| 🔴 Critical | 0 |
| 🟠 High | 2 |
| 🟡 Medium | 2 |
| 🟢 Low | 3 |
| **Total** | **7** |

## Recommended Execution Order

### Phase 1: Foundation (High Priority)
1. ARCH-003: Implement error handling strategy
2. ARCH-001: Split CharacterRepository

### Phase 2: Quality (Medium Priority)
3. ARCH-005: Implement caching strategy
4. FEAT-001: Scenario rewards distribution

### Phase 3: Enhancement (Low Priority)
5. REF-001: Unify state class patterns
6. FEAT-002: Database export/import
7. FEAT-003: Cloud sync
