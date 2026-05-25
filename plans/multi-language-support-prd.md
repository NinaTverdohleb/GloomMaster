# PRD: Multi-Language Support (English + Russian)

## Problem Statement

Gloom Master is currently a Russian-only app. All of its on-screen text — both the
static interface labels and the entire game dataset (scenarios, items, perks, personal
quests, achievements, monsters, locations) — is presented in Russian. A large share of
the Gloomhaven player base reads English, and the game itself is English-origin, but
today an English-speaking user cannot use the app at all.

The user wants the app to be genuinely multi-language (not hard-wired to a single
locale), with English as the first additional language. Two realities make this more
than a simple string-translation task:

1. **The static UI is already localized**, but the bulk of the user-visible text is
   *game data* stored in the local database, seeded once from Russian JSON files at
   first launch. None of that content is translated.
2. **Game logic and saved progress are keyed by Russian display strings.** Scenario
   unlock rules match achievements and monsters by their literal Russian name, and a
   team's earned achievements are persisted by name. Naively translating those strings
   would silently break scenario unlocking and corrupt every existing save.

## Solution

Introduce a localization layer that lets the app display all content — UI and game
data — in the user's chosen language, while keeping the underlying game logic and saved
progress completely intact.

From the user's perspective:

- On first launch the app appears in their phone's language if it's supported
  (English or Russian); otherwise it defaults to English.
- A language setting inside the app lets them switch between English and Russian at any
  time, overriding the system language. The change takes effect immediately across the
  whole app.
- Every screen — party, characters, shop, scenarios, active play, dialogs — shows
  scenario names, item names, perk text, quest text, achievement names, monster names,
  and locations in the selected language.
- Switching language never loses progress: teams, unlocked scenarios, earned
  achievements, characters, and items are all preserved exactly.
- Card and item artwork appears in the selected language where a localized image is
  available, falling back to the existing Russian artwork when it isn't.

Under the hood, the existing Russian-keyed data remains the single source of truth for
all game *logic*, and a new translation layer supplies *display* text per language. The
app ships with English and Russian and is structured so additional languages can be
added later without reworking the architecture.

## User Stories

### Language selection & persistence

1. As a new user on an English phone, I want the app to open in English automatically, so that I can use it without changing any settings.
2. As a new user on a Russian phone, I want the app to open in Russian automatically, so that the experience matches my device.
3. As a new user on a phone set to an unsupported language (e.g. French), I want the app to default to English, so that I always see a language I'm likely to understand.
4. As a user, I want a language option inside the app, so that I can choose English or Russian regardless of my phone's system language.
5. As a user, I want my chosen language to override the system language, so that I'm not forced to change my whole device to use the app in my preferred language.
6. As a user, I want my language choice to be remembered across app restarts, so that I only set it once.
7. As a user, I want to switch back to "follow system language," so that the app tracks my device setting again if I change my mind.
8. As a user, I want the language change to apply immediately to the screen I'm on, so that I don't have to restart the app to see the effect.

### Localized UI

9. As a user, I want all tab labels, buttons, and titles in my chosen language, so that the interface is fully understandable.
10. As a user, I want all dialogs (add/edit/delete team, character, item, perk, quest) in my chosen language, so that no interaction drops back to another language.
11. As a user, I want validation messages, empty states, and confirmation prompts localized, so that the whole flow is consistent.
12. As a user, I want numbers and signed values (e.g. discounts, modifiers) formatted according to my chosen language, so that formatting matches the rest of the UI.

### Localized game content

13. As a user, I want scenario names and locations in my chosen language, so that I can find and recognize scenarios.
14. As a user, I want scenario unlock requirements shown in my chosen language, so that I understand what I need to progress.
15. As a user, I want item/goods names in my chosen language, so that I can shop and manage inventory.
16. As a user, I want item types/categories shown in my chosen language, so that filtering and grouping make sense.
17. As a user, I want perk descriptions in my chosen language, so that I can choose perks correctly.
18. As a user, I want the symbols/icons embedded in perk and ability text to render the same in every language, so that game notation stays unambiguous.
19. As a user, I want personal quest titles, narrative descriptions, and task text in my chosen language, so that I can read and track my quest.
20. As a user, I want achievement names (global and party) in my chosen language, so that I understand my campaign state.
21. As a user, I want monster names in my chosen language wherever they appear (scenario lists, the scenario constructor, active play), so that I can identify monsters.
22. As a user, I want game-level / difficulty labels in my chosen language, so that difficulty selection is clear.
23. As a user, I want character class names in my chosen language, so that class selection and headers read correctly.
24. As a user, I want content from the Forgotten Circles expansion localized too, so that expansion play is consistent with the base game.

