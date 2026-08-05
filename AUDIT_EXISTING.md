# AntiChaos — Повний Аудит Існуючого Бота

## Мета аудиту
Визначити що з існуючого бота беремо в новий Android-додаток, що покращуємо, а що зайве.

---

## 1. ІНФРАСТРУКТУРА БОТА (НЕ ПЕРЕНОСИМО)

### Telegram-specific (викидаємо повністю):
- aiogram/telegram.ext integration (~4000 рядків у bot.py)
- Telegram keyboards, inline buttons, callbacks
- Telegram message handlers
- Telegram user auth via telegram_id
- Bot commands (/start, /help тощо)
- Telegram voice note handling

### Backend-specific (переробляємо):
- SQLite schema з 25+ таблицями — переписати під Room/SQLite для Android
- Python scheduler (APScheduler) → замінити на WorkManager + AlarmManager
- FastAPI wrapper (якщо планувався) → не потрібен, все локально

---

## 2. ФУНКЦІОНАЛ БОТА — ДЕТАЛЬНИЙ РОЗБИР

### ✅ ЗАДАЧІ (tasks) — БЕРЕМО І ПОКРАЩУЄМО
**Що є:**
- Створення задач з назвою, описом, пріоритетом, проєктом
- Статуси: planned → in_progress → waiting_validation → validated_done / postponed / cancelled
- Дедлайни та нагадування до задач
- Task steps (підкроки)
- Validation checklist (перевірка якості виконання)
- Recurring tasks (повторювані задачі)

**Що покращити:**
- Додати категорії/теги для задач
- Додати енергетичний рівень задачі (легка/середня/важка)
- Додати емоційну складову (що відчуваєш коли робиш цю задачу?)
- Визуальні спринти/таймблоки

**Вердикт:** ✅ Базова структура хороша, розширюємо.

---

### ⏰ НАГАДУВАННЯ (reminders) — БЕРЕМО І ПОКРАЩУЄМО
**Що є:**
- Одноразові нагадування з точним часом
- Recurring reminders (щодня, щотижня, по днях тижня)
- Natural language parsing ("нагадай через 5 хвилин")
- Follow-up система (якщо не підтвердив — повторює)
- Recurring reminder events та exceptions

**Що покращити:**
- Smart reminders на основі звичок користувача
- Контекстні нагадування ("ти зазвичай робиш спорт о 10:45")
- Гнучкіша система повторів (не тільки fixed time)

**Вердикт:** ✅ Сильна частина, беремо повністю.

---

### 📝 INBOX / ШВИДКИЙ ЗАХВАТ — БЕРЕМО
**Що є:**
- Inbox для швидкого запису думок/задач/ідей
- AI parsing хаотичних повідомлень на структуризовані елементи
- Detected type (task, reminder, idea, journal)

**Вердикт:** ✅ Критично важливо для людей з хаосом в голові. Зберігаємо як "Quick Capture".

---

### 💡 ІДЕЇ — БЕРЕМО
**Що є:**
- Збереження ідей з назвою, описом, проєктом
- Статуси: captured / developing / done / archived

**Вердикт:** ✅ Корисно, але можна об'єднати з Notes/Journal.

---

### 📚 БІБЛІОТЕКА — ПОКРАЩУЄМО ЗНАЧНО
**Що є:**
- Збереження нотаток, цитат, висновків
- Категорії: saved, notes
- Історія задач та нагадувань

**Що НЕСТАЄ (критично):**
- ❌ Структурована бібліотека книг/ресурсів
- ❌ Цитати від коучів/експертів
- ❌ Методики саморозвитку
- ❌ Виступи та лекції
- ❌ Зв'язок контенту з практикою

**Вердикт:** ⚠️ Існуюча бібліотека — це просто notes. Потрібно повністю перебудувати в Knowledge Base для саморозвитку.

---

### 📓 ЖУРНАЛ (journal) — БЕРЕМО І РОЗШИРЮЄМО
**Що є:**
- Journal entries з raw_text, summary, mood
- Morning/evening review flows

**Що НЕСТАЄ:**
- ❌ Структуровані щоденники (ранковий/вечірній шаблони)
- ❌ gratitude journal
- ❌ reflection prompts
- ❌ зв'язок з настроєм та енергією
- ❌ AI-аналіз патернів у записах

