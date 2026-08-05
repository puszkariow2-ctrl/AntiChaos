# AntiChaos — Checklist Розробки

## Phase 0: Analysis & Concept ✅ ЗАВЕРШЕНО
- [x] Аудит існуючого бота (AntiChaosBot)
- [x] Концепція нового продукту
- [x] Модель даних (14+ entity)
- [x] План роботи на 12 тижнів
- [x] Структура Android проєкту створена

## Phase 1: Setup & Core Foundation (~2 тижні)

### Week 1: Project Setup + Tasks Module
- [ ] Відкрити проєкт в Android Studio та налаштувати SDK (див. SETUP_INSTRUCTIONS.md)
- [ ] Перший успішний Gradle sync
- [ ] Реалізувати TaskRepository повністю
- [ ] Створити TasksViewModel
- [ ] Реалізувати TasksScreen UI (список, фільтри, створення)
- [ ] Реалізувати HomeScreen UI (dashboard)
- [ ] Додати launcher icons

### Week 2: Reminders + Quick Capture
- [ ] ReminderRepository + ViewModel
- [ ] RemindersScreen UI з natural language input
- [ ] WorkManager інтеграція для сповіщень
- [ ] NaturalLanguageParser тестування та покращення
- [ ] Quick Capture FAB на Home screen

## Phase 2: Habits + Journal (~3 тижні)

### Week 3: Habit Tracker
- [ ] HabitRepository + ViewModel
- [ ] HabitsScreen UI (список звичок на сьогодні, one-tap completion)
- [ ] Streak calculation логіка
- [ ] Calendar view для історії виконання
- [ ] Create/Edit Habit dialog

### Week 4: Journal System
- [ ] JournalRepository + ViewModel
- [ ] JournalScreen UI (вибір типу запису)
- [ ] Free Write editor
- [ ] Evening Review template screen
- [ ] Gratitude Journal mode
- [ ] Journal history з пошуком

### Week 5: Daily OS + Life Areas
- [ ] SettingsRepository + ViewModel
- [ ] SettingsScreen UI (налаштування anchors, AI, сповіщень)
- [ ] Daily anchors configuration в settings
- [ ] Life areas прив'язка до задач/звичок
- [ ] Onboarding flow реалізація

## Phase 3: AI Coach (~2 тижні)

### Week 6: AI Integration + Techniques Library
- [ ] AiCoachService — реальна OpenAI API інтеграція
- [ ] CoachScreen UI (chat-like interface)
- [ ] DatabaseSeeder розширити до 50+ технік
- [ ] Technique detail screen
- [ ] Offline rule-based fallback тестування

### Week 7: Advanced AI Features
- [ ] UserStateLog автоматичне логування
- [ ] Pattern detection логіка
- [ ] Weekly insights генерація
- [ ] API budget control (daily/monthly limits)

## Phase 4: Knowledge Base (~2 тижні) — Phase 2 feature

### Week 8: Content Library
- [ ] LibraryRepository + ViewModel
- [ ] LibraryScreen UI (книги, цитати, методики, нотатки)
- [ ] Завантажити seed content (20+ книг, 100+ цитат)
- [ ] Book detail screen
- [ ] Quote of the day на Home screen

### Week 9: Saved Notes + Integration
- [ ] Saved notes CRUD
- [ ] Search across all library content
- [ ] "Apply to practice" → створити звичку/задачу з контенту

## Phase 5: Polish & Analytics (~2 тижні)

### Week 10: Progress Visualization
- [ ] DailySummary автоматичний розрахунок
- [ ] Графіки (mood trend, habit consistency, tasks completed)
- [ ] Achievements system (10+ досягнень)
- [ ] Progress/Insights screen

### Week 11: UX Polish + Onboarding
- [ ] Onboarding flow (3-5 екранів)
- [ ] Dark theme refinement
- [ ] Empty states для всіх екранів
- [ ] Loading skeletons та animations
- [ ] Error handling + retry logic

## Phase 6: Testing & Launch Prep (~1 тиждень)

### Week 12: Final Steps
- [ ] End-to-end testing основного flow
- [ ] Performance optimization
- [ ] Firebase Crashlytics інтеграція
- [ ] Generate signed release APK
- [ ] Play Store listing assets (description, screenshots)
- [ ] Backup/Export functionality

---

## Поточний статус: Phase 0 ✅ | Наступний: Phase 1 Week 1
