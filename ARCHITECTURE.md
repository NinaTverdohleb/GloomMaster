# Gloomhaven Helper - Technical Architecture Documentation

## Database Layer (Room/SQLite)

### Database Configuration

- **Database Name:** `glHelperDatabase`
- **Version:** 1
- **Identity Hash:** `0e902157bbaf4c09fdd6af1e178a8b43`

### Entity-Relationship Diagram

```mermaid
erDiagram
    TeamBd ||--o{ CharacterBd : "has"
    TeamBd ||--o{ TeamScenarioBd : "has"
    ScenarioBd ||--o{ TeamScenarioBd : "referenced by"
    CharacterClassBd ||--o{ CharacterBd : "defines"
    CharacterClassBd ||--o{ PerkBd : "has"
    CharacterBd ||--o{ CharacterGoodBd : "owns"
    CharacterBd ||--o{ CharacterPerkBd : "has"
    CharacterBd ||--o{ CharacterPersonalQuestBd : "assigned"
    GoodBd ||--o{ CharacterGoodBd : "referenced by"
    PerkBd ||--o{ CharacterPerkBd : "referenced by"
    PersonalQuestBd ||--o{ CharacterPersonalQuestBd : "referenced by"

    TeamBd {
        int teamId PK "AUTOINCREMENT"
        string name
        string teamAchievement
        string globalAchievement
        int reputation
        int prosperity
    }

    CharacterBd {
        int characterId PK "AUTOINCREMENT"
        string name
        int level
        int experience
        int goldCount
        string characterType FK
        int teamId FK "NULLABLE"
        boolean isAlive
        string notes
        int checkMarkCount
    }

    CharacterClassBd {
        string characterType PK
        string name
    }

    GameLevelInfoBd {
        int level PK
        int monsterLevel
        int goldCount
        int trapDamage
        int experience
    }

    ScenarioBd {
        int scenarioNumber PK
        string name
        string newScenarios
        string teamAchievement
        string globalAchievement
        string requirements
    }

    TeamScenarioBd {
        int id PK "AUTOINCREMENT"
        int teamId FK
        int scenarioNumber FK
        string scenarioName
        string scenarioRequirements
        boolean completed
    }

    GoodBd {
        int goodId PK "AUTOINCREMENT"
        int number
        string name
        string type
        int cost
        boolean is_drawing
    }

    CharacterGoodBd {
        int id PK "AUTOINCREMENT"
        int characterId FK
        int goodId FK
    }

    PerkBd {
        int perkId PK "AUTOINCREMENT"
        string text
        string characterType FK
    }

    CharacterPerkBd {
        int id PK "AUTOINCREMENT"
        int characterId FK
        int perkId FK
    }

    PersonalQuestBd {
        string questId PK
        string title
        string description
        string specialText
        string characterType "NULLABLE"
        string tasks "JSON"
    }

    CharacterPersonalQuestBd {
        int id PK "AUTOINCREMENT"
        int characterId FK
        string questId FK
        string tasks "JSON"
    }
```

### Entity Definitions

#### TeamBd
| Column | Type | Constraints |
|--------|------|-------------|
| `teamId` | INTEGER | PRIMARY KEY, AUTOINCREMENT, NOT NULL |
| `name` | TEXT | NOT NULL |
| `teamAchievement` | TEXT | NOT NULL |
| `globalAchievement` | TEXT | NOT NULL |
| `reputation` | INTEGER | NOT NULL |
| `prosperity` | INTEGER | NOT NULL |

#### CharacterBd
| Column | Type | Constraints |
|--------|------|-------------|
| `characterId` | INTEGER | PRIMARY KEY, AUTOINCREMENT, NOT NULL |
| `name` | TEXT | NOT NULL |
| `level` | INTEGER | NOT NULL |
| `experience` | INTEGER | NOT NULL |
| `goldCount` | INTEGER | NOT NULL |
| `characterType` | TEXT | NOT NULL |
| `teamId` | INTEGER | NULLABLE |
| `isAlive` | INTEGER | NOT NULL |
| `notes` | TEXT | NOT NULL |
| `checkMarkCount` | INTEGER | NOT NULL |

#### CharacterClassBd
| Column | Type | Constraints |
|--------|------|-------------|
| `characterType` | TEXT | PRIMARY KEY, NOT NULL |
| `name` | TEXT | NOT NULL |

