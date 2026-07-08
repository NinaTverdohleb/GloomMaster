# Gloomhaven Master

<p align="center">
  <strong>Android companion app for the Gloomhaven board game</strong>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Android-31%2B-green?logo=android" alt="Android 31+"/>
  <img src="https://img.shields.io/badge/Kotlin-2.3-purple?logo=kotlin" alt="Kotlin 2.3"/>
  <img src="https://img.shields.io/badge/Jetpack%20Compose-Material3-blue?logo=jetpackcompose" alt="Jetpack Compose"/>
</p>

---

## Features

## 1. Onboarding

- Step-by-step slides (illustration + title + text) on first launch; shown once,
  after which the main screen opens directly.

## 2. Team Management

- Create and store **multiple teams**, with quick switching of the current team.
- **Renaming** a team.
- **Reputation** — adjustable within the −20…+20 range (per the game rules);
  affects shop discounts.
- **Prosperity** — changes per the game rules: stored as a single cumulative
  value from which the prosperity level (1–9) is derived by fixed thresholds.
  When the level increases, new items of that level are automatically added to
  the team's shop. Bounded below by the starting value and above by the maximum
  level.
- **Difficulty level** of the game, set per team.
- **Donations to the church (donate)** — raises prosperity through donations,
  with the next threshold calculated.
- **Reputation discounts** — the shop price depends on the team's reputation.
- **Expansion packs** — attaching expansions to a team (base set +
  Forgotten Circles).
- **Deleting a team** (the current one or an arbitrary one).
- **Team export / import** — export the team's entire progress to a JSON file
  (share) and import it back.
- **Team achievements** — view, add, remove, and change the value for
  achievements that have levels.
- **Global achievements** — view, add, remove, and change the value for
  achievements that have levels.

## 3. Working with Characters

- Managing the team's unlocked **character classes** (add / remove), accounting
  for classes already available.
- Creating new characters for the team (no more than 4 active) — the number of
  perks available to a character accounts for characters retired before it was
  created.
- Deleting characters.
- Retiring a character.
- Bringing retired characters back.

## 4. Character Details

- Full character sheet: **level, experience, gold, notes**, name.
- **Level up** — explicitly raises the character's level and recalculates the
  number of available perks.
- **Check marks** for unlocking perks (change the number of perks available to
  add).
- Assigning a personal quest to a character (search / pick from a list).
- Tracking progress on the quest's tasks (marking completed tasks).

### 4.1. Character Perks

- Choosing perks according to the **character class** (class-specific options).
- Adding and removing perks; the number of perks available to add is a hint, not
  a hard limit — for special game conditions a perk can be added beyond it.

### 4.2. Character Inventory

- Managing the character's items: **buy for gold**, sell, remove.
- The list of items available to buy depends on the team's pool and the pool of
  active characters.
- The purchase price accounts for the team's **reputation discount**.
- Besides buying, there's an option to add items without deducting from the
  wallet.

## 5. Shop / Items

- Browsing items available to the team (by prosperity level); items already held
  by active characters are not shown.
- **Team item pool** — adding items to the shared pool (by number / by level)
  and removing them from the pool.

## 6. Scenarios

- The team's scenario list with **filtering**; available scenarios depend on the
  team's progress.
- Adding a scenario to the team, removing a scenario.
- **Completing a scenario** with the result recorded.

## 7. Active Battle / Scenario Play

- **Adding monsters** to the battle and **units** (normal / elite) with numbers.
- **Unit level** and **life (HP)** — editable per unit.
- **Effects / conditions** on units (toggling status effects by type) — not
  cleared automatically on a round change.
- **Monster ability card deck** — drawing cards per deck, with a deterministic
  randomness source.
- **Round counter** (advancing to the next round).
- **Magic charges (elements)** — toggling element charge levels, waning between
  rounds.
- **Monster stats by level** (normal and elite).
- **Saving and restoring battle state** across sessions, including the team
  level: you can return to an active battle at any time, even after fully closing
  the app.
- Removing monsters and individual units from the battle.

---

## 8. Settings and Localization

- Settings screen: current language, current team, team list with switching,
  adding / removing a team, sharing a team.
