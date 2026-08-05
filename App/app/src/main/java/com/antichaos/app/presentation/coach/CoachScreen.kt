package com.antichaos.app.presentation.coach

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun CoachScreen(
    navController: NavHostController
) {
    // AI Coach screen — chat-like interface for emotional support + technique recommendations
    
    /* TODO: Implement
    val viewModel = hiltViewModel<CoachViewModel>()
    
    Scaffold { padding ->
        Column(modifier = Modifier.padding(padding)) {
            // Chat history (user messages + AI responses)
            LazyColumn(reverseLayout = true) {
                items(viewModel.sessions.reversed()) { session ->
                    CoachMessageBubble(
                        message = session,
                        isUser = session.isUser
                    )
                    
                    // Show suggested techniques as tappable cards under AI response
                    if (!session.isUser && session.suggestedTechniques.isNotEmpty()) {
                        TechniquesSuggestionRow(
                            techniques = session.suggestedTechniques,
                            onTechniqueClick = { code -> 
                                navController.navigate("technique_detail/$code") 
                            }
                        )
                    }
                }
            }
            
            // Input bar for user message
            CoachInputBar(
                text = viewModel.inputText,
                onTextChange = { viewModel.updateInput(it) },
                onSend = { viewModel.sendMessage() }
            )
        }
    }
    
    // AI flow:
    // 1. User writes/says something (e.g., "I'm overwhelmed with work")
    // 2. AI detects emotional state + context from recent data
    // 3. AI responds empathetically + suggests relevant techniques from master list
    // 4. User can tap technique to see full instructions
    */
}