#### GameLevelInfoBd
| Column | Type | Constraints |
|--------|------|-------------|
| `level` | INTEGER | PRIMARY KEY, NOT NULL |
| `monsterLevel` | INTEGER | NOT NULL |
| `goldCount` | INTEGER | NOT NULL |
| `trapDamage` | INTEGER | NOT NULL |
| `experience` | INTEGER | NOT NULL |

#### ScenarioBd
| Column | Type | Constraints |
|--------|------|-------------|
| `scenarioNumber` | INTEGER | PRIMARY KEY, NOT NULL |
| `name` | TEXT | NOT NULL |
| `newScenarios` | TEXT | NOT NULL |
| `teamAchievement` | TEXT | NOT NULL |
| `globalAchievement` | TEXT | NOT NULL |
| `requirements` | TEXT | NOT NULL |

#### TeamScenarioBd
| Column | Type | Constraints |
|--------|------|-------------|
| `id` | INTEGER | PRIMARY KEY, AUTOINCREMENT, NOT NULL |
| `teamId` | INTEGER | FK → TeamBd(teamId), ON DELETE CASCADE |
| `scenarioNumber` | INTEGER | FK → ScenarioBd(scenarioNumber), ON DELETE CASCADE |
| `scenarioName` | TEXT | NOT NULL |
| `scenarioRequirements` | TEXT | NOT NULL |
| `completed` | INTEGER | NOT NULL |

**Indices:**
- `index_TeamScenarioBd_teamId` on `teamId`
- `index_TeamScenarioBd_scenarioNumber` on `scenarioNumber`

#### GoodBd
| Column | Type | Constraints |
|--------|------|-------------|
| `goodId` | INTEGER | PRIMARY KEY, AUTOINCREMENT, NOT NULL |
| `number` | INTEGER | NOT NULL |
| `name` | TEXT | NOT NULL |
| `type` | TEXT | NOT NULL |
| `cost` | INTEGER | NOT NULL |
| `is_drawing` | INTEGER | NOT NULL |

#### CharacterGoodBd
| Column | Type | Constraints |
|--------|------|-------------|
| `id` | INTEGER | PRIMARY KEY, AUTOINCREMENT, NOT NULL |
| `characterId` | INTEGER | FK → CharacterBd(characterId), ON DELETE CASCADE |
| `goodId` | INTEGER | FK → GoodBd(goodId), ON DELETE CASCADE |

**Indices:**
- `index_CharacterGoodBd_characterId` on `characterId`
- `index_CharacterGoodBd_goodId` on `goodId`

#### PerkBd
| Column | Type | Constraints |
|--------|------|-------------|
| `perkId` | INTEGER | PRIMARY KEY, AUTOINCREMENT, NOT NULL |
| `text` | TEXT | NOT NULL |
| `characterType` | TEXT | FK → CharacterClassBd(characterType), ON DELETE CASCADE |

**Indices:**
- `index_PerkBd_characterType` on `characterType`

#### CharacterPerkBd
| Column | Type | Constraints |
|--------|------|-------------|
| `id` | INTEGER | PRIMARY KEY, AUTOINCREMENT, NOT NULL |
| `characterId` | INTEGER | FK → CharacterBd(characterId), ON DELETE CASCADE |
| `perkId` | INTEGER | FK → PerkBd(perkId), ON DELETE CASCADE |

**Indices:**
- `index_CharacterPerkBd_characterId` on `characterId`
- `index_CharacterPerkBd_perkId` on `perkId`

#### PersonalQuestBd
| Column | Type | Constraints |
|--------|------|-------------|
| `questId` | TEXT | PRIMARY KEY, NOT NULL |
| `title` | TEXT | NOT NULL |
| `description` | TEXT | NOT NULL |
| `specialText` | TEXT | NOT NULL |
| `characterType` | TEXT | NULLABLE |
| `tasks` | TEXT | NOT NULL, JSON serialized |

#### CharacterPersonalQuestBd
| Column | Type | Constraints |
|--------|------|-------------|
| `id` | INTEGER | PRIMARY KEY, AUTOINCREMENT, NOT NULL |
| `characterId` | INTEGER | FK → CharacterBd(characterId), ON DELETE CASCADE |
| `questId` | TEXT | FK → PersonalQuestBd(questId), ON DELETE CASCADE |
| `tasks` | TEXT | NOT NULL, JSON serialized |

