package com.antichaos.app.presentation.habits

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun HabitsScreen(
    navController: NavHostController
) {
    // Habits screen — today's habits with one-tap completion
    
    /* TODO: Implement
    val viewModel = hiltViewModel<HabitsViewModel>()
    
    Scaffold(
        floatingActionButton = { AddHabitFAB() }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            // Today's progress header
            HabitProgressHeader(
                completedCount = viewModel.completedToday,
                totalCount = viewModel.totalActiveHabits
            )
            
            // Habits for today (based on frequency + day of week)
            LazyColumn {
                items(viewModel.todayHabits) { habit ->
                    HabitItem(
                        habit = habit,
                        isCompleted = viewModel.isHabitCompletedToday(habit.id),
                        onComplete = { viewModel.completeHabit(habit.id) },
                        onUndo = { viewModel.undoCompletion(habit.id) },
                        onClick = { navController.navigate("habit_detail/${habit.id}") }
                    )
                }
            }
        }
    }
    */
}
