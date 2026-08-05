# AntiChaos Android — План Роботи

## Огляд Проєкту
Створення нового Android-додатку: планер + трекер звичок + щоденник + AI-психолог + бібліотека саморозвитку. Існуючий бот використовується ТІЛЬКИ як референс функціоналу, НЕ як backend.

---

## Phase 0: ANALYSIS & CONCEPT ✅ ЗАВЕРШЕНО
**Тривалість:** завершено

### Виконано:
- [x] Повний аудит існуючого бота (AntiChaosBot)
- [x] Визначено що беремо, що створюємо з нуля, що зайве
- [x] Розроблено концепцію нового продукту
- [x] Створено модель даних для Android додатку
- [x] Визначено MVP scope та roadmap

### Результати:
- `AUDIT_EXISTING.md` — детальний розбір існуючого коду
- `CONCEPT.md` — повна концепція продукту
- `DATA_MODEL.md` — модель даних (14+ entity для MVP)

---

## Phase 1: PROJECT SETUP & CORE FOUNDATION (~2 тижні)

### Week 1: Setup + Tasks Module
**Мета:** Працюючий Android-проєкт з базовою архітектурою та модулем задач.

#### Tasks:
- [ ] Створити Kotlin + Jetpack Compose проєкт в Android Studio
- [ ] Налаштувати MVVM + Clean Architecture структуру
- [ ] Додати залежності (Hilt, Room, Retrofit, Coroutines, Navigation)
- [ ] Реалізувати Room database з Task та TaskStep entity
- [ ] Створити TasksRepository + UseCases
- [ ] Реалізувати TasksScreen (список задач з фільтрами)
- [ ] Реалізувати Create/Edit Task dialog
- [ ] Додати статуси: Planned → In Progress → Done / Postponed

#### Deliverables:
- Компілюючий Android проєкт
- CRUD для задач працює локально (без сервера)
- Базовий UI зі списком задач

---

### Week 2: Reminders + Quick Capture
**Мета:** Smart reminders та швидкий захват думок.

#### Tasks:
- [ ] Реалізувати Reminder entity + Room DAO
- [ ] Налаштувати WorkManager для push notifications
- [ ] Створити Natural Language Parser (портуємо логіку з бота)
  - "нагадай через 5 хвилин" → парсить і створює reminder
  - "о 15:00 нагадати про зустріч" → fixed time reminder
- [ ] Реалізувати Quick Capture screen (велика кнопка "+")
- [ ] AI/Rule-based routing: quick capture text → task OR reminder OR journal entry
- [ ] Reminder follow-up логіка (якщо не підтвердив → повтор)

#### Deliverables:
- Нагадування спрацьовують з push notification
- Quick Capture працює з natural language parsing
- Follow-up система активна

---

## Phase 2: HABITS + JOURNAL (~3 тижні)

### Week 3: Habit Tracker
**Мета:** Повноцінний трекер звичок зі streaks та візуалізацією.

#### Tasks:
- [ ] Реалізувати Habit, HabitCompletion, MissedHabitDay entity
- [ ] HabitsScreen (список активних звичок на сьогодні)
- [ ] Create/Edit Habit dialog (назва, частота, час, складність)
- [ ] One-tap completion з опціональним коментарем
- [ ] Streak calculation logic (current streak, longest streak)
- [ ] Calendar view для історії виконання звички
- [ ] Habit stacking підтримка ("після звички A роби B")

#### Deliverables:
- Користувач може створювати та відмічати звички
- Streaks рахуються коректно
- Календарний вигляд працює

---

### Week 4: Journal System
**Мета:** Щоденник з шаблонами та evening review.

#### Tasks:
- [ ] Реалізувати JournalEntry, EveningReview entity
- [ ] Free Write screen (просто текст + вибір настрою)
- [ ] Evening Review template (3 поля: вдалося/складно/навчився + рейтинг дня)
- [ ] Gratitude Journal mode (3 речі за які вдячний)
- [ ] Journal history view (список записів з пошуком)
- [ ] Mood tracking integration (настрій з журнал entries → графік)

#### Deliverables:
- 4 режими журналу працюють
- Evening review збережений і доступний для перегляду
- Mood data збирається для майбутньої аналітики

---

### Week 5: Daily OS + Life Areas
**Мета:** Налаштовувані daily anchors та система сфер життя.

#### Tasks:
- [ ] Реалізувати DailyAnchor entity + default anchors
- [ ] Settings screen для налаштування daily anchors (час, активність)
- [ ] Home screen показує наступні anchors дня
- [ ] Реалізувати LifeArea entity з 8 default сферами
- [ ] Прив'язка задач та звичок до life areas
- [ ] Простий індикатор балансу сфер (attention level)

#### Deliverables:
- Daily OS працює з налаштовуваними anchors
- Life areas система активна
- Задачі/звички можна категоризувати по сферах

---

## Phase 3: AI COACH (~2 тижні)

### Week 6: AI Integration + Techniques Library
**Мета:** AI психолог з бібліотекою технік.

#### Tasks:
- [ ] Реалізувати Technique entity + завантажити master list (50+ технік)
- [ ] Налаштувати OpenAI API integration (GPT-4o-mini для швидких відповідей)
- [ ] CoachingSession flow: user message → AI analyzes state → suggests techniques
- [ ] Emotional state detection з тексту користувача
- [ ] Context-aware responses (враховує recent tasks, habits, journal entries)
- [ ] Offline fallback: rule-based suggestions без API

