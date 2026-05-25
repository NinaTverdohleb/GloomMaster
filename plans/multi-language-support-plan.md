# Plan: Multi-Language Support (English + Russian)

> Source PRD: `plans/multi-language-support.md`

## Architectural decisions

Durable decisions that apply across all phases:

- **Locale mechanism**: Runtime language switching uses AndroidX per-app locales
  (`AppCompatDelegate.setApplicationLocales`), which requires adding the
  `androidx.appcompat` dependency (the entry activity is a plain Compose
  `ComponentActivity` today). The active locale is exposed as an app-wide reactive value
  so static resources *and* content queries re-resolve on change.
- **Default & override**: On first launch, derive language from the system locale mapped
  to the supported set; if unsupported, default to English. An in-app picker offers
  *Follow system / English / Russian*, overrides the system locale, and persists the
  choice in the existing preferences data source.
- **Single source of truth**: The existing data remains canonical for the game *logic
  graph* and is keyed by stable identifiers — scenario number, item number, quest ID —
  and by canonical (Russian) names for achievement/monster references. Scenario unlock
  evaluation and saved team progress are **unchanged**, so there is **no migration of
  user data** and existing campaigns are never at risk.
- **Translation store (schema)**: A normalized localized-text store keyed by
  `(entityType, entityKey, fieldName, locale)` → `text`. It is seeded for **all**
  supported languages at database build time from per-locale dictionary assets. Adding it
  requires a DB version bump, a migration appended to the migrations array, and a
  regenerated schema export. Existing content/user tables are untouched.
- **Translation dictionaries**: Per-locale dictionaries shipped in assets, keyed by the
  stable IDs above. English is produced by translating the Russian content; the canonical
  Russian data is not modified.
- **Display resolution & fallback**: Content repositories resolve display text by the
  active locale. Missing text shows a visible key marker (debug) and falls back to the
  Russian source (release). Missing localized images fall back to the existing Russian
  image.
- **Image resolution**: Card and goods images resolve from a per-locale asset subfolder,
  falling back to the current Russian asset when a localized file is absent.
- **Supported-languages registry**: A single registry lists supported locales (en, ru)
  and drives the picker, resource configuration, content seeding, and image-folder
  lookup. Adding a language = one registry entry + a dictionary (+ optional image folder).

---

## Phase 1: Language-switch skeleton (static UI live + persisted)

**User stories**: 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 22, 23

### What to build

Add per-app locale capability and a discoverable in-app language control so the **entire
static UI** switches language live and the choice persists. On first launch, language is
derived from the system locale (mapped to the supported set; English if unsupported). The
control offers *Follow system / English / Russian*; choosing one updates the visible UI
immediately and survives restart. Numeric/locale-sensitive formatting follows the active
language. No game-content text translates yet — that arrives in later phases.

### Acceptance criteria

- [ ] App launches in the system language when supported, otherwise in English.
- [ ] A discoverable in-app control offers *Follow system / English / Russian*.
- [ ] Choosing a language switches all static UI (tabs, buttons, dialogs, class names, difficulty labels) live, without a restart.
- [ ] The chosen language overrides the system locale and persists across app restarts.
- [ ] Selecting *Follow system* reverts to tracking the device language.
- [ ] Signed/numeric values format according to the active language, not only the system locale.
- [ ] Game-content text (scenario/item/quest/etc.) intentionally remains Russian at this stage.

---

## Phase 2: Content translation foundation + scenarios

**User stories**: 13, 25, 26, 32, 33, 35, 36

### What to build

Introduce the translation store and seed it for **all** supported languages from
per-locale dictionaries during database population, then resolve scenario **name** and
**location** for display by the active locale. The canonical scenario graph and all saved
progress are unchanged; switching language re-renders scenario text live (no re-seed).
Missing translations render a visible key marker. This phase establishes the entire
content-translation machinery via the thinnest demoable content slice.

### Acceptance criteria

- [ ] DB version incremented with a migration that adds the translation store; schema export regenerated; existing teams/characters/scenario progress preserved on upgrade.
- [ ] The translation store is seeded for every supported language at fill time from per-locale dictionaries.
- [ ] Scenario names and locations display in the active language across scenario lists/screens.
- [ ] Switching language updates scenario names/locations live — no restart, no re-seed.
- [ ] A scenario's locked/unlocked/completed state is identical before and after switching language.
- [ ] A missing scenario translation shows a visible key marker rather than a blank.
- [ ] Dictionaries include both MAIN and FORGOTTEN_CIRCLES scenario entries.

---

## Phase 3: Item / goods names

**User stories**: 15 (story 16 is satisfied by existing type icons — no text work)

