package com.antichaos.app.presentation.tasks

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun TasksScreen(
    navController: NavHostController
) {
    // Tasks screen — full task management with filters
    
    /* TODO: Implement with Hilt ViewModel
    val viewModel = hiltViewModel<TasksViewModel>()
    
    Scaffold(
        floatingActionButton = { AddTaskFAB(onClick = { /* navigate to create */ }) }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            // Filter tabs: All / Active / Today / Overdue / By Life Area
            TaskFilterTabs(selectedFilter = viewModel.selectedFilter)
            
            // Task list with swipe actions (complete, postpone, delete)
            LazyColumn {
                items(viewModel.tasks) { task ->
                    TaskItem(
                        task = task,
                        onStatusChange = { newStatus -> viewModel.updateTaskStatus(task.id, newStatus) },
                        onClick = { navController.navigate("task_detail/${task.id}") }
                    )
                }
            }
        }
    }
    */
}