#### Deliverables:
- Користувач може "поговорити" з AI coach
- AI пропонує конкретні техніки зі списку
- Працює без інтернету (fallback mode)

---

### Week 7: Advanced AI Features
**Мета:** Патерни, інсайти, контекстна допомога.

#### Tasks:
- [ ] UserStateLog entity + автоматичне логування стану
- [ ] Pattern detection: "твої найкращі дні — понеділок і середа"
- [ ] Proactive suggestions на основі патернів
- [ ] AI insights у weekly review
- [ ] Budget control для API usage (daily/monthly limits)

#### Deliverables:
- AI генерує персональні інсайти
- Проактивні поради працюють
- API costs під контролем

---

## Phase 4: KNOWLEDGE BASE (~2 тижні)

### Week 8: Content Library
**Мета:** Бібліотека книг, цитат та методик.

#### Tasks:
- [ ] Реалізувати Book, Quote, Methodology entity
- [ ] Завантажити seed content (20+ книг, 100+ цитат, 30+ методик)
- [ ] Books screen з пошуком та фільтрами по категоріях
- [ ] Quotes feed (випадкова цитата дня + favorites)
- [ ] Methodology detail view з покроковими інструкціями
- [ ] "Apply to practice" → створити звичку/задачу з методики

#### Deliverables:
- Knowledge base заповнена контентом
- Користувач може читати та застосовувати знання
- Зв'язок content → habit/task працює

---

### Week 9: Saved Notes + Integration
**Мета:** Нотатки користувача та інтеграція з knowledge base.

#### Tasks:
- [ ] SavedNote entity + CRUD operations
- [ ] Link notes to books/quotes/methodologies
- [ ] Tag system для нотаток
- [ ] Search across all content (notes, quotes, books)
- [ ] "Today's inspiration" на home screen (цитата або інсайт)

#### Deliverables:
- Повноцінна система нотаток
- Пошук по всьому контенту працює

---

## Phase 5: POLISH & ANALYTICS (~2 тижні)

### Week 10: Progress Visualization
**Мета:** Графіки, статистика, досягнення.

#### Tasks:
- [ ] DailySummary entity + автоматичний розрахунок
- [ ] WeeklyInsight generation (AI + rules-based)
- [ ] Charts: mood over time, habit consistency, tasks completed
- [ ] Achievements system (10+ базових досягнень)
- [ ] Progress screen з ключовими метриками

#### Deliverables:
- Візуалізація прогресу працює
- Weekly insights генеруються автоматично
- Achievements мотивують користувача

---

### Week 11: UX Polish + Onboarding
**Мета:** Фінальна обробка та онбординг.

#### Tasks:
- [ ] Onboarding flow (3-5 екранів: вітаємо → налаштовуємо цілі → готово)
- [ ] Dark theme refinement (за замовчуванням)
- [ ] Empty states для всіх екранів
- [ ] Loading skeletons та animations
- [ ] Error handling + retry logic
- [ ] Home screen optimization (найважливіше — в 1 скроллі)

#### Deliverables:
- Onboarding готовий
- UX консистентний по всьому додатку
- Жодних критичних багів

---

## Phase 6: TESTING & LAUNCH PREP (~1 тиждень)

### Week 12: Final Steps
**Мета:** Готовність до релізу.

#### Tasks:
- [ ] End-to-end testing основного flow
- [ ] Performance optimization (startup time, memory usage)
- [ ] Crash reporting integration (Firebase Crashlytics)
- [ ] Analytics setup (privacy-respecting, optional)
- [ ] Generate signed release APK
- [ ] Prepare Play Store listing (description, screenshots)
- [ ] Backup/Export functionality

#### Deliverables:
- Signed APK готовий до релізу
- Мониторинг налаштований
- Документація для публікації готова

---

## Підсумкова Оцінка

| Phase | Тривалість | Ключовий Результат |
|-------|-----------|-------------------|
| 0 - Analysis & Concept | ✅ Done | Концепція та модель даних |
| 1 - Setup + Core | ~2 тижні | Tasks + Reminders + Quick Capture |
| 2 - Habits + Journal | ~3 тижні | Habit Tracker + Journal System |
| 3 - AI Coach | ~2 тижні | AI психолог з техніками |
| 4 - Knowledge Base | ~2 тижні | Книги, цитати, методики |
| 5 - Polish + Analytics | ~2 тижні | Графіки, онбординг, UX |
| 6 - Launch Prep | ~1 тиждень | Signed APK готовий |

**Загальна оцінка: ~12 тижнів (3 місяці) до повноцінного MVP**

---

## Критичні Рішення Перед Початком Phase 1

### 1. AI Integration Strategy
- **Варіант A:** OpenAI API напряму з додатку (простіше, але user платить або ми платимо)
- **Варіант B:** Локальний LLM через ONNX/MLC (повільніше, але безкоштовно та приватно)
- **Рекомендація:** Гібрид — прості речі rule-based, складні через OpenAI API з бюджетним лімітом

### 2. Content Strategy for Knowledge Base
- Хто створює контент (книги, цитати, методики)?
- **Рекомендація:** Ми manually seed перші 50 книг + 200 цитат + 50 методик як baseline

### 3. Monetization (на майбутнє)
- Free tier з базовим функціоналом?
- Premium для AI coach та advanced analytics?
- **Рекомендація:** Спочатку free, потім premium features після validation

---

## Наступний Крок
**Почати Phase 1, Week 1:** Створити Android проєкт в Android Studio.
