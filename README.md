# AntiChaos — Android App Project

## Візія
Особистий штаб для людей, яким важко систематизувати життя. Планер + трекер звичок + щоденник + AI-психолог + бібліотека саморозвитку в одному додатку.

**Target:** Люди 20-45 років у хаосі думок, які хочуть змінити життя але не знають з чого почати.

## Структура Проєкту

| Папка | Опис |
|-------|------|
| [App/](./App/) | Android проєкт (Kotlin + Jetpack Compose) — ГОТОВИЙ до відкриття в Android Studio |
| [CONCEPT.md](./CONCEPT.md) | Повна концепція продукту (візія, фічі, UX, roadmap) |
| [AUDIT_EXISTING.md](./AUDIT_EXISTING.md) | Аудит існуючого бота: що беремо, що створюємо з нуля |
| [DATA_MODEL.md](./DATA_MODEL.md) | Модель даних для нового додатку (14+ entity) |
| [WORK_PLAN.md](./WORK_PLAN.md) | План роботи на 12 тижнів по фазах |
| [PHASE_0_SUMMARY.md](./PHASE_0_SUMMARY.md) | Підсумок Phase 0: Analysis & Concept |

## Статус Проєкту
🟢 **Phase 0: ANALYSIS & CONCEPT** — ✅ ЗАВЕРШЕНО
- Аудит існуючого бота виконано
- Концепція нового продукту затверджена
- Модель даних визначена
- Android проєкт створено з повною архітектурою

## Як Почати Розробку

1. Відкрити `App/` в Android Studio (Ladybug або новіша)
2. Зачекати Gradle sync (~5-10 хв перший раз)
3. Прочитати `App/README.md` для огляду структури
4. Починати з реалізації ViewModels та UI компонентів

## Наступний Крок
**Phase 1, Week 1:** Реалізувати Tasks модуль:
- Додати ViewModels (TasksViewModel, HomeViewModel)
- Створити реальні Compose UI компоненти для екранів
- Підключити TaskRepository до UI через Flow