### Progress preservation

25. As a returning user, I want all my existing teams, characters, and progress intact after the app gains multi-language support, so that the update doesn't reset me.
26. As a user, I want switching language to keep my unlocked, locked, and completed scenarios exactly as they were, so that translation never alters my campaign.
27. As a user, I want my earned achievements to keep correctly gating scenarios after switching language, so that unlock logic behaves identically in any language.
28. As a user, I want custom text I typed (team names, character names) to stay exactly as I entered it, so that my personal data is never translated or altered.

### Localized imagery

29. As a user, I want monster ability cards shown in my chosen language when a localized image exists, so that I can read card effects.
30. As a user, I want item/goods card images shown in my chosen language when available, so that artwork matches the language.
31. As a user, I want the Russian image shown when an English image is missing, so that I never see a broken or blank card.

### Missing-translation behavior

32. As a user, I want a clearly visible marker (rather than a blank) where a translation is missing, so that gaps are obvious and reportable.
33. As a QA tester, I want missing translations to stand out on screen, so that I can find and report untranslated content quickly.

### Maintainer / extensibility

34. As a maintainer, I want to add a new language by supplying a translation dictionary and (optionally) localized images plus one registry entry, so that adding languages doesn't require architectural changes.
35. As a maintainer, I want the game logic graph defined only once (not duplicated per language), so that scenario rules can't drift between languages.
36. As a maintainer, I want to author new game content in one place and add translations separately, so that content and translation work stay decoupled.
37. As a maintainer, I want a single list of supported languages that drives the picker, resource selection, content seeding, and image lookup, so that the supported set is consistent everywhere.
38. As a maintainer, I want a straightforward way to see which keys are untranslated, so that I can complete and verify a language.

## Implementation Decisions

### Two-layer localization model

- **Static UI text** continues to use Android string resources with locale qualifiers.
  The default resource set is English and the Russian set already exists; the work here
  is to (a) confirm completeness of the extracted strings and (b) ensure resource
  selection follows the in-app language override, not only the system locale.
- **Dynamic game content** (currently seeded from Russian JSON into the database) gains
  a dedicated translation layer described below.

### Single source of truth for game logic

- The existing data remains the canonical source for the game *graph*: scenario numbers,
  unlock expressions, the achievement/monster references those expressions use, item
  numbers, quest IDs, etc.
- The internal *matching keys* for achievements and monsters stay as the current
  (Russian) names. Scenario unlock evaluation and saved team progress are **unchanged**,
  which is what makes live language switching possible with **zero migration of user
  data** and no risk to existing campaigns.

### Translation store

- Introduce a normalized localized-text store (e.g. a translation table keyed by entity
  type, stable entity key, field name, and locale). It is seeded for **all** supported
  languages at database build time, from per-locale translation dictionaries shipped
  with the app.
- Stable join keys already exist for most content (scenario number, item number, quest
  ID); achievements and monsters are keyed by their canonical name. Frequently repeated
  strings (locations, monster names) are deduplicated into their own keyed maps rather
  than repeated per scenario.
- Because every language is seeded up front, switching language is a query/display
  concern, not a re-seed — this is the mechanism that satisfies "switch live, preserve
  progress."

### Translation source & authoring

- English text is produced by translating the existing Russian content. The canonical
  Russian data is not modified; English is added as a parallel dictionary keyed by the
  stable IDs.
- Language-neutral notation embedded in text (the symbol/icon placeholders in perk and
  ability text) is preserved across languages and resolved at render time as today.

### Display resolution & reactivity