### What to build

Add goods **name** dictionaries (MAIN + FC) and resolve item names by the active locale
in the shop and in character inventory. Item *type* continues to render as a
language-neutral icon, so no type-text translation is needed.

### Acceptance criteria

- [ ] Item names display in the active language in the shop and in character inventory.
- [ ] Switching language updates item names live.
- [ ] Owned items and their counts are unchanged across switching.
- [ ] Item-type indicators remain icon-based (no regression).
- [ ] Goods dictionaries include both MAIN and FORGOTTEN_CIRCLES.

---

## Phase 4: Personal quests

**User stories**: 19

### What to build

Add quest dictionaries (title, narrative description, special text, and each task's text)
and resolve them by the active locale in quest detail and quest search/selection.

### Acceptance criteria

- [ ] Quest title, description, special text, and task texts display in the active language.
- [ ] Switching language updates quest text live.
- [ ] A character's assigned quest and task progress are unchanged across switching.
- [ ] Quest dictionaries include both MAIN and FORGOTTEN_CIRCLES.

---

## Phase 5: Perks

**User stories**: 17, 18

### What to build

Add perk-text dictionaries and resolve them by the active locale, preserving the embedded
symbol/icon placeholders so game notation renders identically in every language.

### Acceptance criteria

- [ ] Perk descriptions display in the active language in the perks tab and the add-perk dialog.
- [ ] Symbol/icon placeholders render identically regardless of language.
- [ ] Switching language updates perk text live.
- [ ] Selected/owned perks are unchanged across switching.
- [ ] Perk dictionaries include both MAIN and FORGOTTEN_CIRCLES.

---

## Phase 6: Achievements + scenario-requirements display

**User stories**: 14, 20, 27

### What to build

Add achievement display-name dictionaries; localize achievement names wherever shown, and
make the human-readable scenario-requirements text resolve each achievement reference
(canonical key) to its localized name. Unlock **evaluation** continues to operate on
canonical keys, so behavior is language-independent.

### Acceptance criteria

- [ ] Global and party achievement names display in the active language.
- [ ] Scenario requirement text displays in the active language, including achievement references.
- [ ] Scenario lock/unlock results are unaffected by the active language.
- [ ] A team's earned achievements continue to gate scenarios correctly after switching language.
- [ ] Achievement dictionaries include both MAIN and FORGOTTEN_CIRCLES.

---

## Phase 7: Monsters + stat ability text

**User stories**: 21

### What to build

Add monster **name** dictionaries (MAIN + FC) and resolve monster names everywhere they
appear — scenario monster lists, the scenario constructor, and active play — plus any
ability/special text embedded in stats data.

### Acceptance criteria

- [ ] Monster names display in the active language in scenario lists, the constructor, and active play.
- [ ] Any embedded ability/special text from stats displays in the active language.
- [ ] Switching language updates monster names live.
- [ ] Active scenario state (spawned monsters, rounds, cards) is unchanged across switching.
- [ ] Monster dictionaries include both MAIN and FORGOTTEN_CIRCLES.

---

## Phase 8: Localized images

**User stories**: 29, 30, 31

### What to build

Resolve monster ability-card and goods images from a per-locale asset subfolder, falling
back to the existing Russian asset when a localized file is missing. Depends only on the
active-locale source from Phase 1, so it can be scheduled independently of the content
phases.

### Acceptance criteria

- [ ] When a localized image exists for the active language, it is shown.
- [ ] When a localized image is missing, the Russian image is shown — never a blank or broken image.
- [ ] Switching language updates displayed images live where localized assets exist.
- [ ] Applies to both monster ability cards and goods images.

---

## Phase 9: Forgotten Circles coverage, fallback hardening & QA

**User stories**: 24, 28, 38

### What to build

Close out completeness and production readiness: verify Forgotten Circles content is fully
translated across every domain; harden the missing-text fallback (visible key marker in
debug, Russian source in release); apply plural-aware formatting where counts are shown;
add a maintainer aid that lists untranslated keys per language; run a full-screen QA pass
confirming no hardcoded user-facing strings remain and that user-typed text is never
altered.

### Acceptance criteria

- [ ] Forgotten Circles content (scenarios, goods, quests, perks, achievements, monsters) is translated and verified across all phases.
- [ ] Release builds fall back to the Russian source for missing text; debug builds show the key marker.
- [ ] Count-dependent strings use plural-aware formatting in both languages.
- [ ] A maintainer report lists untranslated keys per language.
- [ ] A full-screen pass finds no hardcoded user-facing strings; user-typed team/character names are never translated.
- [ ] Existing campaigns remain fully intact end-to-end in both languages.
