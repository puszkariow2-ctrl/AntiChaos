package com.antichaos.app.presentation.onboarding

import androidx.compose.runtime.Composable
import com.antichaos.app.presentation.navigation.OnboardingViewModel

@Composable
fun OnboardingScreen(
    viewModel: OnboardingViewModel,
    onComplete: () -> Unit
) {
    // First-launch onboarding — 3-5 screens to set up the user
    
    /* TODO: Implement
    val pages = listOf(
        OnboardingPage(
            title = "Вітаєш в AntiChaos",
            description = "Твій особистий штаб для порядку у голові та діях.",
            icon = "🧠"
        ),
        OnboardingPage(
            title = "Хаос — це нормально",
            description = "Ми не змушуємо тебе бути ідеальним. Просто починаймо звідси, де ти зараз.",
            icon = "🌊"
        ),
        OnboardingPage(
            title = "Швидкий захват > досконала система",
            description = "Пиши що в голові — задачі, думки, нагадування. Ми самі все розкладемо.",
            icon = "⚡"
        ),
        OnboardingPage(
            title = "AI Коуч поруч",
            description = "Коли важко — AI допоможе знайти техніку, яка підходить саме тобі зараз.",
            icon = "🤝"
        )
    )
    
    // Name input screen
    if (currentPage == pages.size) {
        NameInputScreen(
            name = viewModel.userName,
            onNameChange = { viewModel.updateName(it) },
            onComplete = onComplete
        )
    } else {
        Pager(pages = pages, currentPage = currentPage, onPageSelected = { /* update */ })
    }
    
    // After onboarding completes:
    // 1. Seed default daily anchors
    // 2. Seed default life areas  
    // 3. Optionally seed starter techniques for AI coach
    // 4. Navigate to Home screen
    */
}
