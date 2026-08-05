package com.antichaos.app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.antichaos.app.presentation.home.HomeScreen
import com.antichaos.app.presentation.tasks.TasksScreen
import com.antichaos.app.presentation.reminders.RemindersScreen
import com.antichaos.app.presentation.habits.HabitsScreen
import com.antichaos.app.presentation.journal.JournalScreen
import com.antichaos.app.presentation.coach.CoachScreen
import com.antichaos.app.presentation.library.LibraryScreen
import com.antichaos.app.presentation.settings.SettingsScreen
import com.antichaos.app.presentation.onboarding.OnboardingScreen

@Composable
fun AntiChaosNavGraph(
    navController: androidx.navigation.NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Onboarding.route,
        modifier = modifier
    ) {
        // Onboarding (first launch only)
        composable(Screen.Onboarding.route) {
            val viewModel = hiltViewModel<OnboardingViewModel>()
            OnboardingScreen(
                viewModel = viewModel,
                onComplete = { navController.navigate(Screen.Home.route) }
            )
        }

        // Main bottom navigation destinations
        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }

        composable(Screen.Tasks.route) {
            TasksScreen(navController = navController)
        }

        composable(Screen.Reminders.route) {
            RemindersScreen(navController = navController)
        }

        composable(Screen.Habits.route) {
            HabitsScreen(navController = navController)
        }

        composable(Screen.Journal.route) {
            JournalScreen(navController = navController)
        }

        // Secondary screens (no bottom nav)
        composable(Screen.Coach.route) {
            CoachScreen(navController = navController)
        }

        composable(Screen.Library.route) {
            LibraryScreen(navController = navController)
        }

        composable(Screen.Settings.route) {
            SettingsScreen(navController = navController)
        }

        // Task detail with ID argument
        composable(
            route = "${Screen.TaskDetail.route}/{taskId}",
            arguments = listOf(navArgument("taskId") { type = NavType.LongType })
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getLong("taskId") ?: 0L
            // TaskDetailScreen(taskId, navController)
        }

        // Habit detail with ID argument
        composable(
            route = "${Screen.HabitDetail.route}/{habitId}",
            arguments = listOf(navArgument("habitId") { type = NavType.LongType })
        ) { backStackEntry ->
            val habitId = backStackEntry.arguments?.getLong("habitId") ?: 0L
            // HabitDetailScreen(habitId, navController)
        }

        // Journal entry detail
        composable(
            route = "${Screen.JournalEntry.route}/{entryId}",
            arguments = listOf(navArgument("entryId") { type = NavType.LongType })
        ) { backStackEntry ->
            val entryId = backStackEntry.arguments?.getLong("entryId") ?: 0L
            // JournalEntryDetailScreen(entryId, navController)
        }

        // Book detail from library
        composable(
            route = "${Screen.BookDetail.route}/{bookId}",
            arguments = listOf(navArgument("bookId") { type = NavType.LongType })
        ) { backStackEntry ->
            val bookId = backStackEntry.arguments?.getLong("bookId") ?: 0L
            // BookDetailScreen(bookId, navController)
        }

        // Technique detail from coach/library
        composable(
            route = "${Screen.TechniqueDetail.route}/{techniqueCode}",
            arguments = listOf(navArgument("techniqueCode") { type = NavType.StringType })
        ) { backStackEntry ->
            val techniqueCode = backStackEntry.arguments?.getString("techniqueCode") ?: ""
            // TechniqueDetailScreen(techniqueCode, navController)
        }
    }
}