**Indices:**
- `index_CharacterPersonalQuestBd_characterId` on `characterId`
- `index_CharacterPersonalQuestBd_questId` on `questId`

### TypeConverters

#### ListCharacterTaskItemTypeConverter
```kotlin
class ListCharacterTaskItemTypeConverter {
    @TypeConverter
    fun fromList(list: List<CharacterTaskItem>): String = Json.encodeToString(list)

    @TypeConverter
    fun toList(value: String): List<CharacterTaskItem> = Json.decodeFromString(value)
}
```
Serializes `List<CharacterTaskItem>` to JSON string for storage in `PersonalQuestBd.tasks` and `CharacterPersonalQuestBd.tasks`.

### DAO Interfaces

#### TeamDao
| Method | Annotation | Query |
|--------|-----------|-------|
| `getAll()` | `@Query` | `SELECT * FROM TeamBd` |
| `getAllFlow()` | `@Query` | `SELECT * FROM TeamBd` → `Flow<List<TeamBd>>` |
| `findById(id)` | `@Query` | `SELECT * FROM TeamBd WHERE teamId LIKE :id LIMIT 1` |
| `insert(team)` | `@Upsert` | — |
| `delete(team)` | `@Delete` | — |
| `update(team)` | `@Update` | — |
| `getTeamWithScenariosFlow(id)` | `@Transaction @Query` | `SELECT * FROM TeamBd WHERE teamId LIKE :id LIMIT 1` → `Flow<TeamWithScenariosBd>` |
| `getTeamWithScenarios(id)` | `@Transaction @Query` | `SELECT * FROM TeamBd WHERE teamId LIKE :id LIMIT 1` |
| `updateReputation(id, reputation)` | `@Transaction @Query` | `UPDATE TeamBd SET reputation=:reputation WHERE teamId = :id` |
| `updateProsperity(id, prosperity)` | `@Transaction @Query` | `UPDATE TeamBd SET prosperity=:prosperity WHERE teamId = :id` |

#### CharacterDao
| Method | Annotation | Query |
|--------|-----------|-------|
| `getAllCharacters()` | `@Query` | `SELECT * FROM CharacterBd` |
| `findByTeamId(teamId)` | `@Query` | `SELECT * FROM CharacterBd WHERE teamId LIKE :teamId` |
| `getCharacterById(characterId)` | `@Query` | `SELECT * FROM CharacterBd WHERE characterId LIKE :characterId LIMIT 1` |
| `findByTeamIdFlow(teamId)` | `@Query` | `SELECT * FROM CharacterBd WHERE teamId LIKE :teamId` → `Flow` |
| `getCharacterByIdFlow(characterId)` | `@Query` | `SELECT * FROM CharacterBd WHERE characterId LIKE :characterId LIMIT 1` → `Flow` |
| `insert(character)` | `@Insert` | — |
| `delete(character)` | `@Delete` | — |
| `update(character)` | `@Update` | — |
| `deleteById(characterId)` | `@Transaction @Query` | `DELETE FROM CharacterBd WHERE characterId = :characterId` |

#### CharacterGoodsDao
| Method | Annotation | Query |
|--------|-----------|-------|
| `getCharacterGoodsFlow(characterId)` | `@Transaction @Query` | `SELECT * FROM CharacterGoodBd WHERE characterId LIKE :characterId` → `Flow` |
| `getCharacterGoods(characterId)` | `@Transaction @Query` | `SELECT * FROM CharacterGoodBd WHERE characterId LIKE :characterId` |
| `getCharacterGoodById(characterGoodId)` | `@Transaction @Query` | `SELECT * FROM CharacterGoodBd WHERE id LIKE :characterGoodId` |
| `insert(characterGood)` | `@Insert` | — |
| `delete(characterGood)` | `@Delete` | — |
| `deleteById(characterGoodId)` | `@Transaction @Query` | `DELETE FROM CharacterGoodBd WHERE id LIKE :characterGoodId` |

