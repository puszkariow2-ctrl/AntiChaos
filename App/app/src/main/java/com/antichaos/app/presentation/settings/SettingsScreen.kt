package com.antichaos.app.presentation.settings

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun SettingsScreen(
    navController: NavHostController
) {
    // Settings screen — app configuration
    
    /* TODO: Implement
    val viewModel = hiltViewModel<SettingsViewModel>()
    
    Scaffold { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            // Profile section
            item { SettingsSection("Профіль") {
                SettingsRow("Ім'я", value = viewModel.userName, onClick = { /* edit */ })
            }}
            
            // Daily OS (anchors) configuration
            item { SettingsSection("Розклад дня") {
                viewModel.anchors.forEach { anchor ->
                    SettingsSwitchRow(
                        title = anchor.title,
                        subtitle = formatTime(anchor.timeOfDayMinutes),
                        checked = anchor.isActive,
                        onCheckedChange = { viewModel.toggleAnchor(anchor.id, it) }
                    )
                }
            }}
            
            // AI settings
            item { SettingsSection("AI Коуч") {
                SettingsSwitchRow(
                    title = "Увімкнути AI",
                    checked = viewModel.aiEnabled,
                    onCheckedChange = { viewModel.toggleAi(it) }
                )
                SettingsRow("API ключ OpenAI", value = if (viewModel.hasApiKey) "••••••" else "Не вказано")
            }}
            
            // Notifications
            item { SettingsSection("Сповіщення") {
                SettingsSwitchRow(
                    title = "Нагадування",
                    checked = viewModel.reminderNotificationsEnabled,
                    onCheckedChange = { /* toggle */ }
                )
            }}
            
            // Data management
            item { SettingsSection("Дані") {
                SettingsRow("Експортувати дані", onClick = { /* export to JSON */ })
                SettingsRow("Імпорт даних", onClick = { /* import from JSON */ })
            }}
        }
    }
    */
}
