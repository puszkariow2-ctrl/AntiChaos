package com.antichaos.app.presentation.home

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun HomeScreen(
    navController: NavHostController
) {
    // Home screen — the "Штаб" (HQ) of the app
    // Shows: greeting + mood check, today's focus tasks, next daily anchor, quick capture button
    
    /* TODO: Implement with Hilt ViewModel
    val viewModel = hiltViewModel<HomeViewModel>()
    
    Scaffold(
        floatingActionButton = { QuickCaptureFAB(onClick = { /* open quick capture */ }) }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            // Greeting + mood selector
            GreetingSection(currentTime = viewModel.currentTime)
            
            // Today's focus (top 3-5 tasks)
            TodayFocusSection(tasks = viewModel.todayTasks)
            
            // Next daily anchor
            NextAnchorSection(anchor = viewModel.nextAnchor)
            
            // Quick stats row
            QuickStatsRow(
                habitsCompleted = viewModel.habitsCompletedToday,
                tasksCompleted = viewModel.tasksCompletedToday
            )
        }
    }
    */
}