#### CharacterPerksDao
| Method | Annotation | Query |
|--------|-----------|-------|
| `getCharacterPerksFlow(characterId)` | `@Transaction @Query` | `SELECT * FROM CharacterPerkBd WHERE characterId LIKE :characterId` → `Flow` |
| `getCharacterPerks(characterId)` | `@Transaction @Query` | `SELECT * FROM CharacterPerkBd WHERE characterId LIKE :characterId` |
| `insert(characterPerk)` | `@Insert` | — |
| `delete(characterPerk)` | `@Delete` | — |
| `deleteById(characterPerkId)` | `@Transaction @Query` | `DELETE FROM CharacterPerkBd WHERE id LIKE :characterPerkId` |

#### CharacterPersonalQuestDao
| Method | Annotation | Query |
|--------|-----------|-------|
| `getCharacterPersonalQuestFlow(characterId)` | `@Transaction @Query` | `SELECT * FROM CharacterPersonalQuestBd WHERE characterId LIKE :characterId LIMIT 1` → `Flow` |
| `insert(characterPerk)` | `@Insert` | — |
| `deleteByCharacterId(characterId)` | `@Query` | `DELETE FROM CharacterPersonalQuestBd WHERE characterId LIKE :characterId` |
| `getCharacterQuestById(characterId)` | `@Transaction @Query` | `SELECT * FROM CharacterPersonalQuestBd WHERE characterId LIKE :characterId LIMIT 1` |

#### ScenarioDao
| Method | Annotation | Query |
|--------|-----------|-------|
| `getAll()` | `@Query` | `SELECT * FROM ScenarioBd` |
| `getScenario(scenarioNumber)` | `@Query` | `SELECT * FROM ScenarioBd WHERE scenarioNumber LIKE :scenarioNumber LIMIT 1` |
| `insertAll(vararg scenarios)` | `@Insert` | — |

#### TeamScenarioDao
| Method | Annotation | Query |
|--------|-----------|-------|
| `insertAll(vararg scenarios)` | `@Insert` | — |
| `update(team)` | `@Update` | — |
| `getTeamScenario(teamId, scenarioNumber)` | `@Query` | `SELECT * FROM TeamScenarioBd WHERE teamId = :teamId AND scenarioNumber = :scenarioNumber LIMIT 1` |
| `getTeamScenarios(teamId)` | `@Query` | `SELECT * FROM TeamScenarioBd WHERE teamId = :teamId` |

#### GoodsDao
| Method | Annotation | Query |
|--------|-----------|-------|
| `getAll()` | `@Query` | `SELECT * FROM GoodBd` |
| `insertAll(vararg users)` | `@Insert` | — |
| `getGoodsByIds(goodId)` | `@Transaction @Query` | `SELECT * FROM GoodBd WHERE goodId IN (:goodId)` |

#### PerksDao
| Method | Annotation | Query |
|--------|-----------|-------|
| `insertAll(vararg users)` | `@Insert` | — |
| `getPerksByCharacterClass(characterType)` | `@Transaction @Query` | `SELECT * FROM PerkBd WHERE characterType LIKE :characterType` |

#### PersonalQuestDao
| Method | Annotation | Query |
|--------|-----------|-------|
| `getQuestsFlow()` | `@Query` | `SELECT * FROM PersonalQuestBd` → `Flow` |
| `getQuest(questId)` | `@Query` | `SELECT * FROM PersonalQuestBd WHERE questId LIKE :questId LIMIT 1` |
| `insertAll(vararg quests)` | `@Insert` | — |

#### GameLevelInfoDao
| Method | Annotation | Query |
|--------|-----------|-------|
| `getAll()` | `@Query` | `SELECT * FROM GameLevelInfoBd` |
| `insertAll(vararg users)` | `@Insert` | — |

#### CharacterClassDao
| Method | Annotation | Query |
|--------|-----------|-------|
| `getAll()` | `@Query` | `SELECT * FROM CharacterClassBd` |
| `findByType(characterType)` | `@Query` | `SELECT * FROM CharacterClassBd WHERE characterType LIKE :characterType LIMIT 1` |
| `insertAll(vararg users)` | `@Insert` | — |

---

## Business Logic & Architecture

### Dependency Injection Graph

