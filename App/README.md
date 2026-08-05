# AntiChaos Android App — Project Structure

## How to Open
1. Open Android Studio
2. File → Open → Select this `App` folder
3. Wait for Gradle sync (first time takes 5-10 minutes)
4. If SDK not found: Tools → SDK Manager → install Android 14 (API 35) + Build Tools

## Architecture Overview

Clean Architecture with MVVM pattern:

```
com.antichaos.app/
├── data/                    # Data layer
│   ├── di/                  # Hilt dependency injection modules
│   │   ├── DatabaseModule.kt      # Room database + DAOs
│   │   └── AppModule.kt           # Core services (AI, NLP, etc.)
│   ├── local/               # Local data sources
│   │   ├── dao/             # Room Data Access Objects
│   │   │   ├── TaskDao.kt
│   │   │   ├── ReminderDao.kt
│   │   │   ├── HabitDao.kt
│   │   │   ├── JournalDao.kt
│   │   │   ├── CoachDao.kt
│   │   │   ├── LibraryDao.kt
│   │   │   └── SettingsDao.kt
│   │   ├── entity/          # Room entities (database tables)
│   │   │   ├── TaskEntity.kt
│   │   │   ├── ReminderEntity.kt
│   │   │   ├── HabitEntity.kt
│   │   │   ├── JournalEntity.kt
│   │   │   ├── CoachEntity.kt
│   │   │   ├── LibraryEntity.kt
│   │   │   └── SystemEntity.kt    # Daily anchors, life areas
│   │   └── room/            # Database configuration
│   │       └── AntiChaosDatabase.kt
│   └── repository/          # Repositories (abstraction over data sources)
│       └── TaskRepository.kt  # (+ more to be added)
│
├── domain/                  # Business logic layer
│   └── model/               # Domain models (business entities)
│       ├── Task.kt
│       └── CommonModels.kt    # Habit, JournalEntry, Technique, etc.
│
├── presentation/            # UI layer
│   ├── home/                # Home screen ("Штаб")
│   │   └── HomeScreen.kt
│   ├── tasks/               # Tasks management
│   │   └── TasksScreen.kt
│   ├── reminders/           # Smart reminders
│   │   └── RemindersScreen.kt
│   ├── habits/              # Habit tracker
│   │   └── HabitsScreen.kt
│   ├── journal/             # Journal & reflection
│   │   └── JournalScreen.kt
│   ├── coach/               # AI psychologist/coach
│   │   └── CoachScreen.kt
│   ├── library/             # Knowledge base (Phase 2)
│   │   └── LibraryScreen.kt
│   ├── settings/            # App settings
│   │   └── SettingsScreen.kt
│   ├── onboarding/          # First-launch setup
│   │   └── OnboardingScreen.kt
│   ├── navigation/          # Navigation graph + routes
│   │   ├── AntiChaosNavGraph.kt
│   │   ├── Screen.kt
│   │   └── OnboardingViewModel.kt
│   └── theme/               # Material 3 theming
│       ├── Theme.kt         # Dark/light color schemes
│       ├── Color.kt         # Brand colors
│       └── Type.kt          # Typography
│
├── core/                    # Cross-cutting utilities
│   ├── ai/                  # AI coach integration
│   │   └── AiCoachService.kt    # OpenAI API + rule-based fallback
│   ├── db/                  # Database initialization
│   │   └── DatabaseSeeder.kt    # Seeds default data on first launch
│   ├── nlp/                 # Natural language processing
│   │   └── NaturalLanguageParser.kt  # Parses "нагадай через 5 хв" etc.
│   └── notifications/       # Push notification system
│       └── ReminderNotificationManager.kt  # WorkManager-based reminders
│
├── MainActivity.kt          # Entry point + navigation host
└── AntiChaosApplication.kt  # Hilt application entry
```

## Tech Stack

| Layer | Technology | Purpose |
|-------|-----------|---------|
| UI | Jetpack Compose + Material 3 | Modern declarative UI |
| Navigation | Compose Navigation | Screen routing with type-safe routes |
| DI | Hilt | Dependency injection |
| Database | Room (SQLite) | Local offline-first storage |
| Background | WorkManager | Reliable reminders & sync |
| Async | Kotlin Coroutines + Flow | Reactive data streams |
| AI | OpenAI API (optional) | Coach responses with rule-based fallback |

## Key Design Decisions

1. **Offline-first**: All core features work without internet. AI is optional enhancement.
2. **Single-user app**: No auth, no cloud sync (yet). Everything local on device.
3. **Epoch timestamps**: Stored as Long (seconds since epoch) for easy cross-timezone queries.
4. **Int enums**: Database stores enum values as Int with companion object converters.
5. **JSON arrays in DB**: Lists stored as JSON strings (Room doesn't support List columns natively).

## Next Steps to Make It Compile

1. Add missing repository implementations (HabitRepository, JournalRepository, etc.)
2. Create ViewModels for each screen
3. Implement actual Compose UI components (currently placeholder comments)
4. Add launcher icons and notification drawable resources
5. Connect AI service with real OpenAI API endpoint
6. Add DataStore for user preferences (separate from Room)

## Database Schema Summary

| Table | Purpose | MVP Priority |
|-------|---------|-------------|
| tasks, task_steps | Task management | ✅ Must |
| reminders, recurring_reminders | Smart notifications | ✅ Must |
| habits, habit_completions | Habit tracking | ✅ Must |
| journal_entries, evening_reviews | Reflection & mood | ✅ Must |
| techniques, coaching_sessions, user_state_logs | AI coach system | ✅ Must |
| daily_anchors, life_areas | Daily OS + balance | ✅ Must |
| books, quotes, methodologies, saved_notes | Knowledge base | ⚠️ Phase 2 |