- Content repositories resolve display text by the *active* locale through the
  translation store. The active locale is exposed as a reactive value so that Compose
  screens recompose and content streams re-query when the user switches language.
- The human-readable scenario-requirements renderer maps each achievement reference
  (canonical key) to its localized display name; evaluation logic still operates on
  canonical keys.

### Fallback policy

- **Text:** when a translation is missing for the active language, render a visible
  placeholder/key marker (the user's chosen behavior; doubles as a QA signal for finding
  gaps).
- **Images:** when a localized image is missing, fall back to the existing Russian image.

### Language selection mechanism

- Default behavior: derive the initial language from the system locale, mapped to a
  supported language; if the system locale is unsupported, default to English.
- Provide an in-app language picker that overrides the system locale and persists the
  choice in the existing preferences data source. The picker also offers a "follow
  system" option.
- Runtime switching is implemented with the AndroidX per-app locale APIs
  (`AppCompatDelegate.setApplicationLocales`), which back-ports per-app language support
  to the app's minimum SDK. This requires adding the `androidx.appcompat` dependency
  (the entry activity is currently a plain Compose `ComponentActivity` with no AppCompat
  dependency); justification is that this is the supported, minSdk-compatible mechanism
  for per-app language and reactive resource selection.

### New UI surface

- No settings screen exists today, so a new entry point for the language picker must be
  added (for example, a settings screen or an action in the top app bar). Exact
  placement is a design decision; the requirement is a discoverable, app-global control.

### Localized imagery

- Images are resolved from a per-locale asset subfolder, with fallback to the existing
  Russian asset when a localized file is absent. This applies to monster ability cards
  and goods images and lets English artwork land incrementally.

### Supported-languages registry

- A single registry lists supported locales and drives the picker, resource
  configuration, content seeding, and image-folder lookup. Adding a language means
  adding a dictionary, an image subfolder (optional), and one registry entry.

### Schema & versioning

- Adding the translation store requires a database version bump, a migration added to
  the migrations array, and a regenerated schema export. Seeding of all languages occurs
  within the existing database-fill flow. Existing user tables are untouched, so
  upgrading users keep their data.

### Number & locale formatting

- Formatting that currently relies on the system locale must follow the active app
  locale once an override is set, so numeric formatting stays consistent with the chosen
  language.

## Out of Scope

- Languages beyond English and Russian at launch. The architecture supports more, but
  only EN + RU ship initially.
- Right-to-left layout support (no RTL language is in scope).
- Correcting or re-authoring game-data accuracy. English is a translation of the current
  Russian content and may diverge from official Gloomhaven English terminology;
  aligning to official terms is a possible later pass.
- Automatic translation of text baked into images. English images must be supplied
  separately; until they exist, the Russian image is shown.
- Translating non-user-facing code comments (a small number of Russian code comments
  remain; optional cleanup, non-blocking).
- Cloud/account sync of the language preference; the choice is stored locally on device.
- Per-team or per-character language. Language is a single app-global setting.

## Further Notes

- **Production fallback tradeoff.** Showing a visible placeholder/key for missing text
  is great for catching gaps but undesirable to ship to end users. Recommended follow-up:
  keep the key-placeholder in debug builds but fall back to the Russian source in release
  builds, so production users never see raw keys. Flagged for revisit before release.
- **English-image dependency.** Localizing card/item images depends on sourcing or
  creating English artwork. With Russian fallback in place, the feature can ship and
  improve incrementally as English images become available; image localization should be
  tracked as its own work stream.
- **Canonical keys remain Russian strings.** This is invisible to users and avoids any
  save migration. If a future cleanup wants language-neutral keys (slugs), that becomes a
  larger refactor that *would* require migrating existing saves, and should be weighed
  separately.
- **Plurals and grammar.** Russian and English pluralization differ; where counts are
  shown, use Android plural resources rather than a single templated string.
- **Stats data.** Monster stats files are largely numeric and mostly language-neutral;
  only any embedded ability/special text needs translation entries.
- **Completeness check.** The recent "add translations" work extracted most UI strings
  into resources. A verification pass should confirm there are no remaining hard-coded
  user-facing strings before declaring the UI layer done.
