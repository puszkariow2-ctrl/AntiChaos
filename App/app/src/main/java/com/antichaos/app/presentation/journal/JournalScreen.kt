package com.antichaos.app.presentation.journal

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun JournalScreen(
    navController: NavHostController
) {
    // Journal screen — entry types + history
    
    /* TODO: Implement
    val viewModel = hiltViewModel<JournalViewModel>()
    
    Scaffold { padding ->
        Column(modifier = Modifier.padding(padding)) {
            // Entry type selector (tabs or cards)
            JournalTypeSelector(
                onFreeWrite = { /* open free write editor */ },
                onMorningPages = { /* open morning pages */ },
                onEveningReview = { /* open evening review template */ },
                onGratitude = { /* open gratitude journal */ }
            )
            
            // Today's entries preview
            SectionHeader("Сьогодні")
            viewModel.todayEntries.forEach { entry ->
                JournalEntryPreview(
                    entry = entry,
                    onClick = { navController.navigate("journal_entry/${entry.id}") }
                )
            }
        }
    }
    */
}
