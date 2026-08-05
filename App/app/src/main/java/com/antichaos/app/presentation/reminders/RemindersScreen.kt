package com.antichaos.app.presentation.reminders

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun RemindersScreen(
    navController: NavHostController
) {
    // Reminders screen — upcoming reminders + create with natural language
    
    /* TODO: Implement
    val viewModel = hiltViewModel<RemindersViewModel>()
    
    Scaffold { padding ->
        Column(modifier = Modifier.padding(padding)) {
            // Natural language input bar at top
            ReminderInputBar(
                onSend = { text -> viewModel.createReminderFromText(text) }
            )
            
            // Upcoming reminders grouped by time
            LazyColumn {
                stickyHeader { DateHeader("Сьогодні") }
                items(viewModel.todayReminders) { reminder -> ReminderItem(reminder) }
                
                stickyHeader { DateHeader("Завтра") }
                items(viewModel.tomorrowReminders) { reminder -> ReminderItem(reminder) }
            }
        }
    }
    */
}
