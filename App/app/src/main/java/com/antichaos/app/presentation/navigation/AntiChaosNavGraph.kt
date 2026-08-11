package com.antichaos.app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.antichaos.app.presentation.home.FeedScreen
import com.antichaos.app.presentation.tasks.TasksScreen
import com.antichaos.app.presentation.habits.HabitsScreen
import com.antichaos.app.presentation.practicum.PracticumScreen

@Composable
fun AntiChaosNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Feed.route
    ) {
        composable(Screen.Feed.route) { FeedScreen(navController) }
        composable(Screen.Tasks.route) { TasksScreen(navController) }
        composable(Screen.Habits.route) { HabitsScreen(navController) }
        composable(Screen.Practicum.route) { PracticumScreen(navController) }
        composable(
            route = Screen.PracticeDetail.route,
            arguments = listOf(navArgument("practiceId") { type = NavType.LongType })
        ) { backStackEntry ->
            val practiceId = backStackEntry.arguments?.getLong("practiceId") ?: 0L
            // TODO: PracticeDetailScreen(practiceId)
        }
    }
}
