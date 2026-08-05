package com.antichaos.app.presentation.navigation

sealed class Screen(val route: String, val title: String) {
    // Onboarding
    data object Onboarding : Screen("onboarding", "Welcome")

    // Main bottom navigation tabs
    data object Home : Screen("home", "Штаб")
    data object Tasks : Screen("tasks", "Задачі")
    data object Reminders : Screen("reminders", "Нагадування")
    data object Habits : Screen("habits", "Звички")
    data object Journal : Screen("journal", "Журнал")

    // Secondary screens (accessed from Home or settings)
    data object Coach : Screen("coach", "AI Коуч")
    data object Library : Screen("library", "Бібліотека")
    data object Settings : Screen("settings", "Налаштування")

    // Detail screens with arguments
    data object TaskDetail : Screen("task_detail", "Задача")
    data object HabitDetail : Screen("habit_detail", "Звичка")
    data object JournalEntry : Screen("journal_entry", "Запис")
    data object BookDetail : Screen("book_detail", "Книга")
    data object TechniqueDetail : Screen("technique_detail", "Техніка")

    // Dialogs / overlays (handled differently)
    data object QuickCapture : Screen("quick_capture", "Швидкий захват")
    data object CreateTask : Screen("create_task", "Нова задача")
    data object CreateHabit : Screen("create_habit", "Нова звичка")
}
