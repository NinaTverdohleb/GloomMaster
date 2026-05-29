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

## Database & Localization

### 📦 LOC-003: Tests for locale-fallback queries
**Priority:** 🟡 Medium

The `COALESCE(target, default, <id>)` + correlated `NOT EXISTS` pattern is easy to get wrong (a wrong-table subquery and an `AND`/`OR` precedence bug were both found and fixed during review). No tests guard it.

**Tasks:**
- [ ] In-memory DB DAO tests: seed target + default locale rows, assert resolution
- [ ] Assert fallback to default locale when target is missing
- [ ] Assert fallback to the stable id when neither locale has a row

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

### 🔧 REF-002: Push pack filtering into SQL in `GoodsRepository.getGoods`
**Priority:** 🟢 Low

`getGoods` loads the whole goods table (double-joined for translations) then filters `packs` in Kotlin. A `getAllByPack` SQL query already exists but is unused.

**Tasks:**
- [ ] Use `getAllByPack` (or add an `IN (:packs)` variant) so the filter runs in SQL
- [ ] Remove the unused query if superseded

---

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
| 🔴 Critical | 0     |
| 🟠 High | 2     |
| 🟡 Medium | 3     |
| 🟢 Low | 3     |
| **Total** | **8** |

## Recommended Execution Order

### Phase 1: Foundation (High Priority)
1. ARCH-003: Implement error handling strategy
2. ARCH-001: Split CharacterRepository

### Phase 2: Quality (Medium Priority)
3. LOC-003: Tests for locale-fallback queries
4. ARCH-005: Implement caching strategy
5. FEAT-001: Scenario rewards distribution

### Phase 3: Enhancement (Low Priority)
6. REF-002: Push pack filtering into SQL
7. REF-001: Unify state class patterns
9. FEAT-003: Cloud sync