- **Changing the app language** (language picker dialog).
- **Reactive multi-language support** — a language change is applied on the fly,
  without restarting the app.

---

## Technical Features

- **Offline-first**: the entire game catalog (monsters, items, scenarios, perks,
  quests, locations) is loaded from JSON assets into Room on first launch (gated
  by `filler_version`), per pack and per locale.
- **Baseline Profile** for faster startup; **Macrobenchmark** tests for key UI
  flows.
- **Screenshot tests** (Roborazzi) for `:design-system`.

---

## Screenshots

<p align="center">
  <img src="screenshots/start.png" width="200"/>
  <img src="screenshots/characters.png" width="200"/>
  <img src="screenshots/character_details.png" width="200"/>
  <img src="screenshots/scenario.png" width="200"/>
</p>

---

## Tech Stack

| Category | Technology |
|----------|------------|
| Language | Kotlin 2.3 |
| UI | Jetpack Compose + Material3 |
| Architecture | Clean Architecture (Presentation → Domain → Data) |
| Database | Room |
| DI | Hilt |
| Async | Kotlin Coroutines + Flow |
| Serialization | Kotlin Serialization |
| Navigation | Compose Navigation (Type-safe) |
| Image Loading | Coil |

---

## Architecture

The app follows **Clean Architecture** principles with clear separation of concerns:

```
┌─────────────────────────────────────────────────────────┐
│                    Presentation                          │
│  Compose UI ← ViewModel ← StateFlow<UiState>            │
├─────────────────────────────────────────────────────────┤
│                      Domain                              │
│  UseCases ← Entity Models ← Error Types                 │
├─────────────────────────────────────────────────────────┤
│                       Data                               │
│  Repositories ← Mappers ← DataSources                   │
├─────────────────────────────────────────────────────────┤
│                    Persistence                           │
│  Room Database ← DAOs ← Entities ← TypeConverters       │
└─────────────────────────────────────────────────────────┘
```

### Key Design Decisions

- **Reactive data flow**: Room `Flow` → Repository → ViewModel `StateFlow` → Compose
- **Type-safe navigation**: `@Serializable` route definitions
- **Character classes as enum**: No database table, tracked via `TeamCharacterClassBd`
- **Scenario state persistence**: Active game state saved to `ScenarioGameStateBd`

---

## Project Structure

```
app/src/main/kotlin/com/rumpilstilstkin/gloomhavenhelper/
├── bd/                    # Room database layer
│   ├── dao/               # Data Access Objects
│   ├── entity/            # Database entities
│   ├── filler/json/       # Initial data from JSON
│   ├── migrations/        # Schema migrations
│   └── typeconverters/    # JSON serialization
├── data/                  # Repository layer
│   ├── datasource/        # SharedPreferences, etc.
│   └── mappers/           # Entity ↔ Domain mapping
├── di/                    # Hilt modules
├── domain/                # Business logic
│   ├── entity/            # Domain models
│   ├── error/             # Error types
│   └── usecase/           # Use cases by feature
├── navigation/            # Navigation setup
├── screens/               # UI screens & ViewModels
└── ui/                    # Shared components & theme
```

---

## Building

### Requirements

- Android Studio Hedgehog or newer
- JDK 11
- Android SDK 36

---

## Database Schema

The app uses Room with 18 entities. Key relationships:

- **Team** → Characters, Scenarios, Goods, Unlocked Classes, Achievements
- **Character** → Goods, Perks, Personal Quest
- **Monster** → Stats (per level), Ability Cards (shared deck)
- **Scenario Game State** → Active monsters, Round, Card deck state

See [ARCHITECTURE.md](ARCHITECTURE.md) for full ER diagram and entity definitions.

---

## Documentation

| Document | Description |
|----------|-------------|
| [ARCHITECTURE.md](ARCHITECTURE.md) | Full technical documentation: database schema, DAOs, repositories, use cases, UI structure |
| [CLAUDE.md](CLAUDE.md) | AI assistant context for code navigation |
| [BACKLOG.md](BACKLOG.md) | Planned improvements and features |

---

## License

This project is for personal use. Gloomhaven is a trademark of Cephalofair Games.

---

<p align="center">
  Made with Kotlin and Jetpack Compose
</p>
