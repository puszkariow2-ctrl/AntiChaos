package com.antichaos.app.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun FeedScreen(
    viewModel: HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    Scaffold(
        bottomBar = { BottomNavigationBar() }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp)
        ) {
            item { GreetingBlock(viewModel = viewModel) }
            item { FocusTodayBlock(viewModel = viewModel) }
            item { NextAnchorBlock() }
            item { HabitsTodayBlock() }
            item { PracticeSuggestionBlock() }
            item { InsightBlock() }
            item { JournalPromptBlock() }
            item { RandomActionBlock() }
            item { CoachCheckInBlock() }
        }
    }
}

@Composable
fun BottomNavigationBar() {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Штаб") },
            label = { Text("Штаб") },
            selected = true,
            onClick = {}
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.ChatBubble, contentDescription = "Коуч") },
            label = { Text("Коуч") },
            selected = false,
            onClick = { /* TODO: Navigate to Coach Chat */ }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Menu, contentDescription = "Меню") },
            label = { Text("Меню") },
            selected = false,
            onClick = { /* TODO: Show menu drawer/sheet */ }
        )
    }
}

// ─── BLOCK 1: Greeting + Mood ──────────────────────────────────────

@Composable
fun GreetingBlock(viewModel: HomeViewModel) {
    val greeting by viewModel.greeting.collectAsState()
    var selectedMood by remember { mutableIntStateOf(-1) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(greeting, style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Як ти сьогодні?", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                val moods = listOf("😊", "😐", "😔", "😰", "🔥")
                moods.forEachIndexed { index, emoji ->
                    MoodButton(emoji = emoji, isSelected = selectedMood == index) {
                        selectedMood = index
                        viewModel.selectMood(index)
                    }
                }
            }
        }
    }
}

@Composable
fun MoodButton(emoji: String, isSelected: Boolean, onClick: () -> Unit) {
    val backgroundColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
    Card(
        modifier = Modifier.size(48.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        onClick = onClick
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(emoji, style = MaterialTheme.typography.headlineMedium)
        }
    }
}

// ─── BLOCK 2: Focus Today (Tasks) ──────────────────────────────────

@Composable
fun FocusTodayBlock(viewModel: HomeViewModel) {
    val tasks by viewModel.tasks.collectAsState()

    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("🎯 Фокус дня", style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f))
                IconButton(onClick = { /* TODO: Navigate to Tasks */ }) {
                    Icon(Icons.Default.ArrowForward, contentDescription = "Всі задачі")
                }
            }

            if (tasks.isEmpty()) {
                Text("Поки порожньо — додай завдання!", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            } else {
                tasks.take(3).forEach { task ->
                    TaskPreviewRow(task = task)
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }
    }
}

@Composable
fun TaskPreviewRow(task: com.antichaos.app.domain.model.Task) {
    val isDone = task.status == com.antichaos.app.data.local.entity.TaskStatus.DONE
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier.size(8.dp).clip(CircleShape).background(if (isDone) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline),
            contentAlignment = Alignment.Center
        ) {
            if (isDone) Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(12.dp), tint = MaterialTheme.colorScheme.onPrimary)
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(task.title, style = MaterialTheme.typography.bodyMedium, textDecoration = if (isDone) androidx.compose.ui.text.style.TextDecoration.LineThrough else null)
    }
}

// ─── BLOCK 3: Next Anchor ──────────────────────────────────────────

@Composable
fun NextAnchorBlock() {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("⚓ Наступний якір", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text("Налаштуй якірні моменти в меню", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

// ─── BLOCK 4: Habits Today ─────────────────────────────────────────

@Composable
fun HabitsTodayBlock() {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("🔁 Звички сьогодні", style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f))
                IconButton(onClick = { /* TODO: Navigate to Habits */ }) {
                    Icon(Icons.Default.ArrowForward, contentDescription = "Всі звички")
                }
            }
            Text("Поки немає звичок — створи першу!", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

// ─── BLOCK 5: Practice Suggestion (Practicum) ──────────────────────

@Composable
fun PracticeSuggestionBlock() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.3f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("🧘 Практика дня", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text("Box Breathing — 2 хв для заспокоєння", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

// ─── BLOCK 6: Quote/Insight (Library) ──────────────────────────────

@Composable
fun InsightBlock() {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("💡 Цитата дня", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text("\"Почни з малого. Мале зроблене краще за велике незавершене.\"", style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.Center)
        }
    }
}

// ─── BLOCK 7: Journal Prompt ───────────────────────────────────────

@Composable
fun JournalPromptBlock() {
    val prompt by remember { mutableStateOf(getTimeBasedJournalPrompt()) }

    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("📝 Щоденник", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(prompt, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

fun getTimeBasedJournalPrompt(): String {
    val hour = java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY)
    return when (hour) {
        in 5..11 -> "Що в голові зараз? Запиши за 30 секунд."
        in 12..17 -> "Як проходить день? Що важливе зробив?"
        in 18..23 -> "Швидкий огляд: що пішло добре сьогодні?"
        else -> "Не можеш заснути? Випиши думки на папір."
    }
}

// ─── BLOCK 8: Random Action 🎲 (NEW!) ──────────────────────────────

@Composable
fun RandomActionBlock() {
    var currentAction by remember { mutableStateOf(getRandomAction()) }
    var isCompleted by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.3f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("🎲 Рандомна дія", style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f))
                IconButton(onClick = { currentAction = getRandomAction(); isCompleted = false }) {
                    Icon(Icons.Default.Refresh, contentDescription = "Реролл")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(currentAction, style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.height(8.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                if (!isCompleted) {
                    OutlinedButton(onClick = { isCompleted = true }) {
                        Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Зроблено!")
                    }
                } else {
                    Text("✅ Зроблено!", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
}

fun getRandomAction(): String {
    val actions = listOf(
        "Зроби 10 віджимань прямо зараз",
        "Напиши другу, яким ти його цінуєш",
        "Вийди на балкон і подихай 3 рази глибоко",
        "Запиши одну річ, яку ти зробив добре сьогодні",
        "Зроби розтяжку шиї — 20 сек вліво, 20 сек вправо",
        "Пий склянку води прямо зараз",
        "Подякуй собі за те що ти вже зробив цього тижня",
        "Зроби Box Breathing: вдих 4с, затримка 4с, видих 4с — 3 цикли",
        "Встань і пройдись 2 хвилини",
        "Подивись у вікно і знайди 5 зелених речей",
        "Зроби одну маленьку справу, яку відкладав",
        "Посміхнись дзеркалу — серйозно!",
        "Запиши три речі за які ти вдячний сьогодні",
        "Зроби прогресивну релаксацію: напруж і розслаб кулаки 5 разів",
        "Прийми теплий душ або просто умийся холодною водою"
    )
    return actions.random()
}

// ─── BLOCK 9: Coach Check-in ──────────────────────────────────────

@Composable
fun CoachCheckInBlock() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.3f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("🧠 Коуч", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text("Щось турбує? Можу допомогти.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}