```mermaid
flowchart TB
    subgraph Hilt["Hilt DI Container"]
        subgraph SingletonComponent["@Singleton Scope"]
            SP[SharedPreferences]
            DB[(GlHelperDatabase)]

            subgraph DAOs["DAO Layer"]
                TeamDao
                CharacterDao
                CharacterClassDao
                GameLevelInfoDao
                ScenarioDao
                TeamScenarioDao
                GoodsDao
                CharacterGoodsDao
                PerksDao
                CharacterPerksDao
                PersonalQuestDao
                CharacterPersonalQuestDao
            end

            subgraph Dispatchers["Coroutine Dispatchers"]
                DefaultDispatcher
                IoDispatcher
                MainDispatcher
                ApplicationScope
            end
        end
    end

    DB --> TeamDao
    DB --> CharacterDao
    DB --> CharacterClassDao
    DB --> GameLevelInfoDao
    DB --> ScenarioDao
    DB --> TeamScenarioDao
    DB --> GoodsDao
    DB --> CharacterGoodsDao
    DB --> PerksDao
    DB --> CharacterPerksDao
    DB --> PersonalQuestDao
    DB --> CharacterPersonalQuestDao

    SP --> CurrentTeamDatasource

    subgraph Repositories["Repository Layer"]
        TeamRepository
        CharacterRepository
        ClassRepository
        ScenarioRepository
        GoodsRepository
        PerksRepository
        QuestsRepository
        LevelInfoRepository
        CurrentTeamDatasource
    end

    TeamDao --> TeamRepository
    CharacterDao --> TeamRepository
    CharacterDao --> CharacterRepository
    CharacterClassDao --> CharacterRepository
    CharacterClassDao --> ClassRepository
    CharacterGoodsDao --> CharacterRepository
    CharacterPerksDao --> CharacterRepository
    CharacterPersonalQuestDao --> CharacterRepository
    ScenarioDao --> ScenarioRepository
    TeamScenarioDao --> ScenarioRepository
    GoodsDao --> GoodsRepository
    PerksDao --> PerksRepository
    PersonalQuestDao --> QuestsRepository
    CharacterPersonalQuestDao --> QuestsRepository
    GameLevelInfoDao --> LevelInfoRepository
    CurrentTeamDatasource --> TeamRepository
    CharacterRepository --> TeamRepository
    ApplicationScope --> TeamRepository
```

### Repository Layer

| Repository | Scope | Dependencies | Reactive Streams |
|------------|-------|--------------|------------------|
| `TeamRepository` | `@Singleton` | `TeamDao`, `CharacterDao`, `CharacterRepository`, `CurrentTeamDatasource`, `@ApplicationScope CoroutineScope` | `currentTeamId: Flow<Int>`, `getTeams(): Flow<List>`, `getTeamWithScenarioFlow(): Flow` |
| `CharacterRepository` | — | `CharacterDao`, `CharacterClassDao`, `TeamDao`, `CharacterGoodsDao`, `CharacterPerksDao`, `CharacterPersonalQuestDao` | `getCharacterPerksFlow()`, `getCharacterGoodsFlow()`, `getCharacterByTeamId()`, `getCharacterByIdFlow()`, `getCharacterPersonalQuestFlow()`, `getAllCharacters()` |
| `ClassRepository` | — | `CharacterClassDao` | — |
| `ScenarioRepository` | — | `ScenarioDao`, `TeamScenarioDao` | — |
| `GoodsRepository` | — | `GoodsDao` | — |
| `PerksRepository` | — | `PerksDao` | — |
| `QuestsRepository` | — | `PersonalQuestDao`, `CharacterPersonalQuestDao` | `getQuestsFlow(): Flow<List<PersonalQuest>>` |
| `LevelInfoRepository` | `@Singleton` | `GameLevelInfoDao` | — (uses caching) |
| `CurrentTeamDatasource` | `@Singleton` | `SharedPreferences` | — |

### Use Cases

#### Team Domain
| UseCase | Dependencies | Operation |
|---------|--------------|-----------|
| `GetCurrentTeamUseCase` | `TeamRepository` | Retrieves current team info flow |
| `GetTeamsUseCase` | `TeamRepository` | Retrieves all teams |
| `SaveTeamUseCase` | `TeamRepository`, `ScenarioRepository` | Saves team and initial scenario |

