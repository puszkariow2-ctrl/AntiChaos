package com.antichaos.app.presentation.practicum

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.antichaos.app.data.local.entity.PracticeCategory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PracticumScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Практикум") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            // Group by category
            val practicesByCategory = getSamplePractices().groupBy { PracticeCategory.entries.find { c -> c.value == it.category } ?: PracticeCategory.BREATHING }

            practicesByCategory.forEach { (category, practices) ->
                item {
                    Text(category.label, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
                }

                items(practices) { practice ->
                    PracticeCard(
                        title = practice.title,
                        durationSeconds = practice.durationSeconds,
                        difficulty = practice.difficulty,
                        whenToUse = practice.whenToUse
                    )
                }
            }
        }
    }
}

@Composable
fun PracticeCard(title: String, durationSeconds: Long, difficulty: Int, whenToUse: String) {
    val minutes = durationSeconds / 60
    val difficultyLabel = when (difficulty) {
        0 -> "Легка"
        1 -> "Середня"
        else -> "Важка"
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, style = MaterialTheme.typography.titleMedium)

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                Icon(Icons.Default.Timer, contentDescription = null, modifier = Modifier.size(16.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(modifier = Modifier.width(4.dp))
                Text("$minutes хв", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)

                Spacer(modifier = Modifier.weight(1f))

                AssistChip(
                    onClick = { /* no-op */ },
                    label = { Text(difficultyLabel, style = MaterialTheme.typography.labelSmall) }
                )
            }

            // Tags
            if (whenToUse.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                val tags = whenToUse.split(",").map { it.trim() }.take(3)
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    tags.forEach { tag ->
                        Text("#$tag", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
                    }
                }
            }
        }
    }
}

// Sample data — will be replaced with real DB queries later
fun getSamplePractices() = listOf(
    com.antichaos.app.data.local.entity.PracticeEntity(id = 1, title = "Box Breathing", category = 0, durationSeconds = 120, difficulty = 0, instructions = "", whenToUse = "anxiety,stress,focus"),
    com.antichaos.app.data.local.entity.PracticeEntity(id = 2, title = "Дихання 4-7-8", category = 0, durationSeconds = 180, difficulty = 0, instructions = "", whenToUse = "sleep,anxiety,stress"),
    com.antichaos.app.data.local.entity.PracticeEntity(id = 3, title = "Когерентне дихання", category = 0, durationSeconds = 300, difficulty = 1, instructions = "", whenToUse = "stress,focus,balance"),
    com.antichaos.app.data.local.entity.PracticeEntity(id = 4, title = "Заземлення 5-4-3-2-1", category = 1, durationSeconds = 180, difficulty = 0, instructions = "", whenToUse = "anxiety,panic"),
    com.antichaos.app.data.local.entity.PracticeEntity(id = 5, title = "Сканування тіла", category = 1, durationSeconds = 300, difficulty = 0, instructions = "", whenToUse = "stress,sleep"),
    com.antichaos.app.data.local.entity.PracticeEntity(id = 6, title = "Розтяжка для спини (5 хв)", category = 2, durationSeconds = 300, difficulty = 0, instructions = "", whenToUse = "desk-work,tension"),
    com.antichaos.app.data.local.entity.PracticeEntity(id = 7, title = "Медитація уваги (3 хв)", category = 3, durationSeconds = 180, difficulty = 1, instructions = "", whenToUse = "focus,mindfulness"),
    com.antichaos.app.data.local.entity.PracticeEntity(id = 8, title = "Назвай емоцію", category = 4, durationSeconds = 120, difficulty = 0, instructions = "", whenToUse = "self-awareness"),
    com.antichaos.app.data.local.entity.PracticeEntity(id = 9, title = "3 речі за які вдячний", category = 5, durationSeconds = 120, difficulty = 0, instructions = "", whenToUse = "gratitude,mood-boost")
)