**Вердикт:** ⚠️ Базова структура є, потрібно значно розширити.

---

### 🔄 DAILY OS / ЯКІСТЬ ДНЯ — БЕРЕМО І РОЗШИРЮЄМО
**Що є:**
- Daily anchors (10:00 ранковий штаб, 15:00 фокус-блок тощо)
- State check (🪫 м'який старт / ⚡ енергія / 🌊 заземлення)
- Morning HQ flow з вибором стану
- Evening review
- Weekly review

**Що НЕСТАЄ:**
- ❌ Гнучкіша система (користувач налаштовує свій день)
- ❌ Daily score/рейтинг дня
- ❌ Зв'язок якості дня зі звичками та задачами

**Вердикт:** ✅ Сильна концепція, розширюємо.

---

### 🎯 ПРОЄКТИ — БЕРЕМО
**Що є:**
- Projects з назвою, описом, статусом (active/on_hold/archived)
- Зв'язок задач з проєктами

**Вердикт:** ✅ Достатньо для MVP.

---

### 📊 STATE SERVICE / ПРОГРЕС — БЕРЕМО ЧАСТИНОВИЙ ФУНКЦІОНАЛ
**Що є:**
- Today overview (задачі, нагадування)
- Week review
- Journal today view
- Daily state log (mood_state, energy_state, body_state, urge_level)

**Вердикт:** ✅ Корисно для дашбордів.

---

### 🧠 AI PARSER / AI SERVICE — БЕРЕМО І РОЗШИРЮЄМО
**Що є:**
- Deterministic parser (прості нагадування без API)
- AI parsing хаотичних повідомлень → структуризовані задачі/нагадування
- LLM router з бюджетним балансувальником
- Fallback система

**Що НЕСТАЄ (критично):**
- ❌ AI психолог / coach
- ❌ Рекомендація технік саморозвитку в реальному часі
- ❌ Аналіз патернів поведінки
- ❌ Персональні інсайти

**Вердикт:** ⚠️ Існуючий AI — це parser. Потрібно додати AI Coach/Psychologist як окремий модуль.

---

### 🎤 VOICE / STT — БЕРЕМО КОНЦЕПЦІЮ
**Що є:**
- Voice-to-text підтримка (OpenAI Whisper)
- Конфігурація через .env

**Вердикт:** ✅ Зберігаємо як опцію швидкого вводу.

---

### 📈 PROACTIVE / ENGAGEMENT — БЕРЕМО ЧАСТИНОВИЙ ФУНКЦІОНАЛ
**Що є:**
- Proactive events (нагадування про перевірки)
- Affirmations system
- User engagement state tracking
- Re-engagement mode

**Вердикт:** ✅ Корисно для утримання користувача.

---

### 🚬 SMOKING URGE / DAILY STATE LOG — СПЕЦИФІЧНО, АДАПТУЄМО
**Що є:**
- Smoking urge tracking (smoking_urge_keyboard)
- Daily state log з cannabis_event, trigger_type, urge_level
- Stabilize flow

**Вердикт:** ⚠️ Це дуже специфічно під одного користувача. Перетворити на універсальний "Trigger/Urges Tracker" для будь-яких залежностей/тригерів.

---

## 3. ЧОГО НЕМАЄ В БОТІ (КРИТИЧНО ДЛЯ НОВОГО ДОДАТКУ)

### ❌ HABIT TRACKER — ПОТРІБНО СТВОРИТИ З НУЛЯ
- Створення звичок з частотою (щодня, 3 рази на тиждень тощо)
- Streak tracking (серії виконання)
- Habit difficulty levels
- Visual progress (календар, графіки)
- Smart suggestions для нових звичок

### ❌ AI PSYCHOLOGIST / COACH — ПОТРІБНО СТВОРИТИ З НУЛЯ
- Розпізнавання емоційного стану з тексту/контексту
- Рекомендація технік зі списку (dismantling, grounding, reframing тощо)
- Контекстні поради ("ти втомився, спробуй 5 хв заземлення")
- Техніки: дихання, медитація, journaling prompts, cognitive reframing

### ❌ KNOWLEDGE BASE / CONTENT LIBRARY — ПОТРІБНО СТВОРИТИ З НУЛЯ
- Каталог книг з анотаціями та ключовими ідеями
- Цитати від експертів (категоризовані)
- Методики саморозвитку з покроковим описом
- Виступи/лекції (посилання + конспекти)
- Зв'язок контенту з практикою ("прочитав про X → створив звичку Y")

### ❌ LIFE AREAS / SFERI ŽITTЯ — ПОТРІБНО СТВОРИТИ З НУЛЯ
- Здоров'я (фізичне + ментальне)
- Кар'єра/бізнес
- Стосунки
- Фінанси
- Особистий ріст
- Творчість
- Відпочинок/задоволення
- Співвідношення та баланс

### ❌ PROGRESS VISUALIZATION — ПОТРІБНО СТВОРИТИ З НУЛЯ
- Графіки настрою за часом
- Статистика звичок (streaks, completion rate)
- Weekly/monthly insights
- Achievement badges / milestones

### ❌ ONBOARDING / PERSONALIZATION — ПОТРІБНО СТВОРИТИ З НУЛЯ
- Перший запуск: налаштування цілей, сфер фокусу
- Вибір рівня складності (початківець/просунутий)
- Персоналізація daily anchors під графік користувача

---

## 4. ЩО ЗАЙВЕ В БОТІ (НЕ ПЕРЕНОСИМО)

### ❌ Видаляємо повністю:
- Telegram-specific код (~60% bot.py)
- Bot commands system
- Inline keyboard markup generation
- Telegram user auth flow
- Bot error log table (замінити на Crashlytics/Sentry)
- User flow state table (не потрібен в app context)
- Action decision log (занадто детальний для production app)
- LLM usage logs (спростити до базового трекингу)

### ⚠️ Спрощуємо:
- 25+ таблиць → оптимізувати до ~10-12 ключових
- Recurring reminder exceptions table → спростити логіку
- Proactive message log → замінити на простіший notification history

---

## 5. ПІДСУМКОВА МАТРИЦЯ

| Функціонал | Статус | Дія |
|-----------|--------|-----|
| Задачі + steps | ✅ Є | Беремо, покращуємо |
| Нагадування (smart) | ✅ Є | Беремо повністю |
| Inbox / Quick Capture | ✅ Є | Беремо |
| Ідеї | ✅ Є | Об'єднати з Notes |
| Бібліотека нотаток | ⚠️ Базова | Перебудувати в Knowledge Base |
| Журнал | ⚠️ Базовий | Розширити (templates, prompts) |
| Daily OS / Якість дня | ✅ Є | Беремо, розширюємо |
| Проєкти | ✅ Є | Достатньо для MVP |
| AI Parser | ✅ Є | Беремо + додаємо AI Coach |
| Voice Input | ✅ Концепція | Реалізувати через Android STT |
| Proactive / Engagement | ✅ Частково | Спрощуємо, беремо ідеї |
| Trigger/Urges Tracker | ⚠️ Специфічний | Універсалізувати |
| **Habit Tracker** | ❌ Немає | Створити з нуля |
| **AI Psychologist/Coach** | ❌ Немає | Створити з нуля (ключова фіча) |
| **Knowledge Base** | ❌ Немає | Створити з нуля |
| **Life Areas System** | ❌ Немає | Створити з нуля |
| **Progress Visualization** | ❌ Немає | Створити з нуля |
| **Onboarding Flow** | ❌ Немає | Створити з нуля |

---

## 6. ПРІОРИТЕТИ ДЛЯ MVP

### Must Have (Phase 1):
1. Quick Capture / Inbox
2. Tasks + Steps
3. Smart Reminders
4. Habit Tracker (базовий)
5. Journal (з шаблонами)
6. Daily OS (налаштовувані anchors)
7. AI Coach (рекомендація технік)

### Should Have (Phase 2):
8. Knowledge Base (книги, цитати, методики)
9. Life Areas System
10. Progress Visualization
11. Onboarding Flow
12. Trigger/Urges Tracker

### Nice to Have (Phase 3+):
13. Voice Input
14. Advanced AI Insights
15. Social/Community features
16. Export/Backup system