#### Character Domain
| UseCase | Dependencies | Operation |
|---------|--------------|-----------|
| `GetCharacterDetailsInfoUseCase` | `CharacterRepository` | Character details flow |
| `GetCharacterGeneralInfoUseCase` | `CharacterRepository` | Character info |
| `GetCharacterGeneralInfoFlowUseCase` | `CharacterRepository` | Character info flow |
| `GetCharacterPerksUseCase` | `CharacterRepository` | Character perks |
| `LevelUpUseCase` | `CharacterRepository` | Level increment |
| `ExperienceChangeUseCase` | `CharacterRepository` | Experience update |
| `UpdateGoldUseCase` | `CharacterRepository` | Gold update |
| `UpdateNotesUseCase` | `CharacterRepository` | Notes update |
| `MarksCheckedChangeUseCase` | `CharacterRepository` | Check marks update |
| `DonateUseCase` | `CharacterRepository` | Gold donation |
| `SetTeamUseCase` | `CharacterRepository` | Team assignment |

#### Character Goods Domain
| UseCase | Dependencies | Operation |
|---------|--------------|-----------|
| `GetCharacterGoodsUseCase` | `CharacterRepository`, `GoodsRepository` | Character goods flow |
| `GetAvaliableCharacterGoodsUseCase` | `CharacterRepository`, `GoodsRepository` | Available goods list |
| `AddGoodForCharacterUseCase` | `CharacterRepository` | Add good without payment |
| `BuyGoodForCharacterUseCase` | `CharacterRepository` | Buy good with gold deduction |
| `SellGoodCharacterUseCase` | `CharacterRepository`, `GoodsRepository` | Sell good with gold refund |
| `DeleteCharacterGoodsUseCase` | `CharacterRepository` | Remove good |

#### Character Perks Domain
| UseCase | Dependencies | Operation |
|---------|--------------|-----------|
| `GetCharacterPerksInfoUseCase` | `CharacterRepository`, `PerksRepository` | Perks with selection state |
| `AddPerksForCharacterUseCase` | `CharacterRepository` | Add perk |
| `DeleteCharacterPerkUseCase` | `CharacterRepository` | Remove perk |

#### Character Quests Domain
| UseCase | Dependencies | Operation |
|---------|--------------|-----------|
| `SetQuestForCharacterUseCase` | `QuestsRepository` | Assign quest |
| `QuestTaskUpdateUseCase` | `QuestsRepository`, `CharacterRepository` | Update task progress |

#### Goods Domain
| UseCase | Dependencies | Operation |
|---------|--------------|-----------|
| `GetAllGoodsUseCase` | `GoodsRepository` | All goods list |

#### Quests Domain
| UseCase | Dependencies | Operation |
|---------|--------------|-----------|
| `GetQuestsFlowUseCase` | `QuestsRepository` | All quests flow |

#### Scenario Domain
| UseCase | Dependencies | Operation |
|---------|--------------|-----------|
| `CompleteScenarioUseCase` | `ScenarioRepository` | Mark scenario completed |

### Data Flow Diagram

```mermaid
sequenceDiagram
    participant UI as Compose UI
    participant VM as ViewModel
    participant UC as UseCase
    participant Repo as Repository
    participant DAO as DAO
    participant DB as Room Database

    Note over UI,DB: Read Flow (Reactive)
    UI->>VM: Observe uiState
    VM->>UC: execute()
    UC->>Repo: getDataFlow()
    Repo->>DAO: queryFlow()
    DAO->>DB: SELECT query
    DB-->>DAO: Flow<Entity>
    DAO-->>Repo: Flow<Entity>
    Repo-->>UC: Flow<DomainModel>
    UC-->>VM: Flow<DomainModel>
    VM-->>UI: StateFlow<UiState>

    Note over UI,DB: Write Flow (One-shot)
    UI->>VM: onAction(UserAction)
    VM->>UC: execute(params)
    UC->>Repo: updateData(params)
    Repo->>DAO: insert/update/delete
    DAO->>DB: SQL mutation
    DB-->>DAO: Result
    DAO-->>Repo: Unit
    Note over DB,UI: Room invalidates Flow automatically
    DB-->>DAO: Flow emission
    DAO-->>Repo: Updated Flow
    Repo-->>VM: Updated Flow
    VM-->>UI: New UiState
```

### State Management Flow

