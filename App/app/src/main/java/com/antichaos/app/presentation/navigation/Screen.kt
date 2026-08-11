package com.antichaos.app.presentation.navigation

sealed class Screen(val route: String) {
    object Feed : Screen("feed")
    object Tasks : Screen("tasks")
    object Habits : Screen("habits")
    object Practicum : Screen("practicum")
    object PracticeDetail : Screen("practice_detail/{practiceId}") {
        fun createRoute(practiceId: Long) = "practice_detail/$practiceId"
    }
    object Journal : Screen("journal")
    object CoachChat : Screen("coach_chat")
    object Library : Screen("library")
    object Settings : Screen("settings")
}
