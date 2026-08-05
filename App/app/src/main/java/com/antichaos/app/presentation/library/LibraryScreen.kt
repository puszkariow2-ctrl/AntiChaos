package com.antichaos.app.presentation.library

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun LibraryScreen(
    navController: NavHostController
) {
    // Knowledge Base screen — books, quotes, methodologies, saved notes
    
    /* TODO: Implement (Phase 2 feature)
    val viewModel = hiltViewModel<LibraryViewModel>()
    
    Scaffold { padding ->
        Column(modifier = Modifier.padding(padding)) {
            // Search bar across all library content
            LibrarySearchBar(
                query = viewModel.searchQuery,
                onQueryChange = { viewModel.updateSearch(it) }
            )
            
            // Content sections (tabs or scrollable)
            TabRow(selectedTabIndex = viewModel.selectedTab) {
                Text("Книги")
                Text("Цитати")
                Text("Методики")
                Text("Нотатки")
            }
            
            when (viewModel.selectedTab) {
                0 -> BookList(books = viewModel.books, onBookClick = { /* navigate */ })
                1 -> QuoteFeed(quotes = viewModel.quotes, onFavorite = { /* toggle */ })
                2 -> MethodologyList(methodologies = viewModel.methodologies)
                3 -> SavedNotesList(notes = viewModel.notes)
            }
        }
    }
    
    // Key design: each piece of content has "Apply to practice" button
    // that creates a related habit or task (knowledge → action link)
    */
}