```mermaid
flowchart LR
    subgraph Presentation["Presentation Layer"]
        Screen["Compose Screen"]
        VM["ViewModel"]
        State["MutableStateFlow<UiState>"]
    end

    subgraph Domain["Domain Layer"]
        UC["UseCase"]
    end

    subgraph Data["Data Layer"]
        Repo["Repository"]
        DS["DataSource"]
    end

    subgraph Persistence["Persistence Layer"]
        DAO["DAO"]
        DB[(Room DB)]
        SP[(SharedPrefs)]
    end

    Screen -->|"collectAsStateWithLifecycle()"| State
    Screen -->|"onAction()"| VM
    VM -->|"viewModelScope.launch"| UC
    UC --> Repo
    Repo --> DAO
    Repo --> DS
    DAO --> DB
    DS --> SP

    DB -.->|"Flow invalidation"| DAO
    DAO -.->|"Flow<Entity>"| Repo
    Repo -.->|"Flow<Domain>"| UC
    UC -.->|"Flow<Domain>"| VM
    VM -.->|"emit()"| State
```

### ViewModel Dependencies

| ViewModel | Injected Dependencies |
|-----------|----------------------|
| `MainActivityViewModel` | `DatabaseFiller` |
| `MainViewModel` | `GetCurrentTeamUseCase`, `LevelInfoRepository`, `ClassRepository`, `TeamRepository`, `CharacterRepository`, `CompleteScenarioUseCase` |
| `TeamTabViewModel` | `GetCurrentTeamUseCase` |
| `TeamCreateViewModel` | `ClassRepository`, `SaveTeamUseCase` |
| `CharactersTabViewModel` | `CharacterRepository` |
| `CharacterDetailsViewModel` | `GetCharacterGeneralInfoFlowUseCase`, `SetTeamUseCase` |
| `CharacterGeneralTabViewModel` | `GetCharacterDetailsInfoUseCase`, `LevelUpUseCase`, `DonateUseCase`, `UpdateGoldUseCase`, `ExperienceChangeUseCase`, `MarksCheckedChangeUseCase`, `UpdateNotesUseCase`, `QuestTaskUpdateUseCase` |
| `CharacterGoodsTabViewModel` | `GetCharacterGoodsUseCase`, `DeleteCharacterGoodsUseCase`, `SellGoodCharacterUseCase` |
| `AddGoodsScreenViewModel` | `AddGoodForCharacterUseCase`, `BuyGoodForCharacterUseCase`, `GetAvaliableCharacterGoodsUseCase`, `GetCharacterGeneralInfoUseCase` |
| `CharacterPerksTabViewModel` | `GetCharacterPerksInfoUseCase`, `DeleteCharacterPerkUseCase`, `AddPerksForCharacterUseCase` |
| `SearchQuestViewModel` | `GetQuestsFlowUseCase`, `SetQuestForCharacterUseCase` |
| `AddScenarioViewModel` | `ScenarioRepository`, `TeamRepository` |
| `AddCharactersDialogViewModel` | `ClassRepository` |
| `TeamListDialogViewModel` | `GetTeamsUseCase` |

---

## UI Layer (Minimal)

### Main Screens

| Screen | Purpose |
|--------|---------|
| `MainActivity` | Application entry point; handles database initialization via `MainActivityViewModel`. |
| `MainScreen` | Primary navigation hub displaying current team info, characters, and scenarios. |
| `StartScreen` | Tab-based screen with Team and Characters tabs for overview. |
| `TeamTab` | Displays current team details including reputation, prosperity, and achievements. |
| `CharactersTab` | Lists all characters with navigation to character details. |
| `TeamCreateScreen` | Form for creating a new team with initial characters. |
| `TeamDetailsScreen` | Team editing interface. |
| `CharacterDetailsScreen` | Tab-based character view with General, Items, and Perks tabs. |
| `CharacterGeneralTab` | Character stats, experience, gold, notes, and personal quest management. |
| `CharacterGoodsTab` | Character inventory management with sell functionality. |
| `CharacterPerksTab` | Perk selection and management for character. |
| `AddGoodsScreen` | Item shop interface for purchasing/adding goods to character. |
| `SearchQuestScreen` | Personal quest selection interface. |
| `ScenarioScreen` | Scenario/monster management with pager-based card navigation. |

### Dialogs

| Dialog | Purpose |
|--------|---------|
| `AddCharacterDialog` | Character class selection for adding new character. |
| `AddScenarioDialog` | Scenario selection for team progression. |
| `TeamListDialog` | Team selection dropdown. |
| `GoodDetailsDialog` | Item detail view. |
| `QuestDetailsDialog` | Quest detail view. |
